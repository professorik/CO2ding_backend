package com.ducklings.domain.branch.model;

import lombok.Data;

@Data
public class YearInfoResponse {
    private Integer year;
    private Double num;

    public static YearInfoResponse of(Integer year, Double num) {
        YearInfoResponse res = new YearInfoResponse();
        res.setYear(year);
        res.setNum(num);
        return res;
    }
}
