package mk.ukim.finki.healthquiz.service.impl;

import mk.ukim.finki.healthquiz.enumeration.QuestionType;
import mk.ukim.finki.healthquiz.models.Question;
import mk.ukim.finki.healthquiz.persistance.QueryRepository;
import mk.ukim.finki.healthquiz.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * Created by Simona on 09.06.2017.
 */
@Service
public class QueryServiceImpl implements QueryService {

    @Autowired
    QueryRepository queryRepository;

    @Override
    public Page<Question> getByPage(QuestionType type, int page, int pageSize) {
        return queryRepository.getByPage(type, page, pageSize);
    }
}
