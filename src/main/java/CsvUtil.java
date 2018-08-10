import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import com.csvreader.CsvReader;


/**
 *
 * csvread
 */
public class CsvUtil {
    public static void readCsvFile(String filePath){
        try {
            ArrayList<String[]> csvList = new ArrayList<String[]>();
            CsvReader reader = new CsvReader(filePath,',',Charset.forName("GBK"));
//          reader.readHeaders(); //跳过表头,不跳可以注释掉

            while(reader.readRecord()){
                csvList.add(reader.getValues()); //按行读取，并把每一行的数据添加到list集合
            }
            reader.close();
            System.out.println("读取的行数："+csvList.size());

            for(int row=0;row<csvList.size();row++){
                System.out.println("-----------------");
                //打印每一行的数据
                System.out.print(csvList.get(row)[0]+",");
                System.out.print(csvList.get(row)[1]+",");
                System.out.print(csvList.get(row)[2]+",");
               
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String filePath = "D:\\data\\02.csv";
        readCsvFile(filePath);
    }
}
