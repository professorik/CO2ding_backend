package com.ducklings.domain.distribution;

import com.ducklings.domain.distribution.model.responses.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/co2/distribution")
@Slf4j
public class DistributionController {
    private final DistributionService distributionService;

    public DistributionController(DistributionService distributionService) {
        this.distributionService = distributionService;
    }

    @GetMapping("summary")
    public SummaryResponse getSummary(@RequestParam int year, @RequestParam int region, @RequestParam int dataType) {
        return distributionService.getSummary(year, region, dataType);
    }

    @GetMapping("regions")
    public RegionsResponse getRegions() {
        return distributionService.getRegions();
    }

    @GetMapping("dataTypes")
    public DataTypeResponse getDataTypes() {
        return distributionService.getDataTypes();
    }
}
