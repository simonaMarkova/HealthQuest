package mk.ukim.finki.healthquiz.models;

import mk.ukim.finki.healthquiz.enumeration.*;

import javax.persistence.*;

/**
 * Created by KM on 07-Feb-17.
 */
@Entity
@Table(name="health_question")
public class Question extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;

    private String question;

    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "disease_id")
    private Disease disease;

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Disease getDisease(){
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

}
