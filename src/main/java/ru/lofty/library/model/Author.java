package ru.lofty.library.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * @author Alex Lavrentyev
 */

@Getter
@Setter
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @SequenceGenerator(name = "authorsIdSeq", sequenceName = "authors_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authorsIdSeq")
    private Long id;

    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "author")
    private List<Book> booksWritten;
}
