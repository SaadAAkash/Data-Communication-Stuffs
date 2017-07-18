
package datalab3rz;

import java.io.*;
import java.net.*;
import java.util.*;
/**
 *
 * @author Student
 */
public class sender
{
    private String line="";
    String signLine ="";

    //private BufferedWriter bw = null;

    private File f = new File("out.txt");
     private File f2 = new File("NRZI.txt");

    FileReader reader = new FileReader("in.txt");

    private Writer fout , fnrzi;  //fk,fw2

    public double total_bit=0.00;

    //Scanner sk = new Scanner(System.in);

    sender() throws FileNotFoundException, IOException
    {

        fout = new PrintWriter(f,"UTF-8");
        fnrzi = new PrintWriter(f2,"UTF-8");

        int r;
        int character;
        int count = 1;

        while (true)
        {
            character = reader.read();
            if(character!=-1)  //if not eof
            {
                char ch= (char)character;
                line= line + ch;
                count++;
                if(count==125)  //fro every 1000 bit, 1 packet
                {
                    application();
                    line="";
                    count=1;
                }
            }
            else  // if eof
            {
                application();
                fout.close();
                reader.close();
                fnrzi.close();
                break;
            }
        }
    }
    void application() throws IOException
    {
        line= "A-H"+line;
        Presentation();
    }
    void Presentation() throws IOException
    {
        line= "P-H"+line;
        session();
    }
    void  session() throws IOException
    {
        line= "S-H"+line;
        transport();
        return;   //??
    }
    void transport() throws IOException
    {
        line= "T-H"+line;
        network();
        return;  //??
    }
    void network() throws IOException
    {
        line= "N-H"+line;
        Data();
        return;
    }
    void Data() throws IOException
    {
        line= "D-H"+line;
        physical();
        return;
    }
    void physical() throws IOException
    {
        line= "Ph-H"+line;
        sign_write();
        return;

    }
    void sign_write() throws IOException
    {
        byte[] bytes = line.getBytes();
        StringBuilder binary = new StringBuilder();
         StringBuilder signstr = new StringBuilder();
         String signneg = "-";
         String signpos = "+";

         boolean state = true;  //phase change er flag

         String zero = "p";

        for (byte b : bytes)  //loop sheshe bit +=8 hbe
        {
            int val = b;
            for (int i = 0; i < 8; i++)  // for 1 charac = 8 bit
            {

                binary.append((val & 128) == 0 ? 0 : 1);
                 //signstr2.append((val & 128) == 0 ? "-" : "+");
                if((val & 128)==0)  //if next bit is zero, append + then -
                {
                   ///toggle/////////
                    if(state = true)
                        state = false;
                    else
                        state = true;
                    /////toggle///////////
                    
                    if (state = true)
                    {
                        signstr.append( signpos );
                        signstr.append( signneg );
                    }
                    else
                    {
                        signstr.append( signneg );
                        signstr.append( signpos );

                    }

                }
                else  //else if next bit is one, append - then +
                {

                    if (state = true)
                    {
                        signstr.append( signpos );
                        signstr.append( signneg );
                    }
                    else
                    {
                        signstr.append( signneg );
                        signstr.append( signpos );

                    }

                    /* if ( sign.equals("+"))
                    {
                        signstr.append("-");
                        sign = "-";   //alternate signs
                    }
                    else  //sign - hole oppposite
                    {
                        signstr.append("+");
                        sign = "+";  //alternate signs
                    } */
                }
                val <<= 1;  // going to next bit till every charac OR 8bit

            }
            String endd = "";
            binary.append( endd );
            total_bit=total_bit+8;
        }

        String str = binary.toString();
        signLine= signstr.toString();
        fout.write(str);
        fnrzi.write( signLine );
        return;
    }

}
