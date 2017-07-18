import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class SRReceiver {
	DataInputStream dIn;
	DataOutputStream dOut;
	Socket socket;
	ServerSocket server;
	Random rand;
    OutputClass fout;
    String phy;
    
	boolean EOF;
	Queue <String> Q;
	Thread t;
	SRReceiver(String a) throws IOException{
		EOF = false;
		Q = new LinkedList<String>();
		server = new ServerSocket(8080);
		rand = new Random();
		fout = new OutputClass();
		fout.setFileName("output.txt");
		t = new rcvThread();
		t.start();
                phy = a;
		
	}
	class rcvThread extends Thread{
		public void run(){
			int MaxFrame = 0;
			
			try {
				socket = server.accept();

				dIn = new DataInputStream(socket.getInputStream());
				dOut = new DataOutputStream(socket.getOutputStream());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			while(true)
			{
				int frameNum =0;
				try{
					frameNum = dIn.readInt();
					System.out.println("Receiver Frame Number: "+frameNum);
					
				}catch(EOFException e){
					continue;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(frameNum < 0){
					try {
						dOut.writeInt(-1);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					EOF = true;
					/*try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
					return;
				}
				else{
					try{
					int SIZE = dIn.readInt();
					String str = "";
					for(int i=0;i<SIZE;i++)
						str+=(char)dIn.read();

					/////////////////////////////
					OutputHandler oh = new OutputHandler( "4b5b",phy, str);
				    String ss=oh.Execute();
					///////////////////////////
					if(frameNum == MaxFrame ){
						Q.add(str);
						
						MaxFrame++;
						//R%=8;
						MaxFrame %= 4;
					}
					if(rand.nextInt(100) >= 5)
					{
						dOut.writeInt( MaxFrame );
						////////////////////////////////
						fout.writeFile(ss);
					}
					
					}catch(Exception e){
						System.out.println(e);
					}
				}
			}

		}
	}
	
	public boolean isEOF(){
		return (EOF && Q.isEmpty());
	}
	
	public String getString(){
		if(Q.isEmpty())return "";
		return Q.remove();
	}
	public void CLOSE() throws IOException, InterruptedException{
		Thread.sleep(100);
		t.join();
		dIn.close();
		dOut.close();
		server.close();
		socket.close();
		fout.closeFile();
		Q.clear();
	}
        
}
