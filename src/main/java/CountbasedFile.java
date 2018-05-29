import com.csvreader.CsvReader;
import com.google.gson.Gson;
import com.mongodb.*;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/*
* * @author decrain

 * 对原始数据统计
* 原始数据来源于CSV文件
* 由于系统内存有限，单个文件过大，这种方式不太好用
* 还是需要把原始数据存储到数据库中，用数据库的机制来解决内存问题
* 需要csvread jar包
* */
public class CountbasedFile {
    public static void count(String count) {
        // String test[] ={"1","2","3","4","5","6","7","8","9","10","11","12,'13','14','15','16','17','18','19','20','21','22','23','24','25','26','27','28','29','30','31','32','33'};

        String ttt = count;
        String filePath = "D:\\data\\08.csv";
        Mongo mongo = new Mongo("127.0.0.1", 27017);
        // Mongo mongo = new Mongo("10.109.31.39", 27017);
        // DB db = mongo.getDB("project");
        DB db = mongo.getDB("datamining");

        DBCollection collectionresulttmp = db.getCollection("resulttmp6");
        DBCollection collectionresult = db.getCollection("result06");
        System.out.println("Connect to database successfully");

        DBCursor cur1 = collectionresulttmp.find();

        collectionresulttmp.drop();
        List<String> list = new LinkedList<String>();
        List<String> list1 = new LinkedList<String>();
        int i = 0;

        result result = new result();
        result result1 = new result();

        try {
            ArrayList<String[]> csvList = new ArrayList<String[]>();
            CsvReader reader = new CsvReader(filePath,',', Charset.forName("GBK"));
//          reader.readHeaders(); //跳过表头,不跳可以注释掉

            while(reader.readRecord()){
                csvList.add(reader.getValues()); //按行读取，并把每一行的数据添加到list集合
            }
            reader.close();
           System.out.println("hangshu:"+csvList.size());

            for(int row=1;row<csvList.size();row++){
               // System.out.println("-----------------");
                //打印每一行的数据
               /* System.out.print(csvList.get(row)[0]+",");
                System.out.print(csvList.get(row)[1]+",");
                System.out.print(csvList.get(row)[2]+",");*/
                String loc = csvList.get(row)[2].toString();
                String time = csvList.get(row)[1].toString();
                if (!list1.contains(time)) {
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
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // list1.add(cur1.next().get("time").toString());

        int[] a = new int[750];
        //String time1 = cur1.next().get("time").toString();
        while (cur1.hasNext()) {
            String time = cur1.next().get("time_stamp").toString();
            // System.out.println("time: "+time);

            if (list1.contains(time)) {
                a[list1.indexOf(time)] += 1;


            }
        }
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
