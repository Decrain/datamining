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
* 没有week的数据处理结果
* */
public class noWeekCountResult {
    public static void main(String args[]){
        String name = null;
        for(int i=1;i<=33;i++){
            name = "result00"+String.valueOf(i)+".csv";
            System.out.println("文件："+name);
            main1(name,String.valueOf(i));//读取处理过的数据
        }
        nextResult.main1("result0","resultnext0");


    }
    public static void main1(String filename,String loc){
        String filePath = "C:/Users/decrain/Desktop/result00/"+filename;
        List<String> list = new LinkedList<String>();
      //  String time[][] = {{"06","13","20","27"},{"07","14","21","28"},{"01","08","15","22","29"},{"02","09","16","23","30"},{"03","10","17","24"},{"04","11","18","25"},{"05","12","19","26"}};
        String num[]=new String[30];
        String time1=null;
        String time11 = null;
        String day=null;
        String numofpeople=null;

        Mongo mongo = new Mongo("127.0.0.1", 27017);
        // Mongo mongo = new Mongo("10.109.31.39", 27017);
        // DB db = mongo.getDB("project");
        DB db = mongo.getDB("dataminingresult1");

        DBCollection collectionresult = db.getCollection("result0");
        System.out.println("Connect to database successfully");
        result result=new result();

        //读取文件
        try {
            ArrayList<String[]> csvList = new ArrayList<String[]>();
            CsvReader reader = new CsvReader(filePath,',', Charset.forName("GBK"));

            while(reader.readRecord()){
                csvList.add(reader.getValues());
            }
            reader.close();

            System.out.println(csvList.size());

            for(int row=0;row<csvList.size();row++){

                time1 = csvList.get(row)[0];

                if(time1.length()<2){
                    time1 ="0"+time1;
                }

                    num[0] = csvList.get(row)[1].substring(1);

                    num[29] = csvList.get(row)[30].substring(0,csvList.get(row)[30].length()-1);

                    for(int i=1;i<=28;i++){
                        num[i] = csvList.get(row)[i+1];

                    }


                    for(int k=1;k<=30;k++){
                        numofpeople = num[k-1];
                        if(k<10) day="0"+String.valueOf(k);
                        else  day = String.valueOf(k);
                        time11 = "2017-11-"+day+" "+time1;
                     //   System.out.println("time "+time11+" "+"numOfPeople "+numofpeople);
                        result.setLoc_id(loc);
                        result.setTime_stamp(time11);
                        result.setNum_of_people(Integer.parseInt(numofpeople));
                        Gson gson = new Gson();
                        DBObject dbObject = (DBObject) com.mongodb.util.JSON.parse(gson.toJson(result));
                        collectionresult.insert(dbObject);

                    }


                }




        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }


