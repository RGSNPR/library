package ru.lofty.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.lofty.library.model.Book;
import ru.lofty.library.repository.BookRepository;

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

    public Page<Book> readAll(int pageNumber, String sortField, String sortDir) {

        int pageSize = 3;

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending()
        );

        return bookRepository.findAll(pageable);
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
