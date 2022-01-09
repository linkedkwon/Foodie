package kr.foodie.service;

import kr.foodie.repo.admin.EpicureRegionRepository;
import kr.foodie.repo.admin.TripCategoryAdminRepository;
import kr.foodie.service.admin.RegionAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TripCategoryService {

    private final EpicureRegionRepository epicureRegionRepository;

    public String getTripCategory(String bCode, String mCode, String sCode, String address){

        String category = "";
        bCode = bCode.startsWith(",")?bCode.substring(1):bCode;
        mCode = mCode.startsWith(",")?mCode.substring(1):mCode;
        sCode = sCode.startsWith(",")?sCode.substring(1):sCode;

        bCode = bCode.contains(",")?bCode.split(",")[0]:bCode;
        mCode = mCode.contains(",")?mCode.split(",")[0]:mCode;
        sCode = sCode.contains(",")?sCode.split(",")[0]:sCode;

        category += address.split(" ")[0]+ " " + address.split(" ")[1];
        category += bCode.equalsIgnoreCase("")? "":" | " + epicureRegionRepository.findByCode(Integer.parseInt(bCode)).get(0).getListName();
        category += mCode.equalsIgnoreCase("")? "":" | " + epicureRegionRepository.findByCode(Integer.parseInt(mCode)).get(0).getListName();
        category += sCode.equalsIgnoreCase("")? "":" | " + epicureRegionRepository.findByCode(Integer.parseInt(sCode)).get(0).getListName();

        return category;
    }
}
