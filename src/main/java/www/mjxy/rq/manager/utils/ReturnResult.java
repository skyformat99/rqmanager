package www.mjxy.rq.manager.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

/**
 * Created by wwhai on 2018/1/10.
 * HTTP返回结果
 */
@Component
public class ReturnResult {
    private Integer state;
    private String message;

    public ReturnResult() {
        super();

    }

    public ReturnResult(Integer state, String message) {
        this.state = state;
        this.message = message;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    /**
     * 具体方法
     */
    public static JSONObject returnResult(Integer state, String message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("state", state);
        jsonObject.put("message", message);
        return jsonObject;
    }

    public static JSONObject returnResultWithData(Integer state, String message, JSONArray data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("state", state);
        jsonObject.put("data", data);
        jsonObject.put("message", message);
        return jsonObject;
    }
}
