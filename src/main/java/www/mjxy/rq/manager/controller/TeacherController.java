package www.mjxy.rq.manager.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import www.mjxy.rq.manager.constants.SuccessMessageEnum;
import www.mjxy.rq.manager.model.AppUser;
import www.mjxy.rq.manager.model.Room;
import www.mjxy.rq.manager.service.ApplyService;
import www.mjxy.rq.manager.service.RoomService;

/**
 * Created by wwhai on 2018/2/23.
 */
@RestController
@PreAuthorize(value = "hasRole('ROLE_ADMIN') AND hasRole('ROLE_USER')")
@RequestMapping(value = "/teacher")
public class TeacherController {
    @Autowired
    ApplyService applyService;
    @Autowired
    RoomService roomService;

    /**
     * 增加一间教室
     *
     * @return
     */

    @RequestMapping(value = "/postRoom")
    public JSONObject postRoom(@RequestBody JSONObject roomJsonBody) {
        String roomName = roomJsonBody.getString("roomName");
        String roomNumber = roomJsonBody.getString("roomNumber");
        String roomInfo = roomJsonBody.getString("roomInfo");
        String location = roomJsonBody.getString("location");
        if (roomInfo == null || roomName == null || roomNumber == null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("state", 0);
            jsonObject.put("message", "请输入有效的参数");
            return jsonObject;
        } else {
            Room room = new Room();
            room.setRoomInfo(roomInfo);
            room.setRoomName(roomName);
            room.setRoomNumber(roomNumber);
            room.setLocation(location);
            roomService.createRoom(room);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("state", 1);
            jsonObject.put("message", SuccessMessageEnum.OPERATE_SUCCESS.getMessage());

            return jsonObject;

        }
    }

    @RequestMapping(value = "/applies", method = RequestMethod.POST)
    public JSONObject applies(@RequestBody JSONObject pageJsonBody) {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer pageNumber = pageJsonBody.getIntValue("pageNumber");
        Integer size = pageJsonBody.getIntValue("size");

        JSONObject data = applyService.getAllApplies(pageNumber, size);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("state", 1);
        jsonObject.put("data", data);
        jsonObject.put("message", "查询成功!");
        return jsonObject;


    }

    /**
     * 处理请求
     *
     * @param body
     * @return
     */
    @RequestMapping(value = "/processApply", method = RequestMethod.POST)
    public JSONObject processApply(@RequestBody JSONObject body) {
        return applyService.processApply(body);

    }

    /**
     * 删除一间教室
     *
     * @param body
     * @return
     */

    @RequestMapping(value = "/deleteRoom", method = RequestMethod.POST)
    public JSONObject deleteRoom(@RequestBody JSONObject body) {
        JSONObject jsonObject = new JSONObject();
        Long roomId = body.getLongValue("roomId");
        Room room = roomService.getByRoomId(roomId);
        if (room != null) {
            /**
             * 如果已经被申请 或者待处理状态的房间 不能删除
             */
            if (room.getState() == 0) {

                roomService.deleteRoom(room);
                jsonObject.put("state", 1);
                jsonObject.put("message", "删除成功!");
            } else if (room.getState() == 1 || room.getState() == 2) {
                jsonObject.put("state", 0);
                jsonObject.put("message", "正在使用中，不能删除!");
            }

        } else {
            jsonObject.put("state", 0);
            jsonObject.put("message", "教室不存在，删除失败!");
        }
        return jsonObject;

    }


    /**
     * 更新教室
     *
     * @param roomJsonBody
     * @return
     */

    @RequestMapping(value = "/updateRoom", method = RequestMethod.POST)
    public JSONObject updateRoom(@RequestBody JSONObject roomJsonBody) {
        JSONObject jsonObject = new JSONObject();
        Long roomId = roomJsonBody.getLongValue("roomId");
        String roomName = roomJsonBody.getString("roomName");
        String roomNumber = roomJsonBody.getString("roomNumber");
        String roomInfo = roomJsonBody.getString("roomInfo");
        String location = roomJsonBody.getString("location");
        if (roomInfo == null || roomName == null || roomNumber == null) {
            jsonObject.put("state", 0);
            jsonObject.put("message", "请输入有效的参数");
            return jsonObject;
        } else {
            Room room = roomService.getByRoomId(roomId);
            if (room != null) {
                room.setRoomInfo(roomInfo);
                room.setRoomName(roomName);
                room.setRoomNumber(roomNumber);
                room.setLocation(location);
                roomService.save(room);
                jsonObject.put("state", 1);
                jsonObject.put("message", "更新成功!");
                return jsonObject;
            } else {
                jsonObject.put("state", 0);
                jsonObject.put("message", "教室不存在，删除失败!");
                return jsonObject;
            }

        }

    }


}
