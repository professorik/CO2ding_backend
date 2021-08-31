package com.ducklings.domain.branch;

import com.ducklings.domain.branch.model.Branch;
import com.ducklings.domain.branch.model.DistributionResponse;
import com.ducklings.domain.branch.model.PeaksResponse;
import com.ducklings.domain.branch.model.YearInfoResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/co2")
public class BranchController {
    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping("all")
    public List<Branch> getBranches() { return branchService.getAll(); }

    @DeleteMapping("delete/{branchId}")
    public void deleteBranch(@PathVariable UUID branchId) {
        branchService.delete(branchId);
    }

    @PutMapping("create")
    public Branch createBranch(@RequestBody Branch branch) {
        return branchService.create(branch);
    }

    @PatchMapping("update")
    public Branch updateBranch(@RequestBody Branch branch) {
        return branchService.update(branch);
    }

    @GetMapping("{branchId}")
    public Branch getBranch(@PathVariable UUID branchId) {
        return branchService.getById(branchId);
    }

    @GetMapping("summary")
    public List<YearInfoResponse> getSummary() {
        return branchService.getSummary();
    }

    @GetMapping("distribution")
    public List<DistributionResponse> getDistribution() {
        return branchService.getDistribution();
    }

    @GetMapping("diffs")
    public List<YearInfoResponse> getDiffs() {
        return branchService.getDiffs();
    }

    @GetMapping("peaks")
    public List<PeaksResponse> getPeaks() {
        return branchService.getPeaks();
    }
}
