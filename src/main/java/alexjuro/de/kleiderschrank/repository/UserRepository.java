package alexjuro.de.kleiderschrank.repository;

import alexjuro.de.kleiderschrank.domain.User;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUid(String uid);
}
