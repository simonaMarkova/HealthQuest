package mk.ukim.finki.healthquiz.resources;

import mk.ukim.finki.healthquiz.models.Disease;
import mk.ukim.finki.healthquiz.service.DiseaseService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Random;

/**
 * Created by Simona on 15.03.2017.
 */
@RestController
@RequestMapping(value = "/disease", produces = "application/json")
public class DiseaseResource implements ApplicationContextAware {

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DiseaseService bean = applicationContext.getBean(DiseaseService.class);
        System.out.println(bean);
    }

    private DiseaseService service;

    @Autowired
    public DiseaseResource(DiseaseService service)
    {
        this.service = service;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void insert(@Valid @RequestBody Disease disease) {
        service.insert(disease);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Long id, @RequestBody Disease disease) {
        service.update(id, disease);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Disease> getAll() {
        return service.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Disease getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @RequestMapping(value = "/random", method = RequestMethod.GET)
    public Disease getRandom(){
        List<Disease> diseases = service.findAll();
        Random r = new Random();
        int randomId = r.nextInt(diseases.size());
        return diseases.get(randomId);
    }
}
