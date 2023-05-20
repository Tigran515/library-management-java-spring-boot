package dto;

import com.library.libraryspringboot.entity.Author;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class AuthorDTO  { // added Serializable for bytecode conv.
    @Null(message = "The ID field must be null in the POST request")
    private String id;

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

    public AuthorDTO(){

    }
    public AuthorDTO(Author author) {
        this.name = author.getName();
        this.lname = author.getLname();
        this.sname = author.getSname();
        this.born = author.getBorn();
    }

    @Override
    public String toString() {
        return String.format("[name=%s,lname=%s,sname=%s,born=%s]", name, lname, sname, born);
    }
}
