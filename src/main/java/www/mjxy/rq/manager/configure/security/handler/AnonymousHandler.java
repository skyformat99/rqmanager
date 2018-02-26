package www.mjxy.rq.manager.configure.security.handler;

import com.alibaba.fastjson.JSONObject;
 import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import www.mjxy.rq.manager.constants.FailureMessageEnum;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wwhai on 2017/11/15.
 * 匿名访问处理器
 */
public class AnonymousHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        JSONObject returnJson = new JSONObject();
        returnJson.put("state", 0);
        returnJson.put("message", FailureMessageEnum.NOT_AUTH);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().write(returnJson.toJSONString());
        httpServletResponse.getWriter().flush();
    }
}
