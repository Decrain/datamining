import java.text.SimpleDateFormat;
import java.util.Date;
/*
* * @author decrain
   获取时间对应的星期
 * */
public class getWeekOfDate {
    public static String getWeekOfDate(String date1) {
        try{
            SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
            Date date=sdf.parse(date1);
            SimpleDateFormat format = new SimpleDateFormat("EEEE");
            return format.format(date);
        }catch(Exception ex){
            System.out.println("TimeUtil getFullDateWeekTime Error:"+ex.getMessage());
            return "";
        }
    }
}
