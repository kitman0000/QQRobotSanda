package util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhong on 2020-1-5.
 */
public class DateUtil {
    public static String getDateString(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(new Date(System.currentTimeMillis()));
    }
}
