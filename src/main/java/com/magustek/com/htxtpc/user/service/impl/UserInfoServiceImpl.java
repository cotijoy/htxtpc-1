package com.magustek.com.htxtpc.user.service.impl;

import com.magustek.com.htxtpc.user.bean.CompanyModel;
import com.magustek.com.htxtpc.user.bean.UserAuthSet;
import com.magustek.com.htxtpc.user.bean.UserInfo;
import com.magustek.com.htxtpc.user.service.UserInfoService;
import com.magustek.com.htxtpc.util.ContextUtils;
import com.magustek.com.htxtpc.util.OdataUtils;
import com.magustek.com.htxtpc.util.httpUtil.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {
    private HttpUtils httpUtils;

    public UserInfoServiceImpl(HttpUtils httpUtils) {
        this.httpUtils = httpUtils;
    }


    @Override
    public UserInfo userLogin(String Loginname, String Password, String Aflag) {
        log.info("调用ODATA接口认证用户...");

        Map<String, Object> map = new HashMap<>();
        map.put("Loginname", Loginname.toUpperCase());
        map.put("Password", Password);
        map.put("Aflag", Aflag);

        //调用ODATA接口认证用户，并返回用户信息
        try {
            //获取用户
            Map<String, Object> result = httpUtils.getMapByUrl(OdataUtils.UserLogonSet+"?", map, HttpMethod.POST);
            //获取该用户权限
            List<Map<String, Object>> authResult = httpUtils.getListByUrl(OdataUtils.UserAuthSet + "?&$filter=Loginname eq '" + Loginname.toUpperCase() + "' and Usersource eq 'I'", null, HttpMethod.GET);

            UserInfo userInfo = UserInfo.paresMap(result);
            userInfo.setAuthList(UserAuthSet.paresMap(authResult));
            return userInfo;
        } catch (Exception e) {
            log.error("调用ODATA接口认证用户失败："+e.getMessage());
        }
        return null;
    }

    @Override
    public UserInfo getUserByLoginName(String Loginname) {
        return this.userLogin(Loginname, "", "O001");
    }

    @Override
    public List<CompanyModel> getCompanyModelList(String loginName, String phone) {
        String url = OdataUtils.OrgInforSet+"?&$filter=Loginname eq '" + loginName + "' and Phone eq '"+phone+"'";
        //调用ODATA接口认证用户，并返回用户信息
        try {
            String result = httpUtils.getResultByUrl(url, null, HttpMethod.GET);
            return OdataUtils.getListWithEntity(result, CompanyModel.class);
        } catch (Exception e) {
            log.error("调用ODATA接口认证用户失败："+e.getMessage());
            return null;
        }
    }

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = this.userLogin(username, "" , "O001");
        if(userInfo == null){
            throw new UsernameNotFoundException("用户不存在！");
        }
        HttpSession session = ContextUtils.getSession();
        //将用户所属公司列表存入session，用户登录成功后，可以直接使用。
        if(session!=null){
            session.setAttribute("CompanyList",this.getCompanyModelList(userInfo.getLoginname(), userInfo.getPhone()));
        }
        return new User(userInfo.getLoginname(), userInfo.getPassword().toLowerCase(), new ArrayList<>());
    }
}
