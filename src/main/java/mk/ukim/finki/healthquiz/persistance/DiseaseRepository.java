package mk.ukim.finki.healthquiz.persistance;
import mk.ukim.finki.healthquiz.models.Disease;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Simona on 15.03.2017.
 */
@Repository
public interface DiseaseRepository extends CrudRepository<Disease,Long>{

}
