package ru.lofty.library.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Alex Lavrentyev
 */

@Entity
@Table(name = "books")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Book implements Serializable {

    @Id
    @SequenceGenerator(name = "booksIdSeq", sequenceName = "books_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booksIdSeq")
    private Long id;
    private String name;
    private int publicationYear;

    // Так автора добавить никогда не получится, т.к. стоит read-only, это первое
    // логичнее, что у книги могут быть несколько авторов
    @ManyToOne
    @JoinColumn(name = "author_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Author author;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public Author getAuthor() {
        return author;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
