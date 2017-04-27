package mk.ukim.finki.healthquiz.service;

import mk.ukim.finki.healthquiz.models.Level;

import java.util.List;

/**
 * Created by Simona on 19.04.2017.
 */
public interface LevelService {
    List<Level> findAll();
    Level findById(Long id);
    void insert(Level entity);
    void update(Long id, Level entity);
    void deleteById(Long id);
}
