package com.zyf.javabase.lambdad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapTest {
    public static void main(String[] args) {
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        Map<String,String> map = new HashMap<>();
        map.put("time","2021-01-04" ); list.add(map);
        map = new HashMap<>(); map.put("time","2021-01-03" ); list.add(map);
        map = new HashMap<>(); map.put("time","2021-01-05" ); list.add(map);
        map = new HashMap<>(); map.put("time","2021-01-02" ); list.add(map);

        System.out.println(list);

        Collections.sort(list, (Map<String,String> o1, Map<String,String> o2) ->  o1.get("time").compareTo(o2.get("time")));
//        Collections.sort(list, Comparator.comparing((Map<String, String> o) -> o.get("time")));

        System.out.println(list);
    }
}
