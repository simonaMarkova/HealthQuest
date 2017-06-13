package mk.ukim.finki.healthquiz.resources;

import mk.ukim.finki.healthquiz.enumeration.QuestionType;
import mk.ukim.finki.healthquiz.models.Question;
import mk.ukim.finki.healthquiz.service.QueryService;
import mk.ukim.finki.healthquiz.service.QuestionService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @Autowired
    public QuestionResource(QuestionService service, QueryService queryService) {
        this.service = service;
        this.queryService = queryService;
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
}