package mk.ukim.finki.healthquiz.persistance;

import mk.ukim.finki.healthquiz.models.UserQuestion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by user on 19.4.2017.
 */
@Repository
public interface UserQuestionRepository extends CrudRepository<UserQuestion,Long> {
    List<UserQuestion> findByQuestionId(Long id);
    List<UserQuestion> findByUserId(Long id);

    @Query("select sum(q.points) from UserQuestion q where q.user.id = :userId")
    int getPoints(@Param("userId") Long userId);
}
