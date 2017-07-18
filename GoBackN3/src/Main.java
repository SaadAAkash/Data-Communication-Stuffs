import java.io.IOException;

public class Main {

	public static void main(String[] args)throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		InputClass inp = new InputClass();
		int mod  = 1;

		inp.setFileName("in1.txt");
		GBNSender gbn = new GBNSender();
		
		while(!inp.isEOF())
		{
			String str = inp.takeInput(150);
			for(int i=str.length();i<150;i++)
				str+=" ";

			////////////////////////////////////////////////////////			

			InPutHandler in = new InPutHandler( "4b5b","NRZ-L", str);
	      String ss=in.Execute();
			gbn.send(ss);
			////////////////////////////////////////////////////////
			//gbn.send(str);
		
		}
		gbn.send("*");
		gbn.CLOSE();
	}

}
