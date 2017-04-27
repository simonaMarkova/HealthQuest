package mk.ukim.finki.healthquiz.resources;

import mk.ukim.finki.healthquiz.models.AnswerImage;
import mk.ukim.finki.healthquiz.models.Question;
import mk.ukim.finki.healthquiz.service.AnswerImageService;
import mk.ukim.finki.healthquiz.service.QuestionService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * Created by user on 20.4.2017.
 */
@RestController
@RequestMapping(value = "/answerImage", produces = "application/json")
public class AnswerImageResource implements ApplicationContextAware {

    private AnswerImageService service;
    private final QuestionService questionService;

    @Autowired
    public AnswerImageResource(AnswerImageService service, QuestionService questionService) {
        this.service = service;
        this.questionService = questionService;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AnswerImageService bean = applicationContext.getBean(AnswerImageService.class);
        System.out.println(bean);
    }


    @RequestMapping(value="/",  method = RequestMethod.POST)
    public void insert(MultipartHttpServletRequest request, HttpServletResponse response) {
        int flag = 0;
        Iterator<String> itr=request.getFileNames();
        MultipartFile file=request.getFile(itr.next());

        Long id = Long.valueOf(request.getParameter("question"));
        Question question = questionService.findById(id);

        boolean status = Boolean.parseBoolean(request.getParameter("status"));

        int number = Integer.parseInt(request.getParameter("number"));

        String url = service.savePicture(file);
        List<AnswerImage> answerImages = service.findByQuestionId(id);
        if(answerImages.size() != 0)
        {
            for(AnswerImage a : answerImages)
            {
                if(a.number == number)
                {
                    if(a.imageUrl != null)
                    {
                        File f = new File(a.imageUrl);
                        f.delete();
                    }
                    a.imageUrl = url;
                    a.status = status;
                    service.update(a.id, a);
                    flag =1;
                }
            }
        }
        else
        {
            AnswerImage answerImage = new AnswerImage();
            answerImage.question = question;
            answerImage.imageUrl = url;
            answerImage.status = status;
            answerImage.number = number;
            service.insert(answerImage);
            flag = 1;
        }



        if(flag == 0)
        {
            AnswerImage answerImage = new AnswerImage();
            answerImage.question = question;
            answerImage.imageUrl = url;
            answerImage.status = status;
            answerImage.number = number;
            service.insert(answerImage);
        }



    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Long id, @RequestBody AnswerImage answerImage) {
        service.update(id, answerImage);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<AnswerImage> getAll() {
        return service.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public AnswerImage getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @RequestMapping(value = "/getByQuestion/{id}", method = RequestMethod.GET)
    public List<AnswerImage> getByQuestionId(@PathVariable Long id) {
        return service.findByQuestionId(id);
    }

}
