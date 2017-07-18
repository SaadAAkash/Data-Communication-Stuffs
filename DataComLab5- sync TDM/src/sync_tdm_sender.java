import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class sync_tdm_sender{

    String input;
    int flag = 1;
    PrintWriter p ;
    char c[];
    FileWriter psig;
    sync_tdm_sender() throws FileNotFoundException, IOException{
        FileReader finstream = new FileReader("lab1_in.txt");
        FileReader fin1stream = new FileReader("in1.txt");
        FileReader fin2stream = new FileReader("in2.txt");
        FileReader fin3stream = new FileReader("in3.txt");
        FileReader fin4stream = new FileReader("in4.txt");
        BufferedReader brin = new BufferedReader(finstream);
        BufferedReader brin1 = new BufferedReader(fin1stream);
        BufferedReader brin2 = new BufferedReader(fin2stream);
        BufferedReader brin3 = new BufferedReader(fin3stream);
        BufferedReader brin4 = new BufferedReader(fin4stream);
        Scanner br = new Scanner(finstream);
        Scanner br1 = new Scanner(fin1stream);
        Scanner br2 = new Scanner(fin2stream);
        Scanner br3 = new Scanner(fin3stream);
        Scanner br4 = new Scanner(fin4stream);

        FileWriter ptemp = new FileWriter("lab1_temp.txt");
         psig = new FileWriter("lab1_sigtemp.txt");
        FileWriter pbintemp = new FileWriter("lab1_bintemp.txt");
        String instring,instring1,instring2,instring3,instring4,tempstring,tempbinstring;
        int countin;
        countin = 0;
         c = new char[1200];
        instring = "";
        instring1 = "";
        instring2 = "";
        instring3 = "";
        instring4 = "";



        char inch,inch1;
        char inch2,inch3,inch4;

        while(true){
            int x = 0;
            int a = 0;
            int b = 0;
            int c = 0;
            int d = 0;
            //x = brin.read();
            a = brin1.read();
            b = brin2.read();
            c = brin3.read();
            d = brin4.read();
            if (a == -1 && b == -1 && c == -1 && d == -1)
                break;
            else{
            if(a != -1){
                inch1 = (char) a;
                instring1 = instring1 + inch1;
            }
            else{
                instring1 = instring1 + ' ';
            }

            if(b != -1){
                inch2 = (char) b;
                instring2 = instring2 + inch2;
            }
            else{
                instring2 = instring2 + ' ';
            }


            if(c != -1){
                inch3 = (char) c;
                instring3 = instring3 + inch3;
            }
            else{
                instring3 = instring3 + ' ';
            }

            if(d != -1){
                inch4 = (char) d;
                instring4 = instring4 + inch4;
            }
            else{
                instring4 = instring4 + ' ';
            }
        }

            countin++;
            if(countin == 125){
                se(instring1,instring2,instring3,instring4);
                tempstring = meth();
                tempbinstring = bintemp();
                pbintemp.write(tempbinstring);
                ptemp.write(tempstring);
                countin = 0;
                instring1 = "";
                instring2 = "";
                instring3 = "";
                instring4 = "";
            }

        }
       /* if(countin != 0){
            se(instring1,instring2,instring3,instring4);
            tempstring = meth();
            tempbinstring = bintemp();
            pbintemp.write(tempbinstring);
            ptemp.write(tempstring);
        }*/

        pbintemp.close();
        ptemp.close();
        psig.close();
    }


    void se(String in1,String in2,String in3,String in4) throws IOException{
        application(in1,in2,in3,in4);


    }

    void application(String in1,String in2,String in3,String in4) throws IOException{
        //in = "A_H" + in;
        in1 = "A_H" + in1;
        in2 = "A_H" + in2;
        in3 = "A_H" + in3;
        in4 = "A_H" + in4;
        presentation(in1,in2,in3,in4);
    }

    void presentation(String in1,String in2,String in3,String in4) throws IOException{
        //in = "P_H" + in;
        in1 = "P_H" + in1;
        in2 = "P_H" + in2;
        in3 = "P_H" + in3;
        in4 = "P_H" + in4;
        session(in1,in2,in3,in4);
    }

        void session(String in1,String in2,String in3,String in4) throws IOException{
        //in = "S_H" + in;
        in1 = "S_H" + in1;
        in2 = "S_H" + in2;
        in3 = "S_H" + in3;
        in4 = "S_H" + in4;
        transport(in1,in2,in3,in4);
    }

    void transport(String in1,String in2,String in3,String in4) throws IOException{
        //in = "T_H" + in;
        in1 = "T_H" + in1;
        in2 = "T_H" + in2;
        in3 = "T_H" + in3;
        in4 = "T_H" + in4;
        network(in1,in2,in3,in4);
    }

    void network(String in1,String in2,String in3,String in4) throws IOException{
       // in = "N_H" + in;
        in1 = "N_H" + in1;
        in2 = "N_H" + in2;
        in3 = "N_H" + in3;
        in4 = "N_H" + in4;
        datalink(in1,in2,in3,in4);
    }

    void datalink(String in1,String in2,String in3,String in4) throws IOException{
        //in = "D_H" + in + "D_T";
        in1 = "D_H" + in1 + "D_T";
        in2 = "D_H" + in2 + "D_T";
        in3 = "D_H" + in3 + "D_T";
        in4 = "D_H" + in4 + "D_T";
        physical(in1,in2,in3,in4);
    }

    void physical(String in1,String in2,String in3,String in4) throws IOException{
        String tempbin,tempsig;
       // in = "PH_H" + in;
        in1 = "PH_H" + in1;
        in2 = "PH_H" + in2;
        in3 = "PH_H" + in3;
        in4 = "PH_H" + in4;

        input =  in1 + in2 + in3 + in4 ;
        if(flag == 1){
            input = "1" + input;
            flag = 0;
        }
        else
        {
            input = "0" + input;
            flag = 1;
        }


        //this.input = in;
        tempbin = AsciiToBinary(input);
        tempsig = blocked_send(tempbin);
        psig.write(tempsig);
    }

    String meth(){
        return input;
    }

    String bintemp(){
        return AsciiToBinary(input);
    }


    public static String AsciiToBinary(String asciiString){
        byte[] bytes = asciiString.getBytes();
        StringBuilder binary = new StringBuilder();
        for(byte i : bytes){
            int bin_val = i;
            for(int j = 0;j < 8; j++){
                if((bin_val & 128) == 0)
                    binary.append(0);
                else
                    binary.append(1);

                bin_val <<= 1;
            }
        }
        return binary.toString();
    }

    String blocked_send(String STR) throws IOException{
        String S,p;
        S = "";
        for(int i = 0;i < STR.length() ; i = i+4)
        {
            p = STR.substring(i, i+4);
            if(p.matches("0000")){
                S = S + "11110";
            }
            else if(p.matches("0001") ){
                S = S + "01001";
            }
            else if(p.matches("0010") ){
                S = S + "10100";
            }
            else if(p.matches("0011") ){
                S = S + "10101";
            }
            else if(p.matches("0100") ){
                S = S + "01010";
            }
            else if(p.matches("0101") ){
                S = S + "01011";
            }
            else if(p.matches("0110") ){
                S = S + "01110";
            }
            else if(p.matches("0111") ){
                S = S + "01111";
            }
            else if(p.matches("1000") ){
                S = S + "10010";
            }
            else if(p.matches("1001") ){
                S = S + "10011";
            }
            else if(p.matches("1010") ){
                S = S + "10110";
            }
            else if(p.matches("1011") ){
                S = S + "10111";
            }
            else if(p.matches("1100") ){
                S = S + "11010";
            }
            else if(p.matches("1101") ){
                S = S + "11011";
            }
            else if(p.matches("1110") ){
                S = S + "11100";
            }
            else if(p.matches("1111") ){
                S = S + "11101";
            }
            else
                S = S + "error";
        }

        return S;
    }

}
