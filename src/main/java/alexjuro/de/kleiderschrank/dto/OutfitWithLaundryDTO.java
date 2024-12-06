package alexjuro.de.kleiderschrank.dto;

import alexjuro.de.kleiderschrank.domain.Closet;
import alexjuro.de.kleiderschrank.domain.Clothing;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OutfitWithLaundryDTO {
    private Integer id;
    private Closet closet;
    private List<Clothing> clothings;
    private Boolean inLaundry;
}
