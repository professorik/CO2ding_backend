package com.ducklings.domain.distribution.model.responses;

import lombok.Data;

@Data
public class StringPairResult {
    private String dateStart;
    private String value;

    public static StringPairResult of(String dateStart, String value) {
        StringPairResult res = new StringPairResult();
        res.setDateStart(dateStart);
        res.setValue(value);
        return res;
    }
}
