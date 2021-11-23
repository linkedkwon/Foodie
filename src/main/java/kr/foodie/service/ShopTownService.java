package kr.foodie.service;

import kr.foodie.domain.shop.Region;
import kr.foodie.domain.shop.ShopTown;
import kr.foodie.repo.RegionRepository;
import kr.foodie.repo.ShopTownRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopTownService {

    private final ShopTownRepository shopTownRepository;


    public ShopTownService(ShopTownRepository shopTownRepository) {
        this.shopTownRepository = shopTownRepository;
    }

    public List<ShopTown> getAll(String type) {
        return shopTownRepository.findByParentNoAndVisiable(type);
    }
}
