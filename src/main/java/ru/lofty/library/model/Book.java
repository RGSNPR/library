package ru.lofty.library.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * @author Alex Lavrentyev
 */

@Getter
@Setter
@Entity
@Table(name = "books")
public class Book {

    @Id
    @SequenceGenerator(name = "booksIdSeq", sequenceName = "books_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booksIdSeq")
    private Long id;
    private String name;
    private int publicationYear;

    @ManyToOne
    @JoinColumn(name = "author_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Author author;

}
