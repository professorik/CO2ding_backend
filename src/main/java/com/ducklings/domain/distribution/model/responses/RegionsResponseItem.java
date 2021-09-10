package com.ducklings.domain.distribution.model.responses;

import lombok.Data;

@Data
public class RegionsResponseItem {
    private Integer id;
    private String name;

    public static RegionsResponseItem of(Integer id, String name){
        RegionsResponseItem res = new RegionsResponseItem();
        res.setId(id);
        res.setName(name);
        return res;
    }
}
