package mk.ukim.finki.healthquiz.service.impl;

import mk.ukim.finki.healthquiz.enumeration.QuestionType;
import mk.ukim.finki.healthquiz.models.*;
import mk.ukim.finki.healthquiz.persistance.*;
import mk.ukim.finki.healthquiz.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * Created by Simona on 04.03.2017.
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionAnswerRepository questionAnswerRepository;
    private final QuestionConnectingRepository questionConnectingRepository;
    private final QuestionImageRepository questionImageRepository;
    private final AnswerImageRepository answerImageRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, QuestionAnswerRepository questionAnswerRepository, QuestionConnectingRepository questionConnectingRepository, QuestionImageRepository questionImageRepository, AnswerImageRepository answerImageRepository)
    {
        this.questionRepository = questionRepository;
        this.questionAnswerRepository = questionAnswerRepository;
        this.questionConnectingRepository = questionConnectingRepository;
        this.questionImageRepository = questionImageRepository;
        this.answerImageRepository = answerImageRepository;
    }


    @Override
    public Question insert(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public void update(Long id, Question question) {
        questionRepository.save(question);
    }

    @Override
    public List<Question> findAll() {
        return (List<Question>) questionRepository.findAll();
    }

    @Override
    public Question findById(Long id) {
        return questionRepository.findOne(id);
    }

    @Override
    public void deleteById(Long id) {
        int flag = 0;
        Question question = questionRepository.findOne(id);
        if(question.getQuestionType() == QuestionType.ANSWER_SELECT || question.getQuestionType() == QuestionType.BONUS)
        {
            List<QuestionAnswer> list = questionAnswerRepository.findByQuestionId(id);
            for (QuestionAnswer q: list)
            {
                questionAnswerRepository.delete(q.id);
            }
            flag = 1;
        }
        else if(question.getQuestionType() == QuestionType.CONNECTING_PHRASES)
        {
            List<QuestionConnecting> list = questionConnectingRepository.findByQuestionId(id);
            for (QuestionConnecting q: list)
            {
                questionConnectingRepository.delete(q.id);
            }
            flag = 1;
        }
        else if(question.getQuestionType() == QuestionType.IMAGE_SELECT)
        {
            List<QuestionAnswer> list = questionAnswerRepository.findByQuestionId(id);
            for (QuestionAnswer q: list)
            {
                questionAnswerRepository.delete(q.id);
            }
            QuestionImage questionImage = questionImageRepository.findByQuestionId(id);
            if(questionImage != null)
            {
                if(questionImage.imageUrl != null)
                {
                    File f = new File(questionImage.imageUrl);
                    f.delete();
                }
                questionImageRepository.delete(questionImage.id);
            }
            flag = 1;
        }
        else if(question.getQuestionType() == QuestionType.MULTIPLE_IMAGE_SELECT)
        {
            List<AnswerImage> list = answerImageRepository.findByQuestionId(id);
            for (AnswerImage q: list)
            {
                if(q.imageUrl != null)
                {
                    File f = new File(q.imageUrl);
                    f.delete();
                }
                answerImageRepository.delete(q.id);
            }
            flag = 1;
        }
        else
        {
            flag = 1;
        }


        if(flag == 1)
        {
            questionRepository.delete(id);
        }

    }

    @Override
    public List<Question> findByQuestionType(QuestionType type) {
        return questionRepository.findByQuestionType(type);
    }

    @Override
    public List<Question> findByLevelId(Long id) {
        return questionRepository.findByLevelId(id);
    }

    @Override
    public List<Question> findByDiseaseId(Long id) {
        return questionRepository.findByDiseaseId(id);
    }

    @Override
    public List<Question> getAllBonusQuestions() {
        return questionRepository.findByQuestionType(QuestionType.BONUS);
    }


}
