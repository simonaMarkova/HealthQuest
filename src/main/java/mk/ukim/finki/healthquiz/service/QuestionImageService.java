package mk.ukim.finki.healthquiz.service;

import mk.ukim.finki.healthquiz.models.QuestionImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by user on 20.4.2017.
 */
public interface QuestionImageService {
    List<QuestionImage> findAll();
    QuestionImage findById(Long id);
    void insert(QuestionImage entity);
    void update(Long id, QuestionImage entity);
    void deleteById(Long id);
    String savePicture(MultipartFile file);
    QuestionImage findByQuestionId(Long id);
}
