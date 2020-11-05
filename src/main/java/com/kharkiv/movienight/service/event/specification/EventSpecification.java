package com.kharkiv.movienight.service.event.specification;

import com.kharkiv.movienight.persistence.model.event.Event;
import com.kharkiv.movienight.service.utils.specification.SearchCriteria;
import com.kharkiv.movienight.service.utils.specification.SearchOperation;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class EventSpecification implements Specification<Event> {

    private final List<SearchCriteria> criteriaList;

    public EventSpecification() {
        this.criteriaList = new ArrayList<>();
    }

    public void addCriteria(SearchCriteria criteria) {
        criteriaList.add(criteria);
    }

    public List<SearchCriteria> getCriteriaList(){
        return this.criteriaList;
    }

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        for (SearchCriteria criteria : criteriaList) {
            if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
                Path<Object> key = root.get(criteria.getKey());
                Object value = criteria.getValue();

                predicates.add(builder.equal(key, value));

            } else if (criteria.getOperation().equals(SearchOperation.IN)) {
                Path<Object> key = root.get(criteria.getKey());
                Object value = criteria.getValue();

                predicates.add(builder.in(key).value(value));

            } else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
                Expression<String> key = builder.lower(root.get(criteria.getKey()));
                String value = "%" + criteria.getValue().toString().toLowerCase() + "%";

                predicates.add(builder.like(key, value));
            }
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
