package com.hbpu.service;

import com.hbpu.mapper.AdminMapper;
import com.hbpu.mapper.MeunMapper;
import com.hbpu.mapper.ShouQuanMapper;
import com.hbpu.pojo.Admininformation;
import com.hbpu.pojo.Meun;
import com.hbpu.pojo.Shouquan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    MeunMapper meunMapper;

    @Autowired
    ShouQuanMapper shouQuanMapper;

    public Admininformation queryAdminByPhone(String phone) {
        Admininformation admin = new Admininformation();
        admin.setTphone(phone);
        Admininformation admininformation = this.adminMapper.selectOne(admin);
        return admininformation;
    }

    public int insertAdmin(String tname,String tpassword,String tphone,String temail) {
        Admininformation admin = new Admininformation();
        admin.setTname(tname);
        admin.setTpassword(tpassword);
        admin.setTphone(tphone);
        admin.setTemail(temail);
        admin.setUsertype(1);
        int i = this.adminMapper.insertSelective(admin);
        return i;
    }

    public List<Admininformation> queryAllAdmin() {
        List<Admininformation> adminList = this.adminMapper.selectAll();
        return adminList;
    }

    public Admininformation queryUserBycurrentame(String tname) {
        Admininformation admininformation = new Admininformation();
        admininformation.setTname(tname);
        Admininformation admin= this.adminMapper.selectOne(admininformation);
        return admin;
    }

    public Map queryMapByNumList(List<Shouquan> lists) {
        Map<Integer,String> map=new HashMap<>();
        for (Shouquan sq : lists) {
            Meun meun = this.meunMapper.selectByPrimaryKey(sq.getMeunID());
            map.put(sq.getMeunID(),meun.getAuthorityName());
        }
        return map;
    }


    public Integer insertMeunByMeunId(Integer adminid,Integer meunId) {
        Shouquan shouquan = new Shouquan();
        shouquan.setUserName(adminid);
        shouquan.setMeunID(meunId);
        int i = this.shouQuanMapper.insertSelective(shouquan);
        return i;
    }

    public Integer deleteMeunByAdminId(Integer adminId,Integer meunId) {
        Shouquan shouquan = new Shouquan();
        shouquan.setUserName(adminId);
        shouquan.setMeunID(meunId);
        int delete = this.shouQuanMapper.delete(shouquan);
        return delete;
    }

    public void deleteDbaById(Integer id) {
        Admininformation admininformation = new Admininformation();
        admininformation.setUserName(id);
        this.adminMapper.delete(admininformation);
    }

    public List<Admininformation> queryLikeByName(String tname) {
        Example example = new Example(Admininformation.class);
            example.createCriteria().andLike("tname", "%" + tname + "%");
        return adminMapper.selectByExample(example);
    }
}
