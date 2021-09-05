package com.hbpu.service;

import com.hbpu.mapper.MeunMapper;
import com.hbpu.mapper.ShouQuanMapper;
import com.hbpu.pojo.Meun;
import com.hbpu.pojo.Shouquan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShouQuanService {

    @Autowired
    ShouQuanMapper shouQuanMapper;

    @Autowired
    MeunMapper meunMapper;

    public List<Shouquan> selectshouquanTable(Integer adminId) {
        Shouquan shouquan = new Shouquan();
        shouquan.setUserName(adminId);
        List<Shouquan> currentUserShouquan = this.shouQuanMapper.select(shouquan);
        return currentUserShouquan;
    }

    public List<Integer> queryHasNotmeunNum(List<Shouquan> lists) {
        List<Integer> allMeunNum = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        List<Integer> res = new ArrayList<>();
        List<Integer> shouquanNum= new ArrayList<>();
        for (Shouquan shouquan : lists) {
            shouquanNum.add(shouquan.getMeunID());
        }
        for (Integer all : allMeunNum) {
            if(shouquanNum.contains(all)==true){
                continue;
        }
            res.add(all);
        }
        return res;
    }

    public Map<Integer,String> queryMapHasNot(List<Integer> hasNotNum) {
        Map<Integer, String> hasNotMeunMap = new HashMap<>();
        for (Integer num : hasNotNum) {
            Meun hasNotMeun = this.meunMapper.selectByPrimaryKey(num);
            hasNotMeunMap.put(num,hasNotMeun.getAuthorityName());
        }
        return hasNotMeunMap;
    }

    public List<Integer> queryMeunListByUserName(Integer username) {
        List<Integer> meunIdList=new ArrayList<>();
        Shouquan shouquan = new Shouquan();
        shouquan.setUserName(username);
        List<Shouquan> select = this.shouQuanMapper.select(shouquan);
        for (Shouquan s : select) {
            meunIdList.add(s.getMeunID());
        }
        return meunIdList;
    }
}
