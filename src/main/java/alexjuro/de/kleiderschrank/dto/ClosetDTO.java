package alexjuro.de.kleiderschrank.dto;

import alexjuro.de.kleiderschrank.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClosetDTO {
    private Integer id;
    private User user;
    private List<ClothingDTO> clothings;
}
