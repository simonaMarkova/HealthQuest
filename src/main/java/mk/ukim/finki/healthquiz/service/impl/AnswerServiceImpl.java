package mk.ukim.finki.healthquiz.service.impl;

import mk.ukim.finki.healthquiz.models.Answer;
import mk.ukim.finki.healthquiz.persistance.AnswerRepository;
import mk.ukim.finki.healthquiz.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Simona on 06.03.2017.
 */
@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerServiceImpl(AnswerRepository answerRepository){
        this.answerRepository = answerRepository;
    }

    @Override
    public List<Answer> findAll() {
        return (List<Answer>) answerRepository.findAll();
    }

    @Override
    public Answer findById(Long id) {
        return answerRepository.findOne(id);
    }

    @Override
    public void insert(Answer entity) {
        answerRepository.save(entity);
    }

    @Override
    public void update(Long id, Answer entity) {
        answerRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        answerRepository.delete(id);
    }
}
