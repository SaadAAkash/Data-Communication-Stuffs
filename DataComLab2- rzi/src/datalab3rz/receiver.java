
package datalab3rz;

import java.io.*;
import java.net.*;
import java.util.*;
/**
 *
 * @author Student
 */
public class receiver
{
    private String line="";
    String emp="";
   // private BufferedWriter bw = null;
    private File ftemp = new File("temp.txt");
    private File fout1 = new File("lab1_out.txt");

    FileReader reader = new FileReader("NRZI.txt");

    private Writer fw,fw1;

    double mini;
    public double tmp;
////////////////////////lab 02 range/////////////////
    int n1= 12000-10000+1;
    int n2= 10000-9000+1;
////////////////////////
    public double th;
    int drop=0;

    receiver() throws FileNotFoundException, IOException
    {
        fw = new PrintWriter(fout1,"UTF-8");
        fw1 = new FileWriter(ftemp);
        int character;
        double o=12000.00/9000;
        th = 10 * Math.log(o);
        int count =1;
        char sign = '+';
        while (true)
        {
            character = reader.read();
            if(character!=-1)
            {
                char ch= (char)character;
                if(ch== sign )
                    ch='0';
                else
                {
                    sign = ch;
                    ch='1';

                }
                line=line+ch;
                count++;
                if(count==1001)
                {
                    String s2 = "";
                    int nextChar;

                    for(int i = 0; i <= line.length()-8; i += 8)
                    {
                        nextChar = (char)Integer.parseInt(line.substring(i, i+8), 2);
                        s2 += (char)nextChar;
                    }
                    ////////////lab 02/////////////////////
                    Random rn = new Random();
                   double p1 = rn.nextInt(n1) + 1;
                    double p2 = rn.nextInt(n2) + 1;
                    tmp= 10* Math.log(p1/p2);
                   /* if(tmp<th)
                    {
                        fw.write("this packet can't be transmitted\r\n");
                      drop++;
                        line="";
                        count=1;
                        continue;
                    }*/
                    line = s2;
                    fw1.write(line);
                //////////lab 02/////////////////
                     physical();
                    line="";
                    count=1;
                }
            }
            else  /// if eof
            {
                String s2 = "";
                    int nextChar;

                    for(int i = 0; i <= line.length()-8; i += 8)
                    {
                        nextChar = (char)Integer.parseInt(line.substring(i, i+8), 2);
                        s2 += (char)nextChar;
                    }
                    ///////////lab 02/////////////
                    Random rn = new Random();
                    int p1 = rn.nextInt(n1) + 1;
                    int p2 = rn.nextInt(n2) + 1;
                    tmp= 10* Math.log(p1/p2);
                   /* if(tmp<th)
                    {
                        drop++;
                        fw.write("this packet can't be transmitted\r\n");

                        line="";
                        count=1;
                        continue;
                    }*/
                    line = s2;
                    fw1.write(line);
                physical();
                fw.close();
                fw1.close();
                reader.close();
                break;
            }

        }
    }

    void physical() throws IOException
    {

        line= line.replaceFirst("Ph-H", emp);
        Data();

    }
    void Data() throws IOException
    {
        line= line.replaceFirst("D-H", emp);
        network();
    }
    void network() throws IOException
    {
        line= line.replaceFirst("N-H", emp);
        transport();
    }
    void transport() throws IOException
    {
        line= line.replaceFirst("T-H", emp);
        session();
    }
    void  session() throws IOException
    {
        line= line.replaceFirst("S-H", emp);
        Presentation();
    }
    void Presentation() throws IOException
    {
        line= line.replaceFirst("P-H", emp);
        application();
    }
    void application() throws IOException
    {
        line= line.replaceFirst("A-H", emp);
        fw.write(line);
    }


}


