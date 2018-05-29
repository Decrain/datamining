import com.google.gson.Gson;
import com.mongodb.*;
/*
*
* 对数据库进行排序，按照时间顺序生成数据结果
*
* 考虑到数据量不大，用数组即可存储中间结果
* */
public class nextResult {
    public static void main1(String result1,String resultnext){
        Mongo mongo = new Mongo("127.0.0.1", 27017);
        // Mongo mongo = new Mongo("10.109.31.39", 27017);
        // DB db = mongo.getDB("project");
        DB db = mongo.getDB("dataminingresult1");

        DBCollection collectionresult = db.getCollection(result1);
        DBCollection collectionresultnext = db.getCollection(resultnext);
        System.out.println("Connect to database successfully");
        DBCursor cur = collectionresult.find();
        result result=new result();
        DBObject text;
        //String time[][][] = new String[][][]{null};
        String num[][][] = new String[33][30][24];//33*30*24 共23760个数
        String timetmp;
        String day;
        String hour;
        String loc;
        String num_of_people;
        day=null;
        hour=null;
        while (cur.hasNext()){
             text=cur.next();
           //System.out.println(text.toString());
            loc = text.get("loc_id").toString();
            day = text.get("time_stamp").toString().substring(8,10);
            hour = text.get("time_stamp").toString().substring(11,13);
          //  System.out.println(loc+" "+day+" "+hour);
            num_of_people = text.get("num_of_people").toString();
            if(Integer.parseInt(num_of_people)<0) num_of_people = "25";
            if(day.substring(0,1).equals("0")) day = day.substring(1,2);
            if(hour.substring(0,1).equals("0")) hour = hour.substring(1,2);

            num[Integer.parseInt(loc)-1][Integer.parseInt(day)-1][Integer.parseInt(hour)] = num_of_people;
            //System.out.println();
        }
        timetmp = null;
        day=null;
        hour=null;
        // System.out.println(num.length);


            for (int j=0;j<30;j++){
                for (int k=0;k<=23;k++){
                    for(int i=0;i<33;i++){

                   // System.out.println(num[i][j][k]);

                            loc = String.valueOf(i+1);
                            day = String.valueOf(j+1);
                            if(day.length()==1) day = "0"+day;
                            hour = String.valueOf(k);
                            if(hour.length()==1) hour = "0"+hour;
                            timetmp = "2017-11-"+day+" "+hour;
                            result.setLoc_id(loc);
                            result.setTime_stamp(timetmp);
                            result.setNum_of_people(Integer.parseInt(num[i][j][k]));
                            Gson gson = new Gson();
                            //转换成json字符串，再转换成DBObject对象
                            DBObject dbObject = (DBObject) com.mongodb.util.JSON.parse(gson.toJson(result));
                            collectionresultnext.insert(dbObject);


                }
            }
        }
    }
}

