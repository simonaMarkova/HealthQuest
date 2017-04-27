package mk.ukim.finki.healthquiz.service.impl;

import mk.ukim.finki.healthquiz.models.User;
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

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public void insert(User entity) {
        userRepository.save(entity);
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

        //TODO: change with real points of the gamer
        user.setPoints(10);

        return user;
    }
}
