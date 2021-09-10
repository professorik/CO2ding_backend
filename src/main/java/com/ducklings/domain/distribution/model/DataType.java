package com.ducklings.domain.distribution.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = "datatype")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataType {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;
    @Column
    private String name;
    @Column
    private String unit;

    public static DataType of(Integer id, String name, String unit) {
        DataType dataType = new DataType();
        dataType.setId(id);
        dataType.setName(name);
        dataType.setUnit(unit);
        return dataType;
    }
}
