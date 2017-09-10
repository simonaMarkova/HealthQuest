package mk.ukim.finki.healthquiz.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * Created by user on 20.4.2017.
 */
@Entity
@Table (name = "health_question_image")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionImage extends BaseEntity {

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")
    public Question question;

    public String imageUrl;
}
