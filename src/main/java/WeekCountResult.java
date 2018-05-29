import com.csvreader.CsvReader;
import com.google.gson.Gson;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/*
* 统计需要星期的数据统计
* */
public class WeekCountResult {
    public static void main(String[] args){
        String name = null;
       /* for(int i=1;i<=33;i++){
            if(i!=16&&i!=22&&i!=24&&i!=32){
                name = "result"+String.valueOf(i)+".csv";
                countresult(name,String.valueOf(i));
            }

        }*/
        nextResult.main1("result","resultnext");
    }
    public static void countresult(String filename,String loc){


        String filePath = "C:/Users/decrain/Desktop/result/"+filename;
        List<String> list = new LinkedList<String>();
        String time[][] = {{"06","13","20","27"},{"07","14","21","28"},{"01","08","15","22","29"},{"02","09","16","23","30"},{"03","10","17","24"},{"04","11","18","25"},{"05","12","19","26"}};
        String num[]=new String[5];
        String time1=null;
        String time11 = null;
        String numofpeople=null;

        Mongo mongo = new Mongo("127.0.0.1", 27017);
        // Mongo mongo = new Mongo("10.109.31.39", 27017);
        // DB db = mongo.getDB("project");
        DB db = mongo.getDB("dataminingresult1");
        DBCollection collectionresult = db.getCollection("result");
        System.out.println("Connect to database successfully");
        result result=new result();


        try {
            ArrayList<String[]> csvList = new ArrayList<String[]>();
            CsvReader reader = new CsvReader(filePath,',', Charset.forName("GBK"));

            while(reader.readRecord()){
                csvList.add(reader.getValues()); //???ж???????????е?????????list????
            }
            reader.close();


            for(int row=0;row<csvList.size();row++){
                 time1 = csvList.get(row)[1];
               if(time1.length()<2){
                    time1 ="0"+time1;
                }
                if(csvList.get(row).length==7){
                    int week = Integer.parseInt(csvList.get(row)[0]);
                   // System.out.println(week);
                    num[0] = csvList.get(row)[2].substring(1);
                    num[1] = csvList.get(row)[3];
                    num[2] = csvList.get(row)[4];
                    num[3] = csvList.get(row)[5];
                    num[4] = csvList.get(row)[6].substring(0,csvList.get(row)[6].length()-1);
                    System.out.println("hang： "+num[3]);
                   // System.out.println("hang： "+num[0]);
                for(int k=0;k<5;k++){

                         time11 = "2017-11-"+time[week-1][k]+" "+time1;
                         numofpeople = num[k];
                       //  System.out.println("??? "+time11+" "+"?????? "+numofpeople);
                        result.setLoc_id(loc);
                        result.setTime_stamp(time11);
                        result.setNum_of_people(Integer.parseInt(numofpeople));

                        Gson gson = new Gson();
                        DBObject dbObject = (DBObject) com.mongodb.util.JSON.parse(gson.toJson(result));
                        collectionresult.insert(dbObject);

                }


            }else {
                    int week = Integer.parseInt(csvList.get(row)[0]);
                  //  System.out.println(week);
                    num[0] = csvList.get(row)[2].substring(1);

                    num[1] = csvList.get(row)[3];
                    num[2] = csvList.get(row)[4];
                    num[3] = csvList.get(row)[5].substring(0,csvList.get(row)[5].length()-1);
                    System.out.println("hang： "+num[3]);
                    for(int k=0;k<4;k++){
                         time11 = "2017-11-"+time[week-1][k]+" "+time1;
                         numofpeople = num[k];
                        // System.out.println("??? "+time11+" "+"?????? "+numofpeople);

                        result.setLoc_id(loc);
                        result.setTime_stamp(time11);
                        result.setNum_of_people(Integer.parseInt(numofpeople));

                        Gson gson = new Gson();

                        DBObject dbObject = (DBObject) com.mongodb.util.JSON.parse(gson.toJson(result));
                        collectionresult.insert(dbObject);

                    }
                }



            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
