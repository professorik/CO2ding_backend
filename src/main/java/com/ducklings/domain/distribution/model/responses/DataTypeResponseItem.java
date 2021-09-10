package com.ducklings.domain.distribution.model.responses;

import lombok.Data;

@Data
public class DataTypeResponseItem {
    private Integer id;
    private String name;
    private String units;

    public static DataTypeResponseItem of(Integer id, String name, String units) {
        DataTypeResponseItem res = new DataTypeResponseItem();
        res.setId(id);
        res.setName(name);
        res.setUnits(units);
        return res;
    }
}
