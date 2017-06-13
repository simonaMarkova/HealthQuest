package mk.ukim.finki.healthquiz.persistance.impl;

import mk.ukim.finki.healthquiz.enumeration.QuestionType;
import mk.ukim.finki.healthquiz.models.Question;
import mk.ukim.finki.healthquiz.persistance.QueryRepository;
import mk.ukim.finki.healthquiz.persistance.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by Simona on 09.06.2017.
 */
@Repository
public class QueryRepositoryImpl implements QueryRepository {

    @Autowired
    private QuestionRepository questionRepository;


    @Override
    public Page<Question> getByPage(QuestionType type, int page, int pageSize) {

        final QuestionType questionType = type;

        Specification<Question> specification = new Specification<Question>() {
            @Override
            public Predicate toPredicate(Root<Question> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("questionType"), questionType);
            }
        };

        return questionRepository.findAll(specification, new PageRequest(page, pageSize));

    }
}
