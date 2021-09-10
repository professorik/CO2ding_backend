package com.ducklings.domain.distribution.model.responses;

import lombok.Data;

import java.util.List;

@Data
public class SummaryResponse {
    private DataTypeResponseItem dataType;
    private RegionsResponseItem region;
    private List<StringPairResult> results;

    public static SummaryResponse of(DataTypeResponseItem dataType, RegionsResponseItem region, List<StringPairResult> results) {
        SummaryResponse res = new SummaryResponse();
        res.setDataType(dataType);
        res.setRegion(region);
        res.setResults(results);
        return res;
    }
}
