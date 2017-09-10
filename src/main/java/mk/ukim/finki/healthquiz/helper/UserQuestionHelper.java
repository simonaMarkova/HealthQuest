package mk.ukim.finki.healthquiz.helper;

import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * Created by Simona on 9/8/2017.
 */
public class UserQuestionHelper {

    Long userId;
    Long answerImageId;
    Long questionId;
    Long questionAnswerId;
    Boolean win;
    Integer points;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAnswerImageId() {
        return answerImageId;
    }

    public void setAnswerImageId(Long answerImageId) {
        this.answerImageId = answerImageId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getQuestionAnswerId() {
        return questionAnswerId;
    }

    public void setQuestionAnswerId(Long questionAnswerId) {
        this.questionAnswerId = questionAnswerId;
    }

    public Boolean getWin() {
        return win;
    }

    public void setWin(Boolean win) {
        this.win = win;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
