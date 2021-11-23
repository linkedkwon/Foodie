package kr.foodie.domain.shop;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@FieldDefaults(level = PRIVATE)
public class CreateRegionParam {

    List<RegionCreateDTO> regionCreateDTOList;

    @JsonCreator
    public CreateRegionParam(List<RegionCreateDTO> regionCreateDTOList) {
        this.regionCreateDTOList = regionCreateDTOList;
    }
}
