package alexjuro.de.kleiderschrank.dto;

import alexjuro.de.kleiderschrank.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class ClosetDTO {
    private Integer id;
    private UserDTO userDTO;
    private List<ClothingDTO> clothings;
}
