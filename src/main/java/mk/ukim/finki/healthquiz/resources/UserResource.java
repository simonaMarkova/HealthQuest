package mk.ukim.finki.healthquiz.resources;

import mk.ukim.finki.healthquiz.models.Level;
import mk.ukim.finki.healthquiz.models.User;
import mk.ukim.finki.healthquiz.persistance.UserQuestionRepository;
import mk.ukim.finki.healthquiz.service.LevelService;
import mk.ukim.finki.healthquiz.service.UserService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Simona on 7/18/2017.
 */
@RestController
@RequestMapping(value = "/user", produces = "application/json")
public class UserResource implements ApplicationContextAware{

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        UserService bean = applicationContext.getBean(UserService.class);
        System.out.println(bean);
    }

    private UserService userService;
    private LevelService levelService;
    private final UserQuestionRepository userQuestionRepository;

    @Autowired
    public UserResource(UserService userService, LevelService levelService, UserQuestionRepository userQuestionRepository){
        this.userService = userService;
        this.levelService = levelService;
        this.userQuestionRepository = userQuestionRepository;
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@Valid @RequestBody User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        userService.insert(user);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Long id, @RequestBody User user) {
        userService.update(id, user);
    }

    @RequestMapping(value = "/find-all", method = RequestMethod.GET)
    public List<User> getAll()
    {
        return userService.findAll();
    }

    @RequestMapping(value = "/find/{id}", method = RequestMethod.GET)
    public User getById(@PathVariable Long id) {
        User user = userService.findById(id);
        return user;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @RequestMapping(value="/updateLevel/{userId}/{levelId}", method = RequestMethod.POST)
    public User updateLevelForUser(@PathVariable Long userId, @PathVariable Long levelId)
    {
        User user = userService.findById(userId);
        Level level = levelService.findById(levelId);
        int nextLevel = level.getLevel() + 1;
        for (Level l: levelService.findAll()) {
            if (nextLevel == l.getLevel())
            {
                user.setLevel(l);
            }
        }
        userService.insert(user);
        return user;
    }


}
