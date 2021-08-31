package com.ducklings.domain.branch.model;

import lombok.Data;

@Data
public class Response<V,T> {
    private V first;
    private T second;
}
