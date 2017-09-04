package mk.ukim.finki.healthquiz.service.impl;

import mk.ukim.finki.healthquiz.models.Level;
import mk.ukim.finki.healthquiz.persistance.LevelRepository;
import mk.ukim.finki.healthquiz.service.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Simona on 19.04.2017.
 */
@Service
public class LevelServiceImpl implements LevelService {

    private final LevelRepository levelRepository;

    @Autowired
    public LevelServiceImpl(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    @Override
    public List<Level> findAll() {
        return (List<Level>) levelRepository.findAll();
    }

    @Override
    public Level findById(Long id) {
        return levelRepository.findOne(id);
    }

    @Override
    public void insert(Level entity) {
        levelRepository.save(entity);
    }

    @Override
    public void update(Long id, Level entity) {
        levelRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        levelRepository.delete(id);
    }

    @Override
    public Level findByLevel(int level) {
        return levelRepository.findByLevel(level);
    }
}
