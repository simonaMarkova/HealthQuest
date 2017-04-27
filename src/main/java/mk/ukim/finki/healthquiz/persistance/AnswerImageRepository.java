package mk.ukim.finki.healthquiz.persistance;

import mk.ukim.finki.healthquiz.models.AnswerImage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by user on 20.4.2017.
 */
@Repository
public interface AnswerImageRepository extends CrudRepository<AnswerImage, Long>
{
    List<AnswerImage> findByQuestionId(Long id);
}
