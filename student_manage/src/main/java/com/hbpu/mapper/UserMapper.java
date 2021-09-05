package com.hbpu.mapper;

import com.hbpu.pojo.Studentinformation;
import com.hbpu.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<Studentinformation> {
    @Update("UPDATE tb_studentinformation SET spassword = #{spassword} WHERE sphone = #{sphone}")
    public int pwdupdate(@Param("spassword") String spassword, @Param("sphone") String sphone);
}
