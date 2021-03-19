package kr.foodie.controller;

import kr.foodie.domain.category.Category;
import kr.foodie.service.CategoryService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ResponseBody
    @GetMapping(value ="/region/{regionType}")
    public List<Category> getCategory(Model model, @PathVariable String regionType, HttpServletRequest request){
        String regionName = request.getParameter("name");
        List<Category> commentList = categoryService.getCategory(regionType, regionName);
        model.addAttribute("infos", categoryService.getCategory(regionType, regionName));
        return commentList;
    }

}
