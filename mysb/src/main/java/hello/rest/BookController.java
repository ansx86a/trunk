package hello.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collection;

@RestController
@RequestMapping("/api/book")
/**
 * <pre>
 *     https://www.baeldung.com/spring-rest-openapi-documentation
 *     有空再來補完，目前看到flex-doc
 *
 *
 * 文件網址設定在application.properties
 * swagger 測試網扯=http://localhost:8080/swagger-ui-custom.html
 * 一定要用XxxMapping不能用RequestMapping
 * </pre>
 */
public class BookController {

    @GetMapping("/{id}")
    public String findById(@PathVariable long id) {
        return "book id = " + id;
    }

    @GetMapping("/")
    public Collection<String> findBooks() {
        return Arrays.asList("book1", "book2", "book3");
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String updateBook(@PathVariable("id") final String id, final String book) {
        return book + " update ok";
    }
}