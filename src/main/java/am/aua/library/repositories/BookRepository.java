package am.aua.library.repositories;

import am.aua.library.database.DatabaseException;
import am.aua.library.models.Book;

import java.util.ArrayList;
import java.util.List;

public interface BookRepository extends Repository<Book> {
    List<Book> findByTitle(String contains);

    boolean rentById(Long id, Long userId) throws DatabaseException;

    boolean giveBackById(Long id, Long userId) throws DatabaseException;
}
