package kr.foodie.service;

import kr.foodie.domain.shopItem.MainBoard;
import kr.foodie.repo.MainBoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainBoardService {

    private final MainBoardRepository mainBoardRepository;


    public MainBoardService(MainBoardRepository mainBoardRepository) {
        this.mainBoardRepository = mainBoardRepository;
    }

    public List<MainBoard> getMainBoardInfo(List<String> types) {
        return mainBoardRepository.findByType(types);
    }
}
