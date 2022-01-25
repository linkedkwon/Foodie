package kr.foodie.common.utils;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ImageStrUtils {

    public static List<String> strToList(String imgStr) {
        if (StringUtils.isBlank(imgStr)) {
            return Collections.emptyList();
        }

        if (onlyCommaString(imgStr)) {
            return new ArrayList<>(List.of(
                    imgStr.replace("[", "").replace("]", "").split(",")
            ));
        }

        try {
            return new Gson().fromJson(imgStr, new ParameterizedTypeReference<List<String>>() {
            }.getType());
        } catch (JsonParseException e) {
            log.error("json parse error! {}", imgStr);
        }
        return Collections.emptyList();
    }

    private static boolean onlyCommaString(String imgStr) {
        return (!imgStr.contains("[")||!imgStr.contains("]"));
    }

    public static String listToStr(List<String> images) {
        return new Gson().toJson(images);
    }
}
