package alexjuro.de.kleiderschrank.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "clothings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Clothing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 30)
    private String brand;

    private String description;

    @Lob
    private String imageBase64;

    @Column(nullable = false)
    private boolean inLaundry;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Color color;

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne
    @JoinColumn(name = "closet_id")
    private Closet closet;

    @ManyToMany(mappedBy = "clothings")
    private List<Outfit> outfits;
}
