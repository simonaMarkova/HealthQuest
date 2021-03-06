package mk.ukim.finki.healthquiz.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Simona on 22.02.2017.
 */
@Entity
@Table(name="health_answer")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Answer extends BaseEntity {

    private String description;

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
}
