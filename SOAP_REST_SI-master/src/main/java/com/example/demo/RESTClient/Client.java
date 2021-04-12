package com.example.demo.RESTClient;

import com.example.demo.model.Book;
import com.example.demo.model.Student;
import com.example.demo.service.SOAPISBNService;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Client {

    static final String BOOK_BASE_URL = "http://localhost:8080/ISBN/";
    static final String BOOK_ID_URL = "http://localhost:8080/ISBN/{isbn}";
    static RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // RestTemplate is a class, which provides template for making HTTP requests to RESTful services


        // JSON values
        Map<String, String> params = new HashMap<String, String>();


        System.out.println("METHODS SYNTAX:");
        System.out.println("GETALL - Gets all books");
        System.out.println("GET,{ISBN} - Get a specific book by ISBN");
        System.out.println("POST,{ISBN},{TITLE},{AUTHOR} - Creates new book with supplied params");
        System.out.println("PUT,{ISBN},{TITLE},{AUTHOR} - Updates existing book by ISBN with supplied params");
        System.out.println("DELETE,{ISBN} - Deletes a specific book by ISBN");
        while (scanner.hasNext()) {
            String[] vars;
            String method = "GETALL";
            String isbn = "";
            try {
                String input = scanner.next();
                if (input.contains("GETALL")){
                    printAll();
                } else {
                    vars = input.split(",");
                    method = vars[0];
                    isbn = vars[1];
                    // SOAPISBNService has a single static method to check whether a supplied ISBN input is valid
                    // This is where the client application consumes the SOAP service
                    if (SOAPISBNService.isValid(isbn)) {
                        // Depending on the case, the client application then consumes the Spring Boot REST service
                        switch (method) {
                            case "GET":
                                // Simple HTTP GET commands
                                ResponseEntity<Book> string = restTemplate.getForEntity(BOOK_ID_URL, Book.class, isbn);
                                printOne(string);
                                System.out.println(string.getHeaders().toString());

                                break;
                            case "POST":
                                String title = vars[2];
                                String author = vars[3];
                                // Simple HTTP POST command. Creates a predefined book
                                Book newBook = new Book(isbn, title, author);

                                ResponseEntity<String> entity = restTemplate.postForEntity(BOOK_BASE_URL, newBook, String.class);
                                System.out.println(entity);
                                printAll();
                                break;
                            case "PUT":
                                // Put
                                String putTitle = vars[2];
                                String putAuthor = vars[3];
                                params.put("isbn", isbn);
                                Book updatedBook = new Book(isbn, putTitle, putAuthor);
                                restTemplate.put(BOOK_ID_URL, updatedBook, params);

                                printAll();
                                break;

                            case "DELETE":

                                // Delete
                                params.put("isbn", isbn);
                                restTemplate.delete(BOOK_ID_URL, params);
                                printAll();
                                break;
                        }
                    }
                }



            } catch (Exception e) {
                System.out.println(Arrays.toString(e.getStackTrace()));
            }


        }
    }

    public static void printAll() {
        Object[] results = restTemplate.getForObject(BOOK_BASE_URL, Object[].class);
        System.out.println(Arrays.toString(results));
    }

    public static void printOne(ResponseEntity<Book> s) {
        System.out.println("Book title: " + s.getBody().getisbn());
        System.out.println("Book title: " + s.getBody().getAuthor());
        System.out.println("Book title: " + s.getBody().getTitle());
    }
}
