package lab3nrzi;


import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.IOException;

public class sender
{
    private String line="";
    private BufferedWriter bw = null;
    private File f = new File("out.txt");
     private File f2 = new File("NRZI.txt");
    FileReader reader = new FileReader("in.txt");
    private Writer fk,fw2;
    String line2="";
    public double bit=0.00;
    Scanner sk = new Scanner(System.in);
    sender() throws FileNotFoundException, IOException
    {

        fk = new PrintWriter(f,"UTF-8");
         fw2 = new PrintWriter(f2,"UTF-8");
        int r;
        int character;
        int count =1;
        while (true)
        {
            character = reader.read();
            if(character!=-1)
            {
                char ch= (char)character;
                line=line+ch;
                count++;
                if(count==125)
                {
                    application();
                    line="";
                    count=1;
                }
            }
            else
            {
                 application();
                fk.close();
                reader.close();
                fw2.close();
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
        return;
    }
    void transport() throws IOException
    {
        line= "T-H"+line;
        network();
        return;
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
        file_write();
        return;

    }
    void file_write() throws IOException
    {
        byte[] bytes = line.getBytes();
        StringBuilder binary = new StringBuilder();
         StringBuilder binary2 = new StringBuilder();
         String sudo="+";
for (byte b : bytes)
        {
            int val = b;
            for (int i = 0; i < 8; i++)
            {

                binary.append((val & 128) == 0 ? 0 : 1);
                 //binary2.append((val & 128) == 0 ? "-" : "+");
                if((val & 128)==0)
                {
                    binary2.append(sudo);
                }
                else
                {
                    if(sudo.equals("+")){
                        binary2.append("-");
                        sudo="-";
                    }
                    else
                    {
                        binary2.append("+");
                        sudo="+";
                    }
                }
                val <<= 1;

            }
            String emp="";
            binary.append(emp);
            bit=bit+8;
        }

        String str = binary.toString();
        line2=binary2.toString();
        fk.write(str);
        fw2.write(line2);
        return;
    }
}

