package FirstCodingWork;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.collection.sequence.SString;

import java.io.*;


public class Test {
    //导入并使用HanLp
    @org.junit.Test
    public void HanLpTest(){
        System.out.println(HanLP.segment("欢迎使用HanLP"));
    }
    //文件读入测试,并将文件转化为字符串:fileReader不好用
    @org.junit.Test
    public void FileReadTest(String args[]){
        InputStreamReader origFile = null;
        InputStreamReader copyFile = null;
        try {
            origFile = new InputStreamReader(new FileInputStream(args[0]),"UTF-8");
            copyFile = new InputStreamReader(new FileInputStream(args[1]),"UTF-8");
        }catch (UnsupportedEncodingException | FileNotFoundException e){
            e.printStackTrace();
        }
        System.out.println(ToString(origFile));

    }

    public String ToString(InputStreamReader file){
        BufferedReader bf = new BufferedReader(file);
        String str = new String();
        StringBuffer sb = new StringBuffer();

        try {
            while((str = bf.readLine()) != null) {
                sb.append(str);
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return sb.toString();
    }
    //文件的输出
   /* @org.junit.Test
    public void OutPut(String str){
        try {
            FileWriter fw = new FileWriter(str,true);
            fw.write(result.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}
