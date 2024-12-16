package top.soft.classoa.service;
import top.soft.classoa.entity.Employee;
import top.soft.classoa.entity.LeaveForm;
import top.soft.classoa.entity.Notice;
import top.soft.classoa.entity.ProcessFlow;
import top.soft.classoa.mapper.EmployeeMapper;
import top.soft.classoa.mapper.LeaveFormMapper;
import top.soft.classoa.mapper.NoticeMapper;
import top.soft.classoa.mapper.ProcessFlowMapper;
import top.soft.classoa.service.exception.LeaveFormException;
import top.soft.classoa.utils.DateUtils;
import top.soft.classoa.utils.MyBatisUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author 86189
 * @description: TODO
 * @date 2024/12/7 20:39
 */
public class LeaveFormService {
    private final EmployeeService employeeService = new EmployeeService();

    public LeaveForm createLeaveForm(LeaveForm leaveForm) {
        return (LeaveForm) MyBatisUtils.executeUpdate(sqlSession -> {
            // 一、新增请假单据
            // 1、根据请假中的员工号，查询员工信息
            EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee employee = employeeMapper.selectById(leaveForm.getEmployeeId());
            // 2、根据请假状态判断请假状态为 processing，审批状态为 approved
            if (employee.getLevel() == 8) {
                leaveForm.setState("approved");
            } else {
                leaveForm.setState("processing");
            }
            // 3、持久化请假单据数据
            LeaveFormMapper leaveFormMapper = sqlSession.getMapper(LeaveFormMapper.class);
            leaveFormMapper.insert(leaveForm);

            // 二、新增审批流程数据
            // 1、新增第一条流程数据：因为请假单已提交，所以此流程数据状态为 complete
            ProcessFlowMapper processFlowMapper = sqlSession.getMapper(ProcessFlowMapper.class);
            ProcessFlow flow1 = ProcessFlow.builder()
                    .formId(leaveForm.getFormId())
                    .operatorId(employee.getEmployeeId())
                    .action("apply")
                    .createTime(new Date())
                    .orderNo(1)
                    .state("complete")
                    .isLast(0)
                    .build();
            processFlowMapper.insert(flow1);

            // 2、分情况生成其余流程数据
            int level = employee.getLevel();
            switch (level) {
                case 1,2,3,4,5,6-> {
                    // 2 - 6级员工，生成部门经理审批任务，系统自动通过
                    Employee leader = employeeService.selectLeader(employee.getEmployeeId());

                    ProcessFlow flow2 = new ProcessFlow();
                    flow2.setFormId(leaveForm.getFormId());
                    flow2.setOperatorId(leader.getEmployeeId());
                    flow2.setAction("audit");
                    flow2.setCreateTime(new Date());
                    flow2.setOrderNo(2);
                    flow2.setState("process");

                    long hours = DateUtils.getDiffHours(leaveForm.getCreateTime(),leaveForm.getEndTime());
                    if(hours >= 72){
                        flow2.setIsLast(0);
                        processFlowMapper.insert(flow2);

                        //找到部门经理leader（总经理）
                        Employee boss = employeeService.selectLeader(employee.getEmployeeId());

                        ProcessFlow flow3 = new ProcessFlow();
                        flow3.setFormId(leaveForm.getFormId());
                        flow3.setOperatorId(boss.getEmployeeId());
                        flow3.setAction("audit");
                        flow3.setCreateTime(new Date());
                        flow3.setState("ready");
                        flow3.setOrderNo(3);
                        flow3.setIsLast(1);
                        processFlowMapper.insert(flow3);
                    } else {
                        flow2.setIsLast(1);
                        processFlowMapper.insert(flow2);
                    }
                }



                case 7 -> {
                    // 7级员工，生成总经理审批任务
                    Employee boss = employeeService.selectLeader(employee.getEmployeeId());
                    ProcessFlow flow2 = new ProcessFlow();
                    flow2.setFormId(leaveForm.getFormId());
                    flow2.setOperatorId(boss.getEmployeeId());
                    flow2.setAction("audit");
                    flow2.setCreateTime(new Date());
                    flow2.setState("process");
                    flow2.setOrderNo(2);
                    flow2.setIsLast(1);
                    processFlowMapper.insert(flow2);

                }

                case 8 ->{
                    // 8级员工，生成总经理审批任务，系统自动通过
                    ProcessFlow flow2 = new ProcessFlow();
                    flow2.setFormId(leaveForm.getFormId());
                    flow2.setOperatorId(employee.getEmployeeId());
                    flow2.setAction("audit");
                    flow2.setResult("approve");
                    flow2.setReason("自动通过");
                    flow2.setCreateTime(new Date());
                    flow2.setAuditTime(new Date());
                    flow2.setState("completed");
                    flow2.setOrderNo(2);
                    flow2.setIsLast(1);
                    processFlowMapper.insert(flow2);

                }
                default -> System.out.println("无此等级员工");

                //break;
            }
            return leaveForm;
        });
    }

    public List<Map<String, Object>> getLeaveFormList(String state, Long operatorId){
        return (List<Map<String, Object>>) MyBatisUtils.executeQuery(sqlSession -> {
            LeaveFormMapper leaveFormMapper = sqlSession.getMapper(LeaveFormMapper.class);
            List<Map<String, Object>> map = leaveFormMapper.selectByParams(state, operatorId);
            return map;
        });
    }

    public void audit(Long formId,Long operatorId, String result,String reason){
        MyBatisUtils.executeUpdate(sqlSession -> {
            ProcessFlowMapper processFlowMapper = sqlSession.getMapper(ProcessFlowMapper.class);
            List<ProcessFlow> flowList = processFlowMapper.selectByFormId(formId);
            if(flowList.size() == 0){
                throw new LeaveFormException("无效的审批流程");
            }

            List<ProcessFlow> processList =flowList.stream().filter(p-> Objects.equals(p.getOperatorId(),operatorId)&&
                    "process".equals(p.getState())).toList();

            ProcessFlow process;
            if(processList.size() == 0){
                throw new LeaveFormException("未找到待处理的1任务节点");
            }else{
                process = processList.get(0);
                process.setState("complete");
                process.setResult(result);
                process.setReason(reason);
                process.setAuditTime(new Date());
                processFlowMapper.update(process);
            }

            LeaveFormMapper leaveFormMapper =
                    sqlSession.getMapper(LeaveFormMapper.class);
            LeaveForm form = leaveFormMapper.selectById(formId);


            Employee employee = employeeService.selectById(form.getEmployeeId());

            Employee operator = employeeService.selectById(operatorId);
            NoticeMapper noticeMapper = sqlSession.getMapper(NoticeMapper.class);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH时");


            if(process.getIsLast() == 1){
                form.setState(result);
                leaveFormMapper.update(form);

                String strResult = null;
                if("approve".equals(result)){
                    strResult = "批准";
                }else if("refused".equals(result)){
                    strResult = "驳回";
                }

                // 发给申请人的通知
                String notice1 = String.format("您的请假申请[%s-%s]%s%s已%s，审批意见:%s，审批流程已结束",
                        sdf.format(form.getStartTime()),
                        sdf.format(form.getEndTime()),
                        operator.getTitle(),
                        operator.getName(),
                        strResult,
                        reason);
                noticeMapper.insert(Notice.builder().receiverId(form.getEmployeeId()).content(notice1).createTime(new Date()).build());

// 发给审批人的通知
                String notice2 = String.format("%s-%s提起请假申请[%s-%s]您已%s，审批意见:%s，审批流程已结束",
                        employee.getTitle(),
                        employee.getName(),
                        sdf.format(form.getStartTime()),
                        sdf.format(form.getEndTime()),
                        strResult, reason);
                noticeMapper.insert(Notice.builder().receiverId(operator.getEmployeeId()).content(notice2).createTime(new Date()).build());



            }else{
                List<ProcessFlow> readyList = flowList.stream().filter(p -> "ready".equals(p.getState())).toList();

                if("approved".equals(result)){
                    ProcessFlow readyProcess = readyList.get(0);
                    readyProcess.setState("process");
                    processFlowMapper.update(readyProcess);

                    // 消息1: 通知表单提交人，部门经理已经审批通过，交由上级继续审批
                    String notice1 = String.format("您的请假申请[%s-%s] %s%s已通过，审批意见:%s，请继续等待上级审批",
                            sdf.format(form.getStartTime()),
                            sdf.format(form.getEndTime()),
                            operator.getTitle(),
                            operator.getName(),
                            reason);
                    noticeMapper.insert(Notice.builder()
                            .receiverId(form.getEmployeeId())
                            .content(notice1)
                            .createTime(new Date())
                            .build());

// 消息2: 通知部门经理（当前经办人），员工的申请你已批准，交由上级继续审批
                    String notice2 = String.format("%s-%s提起请假申请[%s-%s]您已批准，审批意见:%s，申请转至上级领导继续审批",
                            employee.getTitle(),
                            employee.getName(),
                            sdf.format(form.getStartTime()),
                            sdf.format(form.getEndTime()),
                            reason);
                    noticeMapper.insert(Notice.builder()
                            .receiverId(operator.getEmployeeId())
                            .content(notice2)
                            .createTime(new Date())
                            .build());

// 消息3: 通知总经理有新的审批任务
                    String notice3 = String.format("%s-%s提起请假申请[%s-%s]，请尽快审批",
                            employee.getTitle(),
                            employee.getName(),
                            sdf.format(form.getStartTime()),
                            sdf.format(form.getEndTime()));
                    noticeMapper.insert(Notice.builder()
                            .receiverId(readyProcess.getOperatorId())
                            .content(notice3)
                            .createTime(new Date())
                            .build());

                } else if ("refused".equals(result)) {

                    for(ProcessFlow p: readyList){
                        p.setState("cancel");
                        processFlowMapper.update(p);
                    }
                    form.setState("refused");
                    leaveFormMapper.update(form);

                    // 消息1: 通知申请人表单已被驳回
                    String notice1 = String.format("您的请假申请[%s-%s]%s%s已被驳回，审批意见: %s，审批流程已结束",
                            sdf.format(form.getStartTime()),
                            sdf.format(form.getEndTime()),
                            operator.getTitle(),
                            operator.getName(),
                            reason);
                    noticeMapper.insert(Notice.builder()
                            .receiverId(operator.getEmployeeId())
                            .content(notice1)
                            .createTime(new Date())
                            .build());

// 消息2: 通知经办人表单已被驳回
                    String notice2 = String.format("%s-%s 提起请假申请[%s-%s]您已驳回，审批意见: %s，审批流程已结束",
                            employee.getTitle(),
                            employee.getName(),
                            sdf.format(form.getStartTime()),
                            sdf.format(form.getEndTime()),
                            reason);
                    noticeMapper.insert(Notice.builder()
                            .receiverId(operator.getEmployeeId())
                            .content(notice2)
                            .createTime(new Date())
                            .build());
                }
            }
            return null;

        });
    }
}
