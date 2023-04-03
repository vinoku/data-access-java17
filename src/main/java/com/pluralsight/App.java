package com.pluralsight;

import com.pluralsight.model.Book;
import com.pluralsight.repository.BookDao;
import com.pluralsight.repository.Dao;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Dao<Book> bookDao = new BookDao();
        List<Book> books = bookDao.findAll();

        System.out.println("============ findAll ==============");
        for (Book book : books) {
            System.out.println("Id: " + book.getId());
            System.out.println("Title: " + book.getTitle());
            System.out.println("==========================");
        }
        System.out.println("============ END: findAll ==============");

        Optional<Book> optionalBook = bookDao.findById(1);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();

            System.out.println("=========== findById ===============");
            System.out.println("Id: " + book.getId());
            System.out.println("Title: " + book.getTitle());
            System.out.println("=========== END: findById ===============");

            System.out.println("=========== update ===============");
            book.setTitle("Effective Java: Second Edition");
            bookDao.update(book);

            System.out.println("Id: " + book.getId());
            System.out.println("Title: " + book.getTitle());
            System.out.println("=========== END: create ===============");
        }

        System.out.println("=========== create ===============");
        Book newBook = new Book();
        newBook.setTitle("The River Why " + Math.random());
        newBook = bookDao.create(newBook);

        System.out.println("Id: " + newBook.getId());
        System.out.println("Title: " + newBook.getTitle());
        System.out.println("=========== END: create ===============");


        System.out.println("=========== batch ===============");
        books = bookDao.findAll();

        List<Book> updatedEntries = books.stream()
                .peek(b -> b.setRating(ThreadLocalRandom
                        .current()
                        .nextInt(1, 6))) // Random number between 1 and 5 included 5
                .collect(Collectors.toList());
        bookDao.update(books);

        for (Book book : books) {
            System.out.println("Id: " + book.getId());
            System.out.println("Title: " + book.getTitle());
            System.out.println("Rating: " + book.getRating());
            System.out.println("==========================");
        }
        System.out.println("=========== END: batch ===============");
    }
}
