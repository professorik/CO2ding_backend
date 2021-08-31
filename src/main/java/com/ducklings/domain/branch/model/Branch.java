package com.ducklings.domain.branch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author professorik
 * @created 24/08/2021 - 11:40
 * @project training
 */
@Entity
@Data
@Table(name = "branches")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Branch {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column
    private String name;
    @Column
    private Integer year;
    @Column
    private Double ejection;

    public static Branch of(String name, Integer year, Double ejection) {
        Branch branch = new Branch();
        branch.setId(UUID.randomUUID());
        branch.setName(name);
        branch.setYear(year);
        branch.setEjection(ejection);
        return branch;
    }
}
