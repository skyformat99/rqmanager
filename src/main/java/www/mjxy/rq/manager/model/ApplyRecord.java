package www.mjxy.rq.manager.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * 申请记录
 * Created by @wwhai on 2018/3/17.
 */
@Entity
public class ApplyRecord extends BaseEntity {
    @ManyToOne(targetEntity = AppUser.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private AppUser appUser;
    @ManyToOne(targetEntity = Room.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Room room;
    private String reason;
    private Date applyDate;

    private String applyState;

    public String getApplyState() {
        return applyState;
    }

    public void setApplyState(String applyState) {
        this.applyState = applyState;
    }

    @ManyToOne(targetEntity = Apply.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Apply apply;

    public Apply getApply() {
        return apply;
    }

    public void setApply(Apply apply) {
        this.apply = apply;
    }

    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }
}
