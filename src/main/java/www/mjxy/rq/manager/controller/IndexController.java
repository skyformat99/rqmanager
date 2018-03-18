package www.mjxy.rq.manager.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import www.mjxy.rq.manager.service.RoomService;

/**
 * Created by wwhai on 2018/2/19.
 */
@RestController
public class IndexController {
    @Autowired
    RoomService roomService;

    @RequestMapping(value = {"/rooms", "/"})
    public JSONObject rooms() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("state", 1);
        jsonObject.put("data", roomService.getAllRooms());
        jsonObject.put("message", "操作成功!");
        return jsonObject;
    }

    @RequestMapping(value = {"/getRoomInfo/{roomId}"})
    public JSONObject getRoomInfo(@PathVariable("roomId") Long roomId) {

        return roomService.getRoomInfo(roomId);
    }
}
