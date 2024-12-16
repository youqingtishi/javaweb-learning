package top.soft.classoa.mapper;

import top.soft.classoa.entity.ProcessFlow;

import java.util.List;

public interface ProcessFlowMapper {
    void insert(ProcessFlow processFlow);

    /**
     * 根据请假单编号查询其对应的所有流程记录
     *
     * @param formId 请假单编号
     * @return 对应的所有流程记录
     */
    List<ProcessFlow> selectByFormId(Long formId);


    /**
     * 更新流程记录
     *
     * @param processFlow 流程记录
     */
    void update(ProcessFlow processFlow);
}

