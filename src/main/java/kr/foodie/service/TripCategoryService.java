package kr.foodie.service;

import kr.foodie.repo.admin.TripCategoryAdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TripCategoryService {

    private final TripCategoryAdminRepository tripCategoryAdminRepository;

    public String getTripCategory(Integer bCode, Integer mCode, String address){

        String category = "";
        bCode = bCode.toString().startsWith(",")?bCode:bCode;
        mCode = mCode.toString().startsWith(",")?mCode:mCode;

        bCode = bCode.toString().contains(",")?bCode :bCode;
        mCode = mCode.toString().contains(",")?mCode:mCode;


        category += address.split(" ")[0]+ " " + address.split(" ")[1];
        category += bCode.toString().equalsIgnoreCase("0")? "":" | " + tripCategoryAdminRepository.findByBCodeAndLevel(bCode,1).get(0).getCategoryName();
        category += mCode.toString().equalsIgnoreCase("0")? "":" | " + tripCategoryAdminRepository.findByMCodeAndLevel(mCode,2).get(0).getCategoryName();

        return category;
    }
}
