package com.ducklings.domain.distribution;

import com.ducklings.domain.distribution.model.DataType;
import com.ducklings.domain.distribution.model.Distribution;
import com.ducklings.domain.distribution.model.Region;
import com.ducklings.domain.distribution.model.responses.*;
import com.ducklings.domain.distribution.repositories.DataTypeRepository;
import com.ducklings.domain.distribution.repositories.DistributionRepository;
import com.ducklings.domain.distribution.repositories.RegionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class DistributionService {
    private final DistributionRepository distributionRepository;
    private final RegionRepository regionRepository;
    private final DataTypeRepository dataTypeRepository;

    public DistributionService(DistributionRepository distributionRepository, RegionRepository regionRepository, DataTypeRepository dataTypeRepository) {
        this.distributionRepository = distributionRepository;
        this.regionRepository = regionRepository;
        this.dataTypeRepository = dataTypeRepository;
    }

    @Cacheable("summary")
    public SummaryResponse getSummary(int year, int region, int dataType) {
        var distributions = distributionRepository.getSearchResult(year, region);
        var regionPOJO = getRegionById(region);
        var dataTypePOJO = getDataTypeById(dataType);
        List<StringPairResult> resList = new ArrayList<>();
        for (Distribution i: distributions){
            var value = "-1.0";
            switch(dataTypePOJO.getUnits()){
                case "lbs":
                    value = i.getValue().toString();
                    break;
                case "pcs":
                    value = i.getTrees().toString();
                    break;
                case "kWh":
                    value =i.getEnergy().toString();
                    break;
            };
            resList.add(StringPairResult.of(i.getDatestart().toString(), value));
        }
        return SummaryResponse.of(dataTypePOJO, regionPOJO, resList);
    }

    @Cacheable("regions")
    public RegionsResponse getRegions() {
        var res = regionRepository.findAll();
        List<RegionsResponseItem> ans = new ArrayList<>();
        for (Region region: res) {
            ans.add(RegionsResponseItem.of(region.getId(), region.getName()));
        }
        return RegionsResponse.of(ans);
    }

    @Cacheable("dataTypes")
    public DataTypeResponse getDataTypes() {
        var res = dataTypeRepository.findAll();
        List<DataTypeResponseItem> ans = new ArrayList<>();
        for (DataType dataType: res) {
            ans.add(DataTypeResponseItem.of(dataType.getId(), dataType.getName(), dataType.getUnit()));
        }
        return DataTypeResponse.of(ans);
    }

    @Cacheable("regionsById")
    public RegionsResponseItem getRegionById(int id) {
        var res = regionRepository.findById(id);
        if (res.isPresent()){
            var ans = res.get();
            return RegionsResponseItem.of(ans.getId(), ans.getName());
        }
        return null;
    }

    @Cacheable("dataTypesById")
    public DataTypeResponseItem getDataTypeById(int id) {
        var res = dataTypeRepository.findById(id);
        if (res.isPresent()){
            var ans = res.get();
            return DataTypeResponseItem.of(ans.getId(), ans.getName(), ans.getUnit());
        }
        return null;
    }
}
