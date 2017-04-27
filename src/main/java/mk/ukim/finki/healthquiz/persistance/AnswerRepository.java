package mk.ukim.finki.healthquiz.persistance;

import mk.ukim.finki.healthquiz.models.Answer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Simona on 06.03.2017.
 */
@Repository
public interface AnswerRepository extends CrudRepository<Answer,Long>{

}
