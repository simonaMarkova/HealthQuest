package mk.ukim.finki.healthquiz.service.impl;

import mk.ukim.finki.healthquiz.models.AnswerImage;
import mk.ukim.finki.healthquiz.persistance.AnswerImageRepository;
import mk.ukim.finki.healthquiz.service.AnswerImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * Created by user on 20.4.2017.
 */
@Service
public class AnswerImageServiceImpl implements AnswerImageService {


    @Value("file_upload_resource")
    private String fileUploadResource;

    //private static final String URL_PATTERN="C:\\Users\\user\\Documents\\healthQuestPictures\\multipleImageSelect\\";
    public final AnswerImageRepository answerImageRepository;

    @Autowired
    public AnswerImageServiceImpl(AnswerImageRepository answerImageRepository) {
        this.answerImageRepository = answerImageRepository;
    }

    @Override
    public List<AnswerImage> findAll() {
        return (List<AnswerImage>) answerImageRepository.findAll();
    }

    @Override
    public AnswerImage findById(Long id) {
        return answerImageRepository.findOne(id);
    }

    @Override
    public void insert(AnswerImage entity) {
        answerImageRepository.save(entity);
    }

    @Override
    public void update(Long id, AnswerImage entity) {
        answerImageRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        AnswerImage answerImage = answerImageRepository.findOne(id);
        if(answerImage.imageUrl != null)
        {
            File f = new File(answerImage.imageUrl);
            f.delete();
        }
        answerImageRepository.delete(id);
    }

    @Override
    public String savePicture(MultipartFile file) {
        String destLocation;
        if(file.isEmpty())
        {
            return null;
        }

        try{
            if (!new File(fileUploadResource). exists()) {
                new File(fileUploadResource).mkdir( );
            }

            String orgName = file.getOriginalFilename();
            String ext = orgName.split("\\.")[1];

            String newNamePart1 = UUID.randomUUID().toString();
            String newNamePart2 = UUID.randomUUID().toString();
            String newName = String.format("%s-%s.%s", newNamePart1, newNamePart2, ext);
            destLocation = String.format(fileUploadResource+newName);

            File dest = new File(destLocation);
            file.transferTo(dest);

        }
        catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

        return destLocation;
    }

    @Override
    public List<AnswerImage> findByQuestionId(Long id) {
        return answerImageRepository.findByQuestionId(id);
    }
}
