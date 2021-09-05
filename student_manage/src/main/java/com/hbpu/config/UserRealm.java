package com.hbpu.config;

import com.hbpu.mapper.AdminMapper;
import com.hbpu.pojo.Admininformation;
import com.hbpu.pojo.Studentinformation;
import com.hbpu.pojo.User;
import com.hbpu.service.AdminService;
import com.hbpu.service.ShouQuanService;
import com.hbpu.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;


public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    @Autowired
    AdminService adminService;

    @Autowired
    ShouQuanService shouQuanService;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了授权方法》》》》》》》》》》》");

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();


//        info.addStringPermission("user:add");//return new SimpleAuthenticationInfo(user,user.getPasswd(), "");
        //当前登陆对象
        Subject subject = SecurityUtils.getSubject();
        System.out.println(subject.getSession().getAttribute("user"));
        if(subject.getSession().getAttribute("user").equals("admin")){
            Admininformation currentUser =(Admininformation) subject.getPrincipal();//拿到user对象
            if(currentUser.getUsertype()==0){
                    info.addStringPermission("1");
                    info.addStringPermission("2");
                info.addStringPermission("3");
                info.addStringPermission("4");
                info.addStringPermission("5");
                info.addStringPermission("6");
                info.addStringPermission("7");
                info.addStringPermission("8");
                info.addStringPermission("9");
            }else{
                Integer userName = currentUser.getUserName();
                List<Integer> meunIDlist = this.shouQuanService.queryMeunListByUserName(userName);
                for (Integer mId : meunIDlist) {
                    info.addStringPermission(String.valueOf(mId));
                }
                return info;
            }
        }else {
            Studentinformation currentUser =(Studentinformation) subject.getPrincipal();
            info.addStringPermission("1");
            info.addStringPermission("4");
            info.addStringPermission("7");
            info.addStringPermission("8");
            return info;
        }
        //设置当前用户的权限
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了认证方法》》》》》》》》》》》");
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken userToken=(UsernamePasswordToken)token;
        //数据库获取用户名，密码
        Studentinformation student = this.userService.queryquesBysphone(userToken.getUsername());

        if(student==null){
            Admininformation admin = new Admininformation();
            Admininformation admininformation = this.adminService.queryAdminByPhone(((UsernamePasswordToken) token).getUsername());
            if(admininformation==null){
                return null;
            }else {
                Subject currentSubject = SecurityUtils.getSubject();
                Session session = currentSubject.getSession();
                session.setTimeout(1800000);
                session.setAttribute("loginUser",admininformation.getTname());
                session.setAttribute("user","admin");

                //密码验证
                return new SimpleAuthenticationInfo(admininformation,admininformation.getTpassword(),"");
            }
        }

        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        session.setTimeout(1800000);
        session.setAttribute("loginUser",student.getSname());
        session.setAttribute("user","student");

        //密码验证
        return new SimpleAuthenticationInfo(student,student.getSpassword(),"");
    }
}
