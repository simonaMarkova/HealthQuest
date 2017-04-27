package mk.ukim.finki.healthquiz.persistance;

import mk.ukim.finki.healthquiz.models.QuestionAnswer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Simona on 15.03.2017.
 */
@Repository
public interface QuestionAnswerRepository extends CrudRepository<QuestionAnswer,Long>{
    List<QuestionAnswer> findByQuestionId(Long id);
}
