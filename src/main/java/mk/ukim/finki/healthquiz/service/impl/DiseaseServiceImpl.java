package mk.ukim.finki.healthquiz.service.impl;

import mk.ukim.finki.healthquiz.models.Disease;
import mk.ukim.finki.healthquiz.persistance.DiseaseRepository;
import mk.ukim.finki.healthquiz.service.DiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Simona on 15.03.2017.
 */
@Service
public class DiseaseServiceImpl implements DiseaseService {

    private final DiseaseRepository diseaseRepository;

    @Autowired
    public DiseaseServiceImpl(DiseaseRepository diseaseRepository)
    {
        this.diseaseRepository = diseaseRepository;
    }

    @Override
    public List<Disease> findAll() {
        return (List<Disease>) diseaseRepository.findAll();
    }

    @Override
    public Disease findById(Long id) {
        return diseaseRepository.findOne(id);
    }

    @Override
    public void insert(Disease entity) {
        diseaseRepository.save(entity);
    }

    @Override
    public void update(Long id, Disease entity) {
        diseaseRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        diseaseRepository.delete(id);
    }
}
