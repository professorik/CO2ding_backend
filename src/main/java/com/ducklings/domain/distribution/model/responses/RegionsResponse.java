package com.ducklings.domain.distribution.model.responses;

import lombok.Data;

import java.util.List;

@Data
public class RegionsResponse {
    List<RegionsResponseItem> results;

    public static RegionsResponse of(List<RegionsResponseItem> list){
        RegionsResponse res = new RegionsResponse();
        res.setResults(list);
        return res;
    }
}
