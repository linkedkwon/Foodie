package kr.foodie.service;

import kr.foodie.domain.account.Pagination;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PaginationService {

    private static final int interval = 4;

    public List<Pagination> getPagination(int size, int idx, int itemInterval, String path){

        int len = getLen(size, itemInterval);
        int lef = (idx / interval) * interval;
        String url = "/user/"+path+"/";
        List<Pagination> paginations = new ArrayList<>();

        int cnt = 0;
        String classValue = "";
        for(int i=lef; i<len;i++){
            if(cnt++ == interval)
                return paginations;
            if(i == idx)
                classValue = "active";

            paginations.add(Pagination.builder()
                    .idx(i+1)
                    .path(url+i)
                    .classValue(classValue)
                    .build());
            classValue = "";
        }
        return paginations;
    }

    public Map<String, String> getPaginationBtn(int size, int idx, int itemInterval, String path){

        int len = getLen(size, itemInterval);
        idx = idx/interval;
        String url = "/user/"+path+"/";
        Map<String, String> maps = new HashMap<String, String>();

        String prev = idx > 0 ? url+Integer.toString((idx-1) * interval): "javascript:void(0)";
        String next = idx < len/4 ? url+Integer.toString((idx+1) * interval) : "javascript:void(0)";

        maps.put("prev",prev);
        maps.put("next",next);

        return maps;
    }

    public static int getLen(int size, int itemInterval){
        return (size % itemInterval == 0)? size/itemInterval : size/itemInterval+1;
    }
}
