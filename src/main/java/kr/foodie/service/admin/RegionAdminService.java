package kr.foodie.service.admin;

import kr.foodie.domain.shop.Region;
import kr.foodie.repo.RegionRepository;
import kr.foodie.repo.admin.FoodCategoryAdminRepository;
import kr.foodie.repo.admin.RegionAdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionAdminService {

    private final RegionAdminRepository regionRepository;

    public RegionAdminService(RegionAdminRepository regionRepository) {
        this.regionRepository = regionRepository;
    }
    public List<String> getRegionProvinceInfo() {
        return regionRepository.findRegionProvinceInfo();
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
