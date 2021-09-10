package com.ducklings.domain.distribution.repositories;

import com.ducklings.domain.distribution.model.DataType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataTypeRepository extends JpaRepository<DataType, Integer> {
}
