package mk.ukim.finki.healthquiz.service.impl;

import mk.ukim.finki.healthquiz.models.QuestionAnswer;
import mk.ukim.finki.healthquiz.persistance.QuestionAnswerRepository;
import mk.ukim.finki.healthquiz.service.QuestionAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Simona on 15.03.2017.
 */
@Service
public class QuestionAnswerServiceImpl implements QuestionAnswerService {



    public final QuestionAnswerRepository questionAnswerRepository;

    @Autowired
    public QuestionAnswerServiceImpl(QuestionAnswerRepository questionAnswerRepository)
    {
        this.questionAnswerRepository = questionAnswerRepository;
    }

    @Override
    public List<QuestionAnswer> findAll() {
        return (List<QuestionAnswer>) questionAnswerRepository.findAll();
    }

    @Override
    public QuestionAnswer findById(Long id) {
        return questionAnswerRepository.findOne(id);
    }

    @Override
    public void insert(QuestionAnswer entity) {
        questionAnswerRepository.save(entity);
    }

    @Override
    public void update(Long id, QuestionAnswer entity) {
        questionAnswerRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        questionAnswerRepository.delete(id);
    }

    @Override
    public List<QuestionAnswer> findByQuestionId(Long id) {
        return questionAnswerRepository.findByQuestionId(id);
    }
}
