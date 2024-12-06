package alexjuro.de.kleiderschrank.dto;

import lombok.Data;

import java.util.List;

@Data
public class OutfitPatchDTO {
    private Integer id;
    private List<Integer> clothingIdList;
}
