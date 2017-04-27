package mk.ukim.finki.healthquiz.resources;

import mk.ukim.finki.healthquiz.models.Level;
import mk.ukim.finki.healthquiz.service.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Simona on 19.04.2017.
 */
@RestController
@RequestMapping(value = "/level", produces = "application/json")
public class LevelResource {

    private final LevelService service;

    @Autowired
    public LevelResource(LevelService levelService) {
        this.service = levelService;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void insert(@Valid @RequestBody Level level) {
        service.insert(level);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Long id, @RequestBody Level level) {
        service.update(id, level);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Level> getAll() {
        return service.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Level getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }
}
