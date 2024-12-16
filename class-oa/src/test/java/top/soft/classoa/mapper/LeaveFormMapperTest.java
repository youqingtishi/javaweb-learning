package top.soft.classoa.mapper;

import org.junit.jupiter.api.Test;
import top.soft.classoa.entity.LeaveForm;
import top.soft.classoa.utils.MyBatisUtils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class LeaveFormMapperTest {

    @Test
    void insert() {
        MyBatisUtils.executeUpdate(sqlSession -> {
            LeaveFormMapper leaveFormMapper = sqlSession.getMapper(LeaveFormMapper.class);
            LeaveForm form = new LeaveForm();
            // 员工编号3：研发部 王美美
            form.setEmployeeId(3L);
            // 事假
            form.setFormType(1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = null;
            Date endTime = null;

            try {
                startTime = sdf.parse("2024-12-03 10:00:00");
                endTime = sdf.parse("2024-12-12 10:00:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // 请假截止时间
            form.setStartTime(startTime);
            form.setEndTime(endTime);
            // 请假事由
            form.setReason("个人私事");
            // 请假单创建时间
            form.setCreateTime(new Date());
            // 请假单当前状态
            form.setState("processing");
            leaveFormMapper.insert(form);
            return null;
        });
    }
}