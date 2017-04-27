package mk.ukim.finki.healthquiz.persistance;

import mk.ukim.finki.healthquiz.models.UserQuestion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by user on 19.4.2017.
 */
@Repository
public interface UserQuestionRepository extends CrudRepository<UserQuestion,Long> {
}
