package alexjuro.de.kleiderschrank.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "closets")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Closet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(mappedBy = "closet")
    private User user;

//    @OneToMany(mappedBy = "closet", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Clothing> clothings;
}
