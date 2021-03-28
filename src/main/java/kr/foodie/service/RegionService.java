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

    public List<Region> getRegionInfo(Integer regionId) {
        return regionRepository.findByRegionId(regionId);
    }
}
