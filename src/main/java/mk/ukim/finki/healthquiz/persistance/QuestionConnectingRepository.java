package mk.ukim.finki.healthquiz.persistance;

import mk.ukim.finki.healthquiz.models.QuestionConnecting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Simona on 29.03.2017.
 */
@Repository
public interface QuestionConnectingRepository extends CrudRepository<QuestionConnecting,Long> {
    List<QuestionConnecting> findByQuestionId(Long id);
}
