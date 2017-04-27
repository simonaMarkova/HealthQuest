package mk.ukim.finki.healthquiz.service;

import mk.ukim.finki.healthquiz.models.Disease;

import java.util.List;

/**
 * Created by Simona on 15.03.2017.
 */
public interface DiseaseService {
    List<Disease> findAll();
    Disease findById(Long id);
    void insert(Disease entity);
    void update(Long id, Disease entity);
    void deleteById(Long id);
}
