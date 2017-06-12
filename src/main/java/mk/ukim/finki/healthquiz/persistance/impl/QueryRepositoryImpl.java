package mk.ukim.finki.healthquiz.persistance.impl;

import mk.ukim.finki.healthquiz.enumeration.QuestionType;
import mk.ukim.finki.healthquiz.models.Question;
import mk.ukim.finki.healthquiz.persistance.QueryRepository;
import mk.ukim.finki.healthquiz.persistance.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

/**
 * Created by Simona on 09.06.2017.
 */
@Repository
public class QueryRepositoryImpl implements QueryRepository {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Page<Question> getByPage(QuestionType type, int page, int pageSize) {
        return questionRepository.findAll(
                (question, cq, cb) -> cb.equal(question.get("questionType"), type), new PageRequest(page,pageSize));
    }
}
