package www.mjxy.rq.manager.configure.security.handler;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import www.mjxy.rq.manager.constants.SuccessMessageEnum;
import www.mjxy.rq.manager.model.AppUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wwhai on 2017/11/15.
 * 登录成功处理器
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        JSONObject returnJson = new JSONObject();
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        /**
         "accountNonExpired": true,
         "accountNonLocked": true,
         "authorities": [
         {
         "authority": "ROLE_USER"
         },
         {
         "authority": "ROLE_ADMIN"
         }
         ],
         "avatar": "/avatar/avatar49.png",
         "createTime": 1519461377000,
         "credentialsNonExpired": true,
         "email": "123456@qq.com",
         "enabled": true,
         "id": 1519461377332,
         "password": "77e900e63428d58ac261f05183586924",
         "phone": "110",
         "schoolCode": "100010010",
         "username": "wwhai"
         */

        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("accountNonExpired", appUser.isAccountNonExpired());
//        jsonObject.put("credentialsNonExpired", appUser.getAvatar());
//        jsonObject.put("accountNonLocked", appUser.isAccountNonLocked());
//        jsonObject.put("enabled", appUser.isEnabled());

        jsonObject.put("authorities", appUser.getAuthorities());
        jsonObject.put("avatar", appUser.getAvatar());

        jsonObject.put("email", appUser.getEmail());
        jsonObject.put("phone", appUser.getPhone());
        jsonObject.put("schoolCode", appUser.getSchoolCode());
        jsonObject.put("username", appUser.getUsername());

        returnJson.put("state", 1);
        returnJson.put("data", jsonObject);
        returnJson.put("message", SuccessMessageEnum.LOGIN_SUCCESS);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().write(returnJson.toJSONString());
        httpServletResponse.getWriter().flush();
    }
}
