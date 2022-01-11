package kr.foodie.service;

import kr.foodie.domain.category.Theme;
import kr.foodie.domain.shopItem.RegionCreateDTO;
import kr.foodie.domain.shopItem.ShopTown;
import kr.foodie.repo.ShopTownRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopTownService {

    private final ShopTownRepository shopTownRepository;


    public ShopTownService(ShopTownRepository shopTownRepository) {
        this.shopTownRepository = shopTownRepository;
    }

    public List<ShopTown> getAll(String type) {
        return shopTownRepository.findByParentNoAndVisiable(type);
    }
    @Transactional
    public void update(List<RegionCreateDTO> list) {
        shopTownRepository.saveAll(list.stream()
                .map(ShopTown::from)
                .collect(Collectors.toList()));
    }
}
