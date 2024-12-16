package top.soft.bookonline.service.impl;

/**
 * @author 刘文博
 * @description: TODO
 * @date 2024/11/6 13:35
 */

import top.soft.bookonline.dao.BookDao;
import top.soft.bookonline.dao.impl.BookDaoImpl;
import top.soft.bookonline.entity.Book;
import top.soft.bookonline.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {
    private final BookDao bookDao=new BookDaoImpl();

    @Override
    public List<Book> getBooks() {
        return bookDao.selectAll();
    }

    @Override
    public Book getBookById(int id) {
        return bookDao.getBookById(id);
    }
}