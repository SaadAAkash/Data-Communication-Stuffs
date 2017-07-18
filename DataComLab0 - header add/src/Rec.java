
import java.io.*;

public class Rec{

    public Rec(String filee) throws IOException
    {
        FileInputStream fs= new FileInputStream( filee );
        
        
        BufferedReader r = new BufferedReader(new InputStreamReader(fs));
        FileWriter wr= new FileWriter("lab1_out.txt");
        
        String str;
        
        while(( str =r.readLine())!=null)
        {
            String blabla=phh_lay( str );
            wr.write(blabla+"\r\n");
        }
        wr.flush();
       wr.close();
        r.close();

    }


    String phh_lay(String line)
    {
        String k=line.substring(4);
        return(dh(k));
    }
    String dh(String line)
    {
        int len= line.length();
        String k=line.substring(3,len-3);
        return(nh(k));
    }
    String nh(String line)
    {
        String k=line.substring(3);
        return(th(k));
    }
    String th(String line)
    {
        String k=line.substring(3);
        return(sh(k));
    }
    String sh(String line)
    {
        String k=line.substring(3);
        return(ph(k));
    }
    String ph(String line)
    {
        String k=line.substring(3);
        return(ah(k));
    }
    String ah(String line)
    {
        String k=line.substring(3);
        return(k);
    }

}





