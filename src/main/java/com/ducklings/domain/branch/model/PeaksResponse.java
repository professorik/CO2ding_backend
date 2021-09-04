package com.ducklings.domain.branch.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class PeaksResponse implements Comparable<PeaksResponse> {
    private String name;
    private Integer year;

    public static PeaksResponse of(String name, Integer year){
        PeaksResponse res = new PeaksResponse();
        res.setName(name);
        res.setYear(year);
        return res;
    }

    @Override
    public int compareTo(PeaksResponse temp) {
        return Integer.compare(getYear(), temp.getYear());
    }
}
