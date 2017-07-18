import java.io.*;
import java.util.*;

public class Receiver {

    public void application(String s) {

        s = s.substring(5);
        try {
            File file = new File("Lab1_output.txt");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(s);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void presentation(String s){

        s = s.substring(5);
        application(s);
    }
    public void session(String s){

        s = s.substring(5);
        presentation(s);
    }
    public void transport(String s){

        s = s.substring(5);
        session(s);
    }
    public void network(String s){

        s = s.substring(5);
        transport(s);
    }
    public void dataLink(String s){

        s = s.substring(5);
        network(s);
    }
    public void physical() throws FileNotFoundException{
        
        File fin = new File("Lab1_temp.txt");
        Scanner S = new Scanner(fin);
        
        String b = "", c, s = null;
        s = S.nextLine();
        System.out.println("*"+s);
        String[] words = s.split(" ");
        for(String word : words){
            System.out.println("*"+word);
            String info = word;
            int charCode = Integer.parseInt(info, 2);
            String str = new Character((char)charCode).toString();
            b+=str;
        }
        s = s.substring(4);
        dataLink(b);
    }
}