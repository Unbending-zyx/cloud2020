package com.yuxiao.springcloud;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        List<Map> list=new ArrayList<>();
        Map map1=new HashMap();
        map1.put("name","123");
        map1.put("age","18");
        map1.put("sex","1");
        Map map2=new HashMap();
        map2.put("name","456");
        map2.put("age","20");
        map2.put("sex","2");
        list.add(map1);
        list.add(map2);
        int a=0;
        for (Map map:list){
            map.put("aaa",a);
            a++;
        }
        System.out.println(JSON.toJSONString(list));

        String path="123/456";
        String path1="asdhzjxc";
        System.out.println(path);
        System.out.println(path.replace("/","／"));
        System.out.println(path1.replace("/","／"));
    }
}
