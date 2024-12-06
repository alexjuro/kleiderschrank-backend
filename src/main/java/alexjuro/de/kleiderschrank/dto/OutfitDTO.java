package alexjuro.de.kleiderschrank.dto;

import alexjuro.de.kleiderschrank.domain.Closet;
import alexjuro.de.kleiderschrank.domain.Clothing;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class OutfitDTO {
    private Integer id;
    private ClosetDTO closetDTO;
    private List<ClothingDTO> clothingsDTOs;
}
