package www.mjxy.rq.manager.configure.security.handler;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import www.mjxy.rq.manager.constants.SuccessMessageEnum;
import www.mjxy.rq.manager.dao.DailyLogRepository;
import www.mjxy.rq.manager.model.AppUser;
import www.mjxy.rq.manager.model.DailyLog;
import www.mjxy.rq.manager.service.DailyLogService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wwhai on 2017/11/15.
 * 登录成功处理器
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        JSONObject returnJson = new JSONObject();
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        dailyLogRepository.save(new DailyLog("用户[" + appUser.getTrueName(),
//                "][登录][",
//                "[成功]"));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("avatar", appUser.getAvatar());
        jsonObject.put("email", appUser.getEmail());
        jsonObject.put("phone", appUser.getPhone());
        jsonObject.put("schoolCode", appUser.getSchoolCode());
        jsonObject.put("username", appUser.getUsername());
        jsonObject.put("department", appUser.getDepartment());
        jsonObject.put("trueName", appUser.getTrueName());
        returnJson.put("state", 1);
        returnJson.put("data", jsonObject);
        returnJson.put("message", SuccessMessageEnum.LOGIN_SUCCESS);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().write(returnJson.toJSONString());
        httpServletResponse.getWriter().flush();
    }
}
