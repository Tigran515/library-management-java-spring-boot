package dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookAuthorDTO { //@TODO: is validation needed here ?
    private BookDTO bookDTO;
    private AuthorDTO authorDTO;
}
