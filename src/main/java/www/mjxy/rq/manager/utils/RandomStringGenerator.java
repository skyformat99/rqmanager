package www.mjxy.rq.manager.utils;

/**
 * Created by wwhai on 2018/1/11.
 * 随机字符串生成器
 */
public class RandomStringGenerator {


    private static String string = "123456789abcdefghijklmnopqrstuvwxyzQWERTYUIOPASDFGHJKLZXCVBNM";

    private static int getRandom(int count) {
        return (int) Math.round(Math.random() * (count));
    }

    public static String getRandomString(int length) {
        StringBuffer sb = new StringBuffer();
        int len = string.length();
        for (int i = 0; i < length; i++) {
            sb.append(string.charAt(getRandom(len - 1)));
        }
        return sb.toString();
    }

//    public static void main(String[] args) {
//        System.out.println(getRandomString(10));
//    }
}
