package mk.ukim.finki.healthquiz.persistance;

import mk.ukim.finki.healthquiz.models.Level;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Simona on 19.04.2017.
 */
@Repository
public interface LevelRepository extends CrudRepository<Level,Long>{
}
