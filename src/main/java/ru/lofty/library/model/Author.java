package ru.lofty.library.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Alex Lavrentyev
 */

@Getter
@Setter
@Entity
@Table(name = "authors")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Author implements Serializable {

    @Id
    @SequenceGenerator(name = "authorsIdSeq", sequenceName = "authors_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authorsIdSeq")
    private Long id;

    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "author")
    private List<Book> booksWritten;
}
