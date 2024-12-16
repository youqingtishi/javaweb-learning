package top.soft.classoa.service;

import org.junit.jupiter.api.Test;
import top.soft.classoa.entity.LeaveForm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class LeaveFormServiceTest {
    LeaveFormService leaveFormService = new LeaveFormService();

    /**
     * 市场部员工请假单(72小时以上)测试用例
     * @throws ParseException
     */
    @Test
    void createLeaveForm1() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        LeaveForm form = new LeaveForm();
        form.setEmployeeId(8L);
        form.setStartTime(sdf.parse("202412069"));
        form.setEndTime(sdf.parse("202412149"));
        form.setFormType(1);
        form.setReason("市场部员工请假单(72小时以内)");
        form.setCreateTime(new Date());

        LeaveForm savedForm = leaveFormService.createLeaveForm(form);
        System.out.println(savedForm);
    }
}