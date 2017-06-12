package mk.ukim.finki.healthquiz.persistance;

import mk.ukim.finki.healthquiz.enumeration.QuestionType;
import mk.ukim.finki.healthquiz.models.Question;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Simona on 04.03.2017.
 */
@Repository
public interface QuestionRepository extends CrudRepository<Question, Long>,  JpaSpecificationExecutor<Question> {
    List<Question> findByQuestionType(QuestionType type);
}
