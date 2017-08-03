package mk.ukim.finki.healthquiz.service;

import mk.ukim.finki.healthquiz.models.UserQuestion;

import java.util.List;

/**
 * Created by Simona on 7/19/2017.
 */
public interface UserQuestionService {

    UserQuestion insert(UserQuestion userQuestion);

    void update(Long id, UserQuestion userQuestion);

    List<UserQuestion> findAll();

    UserQuestion findById(Long id);

    void deleteById(Long id);

    List<UserQuestion> findByQuestionId(Long id);

    List<UserQuestion> findByUserId(Long id);
}
