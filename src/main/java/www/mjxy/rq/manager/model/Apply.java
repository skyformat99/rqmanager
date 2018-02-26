package www.mjxy.rq.manager.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by wwhai on 2018/2/19.
 * 这个是  申请记录
 * 比如
 * 小明 申请了  融侨一楼的ID为23423；门牌号：100号房间-申请理由：开会，
 * 则对应的记录为
 * ID：drwe4353453d34543t6f
 * userId:小明的Uid
 * roomId：23423
 * reason：开会
 * 时间：xxxx-xxxx-xxxx-xx:xx:xx
 * state:0/1/2-(拒绝申请/审核/通过)
 */
@Entity
public class Apply extends BaseEntity implements Serializable {
    @ManyToOne(targetEntity = AppUser.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private AppUser appUser;
    @ManyToOne(targetEntity = Room.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Room room;
    private String reason;
    //0 1 2
    private int state = 1;

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
