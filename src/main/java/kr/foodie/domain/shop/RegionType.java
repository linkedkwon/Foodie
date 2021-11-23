package kr.foodie.domain.shop;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RegionType {

    BCODE("bcode"),
    SCODE("scode"),
    MCODE("mcode");

    private final String desc;
}
