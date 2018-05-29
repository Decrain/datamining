import com.google.gson.Gson;
import com.mongodb.*;

import java.util.LinkedList;
import java.util.List;
/*
* * @author decrain

 * 对原始数据进行统计
* 原始数据来源于数据库
* 输入为地点名字1,2,3，...33
* */
public class CountbasedDatabase {
    public static void count(String count) {

        String ttt = count;
        Mongo mongo = new Mongo("127.0.0.1", 27017);
        // Mongo mongo = new Mongo("10.109.31.39", 27017);
        // DB db = mongo.getDB("project");
        DB db = mongo.getDB("datamining");

        DBCollection collectiondata = db.getCollection("col08"); //获取数据库列表
        DBCollection collectionresulttmp = db.getCollection("resulttmp8");//统计临时用表
        DBCollection collectionresult = db.getCollection("result08");//统计结果存储表
        System.out.println("Connect to database successfully");
        int temp = collectiondata.find().count() - 1;
        DBCursor cur = collectiondata.find().limit(temp);
        DBCursor cur1 = collectionresulttmp.find();

        collectionresulttmp.drop();
        List<String> list = new LinkedList<String>();
        List<String> list1 = new LinkedList<String>();
        int i = 0;
        //创建数据对象，用于数据插入数据库
        result result = new result();
        result result1 = new result();

        while (cur.hasNext()) {
            DBObject text=cur.next();
            String loc = text.get("loc").toString();
            String time = text.get("time").toString();
            if (!list1.contains(time)) { //去统计重复
                list1.add(time);
            }
            if (loc.equals(ttt)) {
                list.add(loc);
                result.setLoc_id(loc);
                result.setTime_stamp(time);
                Gson gson = new Gson();
                //转换成json字符串，再转换成DBObject对象
                DBObject dbObject = (DBObject) com.mongodb.util.JSON.parse(gson.toJson(result));
                collectionresulttmp.insert(dbObject);
            }


        }cur.close();

        // list1.add(cur1.next().get("time").toString());

        int[] a = new int[750];
        //String time1 = cur1.next().get("time").toString();
        while (cur1.hasNext()) {
            String time = cur1.next().get("time_stamp").toString();
            // System.out.println("time: "+time);

            if (list1.contains(time)) {
                a[list1.indexOf(time)] += 1;


            }
        }cur1.close();
        for (String data : list1) {
            result1.setLoc_id(ttt);
            result1.setTime_stamp(data);
            result1.setNum_of_people(a[list1.indexOf(data)]);
            Gson gson = new Gson();
            //转换成json字符串，再转换成DBObject对象
            DBObject dbObject = (DBObject) com.mongodb.util.JSON.parse(gson.toJson(result1));
            //插入数据库
            //
            collectionresult.insert(dbObject);


        }
    }
}
