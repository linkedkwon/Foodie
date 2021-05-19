package kr.foodie.service;

import kr.foodie.domain.shop.HashTag;
import kr.foodie.domain.shop.HashTagList;
import kr.foodie.repo.HashTagListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagListService {

    private final HashTagListRepository hashTagListRepository;


    public TagListService(HashTagListRepository hashTagListRepository) {
        this.hashTagListRepository = hashTagListRepository;
    }

    public List<HashTagList> getAllHashTags(String shopType) {
        return hashTagListRepository.findAllList(shopType);
    }
}
