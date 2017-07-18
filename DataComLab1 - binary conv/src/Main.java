import java.util.*;
import java.io.*;

public class Main {
	
    public static void main(String[] args) throws FileNotFoundException {
        
    	String s = null;
        Sender send=new Sender();

        try{
        
        File f=new File("Lab1_in.txt");
        Scanner sc=new Scanner(f);
        while (true)
        {
        	s=sc.nextLine();
            /////////////////
            send.app_lay(s);
            s=send.str;
            
            PrintWriter fw=new PrintWriter("Lab1_temp.txt");
            fw.write(s);
            fw.close();
            
            Receiver rec=new Receiver();

        try{
            File f1=new File("Lab1_temp.txt");
            Scanner sc1=new Scanner(f1);
            s=sc1.nextLine();

            String s1=rec.phy_lay(s);
            s1=rec.dat_lay(s1);
            s1=rec.net_lay(s1);
            s1=rec.trns_lay(s1);
            s1=rec.ses_lay(s1);
            s1=rec.pre_lay(s1);
            s1=rec.app_lay(s1);
            s=s1;

            }catch(FileNotFoundException e){
                e.printStackTrace();

            }
            PrintWriter f2=new PrintWriter("Lab1_out.txt");
            f2.write(s);
            f2.close();
            
            ////////////////////
            if (s == null)
            	break;

        }
            
     }
        catch(FileNotFoundException e){
            e.printStackTrace();

        }
    }
}
    
       /* 
        send.app_lay(s);
        s=send.str;
        
        PrintWriter fw=new PrintWriter("Lab1_temp.txt");
        fw.write(s);
        fw.close();
        
        Receiver rec=new Receiver();

    try{
        File f=new File("Lab1_temp.txt");
        Scanner sc=new Scanner(f);
        s=sc.nextLine();

        String s1=rec.phy_lay(s);
        s1=rec.dat_lay(s1);
        s1=rec.net_lay(s1);
        s1=rec.trns_lay(s1);
        s1=rec.ses_lay(s1);
        s1=rec.pre_lay(s1);
        s1=rec.app_lay(s1);
        s=s1;

        }catch(FileNotFoundException e){
            e.printStackTrace();

        }
        PrintWriter f=new PrintWriter("Lab1_out.txt");
        f.write(s);
        f.close();

    } 
}
*/
