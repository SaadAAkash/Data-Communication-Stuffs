
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author akash
 */
public class SenderSide 
{
    InputClass inp;
    String phy;
    GBNSender gbn;
    SenderSide(String a) throws FileNotFoundException, IOException, InterruptedException
    {
                inp = new InputClass();
		inp.setFileName("in1.txt");
                phy = a;
		gbn = new GBNSender();
                execute();
    }
    
    public void execute() throws IOException, InterruptedException
    {
        
		while(!inp.isEOF())
		{
			String str = inp.takeInput(150);
			for(int i=str.length();i<150;i++)
				str+=" ";

			////////////////////////////////////////////////////////			

			InPutHandler in = new InPutHandler( "4b5b",phy, str);
                        String ss=in.Execute();
			gbn.send(ss);
			////////////////////////////////////////////////////////
			//gbn.send(str);
		
		}
		gbn.send("*");
		gbn.CLOSE();
    }
    
}
