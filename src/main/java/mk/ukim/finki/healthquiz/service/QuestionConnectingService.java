package mk.ukim.finki.healthquiz.service;

import mk.ukim.finki.healthquiz.models.QuestionConnecting;

import java.util.List;

/**
 * Created by Simona on 29.03.2017.
 */
public interface QuestionConnectingService {
    List<QuestionConnecting> findAll();
    QuestionConnecting findById(Long id);
    void insert(QuestionConnecting entity);
    void update(Long id, QuestionConnecting entity);
    void deleteById(Long id);
}
