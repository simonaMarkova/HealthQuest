package mk.ukim.finki.healthquiz.persistance;

import mk.ukim.finki.healthquiz.enumeration.QuestionType;
import mk.ukim.finki.healthquiz.models.Question;
import org.springframework.data.domain.Page;

/**
 * Created by Simona on 09.06.2017.
 */
public interface QueryRepository {
    Page<Question> getByPage(QuestionType type, int page, int pageSize);
}
