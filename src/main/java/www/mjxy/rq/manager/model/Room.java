package www.mjxy.rq.manager.model;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by wwhai on 2018/2/19.
 * 房间实体
 * 门牌号
 */
@Entity
public class Room extends BaseEntity implements Serializable{
    private String roomName;
    private String roomNumber;
    private String roomInfo;
    private String location;
    //0 空闲
    //1 审核中
    //2 已经被申请
    private Integer state=0;

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomInfo() {
        return roomInfo;
    }

    public void setRoomInfo(String roomInfo) {
        this.roomInfo = roomInfo;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
