package org.library.library.service;

import org.library.library.model.Author;

import java.util.List;

public interface AuthorService {
    List<Author> findAll();
    Author findById(Long id);
}
