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
@Table(name = "region")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Region {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;
    @Column
    private String name;

    public static Region of(String name) {
        Region region = new Region();
        region.setName(name);
        return region;
    }
}
