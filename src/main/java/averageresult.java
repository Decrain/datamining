import com.google.gson.Gson;
import com.mongodb.*;
/*
*
* ����ÿ���ص��ƽ��ƽ��
*
* 1��������ת��Ϊ���֣����ǵ�����������������Խ��
* 2���Ѷ�Ӧ���ڶ�Ӧʱ�̵����ݴ���������
 *
 *
 * */
public class averageresult {
    public static void main1(String collection){
        Mongo mongo = new Mongo("127.0.0.1", 27017);
        // Mongo mongo = new Mongo("10.109.31.39", 27017);
        // DB db = mongo.getDB("project");
        DB db = mongo.getDB("dataminingresult");

       // DB db1 = mongo.getDB("dataminingresult1");

        DBCollection collectiondata = db.getCollection(collection);
        DBCollection collectionresult = db.getCollection("result");
        System.out.println("Connect to database successfully");
        int a[][] = new int[7][24];//7��*24Сʱ
        int b[][] = new int[7][24];
        int c[] = new int[168];
       String TIME[][] = new String[30][24];

       //�����ʼ��
        for(int i=0;i<7;i++){
            for(int j=0;j<24;j++){
                a[i][j]=0;
                b[i][j]=0;
               // result[i][j]=0;
            }
        }
        DBCursor cur = collectiondata.find();
        int t=0;
        result result1 = new result();

        while (cur.hasNext()) {

            DBObject text = cur.next();
            String week=text.get("week").toString().substring(2,3).toString();
            int numOfPeople = Integer.parseInt(text.get("num_of_people").toString());
            String time=text.get("time_stamp").toString().substring(11,13);
            if(time.substring(0,1).equals("0")){
                time = time.substring(1,2);
            }
            int timenum = Integer.parseInt(time);
            String num = text.get("num_of_people").toString();
            if(week.equals("һ")) t=0;
            else if(week.equals("��")) t=1;
            else if(week.equals("��")) t=2;
            else if(week.equals("��")) t=3;
            else if(week.equals("��")) t=4;
            else if(week.equals("��")) t=5;
            else  t=6;
           //����ͬ����ͬʱ�̵�����ͳ����һ��
            a[t][timenum] += numOfPeople;
            b[t][timenum] +=1;
            TIME[t][timenum]=text.get("week").toString();

        }
        int tttt=0;
        for(int i=0;i<7;i++){
            for(int j=0;j<24;j++){
               c[tttt]=a[i][j]/b[i][j];
                tttt +=1;

            }
        }
        int q=0;
        //ͳ��30��Ĳ��ѽ�����浽���ݿ���
        for(int k=1;k<=30;k++){
            for(int j=0;j<24;j++){
                String kk=Integer.toString(k);
                String jj=Integer.toString(j);
                if(k<10) kk="0"+kk;
                if(j<10) jj="0"+jj;
                String timet = "2017-11-"+kk+" "+jj;
                result1.setLoc_id(collection);
                result1.setTime_stamp(timet);
                String timett=getWeekOfDate.getWeekOfDate(timet).substring(2,3);
                if(timett.equals("һ")) t=0;
                else if(timett.equals("��")) t=1;
                else if(timett.equals("��")) t=2;
                else if(timett.equals("��")) t=3;
                else if(timett.equals("��")) t=4;
                else if(timett.equals("��")) t=5;
                else  t=6;
                System.out.println(t);
                result1.setNum_of_people(a[t][q%24]/b[t][q%24]);
                q++;
                Gson gson = new Gson();
                //ת����json�ַ�������ת����DBObject����
                DBObject dbObject = (DBObject) com.mongodb.util.JSON.parse(gson.toJson(result1));
                //�������ݿ�
                collectionresult.insert(dbObject);
            }
        }

    }



}
