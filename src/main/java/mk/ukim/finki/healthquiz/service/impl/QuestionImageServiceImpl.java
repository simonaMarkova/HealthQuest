package mk.ukim.finki.healthquiz.service.impl;

import mk.ukim.finki.healthquiz.models.QuestionImage;
import mk.ukim.finki.healthquiz.persistance.QuestionImageRepository;
import mk.ukim.finki.healthquiz.service.QuestionImageService;
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
public class QuestionImageServiceImpl implements QuestionImageService{


    private final String fileUploadResource = "C:/Users/Simona/Documents/healthQuestPictures/";

   // private final String fileUploadResource = "/var/lib/tomcat7/webapps/healthquiz/";
    public final QuestionImageRepository questionImageRepository;

    @Autowired
    public QuestionImageServiceImpl(QuestionImageRepository  questionImageRepository)
    {
        this.questionImageRepository=  questionImageRepository;
    }

    @Override
    public List<QuestionImage> findAll() {
        return (List<QuestionImage>) questionImageRepository.findAll();
    }

    @Override
    public QuestionImage findById(Long id) {
        return questionImageRepository.findOne(id);
    }

    @Override
    public void insert(QuestionImage entity) {
        questionImageRepository.save(entity);
    }

    @Override
    public void update(Long id, QuestionImage entity) {
        questionImageRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        QuestionImage questionImage = questionImageRepository.findOne(id);
        if(questionImage.imageUrl != null)
        {
            File f = new File(questionImage.imageUrl);
            f.delete();
        }
        questionImageRepository.delete(id);
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
                new File(fileUploadResource).mkdirs( );
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
    public QuestionImage findByQuestionId(Long id) {
        return questionImageRepository.findByQuestionId(id);
    }
}
