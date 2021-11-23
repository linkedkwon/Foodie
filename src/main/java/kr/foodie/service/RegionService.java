package kr.foodie.service;

import kr.foodie.domain.shop.Region;
import kr.foodie.repo.RegionRepository;
import kr.foodie.repo.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService {

    private final RegionRepository regionRepository;


    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

//    public List<Region> getRegionInfo(Integer area1st, Integer area2st, Integer area3st) {
//        return regionRepository.findByArea1stAndArea2stAndArea3st(area1st, area2st, area3st);
//    }

    public List<Region> getRegionInfoWithType3(String regionType) {
        return regionRepository.findByRegionType(regionType);
    }
}
