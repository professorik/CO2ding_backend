package com.ducklings.domain.branch.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class PeaksResponse{
    private String name;
    private Integer year;

    public static PeaksResponse of(String name, Integer year){
        PeaksResponse res = new PeaksResponse();
        res.setName(name);
        res.setYear(year);
        return res;
    }
}
