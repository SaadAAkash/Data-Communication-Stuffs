
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Student
 */
public class InPutHandler 
{
    String datalink, physical;
    int state=1;
    String mydata;
    String dataToSend;
    InPutHandler(String data, String phy, String s)
    {
        this.datalink=data;
        this.physical=phy;
        dataToSend=s;
    }
    public String Execute()
            
    {
        
       // System.out.println("befor adding head: " + dataToSend.length());
        dataToSend = addHeaders();
        
        System.out.println("after adding head: " + dataToSend.length());
        dataToSend= makeBinary();
        
        System.out.println("after binary: " + dataToSend.length());
        /////////////////////////// for datalink
        if (datalink.equals("4b5b"))
        {
            System.out.println("inside 4b");
            dataToSend = do4B5B(dataToSend);
        }
        
        
        
        System.out.println("after adding 4b5b: " + dataToSend.length());
        
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
        
        return dataToSend;
    }
    public String addHeaders()
    {
        dataToSend = "AH"+"TH"+"NH"+"DH"+dataToSend+"DL";
        return dataToSend;
    }
    public String makeBinary()
    {
      
          byte[] bytes = dataToSend.getBytes();  
          StringBuilder binary = new StringBuilder();  
          for (byte b : bytes)  
          {  
             int val = b;  
             for (int i = 0; i < 8; i++)  
             {  
                binary.append((val & 128) == 0 ? 0 : 1);  
                val <<= 1;  
             }  
            // binary.append(' ');  
          }  
          //writer.print(binary);
          
          //System.out.println(binary.toString());
          //String encoded = NRZL(binary.toString());
        return binary.toString(); 
    }
    
    
    private String NRZI(String str) 
    { //To change body of generated methods, choose Tools | Templates.
        char c[] = str.toCharArray();
        //System.out.println(str);
        StringBuilder binary = new StringBuilder(); 
        for (int i=0;i< c.length;i++)
        {
            if (c[i]=='0')
            {
                if (state==0)
                    binary.append("-");
                else
                    binary.append("+");
            }
            else
            {
                 if (state==0)
                 {
                    binary.append("+");
                    state =1;
                 }
                else
                 {
                    binary.append("-");
                    state=0;
                 }
            }
                
        }
        return binary.toString();
    }
      
    public String NRZL(String str) 
    { //To change body of generated methods, choose Tools | Templates.
        char c[] = str.toCharArray();
        System.out.println(str);
        StringBuilder binary = new StringBuilder(); 
        for (int i=0;i< c.length;i++)
        {
            if (c[i]=='0')
                binary.append("-");
            else
                binary.append("+");
                
        }
        return binary.toString();
    
    }
    
    
    public String RZ ( String str)
    {
        char c[] = str.toCharArray();
        System.out.println(str);
        StringBuilder binary = new StringBuilder(); 
        for (int i=0;i< c.length;i++)
        {
            if (c[i]=='0')
                binary.append("-0");
            else
                binary.append("+0");
                
        }
        return binary.toString();
        
    }
       public String Manchester ( String str)
    {
        char c[] = str.toCharArray();
        System.out.println(str);
        StringBuilder binary = new StringBuilder(); 
        for (int i=0;i< c.length;i++)
        {
            if (c[i]=='0')
                binary.append("01");
            else
                binary.append("10");
                
        }
        return binary.toString();
        
    }
       
        public String DiffManchester ( String str)
    {
        char c[] = str.toCharArray();
        //System.out.println(str);
        StringBuilder binary = new StringBuilder(); 
        for (int i=0;i< c.length;i++)
        {
            if (c[i]=='0')
            {
             if (state==1)
             {
                 binary.append("10");
             }
             else
                  binary.append("01");
            }
            else
            {
                if (state==1)
                {
                    state =0;
                    binary.append("01");
                }
                else
                {
                    state =1;
                    binary.append("10");
                }
            }
                
        }
        return binary.toString();
        
    }

    String do4B5B(String STR){
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
