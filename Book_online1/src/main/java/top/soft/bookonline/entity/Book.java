package top.soft.bookonline.entity;

/**
 * @author 刘文博
 * @description: TODO
 * @date 2024/11/6 13:26
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: TODO
 * @author admin
 * @date 2024/10/19 16:28
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
    private Integer id;
    private String name;
    private String cover;
    private String author;
    //价格
    private String cost;
    private String details;

}
