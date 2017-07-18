package lab3nrzi;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class Main  {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        sender x = new sender();
        double time=(1.00/30000000.00);
        time=time/2;
        System.out.println("for single bit time "+time);
        double tot_time=x.bit*time;
        System.out.println("total bits "+x.bit);
        System.out.println("throughput :"+x.bit/tot_time);
        //double datarate = 2*3000* (Math.log(4.00)/Math.log(2));
       // System.out.println("Datarate using Nyquist theorem is : "+datarate);
        receiver y= new receiver();
       // datarate = 3000* ((Math.log(1+y.tmp))/Math.log(2));
       // System.out.println("Datarate using Shannon theorem is : "+datarate);
    }

}
