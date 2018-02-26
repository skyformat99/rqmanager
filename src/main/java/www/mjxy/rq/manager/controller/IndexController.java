package www.mjxy.rq.manager.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import www.mjxy.rq.manager.service.RoomService;

/**
 * Created by wwhai on 2018/2/19.
 */
@RestController
public class IndexController {
    @Autowired
    RoomService roomService;

    @RequestMapping(value = {"/rooms", "/"}, method = RequestMethod.POST)
    public JSONObject rooms(@RequestBody JSONObject pageJsonBody) {
        Integer pageNumber = pageJsonBody.getIntValue("pageNumber");
        Integer size = pageJsonBody.getIntValue("size");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("state", 1);
        jsonObject.put("data", roomService.getAllRooms(pageNumber, size));
        jsonObject.put("message", "操作成功!");
        return jsonObject;
    }
}
