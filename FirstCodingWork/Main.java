package FirstCodingWork;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.IndexTokenizer;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import sun.security.util.Length;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        //读取文件
        InputStreamReader origFile = null;
        InputStreamReader copyFile = null;
        try {
            origFile = new InputStreamReader(new FileInputStream(args[0]),"UTF-8");
            copyFile = new InputStreamReader(new FileInputStream(args[1]),"UTF-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        String orig = ToString(origFile);
        String copy = ToString(copyFile);
        Double result = Cmp(orig,copy);
        try {
            FileWriter fw = new FileWriter(args[2],true);
            fw.write(result.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String ToString(InputStreamReader file){
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
        //System.out.println(sb.toString());
        return sb.toString();
    }


    public static double Cmp(String orig,String copy){
        /*对比两个*/
//        IndexTokenizer.segment()
        List<Term> origList = StandardTokenizer.segment(orig);
        List<Term> copyList = StandardTokenizer.segment(copy);
        Map<String,List<Integer>>  VOrig=  new HashMap<String, List<Integer>>();
        Map<String,List<Integer>>  VCopy=  new HashMap<String, List<Integer>>();
        int i = 1;
        for(Term term : origList){

            String TermString = term.toString();
            TermString = TermString.replaceAll("[^\\u4e00-\\u9fa5]","");
            //System.out.print(TermString);
            if(VOrig.get(TermString) == null){
                List<Integer> offset = new ArrayList<Integer>();
                VOrig.put(TermString,offset);
                VOrig.get(TermString).add(i++);
            }else{
                VOrig.get(TermString).add(i++);
            }
        }
        i = 1;
        for(Term term : copyList){

            String TermString = term.toString();
            TermString = TermString.replaceAll("[^\\u4e00-\\u9fa5]","");
            //System.out.print(TermString);
            if(VCopy.get(TermString) == null){
                List<Integer> offset = new ArrayList<Integer>();
                VCopy.put(TermString,offset);
                VCopy.get(TermString).add(i++);
            }else{
                VCopy.get(TermString).add(i++);
            }
        }
        /*for (String id : VOrig.keySet()){
            System.out.println(id  + "------" + VOrig.get(id));
        }*/
        return Cos(VOrig,VCopy);
    }
    public static double Cos(Map<String,List<Integer>> orig,Map<String,List<Integer>> copy){
        double result = 0;
        int wordNum = 0;
        for (String id : copy.keySet()) {
            int ab = 0;
            double B = 0;
            double A = 0;
            if (orig.get(id) != null) {
                List<Integer> origVec = orig.get(id);
                List<Integer> copyVec = copy.get(id);
                int length = 0;
                for (Integer vec : copyVec) {
                    if (length < origVec.size()) {
                        ab += vec * origVec.get(length);
                        A += origVec.get(length) * origVec.get(length);
                        B += vec * vec;
                    }
                    length++;
                }
                result += ab / (Math.sqrt(B) * Math.sqrt(A));
                wordNum++;
            }
        }
        System.out.println(result);
        System.out.println(wordNum);
    return result/wordNum;
    }}
