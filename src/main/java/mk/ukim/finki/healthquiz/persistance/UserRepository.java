package mk.ukim.finki.healthquiz.persistance;

import mk.ukim.finki.healthquiz.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Simona on 12.04.2017.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
}
