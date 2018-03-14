package www.mjxy.rq.manager.constants;

/**
 * Created by wwhai on 2018/3/10.
 */
public enum TimeType {
    AM_8_10(1, "1-2节课"),
    AM_10_12(2, "3-4节课"),
    AM_12_14(3, "中午"),
    PM_14_16(4, "5-6节课"),
    PM_16_21(5, "7-8节课"),
    TWO_DAYS(6, "2天"),
    THREE_DAYS(7, "3天");

    private int type;
    private String info;

    private TimeType(int type, String info) {

        this.info = info;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "[Type:" + type + "]" + "Info:[" + info + "]";
    }
}


