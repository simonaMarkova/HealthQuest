package mk.ukim.finki.healthquiz.service.impl;

import mk.ukim.finki.healthquiz.models.QuestionConnecting;
import mk.ukim.finki.healthquiz.persistance.QuestionConnectingRepository;
import mk.ukim.finki.healthquiz.service.QuestionConnectingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Simona on 29.03.2017.
 */
@Service
public class QuestionConnectiongServiceImpl implements QuestionConnectingService {

    public final QuestionConnectingRepository questionConnectingRepository;

    @Autowired
    public QuestionConnectiongServiceImpl(QuestionConnectingRepository questionConnectingRepository)
    {
        this.questionConnectingRepository = questionConnectingRepository;
    }

    @Override
    public List<QuestionConnecting> findAll() {
        return (List<QuestionConnecting>) questionConnectingRepository.findAll();
    }

    @Override
    public QuestionConnecting findById(Long id) {
        return questionConnectingRepository.findOne(id);
    }

    @Override
    public void insert(QuestionConnecting entity) {
        questionConnectingRepository.save(entity);
    }

    @Override
    public void update(Long id, QuestionConnecting entity) {
        questionConnectingRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        questionConnectingRepository.delete(id);
    }

    @Override
    public List<QuestionConnecting> findByQuestionId(Long id) {
        return questionConnectingRepository.findByQuestionId(id);
    }
}
