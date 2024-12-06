package alexjuro.de.kleiderschrank.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "outfits")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Outfit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "closet_id")
    private Closet closet;

    @ManyToMany
    @JoinTable(
            name = "outfit_clothing",
            joinColumns = @JoinColumn(name = "outfit_id"),
            inverseJoinColumns = @JoinColumn(name = "clothing_id"))
    private List<Clothing> clothings;
}
