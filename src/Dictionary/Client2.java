package Dictionary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client2 {
	private Socket socket = null;
	BufferedWriter out=null;
	BufferedReader in=null ;
	BufferedReader stdin=null;
	 
	public Client2 (String address,int port) throws IOException
	{
		socket = new Socket(address,port);
		out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		stdin = new BufferedReader(new InputStreamReader(System.in));
		
		String line="";
		String kq="";
		while(!line.equals("bye"))
		{
			
			line =stdin.readLine();
			if(line.equals("bye"))
			{
			System.out.print("Káº¾T THĂ�C CHÆ¯Æ NG TRĂŒNH \n");	
			out.write(line);
			out.newLine();
			out.flush();
			break;
			}
			out.write(line);
			out.newLine();
			out.flush();
			kq = in.readLine();
			
			if(kq.equals("")) {
			System.out.print("tá»« nĂ y khĂ´ng cĂ³ trong tá»« Ä‘iá»ƒn \n");
			}
			else if(kq.equals("lá»—i")) {
				System.out.print("cĂº phĂ¡p khĂ´ng Ä‘Ăºng \n");
			}
			else if(kq.equals("Already exists")) {
				System.out.print("tá»« Ä‘Ă£ tá»“n táº¡i trong tá»« Ä‘iá»ƒn \n");
			}
			else if(kq.equals("add-success")) {
				System.out.print("thĂªm thĂ nh cĂ´ng \n");
			}
			else if(kq.equals("remove_success")) {
				System.out.print("xĂ³a thĂ nh cĂ´ng \n");
			}
			
			
			else {
	        System.out.print(kq +"\n");
			}
			
		}
		in.close();
		out.close();
		socket.close();
	}
	

	public static void main(String args[]) throws UnknownHostException, IOException 
	{ 
		Client client = new Client("127.0.0.1", 5000); 
	} 

}
