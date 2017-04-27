package mk.ukim.finki.healthquiz.service.impl;

import mk.ukim.finki.healthquiz.models.AnswerImage;
import mk.ukim.finki.healthquiz.persistance.AnswerImageRepository;
import mk.ukim.finki.healthquiz.service.AnswerImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * Created by user on 20.4.2017.
 */
@Service
public class AnswerImageServiceImpl implements AnswerImageService {

    private static final String URL_PATTERN="C:\\Users\\user\\Documents\\healthQuestPictures\\multipleImageSelect\\";
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

    @Override
    public List<AnswerImage> findByQuestionId(Long id) {
        return answerImageRepository.findByQuestionId(id);
    }
}
