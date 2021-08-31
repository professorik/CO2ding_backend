package com.ducklings.domain.branch;

import com.ducklings.domain.branch.model.Branch;
import com.ducklings.domain.branch.model.DistributionResponse;
import com.ducklings.domain.branch.model.PeaksResponse;
import com.ducklings.domain.branch.model.YearInfoResponse;
import com.ducklings.domain.user.model.User;
import com.ducklings.domain.user.model.UserRole;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BranchService {
    private final BranchRepository branchRepository;

    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public void delete(UUID branchId) {
        var userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails.getAuthorities().contains(UserRole.ADMIN)) {
            branchRepository.deleteById(branchId);
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
    }

    public List<Branch> getAll() {
        return branchRepository.findAll();
    }

    public Branch update(Branch branch) {
        var userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userDetails.getAuthorities().contains(UserRole.ADMIN)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        return branchRepository.save(branch);
    }

    public Branch create(Branch branch) {
        return branchRepository.save(branch);
    }

    public Branch getById(UUID branchId) {
        return branchRepository.getById(branchId);
    }

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
        ans.sort(new ResponseComparator());
        return ans;
    }

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
        ans.sort(new ResponseComparator2());
        return ans;
    }

    //todo: make common parent
    class ResponseComparator implements Comparator<YearInfoResponse> {
        @Override
        public int compare(YearInfoResponse a, YearInfoResponse b) {
            return a.getYear().compareTo(b.getYear());
        }
    }

    class ResponseComparator2 implements Comparator<PeaksResponse> {
        @Override
        public int compare(PeaksResponse a, PeaksResponse b) {
            return a.getYear().compareTo(b.getYear());
        }
    }
}
