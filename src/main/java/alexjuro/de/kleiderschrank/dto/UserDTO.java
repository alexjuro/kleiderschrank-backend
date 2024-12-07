package alexjuro.de.kleiderschrank.dto;

import lombok.Builder;
import lombok.Data;
import java.util.Date;

@Data
@Builder
public class UserDTO {
    private String uid;
    private String name;
    private String email;
    private Date createdAt;
    private ClosetDTO closet;
}
