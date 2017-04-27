package mk.ukim.finki.healthquiz.service;

import mk.ukim.finki.healthquiz.models.Answer;

import java.util.List;

/**
 * Created by Simona on 06.03.2017.
 */
public interface AnswerService {
    List<Answer> findAll();
    Answer findById(Long id);
    void insert(Answer entity);
    void update(Long id, Answer entity);
    void deleteById(Long id);
}
