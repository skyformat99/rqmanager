package www.mjxy.rq.manager.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import www.mjxy.rq.manager.dao.RoomRepository;
import www.mjxy.rq.manager.model.Room;

/**
 * Created by wwhai on 2018/2/24.
 */
@Service
public class RoomService {
    @Autowired
    RoomRepository roomRepository;

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

    public JSONObject getAllRooms(Integer pageNumber, Integer size) {
        PageRequest pageRequest = new PageRequest(pageNumber, size, new Sort(Sort.Direction.DESC, "id"));
        Page<Room> applyPage = roomRepository.findAll(pageRequest);

        JSONArray jsonArray = new JSONArray();
        for (Room room : applyPage.getContent()) {
            JSONObject dataObject = new JSONObject();
            dataObject.put("id", room.getId());
            dataObject.put("roomName", room.getRoomName());
            dataObject.put("roomNumber", room.getRoomNumber());
            dataObject.put("location", room.getLocation());
            dataObject.put("roomInfo", room.getRoomInfo());
            dataObject.put("state", room.getState());
            dataObject.put("createTime", room.getCreateTime());

            jsonArray.add(dataObject);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", jsonArray);
        jsonObject.put("page", pageNumber);
        jsonObject.put("isLast", applyPage.isLast());
        jsonObject.put("isFirst", applyPage.isFirst());
        jsonObject.put("totalPage", applyPage.getTotalPages());
        return jsonObject;

    }


}
