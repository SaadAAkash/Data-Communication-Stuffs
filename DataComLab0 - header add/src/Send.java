
import java.io.*;

public class Send {
   
    public Send(String filee ) throws IOException
    {
        FileInputStream fs= new FileInputStream( filee );
        
        
        BufferedReader r = new BufferedReader(new InputStreamReader(fs));
        FileWriter wr= new FileWriter("lab1_temp.txt");
        
        String str;
        
        while(( str = r.readLine())!= null )
        {
            String blabla=ph_lay( str );
            wr.write(blabla+"\r\n");
            
        }
        wr.flush();
        wr.close();
        r.close();
    }
    
        
    String ph_lay(String s)
    {
        return("PH-H"+dh_lay(s));
    }
    String dh_lay(String s)
    {
        return("D-H"+nh_lay(s)+"D-T");
    }
    String nh_lay(String s)
    {
        return("N-H"+th_lay(s));
    }
    String th_lay(String s)
    {
        return("T-H"+sh_lay(s));
    }
    String sh_lay(String s)
    {
        return("S-H"+ph_lay1(s));
    }
    String ph_lay1(String s)
    {
        return("P-H"+ah_lay(s));
    }
    String ah_lay(String s)
    {
        return("A-H"+s);
    }
    
}
