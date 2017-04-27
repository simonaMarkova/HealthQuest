package mk.ukim.finki.healthquiz.service;
import mk.ukim.finki.healthquiz.models.QuestionAnswer;

import java.util.List;

/**
 * Created by Simona on 15.03.2017.
 */
public interface QuestionAnswerService {
    List<QuestionAnswer> findAll();
    QuestionAnswer findById(Long id);
    void insert(QuestionAnswer entity);
    void update(Long id, QuestionAnswer entity);
    void deleteById(Long id);
    List<QuestionAnswer> findByQuestionId(Long id);
}
