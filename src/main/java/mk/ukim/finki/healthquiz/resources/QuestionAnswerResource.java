package mk.ukim.finki.healthquiz.resources;


import mk.ukim.finki.healthquiz.models.QuestionAnswer;
import mk.ukim.finki.healthquiz.service.QuestionAnswerService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Simona on 15.03.2017.
 */
@RestController
@RequestMapping(value = "/questionAnswer", produces = "application/json")
public class QuestionAnswerResource implements ApplicationContextAware {

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        QuestionAnswerService bean = applicationContext.getBean(QuestionAnswerService.class);
        System.out.println(bean);
    }

    private QuestionAnswerService service;

    @Autowired
    public QuestionAnswerResource(QuestionAnswerService service) {
        this.service = service;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void insert(@Valid @RequestBody QuestionAnswer questionAnswer) {
        service.insert(questionAnswer);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Long id, @RequestBody QuestionAnswer questionAnswer) {
        service.update(id, questionAnswer);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<QuestionAnswer> getAll() {
        return service.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public QuestionAnswer getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @RequestMapping(value = "/getByQuestion/{id}", method = RequestMethod.GET)
    public List<QuestionAnswer> getByQuestionId(@PathVariable Long id) {
        return service.findByQuestionId(id);
    }

}
