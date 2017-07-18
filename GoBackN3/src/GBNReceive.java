
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class GBNReceive {
	DataInputStream dIn;
	DataOutputStream dOut;
	Socket socket;
	ServerSocket server;
	Random rand;
    OutputClass fout;
	//Receiver rec;
	boolean EOF;
	Queue <String> Q;
	Thread t;
	GBNReceive() throws IOException{
		EOF = false;
		Q = new LinkedList<String>();
		server = new ServerSocket(8080);
		rand = new Random();
		fout = new OutputClass();
		fout.setFileName("output.txt");
		//rec = new Receiver();
		t = new RECTHREAD();
		t.start();
		
	}
	class RECTHREAD extends Thread{
		public void run(){
			int R = 0;
			try {
				socket = server.accept();

				dIn = new DataInputStream(socket.getInputStream());
				dOut = new DataOutputStream(socket.getOutputStream());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			while(true){
				int frame_number =0;
				try{
					frame_number = dIn.readInt();
					System.out.println("Receiver Frame Number: "+frame_number);
					
				}catch(EOFException e){
					continue;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(frame_number < 0){
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
					OutputHandler oh = new OutputHandler( "4b5b","NRZ-L", str);
				    String ss=oh.Execute();
					///////////////////////////
					if(frame_number == R){
						Q.add(str);
						R++;
						R%=8;
					}
					if(rand.nextInt(100) >= 5)
					{
						dOut.writeInt(R);
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
		//fout.closeFile();
		t.join();
		dIn.close();
		dOut.close();
		server.close();
		socket.close();
		fout.closeFile();
		Q.clear();
	}
	public static void main(String[] args) throws IOException, InterruptedException{
		GBNReceive gbn = new GBNReceive();
		//gbn.Run();
		gbn.CLOSE();
	}
}
