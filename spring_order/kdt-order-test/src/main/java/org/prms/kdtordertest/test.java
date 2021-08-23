package org.prms.kdtordertest;

import java.io.*;

//public class test {

//    public static void main(String[] args) {
//        // TODO Auto-generated method stub
//
//        FileWriter fw, fw_append; // FileWriter 선언
//
////        try {
////            fw = new FileWriter(".\\java_Text.txt", false); // 파일이 있을경우 덮어쓰기
////            fw.write("Writer 1 : Hello world \r\nWrite Test\r\n");
////            fw.close();
////        } catch (IOException e1) {
////            // TODO Auto-generated catch block
////            e1.printStackTrace();
////        }
//
//        try {
//            fw_append = new FileWriter(".\\java_Text.txt", true); // 파일이 있을경우 이어쓰기
//            fw_append.write("Writer 44444444444444444444444555 : Append Test\r\nGoodbye~");
//            fw_append.close();
//        } catch (IOException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
//
//        return;
//    }
//    }

public class test {
    public static void main(String[] args) throws IOException {
        byte[] b = new byte[1024];
        FileInputStream input = new FileInputStream(".\\java_Text.txt");
        input.read(b);
        System.out.println(new String(b));
        input.close();
    }
}