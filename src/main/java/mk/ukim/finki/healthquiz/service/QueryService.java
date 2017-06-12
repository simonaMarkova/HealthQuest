package mk.ukim.finki.healthquiz.service;

import mk.ukim.finki.healthquiz.enumeration.QuestionType;
import mk.ukim.finki.healthquiz.models.Question;
import org.springframework.data.domain.Page;

/**
 * Created by Simona on 09.06.2017.
 */
public interface QueryService {
    Page<Question> getByPage(QuestionType type, int page, int pageSize);
}
