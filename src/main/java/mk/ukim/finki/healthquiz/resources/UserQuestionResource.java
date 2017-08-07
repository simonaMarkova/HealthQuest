package mk.ukim.finki.healthquiz.resources;

import mk.ukim.finki.healthquiz.models.UserQuestion;
import mk.ukim.finki.healthquiz.service.UserQuestionService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @Autowired
    public UserQuestionResource(UserQuestionService userQuestionService) {
        this.userQuestionService = userQuestionService;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void insert(@Valid @RequestBody UserQuestion userQuestion)   {
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
