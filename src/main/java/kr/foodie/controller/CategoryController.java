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

        String regionName = request.getParameter("name");

        return regionType.equals("2") ? categoryService.getCategorySecondType(regionType, regionName, 0)
                : categoryService.getCategory(regionType, regionName);
    }
}
