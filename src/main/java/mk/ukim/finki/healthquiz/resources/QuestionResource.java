package mk.ukim.finki.healthquiz.resources;

import mk.ukim.finki.healthquiz.enumeration.QuestionType;
import mk.ukim.finki.healthquiz.models.Level;
import mk.ukim.finki.healthquiz.models.Question;
import mk.ukim.finki.healthquiz.models.UserQuestion;
import mk.ukim.finki.healthquiz.service.QueryService;
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

import javax.validation.Valid;
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

    @Autowired
    public QuestionResource(QuestionService service, QueryService queryService, UserQuestionService userQuestionService) {
        this.service = service;
        this.queryService = queryService;
        this.userQuestionService = userQuestionService;
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

        while(flag){

            randomId = r.nextInt(questionList.size());

            if(userList.size()==0){
                flag=false;
            }else{
                for(UserQuestion userQuestion : userList){
                    if(userQuestion.getQuestion().id == questionList.get(randomId).id){
                        if(!userQuestion.isWin()){
                            flag=false;
                            userQuestionService.deleteById(userQuestion.id);
                        }else {
                            break;
                        }
                    }else if(userList.indexOf(userQuestion)==userList.size()-1 && flag && userQuestion.getQuestion().id == questionList.get(randomId).id){
                        if(!userQuestion.isWin()){
                            flag=false;
                            userQuestionService.deleteById(userQuestion.id);
                        }
                    }else if(userList.indexOf(userQuestion)==userList.size()-1&& flag && userQuestion.getQuestion().id != questionList.get(randomId).id){
                        flag=false;
                    }
                }
            }

        }
        return questionList.get(randomId);
    }

    @RequestMapping(value = "/disease/{diseaseId}/{userId}", method = RequestMethod.GET)
    public Question getRandomByDisease(@PathVariable Long diseaseId, @PathVariable Long userId){
        List<Question> questionList = service.findByDiseaseId(diseaseId);
        List<UserQuestion> userList = userQuestionService.findByUserId(userId);
        int randomId = 0;
        boolean flag = true;
        Random r = new Random();

        while(flag){

            randomId = r.nextInt(questionList.size());

            if(userList.size()==0){
                flag=false;
            }else{
                for(UserQuestion userQuestion : userList){
                    if(userQuestion.getQuestion().id == questionList.get(randomId).id){
                        if(!userQuestion.isWin()){
                            flag=false;
                            userQuestionService.deleteById(userQuestion.id);
                        }else {
                            break;
                        }
                    }else if(userList.indexOf(userQuestion)==userList.size()-1 && flag && userQuestion.getQuestion().id == questionList.get(randomId).id){
                        if(!userQuestion.isWin()){
                            flag=false;
                            userQuestionService.deleteById(userQuestion.id);
                        }
                    }else if(userList.indexOf(userQuestion)==userList.size()-1&& flag && userQuestion.getQuestion().id != questionList.get(randomId).id){
                        flag=false;
                    }
                }
            }

        }

        return questionList.get(randomId);
    }

}