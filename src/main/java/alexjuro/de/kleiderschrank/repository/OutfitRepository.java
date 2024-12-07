package alexjuro.de.kleiderschrank.repository;

import alexjuro.de.kleiderschrank.domain.Outfit;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OutfitRepository extends CrudRepository<Outfit, Integer> {
    List<Outfit> findByClosetId(Integer closetId);
    Optional<Outfit> findByClosetIdAndId(Integer closetId, Integer id);
}
