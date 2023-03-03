package dto;

import com.library.libraryspringboot.entity.Author;
import com.library.libraryspringboot.entity.Book;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
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

    public static BookDTO fromEntityToDTO(Book book) {
        return new BookDTO(book);
    }

    public static Book fromDTOToEntity(BookDTO bookDTO) {
        return new Book(bookDTO.getTitle(), bookDTO.getPublished(), bookDTO.getISBN());
    }
}
