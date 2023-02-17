package com.distribuida.rest;

import com.distribuida.clientes.authors.AuthorRestProxy;
import com.distribuida.clientes.authors.AuthorsCliente;
import com.distribuida.db.Book;
import com.distribuida.dtos.BookDto;
import com.distribuida.servicios.BookRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookRest {

    @Inject BookRepository bookService;

    @RestClient
    @Inject AuthorRestProxy proxyAuthor;


    /**
     * GET          /books/{id}     buscar un libro por ID
     * GET          /books          listar todos
     * POST         /books          insertar
     * PUT/PATCH    /books/{id}     actualizar
     * DELETE       /books/{id}     eliminar
     */

    @Operation(summary = "Lista los libros por su id")
    @GET
    @Path("/{id}")
    public Book findById(@PathParam("id") Integer id) {
        return bookService.findById(id);
    }
//    public Book findById(@PathParam("id") Integer id) {
//        return bookService.findById(id);
//    }

    @GET
    @Operation(summary = "Lista todos los libros")
    public List<Book> findAll() {
        return bookService.findAll();
    }


    /*@GET
    @Path("/all")
    public List<BookDto> findAllCompleto() throws Exception {
        var books = bookService.findAll();

        List<BookDto> ret = books.stream()
                .map(s -> {
                    System.out.println("*********buscando " + s.getId() );

                    AuthorsCliente author = proxyAuthor.findById(s.getId().longValue());
                    return new BookDto(
                            s.getId(),
                            s.getIsbn(),
                            s.getTitle(),
                            s.getAuthor(),
                            s.getPrice(),
                            String.format("%s, %s", author.getLastName(), author.getFirtName())
                    );
                })
                .collect(Collectors.toList());

        return ret;
    }*/

    @POST
    @Transactional()
    @Operation(summary = "Inserta nuevos libros")
    public void insert(Book book) {
        bookService.insert(book);
    }

    @PUT
    @Transactional
    @Path("/{id}")
    @Operation(summary = "Actualiza un libro por su id")
    public void update(Book book, @PathParam("id") Integer id) {
        book.setId(id);
        bookService.update(book);
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    @Operation(summary = "Elimina un libro por su id")
    public void delete( @PathParam("id") Integer id ) {
        bookService.delete(id);
    }
}
