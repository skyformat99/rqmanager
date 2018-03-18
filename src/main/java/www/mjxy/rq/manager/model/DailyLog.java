package www.mjxy.rq.manager.model;

import javax.persistence.Entity;

/**
 * Created by wwhai on 2018/3/18.
 */
@Entity
public class DailyLog extends BaseEntity {
    /**
     * 2018-1-2-12:13 wwhai 登录 成功
     * 2018-1-2-12:13 xxx 申请教室 成功
     */
    private String who;
    private String doWhat;
    private String happend;

    public DailyLog(String who, String doWhat, String happend) {
        this.who = who;
        this.doWhat = doWhat;
        this.happend = happend;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getDoWhat() {
        return doWhat;
    }

    public void setDoWhat(String doWhat) {
        this.doWhat = doWhat;
    }

    public String getHappend() {
        return happend;
    }

    public void setHappend(String happend) {
        this.happend = happend;
    }
}
