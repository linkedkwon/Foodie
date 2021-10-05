package kr.foodie.service;

import kr.foodie.repo.admin.TripCategoryAdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TripCategoryService {

    private final TripCategoryAdminRepository tripCategoryAdminRepository;

    public String getTripCategory(String bCode, String mCode, String address){

        String category = "";
        bCode = bCode.startsWith(",")?bCode.substring(1):bCode;
        mCode = mCode.startsWith(",")?mCode.substring(1):mCode;

        bCode = bCode.contains(",")?bCode.split(",")[0]:bCode;
        mCode = mCode.contains(",")?mCode.split(",")[0]:mCode;

        category += address.split(" ")[0]+ " " + address.split(" ")[1];
        category += bCode.equalsIgnoreCase("0")? "":" | " + tripCategoryAdminRepository.findByBCodeAndLevel(Integer.parseInt(bCode),1).get(0).getCategoryName();
        category += mCode.equalsIgnoreCase("0")? "":" | " + tripCategoryAdminRepository.findByMCodeAndLevel(Integer.parseInt(mCode),2).get(0).getCategoryName();

        return category;
    }
}
