package com.ducklings.domain.branch;

import com.ducklings.domain.branch.model.Branch;
import com.ducklings.domain.branch.model.DistributionResponse;
import com.ducklings.domain.branch.model.PeaksResponse;
import com.ducklings.domain.branch.model.YearInfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BranchService {
    private final BranchRepository branchRepository;

    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Cacheable("all")
    public List<Branch> getAll() {
        return branchRepository.findAll();
    }

    @Cacheable("getBranch")
    public Branch getById(UUID branchId) {
        return branchRepository.getById(branchId);
    }

    @Cacheable("summary")
    public List<YearInfoResponse> getSummary() {
        var branches = getAll();
        Map<Integer, Double> res = new HashMap<>();
        for (Branch i: branches) {
            if (res.containsKey(i.getYear())){
                res.put(i.getYear(), res.get(i.getYear()) + i.getEjection());
            }else{
                res.put(i.getYear(), i.getEjection());
            }
        }
        var ans= res.keySet().stream()
                .map(it -> YearInfoResponse.of(it, res.get(it)))
                .collect(Collectors.toList());
        Collections.sort(ans);
        return ans;
    }

    @Cacheable("distribution")
    public List<DistributionResponse> getDistribution() {
        var branches = getAll();
        long lastYear = -1;
        double sum = 0;
        List<DistributionResponse> res = new ArrayList<>();
        for (Branch i: branches) {
            if (i.getYear() > lastYear){
                res = new ArrayList<>();
                lastYear = i.getYear();
            }
            if (i.getYear() == lastYear){
                res.add(DistributionResponse.of(i.getName(), i.getEjection()));
                sum += i.getEjection();
            }
        }
        for (DistributionResponse i: res){
            try {
                i.setNum(i.getNum() / sum);
            }catch (ArithmeticException e){
                i.setNum(0d);
            }
        }
        return res;
    }

    @Cacheable("diffs")
    public List<YearInfoResponse> getDiffs() {
        var summary = getSummary();
        var res = new ArrayList<YearInfoResponse>();
        for (int i = 1; i < summary.size(); i++) {
            try {
                res.add(YearInfoResponse.of(summary.get(i).getYear(),
                        summary.get(i).getNum() / summary.get(i - 1).getNum()));
            }catch (ArithmeticException e){
                res.add(YearInfoResponse.of(summary.get(i).getYear(), 2d));
            }
        }
        return res;
    }

    @Cacheable("peaks")
    public List<PeaksResponse> getPeaks() {
        var branches = getAll();
        Map<Integer, DistributionResponse> res = new HashMap<>();
        for (Branch i: branches) {
            if (!res.containsKey(i.getYear()) || res.get(i.getYear()).getNum() < i.getEjection()){
                res.put(i.getYear(), DistributionResponse.of(i.getName(), i.getEjection()));
            }
        }
        var ans= res.keySet().stream()
                .map(it -> PeaksResponse.of(res.get(it).getName(), it))
                .collect(Collectors.toList());
        Collections.sort(ans);
        return ans;
    }
}
