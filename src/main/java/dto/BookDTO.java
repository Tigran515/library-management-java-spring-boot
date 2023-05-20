package dto;

import com.library.libraryspringboot.entity.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    @Null(message = "The ID field must be null in the POST request")
    private String id;
    @NotBlank(message = "title is required")
    private String title;
    @NotBlank(message = "year is required")
    private Integer published;
    @NotBlank(message = "ISBN is required")
    private String ISBN;  // change to char?

    public BookDTO(Book book) {
        this.title = book.getTitle();
        this.published = book.getPublished();
        this.ISBN = book.getISBN();
    }

    @Override
    public String toString() {
        return String.format("[title=%s,published=%s,ISBN=%s]", title, published, ISBN);
    }
}
