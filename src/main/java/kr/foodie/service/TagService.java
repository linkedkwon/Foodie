package kr.foodie.service;

import kr.foodie.domain.shopItem.HashTag;
import kr.foodie.repo.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;


    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<HashTag> getHashTags(Integer shopId) {
        return tagRepository.findByShopId(shopId);
    }
    public List<HashTag> getAllHashTags(Integer shopId) {
        return tagRepository.findByShopId(shopId);
    }
}
