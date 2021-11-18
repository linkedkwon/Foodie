package kr.foodie.service.admin;

import kr.foodie.domain.shop.EpicureRegion;
import kr.foodie.domain.shop.Region;
import kr.foodie.repo.RegionRepository;
import kr.foodie.repo.admin.EpicureRegionRepository;
import kr.foodie.repo.admin.FoodCategoryAdminRepository;
import kr.foodie.repo.admin.RegionAdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionAdminService {

    private final RegionAdminRepository regionRepository;
    private final EpicureRegionRepository epicureRegionRepository;

    public RegionAdminService(RegionAdminRepository regionRepository, EpicureRegionRepository epicureRegionRepository) {
        this.regionRepository = regionRepository;
        this.epicureRegionRepository = epicureRegionRepository;
    }
    public List<String> getRegionProvinceInfo() {
        return regionRepository.findRegionProvinceInfo();
    }

    public List<EpicureRegion> getEpicureProvince() {
        return epicureRegionRepository.findByParentNoAndVisiable();
    }

    public List<Region> getRegionRegionInfo(Integer regionId) {
        return regionRepository.findByRegionId(regionId);
    }
    public List<Region> getSubwayRegionInfo(Integer regionId) {
        return regionRepository.findByRegionId(regionId);
    }

    public List<Region> getRegionDistrictInfo(String provinceName) {
        return regionRepository.findRegionDistrictInfo(provinceName);
    }
    public List<Region> getRegionFoodRegionInfo() {
        return regionRepository.findRegionFoodRegionInfo();
    }
    public List<String> getRegionProvinceInfoWithRegionType2() {
        return regionRepository.findRegionProvinceInfoWithRegionType2();
    }
    public List<String> getRegionDistrictInfoWithRegionType2(String provinceName) {
        return regionRepository.findRegionDistrictInfoWithRegionType2(provinceName);
    }
    public List<Region> getRegionSubwayInfoWithRegionType2(String provinceName, String districtName) {
        return regionRepository.findRegionSubwaytInfoWithRegionType2(provinceName, districtName);
    }
}

