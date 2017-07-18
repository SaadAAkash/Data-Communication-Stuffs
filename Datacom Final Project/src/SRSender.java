import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JOptionPane;

public class SRSender {
	DataInputStream dataIn;
	DataOutputStream dataOut;
	Socket socket;
	
	Queue <Framing> que;
	
	long frame_time;
	int frNum2;
	Thread ackThread;
	FrameTime timeThread;
	boolean EOF;
	SRSender() throws UnknownHostException, IOException
	{
		
		socket = new Socket("127.0.0.1",8080);
		System.out.println("connect");
		dataOut = new DataOutputStream(socket.getOutputStream());
		dataIn = new DataInputStream(socket.getInputStream());
		
		que = new LinkedList<Framing>();
		
		frNum2 = 0;
		EOF = false;
		
		ackThread = new ACK();
		ackThread.start();
		
		timeThread = new FrameTime();
		timeThread.start();
	
	}
	
	///////////////// ackThread //////////////////////
	
	class ACK extends Thread
	{
		public void run()
		{
			int ack_received = 0;
			while(true)
			{
				try 
				{
					ack_received = dataIn.readInt();
					System.out.println("ACK : "+ack_received+" Frame Size :"+ que.size());
					if(ack_received <0)
					{
						synchronized( que )
						{
						while(true)
							{
								
									if( que.isEmpty())break;
									que.remove();
							}
						}
						return;
					}
				} catch (IOException e) {
					continue;
				}
				
				synchronized( que )
				{
					int sz = que.size();
					int loc = 0,window = -1;
					for(int i=0;i<sz;i++){
						Framing copy = que.remove();
						if(window == -1 && copy.frameNum==ack_received)window = loc;
						loc++;
						que.add(copy);
					}
					if(window==-1) que.clear();
					else{
						for(int i=0;i<=window;i++)
							que.remove();
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
	/////////////////frame tme thread//////////////////
	class FrameTime extends Thread{
		public boolean thread_cutoff;
		FrameTime(){
			thread_cutoff = false;
		}
		public void run(){
			while(!thread_cutoff)
			{
				synchronized( que )
				{
					if(!que.isEmpty())
					{
						Framing f = que.peek();
						if(f==null)continue;
						long cur_time = System.currentTimeMillis()%10000;
						if(cur_time - f.tim > 500)
						{
							int SZ = que.size();
							for(int i=0;i<SZ;i++)
							{
								f = que.peek();
								if(f==null || f.frameNum>3)
									break;
								que.remove();
								try{
								dataOut.writeInt(f.frameNum%4);
								dataOut.writeInt((int)f.data.length());
								dataOut.writeBytes(f.data);
								}catch(IOException e){
									System.out.println(e);
								}
								f.tim = (System.currentTimeMillis()%10000);
								que.add(f);
							}
						}
					}
				}
			}
		}
	}
	////////////////////////////////
	public void send(String str) throws IOException, InterruptedException{
		if(str.equals("*"))EOF = true;
		if(EOF){
			while(!que.isEmpty())
			{
				//System.out.println("EOF BUT NOT EMPTY");
				Thread.yield();
			}
			dataOut.writeInt((int)-1);
			ackThread.join();
			timeThread.thread_cutoff = true;
			timeThread.join();
			return;
		}
		System.out.println(str);
		boolean flag = false;
		while(!flag){
			synchronized( que )
			{
				if(que.size() < 2)flag=true;
			}
			//Thread.sleep(50);
			System.out.println("..");
			Thread.yield();
		}
		System.out.println("Sender frNum2 : "+frNum2);
		dataOut.writeInt((int)frNum2%4);
		dataOut.writeInt((int)str.length());
		dataOut.writeBytes(str);
		synchronized(que)
		{
			que.add(new Framing(frNum2 % 4 , System.currentTimeMillis()%10000,str));
		}
		frNum2++;
		frNum2%=4;
	}
	
	public void CLOSE() throws IOException, InterruptedException{
		Thread.sleep(100);
		ackThread.join();
		timeThread.join();
		dataOut.close();
		dataIn.close();
		socket.close();
		
	}
	
	
}
