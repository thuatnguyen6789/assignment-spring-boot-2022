package com.example.springbootthuatdev.entity.search;

import com.example.springbootthuatdev.entity.Order;
import com.example.springbootthuatdev.entity.OrderDetail;
import com.example.springbootthuatdev.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class OrderSpecification implements Specification<Order> {
    private SearchCriteria searchCriteria; // luu dieu kien search
    public OrderSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }
    @Override
    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        switch (searchCriteria.getOperator()) {
            case EQUALS:
                return criteriaBuilder.equal(root.get(searchCriteria.getKey()), searchCriteria.getValue());
            case GREATER_THAN:
                return criteriaBuilder.greaterThan(root.get(searchCriteria.getKey()), String.valueOf(searchCriteria.getValue()));
            case GREATER_THAN_OR_EQUALS:
                return criteriaBuilder.greaterThanOrEqualTo(root.get(searchCriteria.getKey()),
                        String.valueOf(searchCriteria.getValue()));
            case LESS_THAN:
                return criteriaBuilder.lessThan(root.get(searchCriteria.getKey()),
                        String.valueOf(searchCriteria.getValue()));
            case LESS_THAN_OR_EQUALS:
                return criteriaBuilder.lessThanOrEqualTo(
                        root.get(searchCriteria.getKey()),
                        String.valueOf(searchCriteria.getValue())
                );
            case JOIN:
                Join<OrderDetail, Product> orderDetailProductJoin = root.join("orderDetails").join("product");
                Predicate predicate = criteriaBuilder.or(
                        // tim trong order ban ghi co id giong gia tri truyen vao
                        criteriaBuilder.like(root.get("id"), "%" + searchCriteria.getValue() + "%"),
                        // hoac tim trong bang product ban ghi co name giong voi gia tri
                        criteriaBuilder.like(orderDetailProductJoin.get("name"), "%" + searchCriteria.getValue() + "%")
                );
                return predicate;
        }
        return null;
    }
}
