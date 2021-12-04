package kr.foodie.service;

import kr.foodie.domain.category.Theme;
import kr.foodie.domain.shopItem.EpicureRegion;
import kr.foodie.domain.shopItem.RegionCreateDTO;
import kr.foodie.repo.ThemeRepository;
import kr.foodie.domain.shopItem.RegionCreateDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;


    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public List<Theme> getThemeTags(String type) {
        return themeRepository.findAllTheme(type);
    }

    @Transactional
    public void update(List<RegionCreateDTO> list) {
        themeRepository.saveAll(list.stream()
                .map(Theme::from)
                .collect(Collectors.toList()));
    }
}
