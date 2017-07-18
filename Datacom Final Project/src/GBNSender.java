

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JOptionPane;

class Framing{
	public long tim;
	public int frameNum;
	public String data;
	Framing(int a , long b , String c){
		frameNum = a;
		tim = b;
		data = c;
	}
}

public class GBNSender {
	DataInputStream dIn;
	DataOutputStream dOut;
	Socket socket;
	Queue <Framing> Q;
	//Queue <String> saveingInp;
	long frame_time;
	int frame_number;
	Thread t;
	FrameTime timing;
	boolean EOF;
	GBNSender() throws UnknownHostException, IOException{
		
		/*if(!SaveSettings.TAKEIP){
			JOptionPane pane = new JOptionPane();
			SaveSettings.IPADDRESS=pane.showInputDialog("Enter IP address.(keep null for local host)");
			if(SaveSettings.IPADDRESS.equals("")){
				SaveSettings.IPADDRESS = "127.0.0.1";
			}
			SaveSettings.TAKEIP = true;
		}
		*/
		
		socket = new Socket("127.0.0.1",8080);
		System.out.println("connect");
		dOut = new DataOutputStream(socket.getOutputStream());
		dIn = new DataInputStream(socket.getInputStream());
		Q = new LinkedList<Framing>();
		//saveingInp = new LinkedList<String>();
		frame_number = 0;
		EOF = false;
		t = new ACK();
		t.start();
		timing = new FrameTime();
		timing.start();
	}
	class ACK extends Thread{
		public void run(){
			int ack_received = 0;
			while(true){
				try {
					ack_received = dIn.readInt();
					System.out.println("ACK : "+ack_received+" Frame Size :"+Q.size());
					if(ack_received <0){
						synchronized(Q){
						while(true){
							//synchronized(Q){
								if(Q.isEmpty())break;
							//}
							Q.remove();
							//Thread.yield();
						}
						}
						return;
					}
				} catch (IOException e) {
					continue;
				}
				synchronized(Q){
					int sz = Q.size();
					int loc = 0,take = -1;
					for(int i=0;i<sz;i++){
						Framing copy = Q.remove();
						if(take == -1 && copy.frameNum==ack_received)take = loc;
						loc++;
						Q.add(copy);
					}
					if(take==-1)Q.clear();
					else{
						for(int i=0;i<=take;i++)Q.remove();
					}
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void send(String str) throws IOException, InterruptedException{
		if(str.equals("*"))EOF = true;
		if(EOF){
			while(!Q.isEmpty()){
				//System.out.println("EOF BUT NOT EMPTY");
				Thread.yield();
			}
			dOut.writeInt((int)-1);
			t.join();
			timing.thread_cutoff = true;
			timing.join();
			return;
		}
		System.out.println(str);
		boolean flag = false;
		while(!flag){
			synchronized(Q){
				if(Q.size() < 4)flag=true;
			}
			//Thread.sleep(50);
			System.out.println("..");
			Thread.yield();
		}
		System.out.println("Sender frame_number : "+frame_number);
		dOut.writeInt((int)frame_number%8);
		dOut.writeInt((int)str.length());
		dOut.writeBytes(str);
		synchronized(Q){
			Q.add(new Framing(frame_number%8 , System.currentTimeMillis()%10000,str));
		}
		frame_number++;
		frame_number%=8;
	}
	public void CLOSE() throws IOException, InterruptedException{
		Thread.sleep(100);
		t.join();
		timing.join();
		dOut.close();
		dIn.close();
		socket.close();
		
	}
	
	class FrameTime extends Thread{
		public boolean thread_cutoff;
		FrameTime(){
			thread_cutoff = false;
		}
		public void run(){
			while(!thread_cutoff){
				synchronized(Q){
					if(!Q.isEmpty()){
						Framing f = Q.peek();
						if(f==null)continue;
						long cur_time = System.currentTimeMillis()%10000;
						if(cur_time - f.tim > 500){
							int SZ = Q.size();
							for(int i=0;i<SZ;i++){
								f = Q.peek();
								if(f==null || f.frameNum>7)break;
								Q.remove();
								try{
								dOut.writeInt(f.frameNum%8);
								dOut.writeInt((int)f.data.length());
								dOut.writeBytes(f.data);
								}catch(IOException e){
									System.out.println(e);
								}
								f.tim = (System.currentTimeMillis()%10000);
								Q.add(f);
							}
						}
					}
				}
			}
		}
	}
}
