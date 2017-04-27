package mk.ukim.finki.healthquiz.resources;

import mk.ukim.finki.healthquiz.models.Question;
import mk.ukim.finki.healthquiz.models.QuestionImage;
import mk.ukim.finki.healthquiz.service.QuestionImageService;
import mk.ukim.finki.healthquiz.service.QuestionService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;

/**
 * Created by user on 20.4.2017.
 */
@RestController
@RequestMapping(value = "/questionImage", produces = "application/json")
public class QuestionImageResource implements ApplicationContextAware {

    public final QuestionImageService service;
    private final QuestionService questionService;

    @Autowired
    public QuestionImageResource(QuestionImageService questionImageService, QuestionService questionService) {
        this.service = questionImageService;
        this.questionService = questionService;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        QuestionImageService bean = applicationContext.getBean(QuestionImageService.class);
        System.out.println(bean);
    }

    @RequestMapping(value="/",  method = RequestMethod.POST)
    public void insert(MultipartHttpServletRequest request, HttpServletResponse response) {

        Iterator<String> itr=request.getFileNames();
        MultipartFile file=request.getFile(itr.next());

        Long id = Long.valueOf(request.getParameter("question"));
        Question question = questionService.findById(id);

        String url = service.savePicture(file);

        QuestionImage questionImage = new QuestionImage();
        questionImage.question = question;
        questionImage.imageUrl = url;

        service.insert(questionImage);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Long id, @RequestBody QuestionImage questionImage) {
        service.update(id, questionImage);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<QuestionImage> getAll() {
        return service.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public QuestionImage getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }



}
