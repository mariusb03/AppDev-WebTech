package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/books")
public class BookController {

    private final List<Book> books = new ArrayList<>();

    public BookController() {
        initializeData();
    }

    private void initializeData() {
        books.add(new Book(1, "Clean Code", 2008, 464));
        books.add(new Book(2, "The Pragmatic Programmer", 1999, 352));
        books.add(new Book(3, "Design Patterns", 1994, 395));
    }

    @GetMapping
    public List<Book> getBooks() {
        return books;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());  // Ensures a consistent return type
    }

    @PostMapping
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            return ResponseEntity.badRequest().body("Error, could not add the book");
        }
        books.add(book);
        return ResponseEntity.status(201).body("Book added");
    }
}