package www.mjxy.rq.manager.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wwhai on 2017/11/16.
 */
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(generator = "assignedGenerator")
    @GenericGenerator(name = "assignedGenerator", strategy = "assigned")
    private Long id = System.currentTimeMillis();
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createTime;

    public BaseEntity() {
        createTime = new Date();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}