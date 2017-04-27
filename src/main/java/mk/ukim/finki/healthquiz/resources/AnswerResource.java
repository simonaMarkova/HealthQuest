package mk.ukim.finki.healthquiz.resources;

import mk.ukim.finki.healthquiz.models.Answer;
import mk.ukim.finki.healthquiz.service.AnswerService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Simona on 08.03.2017.
 */
@RestController
@RequestMapping(value = "/answer", produces = "application/json")
public class AnswerResource implements ApplicationContextAware {

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AnswerService bean = applicationContext.getBean(AnswerService.class);
        System.out.println(bean);
    }

    private AnswerService service;

    @Autowired
    public AnswerResource(AnswerService service) {
        this.service = service;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void insert(@Valid @RequestBody Answer answer) {
        service.insert(answer);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Long id, @RequestBody Answer answer) {
        service.update(id, answer);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Answer> getAll() {
        return service.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Answer getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }
}
