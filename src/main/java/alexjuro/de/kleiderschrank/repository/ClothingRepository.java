package alexjuro.de.kleiderschrank.repository;

import alexjuro.de.kleiderschrank.domain.Clothing;
import org.springframework.data.repository.CrudRepository;

public interface ClothingRepository extends CrudRepository<Clothing, Integer> {
}
