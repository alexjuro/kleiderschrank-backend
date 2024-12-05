package alexjuro.de.kleiderschrank.repository;

import alexjuro.de.kleiderschrank.domain.Clothing;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClothingRepository extends CrudRepository<Clothing, Integer> {
    List<Clothing> findByClosetId(Integer closetId);
}
