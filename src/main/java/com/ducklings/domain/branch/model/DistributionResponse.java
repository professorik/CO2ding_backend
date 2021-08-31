package com.ducklings.domain.branch.model;

import lombok.Data;

@Data
public class DistributionResponse {
    private String name;
    private Double num;

    public static DistributionResponse of(String name, Double num) {
        DistributionResponse res = new DistributionResponse();
        res.setName(name);
        res.setNum(num);
        return res;
    }
}
