package mk.ukim.finki.healthquiz.persistance;

import mk.ukim.finki.healthquiz.models.QuestionImage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by user on 20.4.2017.
 */
@Repository
public interface QuestionImageRepository extends CrudRepository<QuestionImage, Long> {
    QuestionImage findByQuestionId(Long id);
}
