package www.mjxy.rq.manager.configure.security.handler;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import www.mjxy.rq.manager.constants.FailureMessageEnum;
import www.mjxy.rq.manager.model.AppUser;
import www.mjxy.rq.manager.model.DailyLog;
import www.mjxy.rq.manager.service.DailyLogService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wwhai on 2017/11/15.
 * 登录失败处理器
 */
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Autowired
    DailyLogService dailyLogService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        JSONObject returnJson = new JSONObject();

        if (e instanceof UsernameNotFoundException) {
            returnJson.put("state", 0);
            returnJson.put("message", FailureMessageEnum.USER_NOT_EXIST);
        } else if (e instanceof DisabledException) {
            returnJson.put("state", 0);
            returnJson.put("message", FailureMessageEnum.USER_DISABLED);

        } else if (e instanceof LockedException) {
            returnJson.put("state", 0);
            returnJson.put("message", FailureMessageEnum.USER_LOCKED);
        } else if (e instanceof NonceExpiredException) {
            returnJson.put("state", 0);
            returnJson.put("message", FailureMessageEnum.USER_EXPIRED);
        } else if (e instanceof InternalAuthenticationServiceException) {
            returnJson.put("state", 0);
            returnJson.put("message", "用户不存在");
        } else {
            returnJson.put("state", 0);
            returnJson.put("message", "登录失败");
        }
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        dailyLogService.save(new DailyLog("用户[" + appUser.getUsername(),
                "][登录][",

                "[失败]"));
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().write(returnJson.toJSONString());
        httpServletResponse.getWriter().flush();
    }
}
