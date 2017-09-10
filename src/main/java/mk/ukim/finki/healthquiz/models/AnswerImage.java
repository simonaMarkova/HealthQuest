package mk.ukim.finki.healthquiz.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * Created by user on 20.4.2017.
 */
@Entity
@Table(name = "health_answer_image")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnswerImage extends BaseEntity {

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "question_id")
    public Question question;

    public boolean status;

    public String imageUrl;

    public int number;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
