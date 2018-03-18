package www.mjxy.rq.manager.model;

import javax.persistence.Entity;
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
    private Date applyDate;
    private String stateArray;

    public String getStateArray() {
        return stateArray;
    }

    public void setStateArray(String stateArray) {
        this.stateArray = stateArray;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }
//

}
