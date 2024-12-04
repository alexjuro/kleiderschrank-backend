package alexjuro.de.kleiderschrank.dto;

import lombok.Builder;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class UserDTO {
    private Integer id;
    private String uid;
    private String name;
    private String email;
    private Date createdAt;
    private ClosetDTO closet;
//    private List<OutfitDTO> outfits;
}
