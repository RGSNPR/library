package ru.lofty.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lofty.library.model.Book;
import ru.lofty.library.repository.BookRepository;

import java.util.List;

/**
 * @author Alex Lavrentyev
 */
@Service
public class BookDao {

    BookRepository bookRepository;

    @Autowired
    public BookDao(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public boolean create(Book book) {
        bookRepository.save(book);

        return true;
    }

    public List<Book> readAll() {
        return bookRepository.findAll();
    }

    public Book read(Long id) {
        return bookRepository.getById(id);
    }

    public boolean update(Book book, Long id) {
        if (bookRepository.existsById(id)) {
            book.setId(id);
            bookRepository.save(book);
            return true;
        }

        return false;
    }

    public boolean delete(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
