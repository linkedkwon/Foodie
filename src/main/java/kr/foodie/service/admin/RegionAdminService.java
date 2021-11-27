package kr.foodie.service.admin;

import kr.foodie.domain.shopItem.EpicureRegion;
import kr.foodie.domain.shopItem.Region;
import kr.foodie.domain.shopItem.RegionCreateDTO;
import kr.foodie.repo.admin.EpicureRegionRepository;
import kr.foodie.repo.admin.RegionAdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegionAdminService {

    private final RegionAdminRepository regionRepository;
    private final EpicureRegionRepository epicureRegionRepository;

    public List<EpicureRegion> getEpicureFirstInfo(String type) {
        return epicureRegionRepository.findByParentNoAndVisiable(type);
    }
    public List<EpicureRegion> getEpicureDistrict(Integer parentNo, String type) {
        return epicureRegionRepository.getEpicureDistrict(parentNo, type);
    }
    public List<EpicureRegion> getRegionFirstInfoByRegionId(Integer id, String type){
        return epicureRegionRepository.getRegionFirstInfoByRegionId(id, type);
    }
    public List<EpicureRegion> getRegionSecondInfoByRegionId(Integer id, String type){
        return epicureRegionRepository.getRegionSecondInfoByRegionId(id, type);
    }

    @Transactional
    public void update(List<RegionCreateDTO> list) {
        epicureRegionRepository.saveAll(list.stream()
                .map(EpicureRegion::from)
                .collect(Collectors.toList()));
    }

    public List<String> getRegionProvinceInfo() {
        return regionRepository.findRegionProvinceInfo();
    }

    public List<Region> getRegionRegionInfo(Integer area1st, Integer area2st, Integer area3st) {
//        return regionRepository.findByArea1stAndArea2stAndArea3st(area1st,  area2st,  area3st);
        return null;
    }
    public List<Region> getSubwayRegionInfo(Integer area1st, Integer area2st, Integer area3st) {
//        return regionRepository.findByArea1stAndArea2stAndArea3st(area1st,  area2st,  area3st);
        return null;
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

