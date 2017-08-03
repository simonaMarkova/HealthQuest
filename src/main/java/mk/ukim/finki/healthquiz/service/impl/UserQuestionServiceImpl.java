package mk.ukim.finki.healthquiz.service.impl;

import mk.ukim.finki.healthquiz.models.UserQuestion;
import mk.ukim.finki.healthquiz.persistance.UserQuestionRepository;
import mk.ukim.finki.healthquiz.service.UserQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Simona on 7/19/2017.
 */
@Service
public class UserQuestionServiceImpl implements UserQuestionService {

    private final UserQuestionRepository userQuestionRepository;

    @Autowired
    public UserQuestionServiceImpl(UserQuestionRepository userQuestionRepository) {
        this.userQuestionRepository = userQuestionRepository;
    }

    @Override
    public UserQuestion insert(UserQuestion userQuestion) {
         return userQuestionRepository.save(userQuestion);
    }

    @Override
    public void update(Long id, UserQuestion userQuestion) {
        userQuestionRepository.save(userQuestion);
    }

    @Override
    public List<UserQuestion> findAll() {
        return (List<UserQuestion>) userQuestionRepository.findAll();
    }

    @Override
    public UserQuestion findById(Long id) {
        return userQuestionRepository.findOne(id);
    }

    @Override
    public void deleteById(Long id) {
        userQuestionRepository.delete(id);
    }

    @Override
    public List<UserQuestion> findByQuestionId(Long id) {
        return userQuestionRepository.findByQuestionId(id);
    }

    @Override
    public List<UserQuestion> findByUserId(Long id){
        return userQuestionRepository.findByUserId(id);
    }
}
