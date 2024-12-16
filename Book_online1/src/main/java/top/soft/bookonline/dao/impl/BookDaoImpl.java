package top.soft.bookonline.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import top.soft.bookonline.dao.BookDao;
import top.soft.bookonline.entity.Book;
import top.soft.bookonline.util.JdbcUtil;


import java.util.List;

/**
 * @description: TODO
 * @author admin
 * @date 2024/10/26 13:46
 */

public class BookDaoImpl implements BookDao {
    private final JdbcTemplate template = new JdbcTemplate(JdbcUtil.getDataSource());

    @Override
    public Book getBookById(int id) {
        String sql = "select * from t_book where id=?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(Book.class), id);
    }

    @Override
    public List<Book> selectAll() {
        String sql = "select * from t_book order by id desc";
        return template.query(sql, new BeanPropertyRowMapper<>(Book.class));
    }

}