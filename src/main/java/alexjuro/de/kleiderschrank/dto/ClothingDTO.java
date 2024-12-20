package alexjuro.de.kleiderschrank.dto;

import alexjuro.de.kleiderschrank.domain.*;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class ClothingDTO {
    private Integer id;
    private String brand;
    private String description;
    private String imageBase64;
    private boolean inLaundry;
    private Category category;
    private Color color;
    private Type type;
    private ClosetDTO closetDTO;
    private List<OutfitDTO> outfitDTOs;
}
