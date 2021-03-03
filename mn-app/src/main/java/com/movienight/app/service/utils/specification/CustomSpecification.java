package com.movienight.app.service.utils.specification;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class CustomSpecification<T> implements Specification<T> {

    private final List<SearchCriteria> criteriaList;

    public CustomSpecification() {
        this.criteriaList = new ArrayList<>();
    }

    public void addCriteria(SearchCriteria criteria) {
        criteriaList.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        for (SearchCriteria criteria : criteriaList) {
            Object value = criteria.getValue();

            if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
                Path<Object> key = root.get(criteria.getKey());
                predicates.add(builder.equal(key, value));

            } else if (criteria.getOperation().equals(SearchOperation.IN)) {
                Path<Object> key = root.get(criteria.getKey());
                predicates.add(builder.in(key).value(value));

            } else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
                Expression<String> key = builder.lower(root.get(criteria.getKey()));
                String valueInLowerCase = "%" + value.toString().toLowerCase() + "%";

                predicates.add(builder.like(key, valueInLowerCase));
            }
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
