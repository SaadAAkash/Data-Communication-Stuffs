
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class sync_tdm_receiver {
    String out,out1,out2,out3,out4;
    char ch;
    int countoutt = 0;
    int count = 0;
    //static double threshold = 2.9;
    FileWriter pout1,pout2,pout3,pout4;
    sync_tdm_receiver() throws IOException{
        FileWriter pout = new FileWriter("lab1_out.txt");
         pout1 = new FileWriter("out1.txt");
         pout2 = new FileWriter("out2.txt");
         pout3 = new FileWriter("out3.txt");
         pout4 = new FileWriter("out4.txt");
        FileReader fsigstream = new FileReader("lab1_sigtemp.txt");
        //FileReader foutstream = new FileReader("lab1_bintemp.txt");
        BufferedReader brout = new BufferedReader(fsigstream);
        String tempoutstring,outstring;
        int countout;
        char binin;
        outstring = new String();
        countout = 0;

       while(true){
           int x = 0;
           count++;
           x = brout.read();
           if(x == -1)
               break;

           binin = (char) x;
           outstring = outstring + binin;
            countout++;
            if(countout == 6010){
                re(outstring);
                meth_out();
                countout = 0;
                outstring = new String();
            }

        }

       if(countout != 0){
           re(outstring);
           meth_out();
       }
        pout1.close();
        pout2.close();
        pout3.close();
        pout4.close();

    }

     void re(String temp){
        String STR = blocked_re(temp);
        out = "";
        for(int i = 0;i <= STR.length() - 8 ; i= i+8 ){
            ch = (char)Integer.parseInt(STR.substring(i,i+8),2);
            out = out + ch;
        }
        out = out.substring(1);
        //System.out.println(out.length());
        out1 = out.substring(0,150);
        out2 = out.substring(150,300);
        out3 = out.substring(300,450);
        out4 = out.substring(450,600);
        //System.out.println(out1.length());
       // System.out.println(out2.length());
       // System.out.println(out3.length());
       // System.out.println(out4.length());
            physical(out1,out2,out3,out4);
    }

    int getrandom(int min,int max){
        Random rand = new Random();
        return rand.nextInt((max-min) + 1) + min;
    }

    void physical(String out1,String out2,String out3,String out4){
        out1 = out1.substring(4);
        out2 = out2.substring(4);
        out3 = out3.substring(4);
        out4 = out4.substring(4);
        datalink(out1,out2,out3,out4);

    }

     void datalink(String out1,String out2,String out3,String out4){
        out1 = out1.substring(3,(out1.length() - 3));
        out2 = out2.substring(3,(out2.length() - 3));
        out3 = out3.substring(3,(out3.length() - 3));
        out4 = out4.substring(3,(out4.length() - 3));
        network(out1,out2,out3,out4);
    }

    void network(String out1,String out2,String out3,String out4){
        out1 = out1.substring(3);
        out2 = out2.substring(3);
        out3 = out3.substring(3);
        out4 = out4.substring(3);
        transport(out1,out2,out3,out4);
    }

    void transport(String out1,String out2,String out3,String out4){
        out1 = out1.substring(3);
        out2 = out2.substring(3);
        out3 = out3.substring(3);
        out4 = out4.substring(3);
        session(out1,out2,out3,out4);
    }

    void session(String out1,String out2,String out3,String out4){
        out1 = out1.substring(3);
        out2 = out2.substring(3);
        out3 = out3.substring(3);
        out4 = out4.substring(3);
        presentation(out1,out2,out3,out4);
    }

    void presentation(String out1,String out2,String out3,String out4){
        out1 = out1.substring(3);
        out2 = out2.substring(3);
        out3 = out3.substring(3);
        out4 = out4.substring(3);
        application(out1,out2,out3,out4);
    }

    void application(String out1,String out2,String out3,String out4){
        out1 = out1.substring(3);
        out2 = out2.substring(3);
        out3 = out3.substring(3);
        out4 = out4.substring(3);
        this.out1 = out1;
        this.out2 = out2;
        this.out3 = out3;
        this.out4 = out4;
    }

    void meth_out() throws IOException{
        pout1.write(out1);
        pout2.write(out2);
        pout3.write(out3);
        pout4.write(out4);
        
    }

    String blocked_re(String STR){
        String S,p,q;
        S = "";
        for(int i = 0;i < STR.length() ; i = i+5)
        {
            //q = STR.substring(i, i+5);
            p = STR.substring(i, i+5);
            if(p.matches("11110")){

                S = S + "0000";
            }
            else if(p.matches("01001") ){

                S = S + "0001";
            }
            else if(p.matches("10100") ){
                S = S + "0010";
            }
            else if(p.matches("10101") ){

                S = S + "0011" ;
            }
            else if(p.matches("01010") ){
                S = S + "0100";
            }
            else if(p.matches("01011") ){
                S = S + "0101";
            }
            else if(p.matches("01110") ){
                S = S +"0110";
            }
            else if(p.matches("01111")){
                S = S + "0111";
            }
            else if(p.matches("10010") ){
                S = S + "1000";
            }
            else if(p.matches("10011") ){
                S = S + "1001";
            }
            else if(p.matches("10110") ){
                S = S + "1010";
            }
            else if(p.matches("10111") ){
                S = S + "1011";
            }
            else if(p.matches("11010") ){
                S = S + "1100";
            }
            else if(p.matches("11011") ){
                S = S + "1101";
            }
            else if(p.matches("11100") ){
                S = S + "1110";
            }
            else if(p.matches("11101") ){
              
                S = S + "1111";
            }
            else{
                S = S + p.substring(0, 4);
            }

        }
        return S;
    }

    String error(String p){
        int a, x;
        String q;
        char[] r = new char[5];
        r = p.toCharArray();
        x = getrandom(1,100);
        if(x < 5){
            a = getrandom(0,4);
            if(r[x] == '0')
                r[x] = '1';
            else
                r[x] = '0';
        }
        q = String.valueOf(r);
        return q;
        
    }
}