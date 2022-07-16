package com.example.springbootthuatdev.entity.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {
    private String key; // truong nao
    private SearchCriteriaOperator operator; // toan tu
    private Object value; // gia tri la gi
}
