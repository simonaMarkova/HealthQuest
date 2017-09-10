package mk.ukim.finki.healthquiz.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by Simona on 22.02.2017.
 */

@Entity
@Table(name="health_disease")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Disease extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "disease", fetch = FetchType.EAGER)
    private List<Question> questions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
