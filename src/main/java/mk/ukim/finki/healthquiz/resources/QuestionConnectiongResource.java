package mk.ukim.finki.healthquiz.resources;

import mk.ukim.finki.healthquiz.models.QuestionConnecting;
import mk.ukim.finki.healthquiz.service.QuestionConnectingService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Simona on 29.03.2017.
 */
@RestController
@RequestMapping(value = "/questionConnecting", produces = "application/json")
public class QuestionConnectiongResource implements ApplicationContextAware {

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        QuestionConnectingService bean = applicationContext.getBean(QuestionConnectingService.class);
        System.out.println(bean);
    }

    private QuestionConnectingService service;

    @Autowired
    public QuestionConnectiongResource(QuestionConnectingService service) {
        this.service = service;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void insert(@Valid @RequestBody QuestionConnecting question) {
        service.insert(question);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Long id, @RequestBody QuestionConnecting question) {
        service.update(id, question);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<QuestionConnecting> getAll() {
        return service.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public QuestionConnecting getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @RequestMapping(value = "/getByQuestion/{id}", method = RequestMethod.GET)
    public List<QuestionConnecting> getByQuestionId(@PathVariable Long id) {
        return service.findByQuestionId(id);
    }
}
