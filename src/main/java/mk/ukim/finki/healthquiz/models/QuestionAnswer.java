package mk.ukim.finki.healthquiz.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * Created by Simona on 22.02.2017.
 */
@Entity
@Table(name="health_question_answer")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionAnswer extends BaseEntity {

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")
    public Question question;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "answer_id")
    public Answer answer;

    private boolean status;

    public void setStatus(boolean status)
    {
        this.status = status;
    }

    public boolean getStatus()
    {
        return status;
    }

}
