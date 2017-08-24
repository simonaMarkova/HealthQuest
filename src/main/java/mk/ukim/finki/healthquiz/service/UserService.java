package mk.ukim.finki.healthquiz.service;

import mk.ukim.finki.healthquiz.models.User;

import java.util.List;

/**
 * Created by Simona on 12.04.2017.
 */
public interface UserService {
    List<User> findAll();
    User findById(Long id);
    User save(User entity);
    void update(Long id, User entity);
    void deleteById(Long id);
    User findByUsername(String username);
    User findByEmail(String email);
}
