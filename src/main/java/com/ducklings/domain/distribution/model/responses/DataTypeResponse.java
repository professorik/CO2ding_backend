package com.ducklings.domain.distribution.model.responses;

import lombok.Data;

import java.util.List;

@Data
public class DataTypeResponse {
    List<DataTypeResponseItem> results;

    public static DataTypeResponse of(List<DataTypeResponseItem> list){
        DataTypeResponse res = new DataTypeResponse();
        res.setResults(list);
        return res;
    }
}
