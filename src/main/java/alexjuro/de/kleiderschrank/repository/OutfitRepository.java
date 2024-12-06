package alexjuro.de.kleiderschrank.repository;

import alexjuro.de.kleiderschrank.domain.Outfit;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OutfitRepository extends CrudRepository<Outfit, Integer> {
    List<Outfit> findByClosetId(Integer closetId);
}
