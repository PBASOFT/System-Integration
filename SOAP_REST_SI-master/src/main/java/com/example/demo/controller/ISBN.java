package com.example.demo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import com.example.demo.exceptions.BookNotFoundException;
import com.example.demo.exceptions.StudentNotFoundException;
import com.example.demo.model.Book;
import com.example.demo.model.Student;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.SOAPISBNService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping("/ISBN")
public class ISBN {

    @Autowired
    BookRepository repository;

    @GetMapping("/")
    public List<Book> verifyISBN() {

        return repository.findAll();

    }

    @GetMapping("/{isbn}")
    public EntityModel<Book> verifyISBN(@PathVariable String isbn) {

        Optional<Book> book = repository.findById(isbn);
        if (book.isEmpty())
            throw new BookNotFoundException("Book by that ISBN not found");

        EntityModel<Book> resource = EntityModel.of(book.get());
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).verifyISBN());
        resource.add(linkTo.withRel("all-books"));

        Link selfLink = linkTo(methodOn(this.getClass()).verifyISBN(isbn)).withSelfRel();
        resource.add(selfLink);

        return resource;

    }

    @PostMapping("/")
    public ResponseEntity<Book> postISBN(@RequestBody Book requestBook) {

        Book savedBook = repository.save(requestBook);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{isbn}").buildAndExpand((savedBook.getisbn())).toUri();

        return ResponseEntity.created(location).build();

    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity deleteISBN(@PathVariable String isbn) {

        repository.deleteById(isbn);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<Book> updateISBN(@PathVariable String isbn, @RequestBody Book putBook) {

        Optional<Book> book = repository.findById(isbn);
        if (book.isEmpty())
            return ResponseEntity.notFound().build();

        putBook.setisbn(isbn);
        repository.save(putBook);


        return ResponseEntity.noContent().build();

    }

}
