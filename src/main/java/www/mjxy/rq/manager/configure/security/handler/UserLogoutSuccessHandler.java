package www.mjxy.rq.manager.configure.security.handler;

import com.alibaba.fastjson.JSONObject;
 import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import www.mjxy.rq.manager.constants.SuccessMessageEnum;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wwhai on 2017/11/15.
 * 注销处理器
 */
public class UserLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        JSONObject returnJson = new JSONObject();
        returnJson.put("state", 1);
        returnJson.put("message", SuccessMessageEnum.LOG_OUT_SUCCESS);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().write(returnJson.toJSONString());
        httpServletResponse.getWriter().flush();

    }
}
