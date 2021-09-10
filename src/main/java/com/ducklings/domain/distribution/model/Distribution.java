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
@Table(name = "distribution")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Distribution {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;
    @Column
    private Date datestart;
    @Column
    private Integer regionid;
    @Column
    private Double value;
    @Column
    private Integer trees;
    @Column
    private Double energy;

    public static Distribution of(Date datestart, Integer regionId, Double value, Integer trees, Double energy) {
        Distribution distribution = new Distribution();
        distribution.setDatestart(datestart);
        distribution.setRegionid(regionId);
        distribution.setValue(value);
        distribution.setTrees(trees);
        distribution.setEnergy(energy);
        return distribution;
    }
}
