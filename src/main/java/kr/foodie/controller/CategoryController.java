package kr.foodie.controller;

import kr.foodie.domain.category.Category;
import kr.foodie.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping(path = "category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping(value ="/region/{regionType}")
    public List<Category> getCategory(@PathVariable String regionType, HttpServletRequest request){
        if(regionType.equals("2")){
            String bigRegionName = request.getParameter("b_name");
            String middleRegionName = request.getParameter("m_name");
            return categoryService.getCategorySecondType(regionType, bigRegionName, middleRegionName, 0);
        }else{
            String regionName = request.getParameter("name");
            return categoryService.getCategory(regionType, regionName);
        }
    }
}
