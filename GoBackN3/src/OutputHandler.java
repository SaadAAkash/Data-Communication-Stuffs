
/**
 *
 * @author Tiash
 */
public class OutputHandler
{
    String datalink, physical;
   // String mydata;
    String dataToSend;


    static int state=1;


    OutputHandler(String data, String phy, String s)
    {
        this.datalink=data;
        this.physical=phy;
        dataToSend=s;
    }
    public String Execute()
    {

       // if (data==1)
           System.out.println(physical);
        if (physical.equals("NRZ-L"))
        {
            System.out.println("inside L");
            dataToSend= NRZL(dataToSend);
        }
        else if (physical.equals("NRZ-I"))
        {
            
            System.out.println("inside I");
            dataToSend= NRZI(dataToSend);
        }
        else if (physical.equals("RZ"))
        {
            
            System.out.println("inside RZ");
            dataToSend= RZ(dataToSend);
        }
        else if (physical.equals("Manchester"))
        {
            
            System.out.println("inside amn");
            dataToSend= Manchester(dataToSend);
        }
        else
        {
            System.out.println("inside diff");
            dataToSend= DiffManchester(dataToSend);
        }
        
        
            System.out.println (" done" + dataToSend.length());  
        
        /////////////////// physical is done
        
         if (datalink.equals("4b5b"))
        {
            System.out.println("inside 4b");
            dataToSend = do4B5B(dataToSend);
            System.out.println ("4b5b done" + dataToSend.length());        
        }
        
         dataToSend = getStringFromBinary(dataToSend);
        System.out.println ("returned from getStrng");        
         
         dataToSend = dataToSend.substring(8, 158);        
        
         System.out.println(dataToSend.length());
        return dataToSend;
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

    String do4B5B(String STR)
    {
        String S,p,q;
        S = "";
        for(int i = 0;i < STR.length() ; i = i+5)
        {
            System.out.println ("in do4B5B loop");
            q = STR.substring(i, i+5);
            if( q.matches("11110")){
                 ;
                S = S + "0000";
            }
            else if( q.matches("01001") ){
                 ;
                S = S + "0001";
            }
            else if( q.matches("10100") ){
                 ;
                S = S + "0010";
            }
            else if( q.matches("10101") ){
                 ;
                S = S + "0011" ;
            }
            else if( q.matches("01010") ){
                 ;
                S = S + "0100";
            }
            else if( q.matches("01011") ){
                 ;
                S = S + "0101";
            }
            else if( q.matches("01110") ){
                 ;
                S = S +"0110";
            }
            else if( q.matches("01111")){
                 ;
                S = S + "0111";
            }
            else if( q.matches("10010") ){
                 ;
                S = S + "1000";
            }
            else if( q.matches("10011") ){
                 ;
                S = S + "1001";
            }
            else if( q.matches("10110") ){
                 ;
                S = S + "1010";
            }
            else if( q.matches("10111") ){
                 ;
                S = S + "1011";
            }
            else if( q.matches("11010") ){
                 ;
                S = S + "1100";
            }
            else if( q.matches("11011") ){
                 ;
                S = S + "1101";
            }
            else if( q.matches("11100") ){
                 ;
                S = S + "1110";
            }
            else if( q.matches("11101") ){
                 ;
                S = S + "1111";
            }
            else{
                S = S + q.substring(0, 4);
            }

        }
        return S;
    }
}
