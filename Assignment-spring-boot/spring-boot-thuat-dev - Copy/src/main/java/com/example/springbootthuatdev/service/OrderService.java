package com.example.springbootthuatdev.service;

import com.example.springbootthuatdev.entity.Order;
import com.example.springbootthuatdev.entity.search.FilterParameter;
import com.example.springbootthuatdev.entity.search.OrderSpecification;
import com.example.springbootthuatdev.entity.search.SearchCriteria;
import com.example.springbootthuatdev.entity.search.SearchCriteriaOperator;
import com.example.springbootthuatdev.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service

public class OrderService {
    final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    public Page<Order> findAll(FilterParameter param) {
        Specification<Order> specification = Specification.where(null);
        if(param.getKeyword() != null && param.getKeyword().length() > 0) {
            SearchCriteria searchCriteria
                    = new SearchCriteria("keyword", SearchCriteriaOperator.JOIN, param.getKeyword());
            OrderSpecification filter = new OrderSpecification(searchCriteria);
            specification = specification.and(filter);
        }
        if(param.getStatus() != 0) {
            SearchCriteria searchCriteria
                    = new SearchCriteria("status", SearchCriteriaOperator.EQUALS, param.getStatus());
            OrderSpecification filter = new OrderSpecification(searchCriteria);
            specification = specification.and(filter);
        }
        if(param.getUserId() != null) {
            SearchCriteria searchCriteria
                    = new SearchCriteria("userId", SearchCriteriaOperator.EQUALS, param.getUserId());
            OrderSpecification filter = new OrderSpecification(searchCriteria);
            specification = specification.and(filter);
        }
        return orderRepository.findAll(
                specification, PageRequest.of(param.getPage() - 1, param.getLimit())
        );
    }
}
