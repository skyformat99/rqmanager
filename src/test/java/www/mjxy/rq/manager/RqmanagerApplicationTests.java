package www.mjxy.rq.manager;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RqmanagerApplicationTests {


//    @Test
//    public void getAllRoomsInfo() {
//
//
//    }

    public static void main(String[] args) {
        Date startDate = new Date();
        Date endDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH");
        String applyDateString = "2018-3-12";
        Scanner scanner = new Scanner(System.in);
        String timeType = scanner.nextLine();

        try {
            switch (timeType) {
                //设置时间类型
                case "AM_8_10":
                    startDate = simpleDateFormat.parse(applyDateString + "-" + 8);
                    endDate = simpleDateFormat.parse(applyDateString + "-" + 10);
                    break;
                case "AM_10_12":
                    startDate = simpleDateFormat.parse(applyDateString + "-" + 10);
                    endDate = simpleDateFormat.parse(applyDateString + "-" + 12);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            //格式解析出错
            System.out.println("解析出错");

        }
        System.out.println(startDate);
        System.out.println(endDate);
    }
}
