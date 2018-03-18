package www.mjxy.rq.manager.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import www.mjxy.rq.manager.dao.AppUserRepository;
import www.mjxy.rq.manager.dao.ApplyRepository;
import www.mjxy.rq.manager.dao.RoomRepository;
import www.mjxy.rq.manager.model.Apply;
import www.mjxy.rq.manager.model.ApplyRecord;
import www.mjxy.rq.manager.model.Room;

import java.util.List;

/**
 * Created by wwhai on 2018/2/24.
 */
@Service
public class RoomService {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    ApplyRepository applyRepository;

    @Autowired
    ApplyRecordService applyRecordService;

    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    public void deleteRoom(Room room) {
        roomRepository.delete(room);
    }

    public void save(Room room) {
        roomRepository.save(room);
    }

    public Boolean isRoomExists(Long id) {
        return roomRepository.exists(id);
    }

    public Room getByRoomId(Long roomId) {
        return roomRepository.findOne(roomId);
    }

    public JSONObject getAllRooms() {
        List<Room> applyPage = roomRepository.findAll();

        JSONArray jsonArray = new JSONArray();
        for (Room room : applyPage) {
            JSONObject dataObject = new JSONObject();
            dataObject.put("id", room.getId());
            dataObject.put("roomName", room.getRoomName());
            dataObject.put("roomNumber", room.getRoomNumber());
            dataObject.put("location", room.getLocation());
            dataObject.put("roomInfo", room.getRoomInfo());
            dataObject.put("createTime", room.getCreateTime());
            jsonArray.add(dataObject);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", jsonArray);
        return jsonObject;

    }


    /**
     * 获取房间的申请日历
     *
     * @param roomId
     * @return
     */
    public JSONObject getRoomInfo(Long roomId) {
        Room room = roomRepository.findOne(roomId);
        if (room != null) {
            return applyRecordService.getRecordByRoom(room);
        } else {
            return null;
        }
    }

}
