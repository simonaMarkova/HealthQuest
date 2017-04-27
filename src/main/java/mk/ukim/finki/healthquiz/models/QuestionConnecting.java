package mk.ukim.finki.healthquiz.models;

import javax.persistence.*;

/**
 * Created by Simona on 29.03.2017.
 */
@Entity
@Table(name="health_question_connecting")
public class QuestionConnecting extends BaseEntity {

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")
    public Question question;

    public String phraseOne;

    public String phraseTwo;
}
