package www.mjxy.rq.manager.model;

import www.mjxy.rq.manager.constants.ApplyState;
import www.mjxy.rq.manager.constants.TimeType;

import javax.persistence.*;
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
    @Enumerated(value = EnumType.STRING)
    private ApplyState state = ApplyState.PENDING;
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
    @Enumerated(value = EnumType.STRING)
    private TimeType timeType;

    private Date applyDate;//申请时间
    private Date start;
    private Date end;

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public TimeType getTimeType() {
        return timeType;
    }

    public void setTimeType(TimeType timeType) {
        this.timeType = timeType;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
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

    public ApplyState getState() {
        return state;
    }

    public void setState(ApplyState state) {
        this.state = state;
    }
}
