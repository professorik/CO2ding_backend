package com.ducklings.domain.branch;

import com.ducklings.domain.branch.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface BranchRepository extends JpaRepository<Branch, UUID> {

}
