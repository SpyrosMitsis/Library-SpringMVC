package org.library.library.service.impl;

import org.library.library.dto.AuthorDto;
import org.library.library.model.Author;
import org.library.library.repository.AuthorRepository;
import org.library.library.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author findById(Long id) {
        return authorRepository.findById(id).orElse(null);
    }

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public List<AuthorDto> findByBookIsbn(String isbn) {
        List<Author> authors = authorRepository.findByBooksIsbn(isbn);
        return authors.stream().map(author -> AuthorDto.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .build()
        ).toList();
    }
}
