package mk.ukim.finki.healthquiz.service.impl;

import mk.ukim.finki.healthquiz.models.QuestionImage;
import mk.ukim.finki.healthquiz.persistance.QuestionImageRepository;
import mk.ukim.finki.healthquiz.service.QuestionImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * Created by user on 20.4.2017.
 */
@Service
public class QuestionImageServiceImpl implements QuestionImageService{

    public final QuestionImageRepository questionImageRepository;
    private static final String URL_PATTERN="C:\\Users\\user\\Documents\\healthQuestPictures\\imageSelect\\";

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
 //           String uploadsDir = "/uploads/";
//            String realPathtoUploads = String.format("%s//%s//", dataFolder, username);
//            if (!new File(realPathtoUploads). exists()) {
//                new File(realPathtoUploads).mkdir( );
//            }

            String orgName = file.getOriginalFilename();

            String filePath = orgName;
            destLocation = String.format(URL_PATTERN+filePath);

            File dest = new File(destLocation);
            file.transferTo(dest);

        }
        catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

        return destLocation;
    }
}
