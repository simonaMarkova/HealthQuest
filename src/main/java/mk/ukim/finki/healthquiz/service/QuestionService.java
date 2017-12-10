package mk.ukim.finki.healthquiz.service;

import mk.ukim.finki.healthquiz.enumeration.QuestionType;
import mk.ukim.finki.healthquiz.models.Question;

import java.util.List;

/**
 * Created by Simona on 04.03.2017.
 */
public interface QuestionService {

    Question insert(Question question);

    void update(Long id, Question question);

    List<Question> findAll();

    Question findById(Long id);

    void deleteById(Long id);

    List<Question> findByQuestionType(QuestionType type);

    List<Question> findByLevelId(Long id);

    List<Question> findByDiseaseId(Long id);

    List<Question> getAllBonusQuestions();
}