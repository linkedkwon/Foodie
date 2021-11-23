package kr.foodie.service;

import kr.foodie.domain.category.Theme;
import kr.foodie.domain.shop.HashTag;
import kr.foodie.repo.TagRepository;
import kr.foodie.repo.ThemeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;


    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public List<Theme> getThemeTags(String type) {
        return themeRepository.findAllTheme(type);
    }
}
