package dto;

import com.library.libraryspringboot.entity.Author;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {
    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Name must only contain alphabetic characters")
    private String name;
    @NotBlank(message = "Last Name is required")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Lastname must only contain alphabetic characters")
    private String lname;
    @NotBlank(message = "Last Name is required")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Surname must only contain alphabetic characters")//@TODO: fix the BUG -> more than one name is not accepted
    private String sname;
    private Integer born;

    public AuthorDTO(Author author) {
        this.name = author.getName();
        this.lname = author.getLname();
        this.sname = author.getSname();
        this.born = author.getBorn();
    }

    public static AuthorDTO fromEntityToDTO(Author author) {
        return new AuthorDTO(author);
    }

    public static Author fromDTOToEntity(AuthorDTO authorDTO) {
        return new Author(authorDTO.getName(), authorDTO.lname, authorDTO.sname, authorDTO.getBorn());
    }
}