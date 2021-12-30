package kr.foodie.controller;

import kr.foodie.domain.category.Category;
import kr.foodie.domain.shopItem.EpicureRegion;
import kr.foodie.service.CategoryService;
import kr.foodie.service.admin.RegionAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping(path = "category")
public class CategoryController {

    private final CategoryService categoryService;
    private final RegionAdminService regionAdminService;
    @ResponseBody
    @GetMapping(value ="/region/{regionType}")
    public Map<String, List> getCategory(Model model, @PathVariable Integer parentNo, @PathVariable String type){
//        if(regionType.equals("2")){
//            String bigRegionName = request.getParameter("b_name");
//            String middleRegionName = request.getParameter("m_name");
          List<EpicureRegion> regionInfos = regionAdminService.getEpicureDistrict(parentNo, type);
          Map<String, List> infos = new HashMap<>();
          infos.put("data", regionInfos);
            return infos;
//        }else{
//            String regionName = request.getParameter("name");
//            return categoryService.getCategory(regionType, regionName);
//        }
    }
}
