package mk.ukim.finki.healthquiz.resources;

import mk.ukim.finki.healthquiz.enumeration.QuestionType;
import mk.ukim.finki.healthquiz.helper.BonusHelper;
import mk.ukim.finki.healthquiz.models.Level;
import mk.ukim.finki.healthquiz.models.Question;
import mk.ukim.finki.healthquiz.models.QuestionAnswer;
import mk.ukim.finki.healthquiz.models.UserQuestion;
import mk.ukim.finki.healthquiz.service.QueryService;
import mk.ukim.finki.healthquiz.service.QuestionAnswerService;
import mk.ukim.finki.healthquiz.service.QuestionService;
import mk.ukim.finki.healthquiz.service.UserQuestionService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import javax.management.Query;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Simona on 04.03.2017.
 */
@RestController
@RequestMapping(value = "/question", produces = "application/json")
public class QuestionResource implements ApplicationContextAware {

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        QuestionService bean = applicationContext.getBean(QuestionService.class);
        System.out.println(bean);
    }

    private QuestionService service;
    private QueryService queryService;
    private UserQuestionService userQuestionService;
    private QuestionAnswerService questionAnswerService;

    @Autowired
    public QuestionResource(QuestionService service, QueryService queryService, UserQuestionService userQuestionService, QuestionAnswerService questionAnswerService) {
        this.service = service;
        this.queryService = queryService;
        this.userQuestionService = userQuestionService;
        this.questionAnswerService = questionAnswerService;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Question insert(@Valid @RequestBody Question question)   {
        return service.insert(question);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Long id, @RequestBody Question question) {
        service.update(id, question);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Question> getAll() {
        return service.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Question getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @RequestMapping(value = "/type/{type}", method = RequestMethod.GET)
    public List<Question> getByType(@PathVariable QuestionType type){
        List<Question> list = service.findByQuestionType(type);
        return list;
    }

    @RequestMapping(value = "/page/{type}/{page}", method = RequestMethod.GET)
    public Page<Question> getByPage(@PathVariable QuestionType type, @PathVariable int page) {
        return queryService.getByPage(type, page, 20);
    }

    @RequestMapping(value = "/random/{levelId}/{userId}", method = RequestMethod.GET)
    public Question getRandomQuestion(@PathVariable Long levelId, @PathVariable Long userId) {
        List<Question> questionList = service.findByLevelId(levelId);
        List<UserQuestion> userList = userQuestionService.findByUserId(userId);
        boolean flag = true;
        int randomId = 0;
        Random r = new Random();

        if(questionList.size()>0) {
            while (flag) {

                randomId = r.nextInt(questionList.size());

                if (userList.size() == 0) {
                    flag = false;
                } else {
                    for (UserQuestion userQuestion : userList) {
                        if (userQuestion.getQuestion().id == questionList.get(randomId).id) {
                            if (!userQuestion.isWin()) {
                                flag = false;
                                userQuestionService.deleteById(userQuestion.id);
                            } else {
                                break;
                            }
                        } else if (userList.indexOf(userQuestion) == userList.size() - 1 && flag && userQuestion.getQuestion().id == questionList.get(randomId).id) {
                            if (!userQuestion.isWin()) {
                                flag = false;
                                userQuestionService.deleteById(userQuestion.id);
                            }
                        } else if (userList.indexOf(userQuestion) == userList.size() - 1 && flag && userQuestion.getQuestion().id != questionList.get(randomId).id) {
                            flag = false;
                        }
                    }
                }

            }
            return questionList.get(randomId);
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/bonus", method = RequestMethod.GET)
    public List<BonusHelper> getBonusQuestions(){

        List<Question> bonus = service.getAllBonusQuestions();
        Random r = new Random();
        List<Question> result = new ArrayList<>();

        for(int i = 0; i<3; i++){
            int random =  r.nextInt(bonus.size());
            while(result.contains(bonus.get(random))){
                random =  r.nextInt(bonus.size());
            }
            result.add(bonus.get(random));
        }

        List<BonusHelper> resultContent = new ArrayList<>();
        for(Question q : result){
            List<QuestionAnswer> answers = questionAnswerService.findByQuestionId(q.id);
            BonusHelper bonusHelper = new BonusHelper();
            bonusHelper.setId(q.id);
            bonusHelper.setQuestion(q.getQuestion());
            bonusHelper.setAnswerList(answers);
            resultContent.add(bonusHelper);
        }

        return resultContent;
    }

}