import java.io.*;
import java.util.*;

public class Sender {

    public void application() throws FileNotFoundException, UnsupportedEncodingException{

        File fin = new File("Lab1_input.txt");
        Scanner S = new Scanner(fin);
        String b = "", c, s = null;
        while(S.hasNextLine()){

            s = S.nextLine();
            
            b = b + '\r' + '\n' + s;
        }
        b = "A-H" + b;
        presentation(b);
    }

    public void presentation(String s) throws FileNotFoundException, UnsupportedEncodingException{

        s = "P-H " + s;
        session(s);
    }
    public void session(String s) throws FileNotFoundException, UnsupportedEncodingException{

        s = "S-H " + s;
        transport(s);
    }
    public void transport(String s) throws FileNotFoundException, UnsupportedEncodingException{
    
        s = "T-H " + s;
        network(s);
    }
    public void network(String s) throws FileNotFoundException, UnsupportedEncodingException{

        s = "N-H " + s;
        dataLink(s);
    }
    public void dataLink(String s) throws FileNotFoundException, UnsupportedEncodingException{

        s = "D-H " + s;
        physical(s);
    }
    public void physical(String s) throws FileNotFoundException, UnsupportedEncodingException{
        
        s = "Ph-H " + s;
        System.out.println("!"+s);
        String A = "";
        byte[] infoBin = null;
        infoBin = s.getBytes("UTF-8");
        for (byte b : infoBin) {
            A+=Integer.toBinaryString(b);
            A+=" ";
        }
        System.out.println(A + ")(");
        try {
            File file = new File("Lab1_temp.txt");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(A);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}