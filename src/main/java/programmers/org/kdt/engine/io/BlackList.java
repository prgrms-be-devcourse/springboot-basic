package programmers.org.kdt.engine.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlackList {
    private static final String fileName = "customer_blacklist.csv";

    private static List<List<String>> ReadCsv (String fileName) {
        //반환용 리스트

        List<List<String>> ret = new ArrayList<List<String>>();
        BufferedReader br = null;

        try{
            br = Files.newBufferedReader(Path.of(fileName));
            //Charset.forName("UTF-8");
            String line = "";

            while((line = br.readLine()) != null){
                //CSV 1행을 저장하는 리스트
                List<String> tmpList = new ArrayList<String>();
                String[] array = line.replace("\uFEFF", "").split(",");
                tmpList = Arrays.asList(array);
                ret.add(tmpList);
            }
        } catch(IOException e){
            e.printStackTrace();
        } finally{
            try{
                if(br != null){
                    br.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return ret;
    }

    public List<List<String>> getBlackList(String fileName) {
        if (fileName == null) fileName = BlackList.fileName;
        return ReadCsv(fileName);
    }

    public List<List<String>> getBlackList() {
        return ReadCsv(fileName);
    }
}
