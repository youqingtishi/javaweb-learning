package top.soft.classoa.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 刘文博
 * @description: TODO
 * @date 2024/11/30 14:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private long userId;
    private String username;
    private String password;
    private Long employeeId;
    private Integer salt;
}
