package alexjuro.de.kleiderschrank.dto;

import lombok.Data;

import java.util.List;

@Data
public class OutfitAddDTO {
    private Integer closetId;
    private List<Integer> clothingIdList;
}
