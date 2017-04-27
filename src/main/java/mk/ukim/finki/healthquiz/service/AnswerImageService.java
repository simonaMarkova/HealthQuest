package mk.ukim.finki.healthquiz.service;

import mk.ukim.finki.healthquiz.models.AnswerImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by user on 20.4.2017.
 */
public interface AnswerImageService {
    List<AnswerImage> findAll();
    AnswerImage findById(Long id);
    void insert(AnswerImage entity);
    void update(Long id, AnswerImage entity);
    void deleteById(Long id);
    String savePicture(MultipartFile file);

    List<AnswerImage> findByQuestionId(Long id);
}
