/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Student
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Student
 */
public class OutputHandler2
{
    int data, phy;
   // String mydata;
    String dataToSend;


    static int state=1;


    OutputHandler2(int data, int phy, String s)
    {
        this.data=data;
        this.phy=phy;
        dataToSend=s;
    }
    public String Execute()
    {

       // if (data==1)

        if (phy==1)
        {
            System.out.println(dataToSend+" inside method ");

            dataToSend = NRZL(dataToSend);

            dataToSend= getStringFromBinary(dataToSend);
            return dataToSend;

        }

        else if (phy==2)
        {
            System.out.println(dataToSend+" inside method 2");

            dataToSend = NRZI(dataToSend);

        dataToSend= getStringFromBinary(dataToSend);
        return dataToSend;
        }
        else if (phy==3)
        {
            System.out.println(dataToSend+" inside method 3");

            dataToSend = RZ(dataToSend);

        dataToSend= getStringFromBinary(dataToSend);
        return dataToSend;
        }
        else if (phy==4)
        {
            System.out.println(dataToSend+" inside method 4");

            dataToSend = Manchester(dataToSend);

        dataToSend= getStringFromBinary(dataToSend);
        return dataToSend;
        }
        else if (phy==5)
        {
            System.out.println(dataToSend+" inside method 5");

            dataToSend = DiffManchester(dataToSend);

        dataToSend= getStringFromBinary(dataToSend);
        return dataToSend;
        }

        return "Output handlerExecur";
    }

    //////////////////////////////////////////////////


   private String NRZL(String str)
    { //To change body of generated methods, choose Tools | Templates.
        //System.out.println(str);
        char c[] = str.toCharArray();

        StringBuilder binary = new StringBuilder();
        for (int i=0;i< c.length;i++)
        {
            if (c[i]=='+')
            {
                binary.append("1");
            }
            else
                binary.append("0");

        }
       // System.out.println(binary.toString());
        return binary.toString();
    }

   /////////////////////////////////////////////////


     private String NRZI(String str)
    { //To change body of generated methods, choose Tools | Templates.
        //System.out.println(str);
        char c[] = str.toCharArray();

        StringBuilder binary = new StringBuilder();
        for (int i=0;i< c.length;i++)
        {
            if (c[i]=='+')
            {
                if (state == 1)
                {
                    binary.append("0");
                }
                else
                {
                    binary.append("1");
                    state = 1;
                }
            }
            else
            {
                if (state == 0)
                {
                    binary.append("0");
                }
                else
                {
                    binary.append("1");
                    state = 0;
                }
            }

        }
        System.out.println(binary.toString());
        return binary.toString();
    }

     ////////////////////////

     private String RZ(String str)
    { //To change body of generated methods, choose Tools | Templates.
        //System.out.println(str);
        char c[] = str.toCharArray();

        StringBuilder binary = new StringBuilder();
        for (int i=0;i< c.length;i++)
        {
            if (c[i]=='+')
            {
                binary.append("1");
            }
            else if (c[i]=='-')
                binary.append("0");

        }
       // System.out.println(binary.toString());
        return binary.toString();
    }

     ///////////////////////////////////

     private String Manchester(String str)
    { //To change body of generated methods, choose Tools | Templates.
        //System.out.println(str);
        char c[] = str.toCharArray();

        StringBuilder binary = new StringBuilder();
        for (int i=0;i< c.length;i++)
        {
            if (c[i]=='0')
            {
                binary.append("0");
            }
            else if (c[i]=='1')
                binary.append("1");

            i++;

        }
       // System.out.println(binary.toString());
        return binary.toString();
    }

      ///////////////////////////////////////

      private String DiffManchester(String str)
    { //To change body of generated methods, choose Tools | Templates.
        //System.out.println(str);
        char c[] = str.toCharArray();

        StringBuilder binary = new StringBuilder();
        for (int i=0;i< c.length;i++)
        {
            if (c[i]=='1' && c[i+1]=='0')
            {
                if (state == 1)
                    binary.append("0");
                else
                {
                    state=1;
                    binary.append("1");
                }
            }
            else if (c[i]=='0' && c[i+1]== '1' )
            {
                 if (state == 1)
                 {
                    binary.append("1");
                    state =0;
                 }
                else
                {

                    binary.append("0");
                }
            }
            i++;

        }
       // System.out.println(binary.toString());
        return binary.toString();
    }

   ////////////////////////////////////////////

   public String getStringFromBinary(String s)
   {
       String s2 = "";
        //String s = NRZL(str);
        //String s = DiffManchester(str);
        char nextChar;
        //System.out.println("This" + s);
        for(int i = 0; i <= s.length()-8; i += 8) //this is a little tricky.  we want [0, 7], [9, 16], etc
        {
             nextChar = (char)Integer.parseInt(s.substring(i, i+8), 2);
             s2 += nextChar;

        }

        return s2;
   }
}

