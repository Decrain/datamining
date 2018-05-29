import com.csvreader.CsvReader;
import com.google.gson.Gson;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

import java.nio.charset.Charset;
import java.util.ArrayList;

public class test {
    public static void main(String[] args){

        Mongo mongo = new Mongo("127.0.0.1", 27017);
        // Mongo mongo = new Mongo("10.109.31.39", 27017);
        // DB db = mongo.getDB("project");
        DB db = mongo.getDB("dataminingresult1");

        DBCollection collectionresult = db.getCollection("result");
        String filePath="C:\\Users\\decrain\\Desktop\\result\\result24.csv";

        result result=new result();
        try {
            ArrayList<String[]> csvList = new ArrayList<String[]>();
            CsvReader reader = new CsvReader(filePath,',', Charset.forName("GBK"));

            while(reader.readRecord()){
                csvList.add(reader.getValues()); //???ж???????????е?????????list????
            }
            reader.close();

            System.out.println(csvList.size());
            for(int row=0;row<csvList.size();row++){


               String loc = csvList.get(row)[0];
                String time11 = csvList.get(row)[1];
                String numofpeople = csvList.get(row)[2];


                        result.setLoc_id(loc);
                        result.setTime_stamp(time11);
                        result.setNum_of_people(Integer.parseInt(numofpeople));

                        Gson gson = new Gson();
                        DBObject dbObject = (DBObject) com.mongodb.util.JSON.parse(gson.toJson(result));
                        collectionresult.insert(dbObject);




                }






        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
