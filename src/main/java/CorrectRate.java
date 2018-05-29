import com.mongodb.*;
/*
* @author decrain
* 计算准确率
* 从数据库中读取数据
*
* */
public class CorrectRate {
    public static void main(String[] args){
        Mongo mongo = new Mongo("127.0.0.1", 27017);

        // DB db = mongo.getDB("project");
        DB db1 = mongo.getDB("dataminingresult");
        DB db2 = mongo.getDB("dataminingresult1");
        DBCollection collectiondata = db1.getCollection("result");
        DBCollection collectionresult = db2.getCollection("resultnext");
        System.out.println("Connect to database successfully");
        int size=collectiondata.find().count();
        int size1=collectionresult.find().count();
        if(size1>size){
            size1 = size;
        }
        int data[]= new int[size];
        int result[] = new int[size];
        DBCursor cur = collectiondata.find().limit(size);
        DBCursor cur1 = collectionresult.find().limit(size1);
        System.out.println("数据长度： "+size);
        int i=0,j=0;
        int num1,num2;
        double correctRate=0;
        while (cur.hasNext()&&cur1.hasNext()){
            DBObject text1 = cur.next();
            DBObject text2 = cur1.next();
            String peoplenum1=text1.get("num_of_people").toString();
            String peoplenum2=text2.get("num_of_people").toString();

            String time1 = text1.get("time_stamp").toString();
            String time2 = text2.get("time_stamp").toString();
            if(time1.length()==13&&time2.length()==13){
                time1 = time1.substring(8,13);
                time2 = time2.substring(8,13);
                if(time1.equals(time2)){
                    num1=Integer.parseInt(peoplenum1);
                    num2 = Integer.parseInt(peoplenum2);
                    result[i]=num2;
                    data[i] = num1;
                    i += 1;
                }
            }

            //  if(text1.get("time_stamp"))
          //  substring(0,5)+t.substring(7,13)
           // System.out.println(text);

        }
        for(int k=0;k<size;k++){
            correctRate +=(data[k]-result[k])*(data[k]-result[k]);
        }
        correctRate =Math.sqrt(correctRate/size);
        System.out.println("分数： "+correctRate);
    }
}
