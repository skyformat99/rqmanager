package www.mjxy.rq.manager.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Date;

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



    /*
    申请的时间段
    1 8-12
    2 12-14
    3 14-16
    4 16-21

    产生的数据格式:
    数学系工号为[SXX100000]的小明在2018年3月8日提交了一份申请，申请的内容是：
    想要在时间段[1]{8-12点}用116教室开年级大会，望批准,这个申请书此时在等待审批的状态;
     */

    private Date startTime;
    private Date endTime;


    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

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
