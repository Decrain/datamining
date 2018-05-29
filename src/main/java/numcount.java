import com.google.gson.Gson;
import com.mongodb.*;
/*
*
* 对原始统计结果数据进行进一步统计包含星期元素
* */
public class numcount {
    public static void main1(String dbname){

        // String test[] ={"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33"};
        //String result[] ={"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33"};

        Mongo mongo = new Mongo("127.0.0.1", 27017);
        // Mongo mongo = new Mongo("10.109.31.39", 27017);
        // DB db = mongo.getDB("project");
        DB db = mongo.getDB("datamining");

        DB db1 = mongo.getDB("dataminingresult");

        DBCollection collectiondata = db.getCollection(dbname);
        System.out.println("Connect to database successfully");
        int temp = collectiondata.find().count() - 1;
        System.out.println("temp: "+temp);
        DBCursor cur = collectiondata.find().limit(temp);
        result result1 = new result();

        while (cur.hasNext()) {
            DBObject text=cur.next();
        //    System.out.println("text: "+text);

            String loc = text.get("loc_id").toString();
            String time = text.get("time_stamp").toString();
            int num = Integer.parseInt(text.get("num_of_people").toString());
           if(time.length()>10){
               result1.setWeek(getWeekOfDate.getWeekOfDate(time.substring(0,10)));
               result1.setLoc_id(loc);
               result1.setTime_stamp(time);
               result1.setNum_of_people(num);
               Gson gson = new Gson();
               //转换成json字符串，再转换成DBObject对象
               DBObject dbObject = (DBObject) com.mongodb.util.JSON.parse(gson.toJson(result1));
               //插入数据库
               db1.getCollection(loc).insert(dbObject);
           }



        }

    }
}
