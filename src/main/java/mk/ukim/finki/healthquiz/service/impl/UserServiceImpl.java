package mk.ukim.finki.healthquiz.service.impl;

import mk.ukim.finki.healthquiz.models.User;
import mk.ukim.finki.healthquiz.persistance.UserQuestionRepository;
import mk.ukim.finki.healthquiz.persistance.UserRepository;
import mk.ukim.finki.healthquiz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Simona on 12.04.2017.
 */
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserQuestionRepository userQuestionRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserQuestionRepository userQuestionRepository) {
        this.userRepository = userRepository;
        this.userQuestionRepository = userQuestionRepository;
    }


    @Override
    public List<User> findAll() {
       List<User> users = (List<User>) userRepository.findAll();
        for (User user: users) {
            if (userQuestionRepository.findByUserId(user.id).size() == 0)
            {
                user.setPoints(0);
            }
            else {
                user.setPoints(userQuestionRepository.getPoints(user.id));
            }
        }
       return users;

    }

    @Override
    public User findById(Long id) {
        User user = userRepository.findOne(id);
        if (userQuestionRepository.findByUserId(user.id).size() == 0)
        {
            user.setPoints(0);
        }
        else {
            user.setPoints(userQuestionRepository.getPoints(user.id));
        }

        return user;
    }

    @Override
    public User save(User entity) {
         return userRepository.save(entity);
    }

    @Override
    public void update(Long id, User entity) {
        userRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.delete(id);
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if(user != null){
            if (userQuestionRepository.findByUserId(user.id).size() == 0) {
                user.setPoints(0);
            }
            else {
                user.setPoints(userQuestionRepository.getPoints(user.id));
            }
        }
        return user;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
