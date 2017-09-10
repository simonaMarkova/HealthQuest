package mk.ukim.finki.healthquiz.resources;

import mk.ukim.finki.healthquiz.helper.UserQuestionHelper;
import mk.ukim.finki.healthquiz.models.*;
import mk.ukim.finki.healthquiz.service.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Simona on 7/19/2017.
 */
@RestController
@RequestMapping(value = "/userQuestion", produces = "application/json")
public class UserQuestionResource implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        UserQuestionService bean = applicationContext.getBean(UserQuestionService.class);
        System.out.println(bean);
    }

    private final UserQuestionService userQuestionService;
    private final UserService userService;
    private final QuestionService questionService;
    private final QuestionAnswerService questionAnswerService;
    private final AnswerImageService answerImageService;

    @Autowired
    public UserQuestionResource(UserQuestionService userQuestionService, UserService userService, QuestionService questionService, QuestionAnswerService questionAnswerService, AnswerImageService answerImageService) {
        this.userQuestionService = userQuestionService;
        this.userService = userService;
        this.questionService = questionService;
        this.questionAnswerService = questionAnswerService;
        this.answerImageService = answerImageService;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void insert(@RequestBody UserQuestion userQuestion)   {
        userQuestionService.insert(userQuestion);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void insertIos(@RequestBody UserQuestionHelper userQuestionHelper, HttpServletResponse response)   {
        UserQuestion userQuestion = new UserQuestion();
        User user = userService.findById(userQuestionHelper.getUserId());
        Question question = questionService.findById(userQuestionHelper.getQuestionId());
        if(userQuestionHelper.getAnswerImageId()!= null){
            AnswerImage answerImage = answerImageService.findById(userQuestionHelper.getAnswerImageId());
            userQuestion.setAnswerImage(answerImage);
        }
        if(userQuestionHelper.getQuestionAnswerId()!= null) {
            QuestionAnswer questionAnswer = questionAnswerService.findById(userQuestionHelper.getQuestionAnswerId());
            userQuestion.setQuestionAnswer(questionAnswer);
        }

        userQuestion.setUser(user);
        userQuestion.setQuestion(question);
        userQuestion.setWin(userQuestionHelper.getWin());
        userQuestion.setPoints(userQuestionHelper.getPoints());
        userQuestionService.insert(userQuestion);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Long id, @RequestBody UserQuestion userQuestion) {
        userQuestionService.update(id, userQuestion);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<UserQuestion> getAll() {
        return userQuestionService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserQuestion getById(@PathVariable Long id) {
        return userQuestionService.findById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable Long id) {
        userQuestionService.deleteById(id);
    }


}
