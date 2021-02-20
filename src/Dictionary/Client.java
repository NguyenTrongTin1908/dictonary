package Dictionary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private Socket socket = null;
	BufferedWriter out=null;
	BufferedReader in=null ;
	BufferedReader stdin=null;
	 
	public Client (String address,int port) throws IOException
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
			System.out.print("ket thuc chuong trinh \n");	
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
			System.out.print("tu nay khong co trong tu dien \n");
			}
			else if(kq.equals("lỗi")) {
				System.out.print("khong dung dinh dang \n");
			}
			else if(kq.equals("Already exists")) {
				System.out.print("tu nay da co trong tu dien \n");
			}
			else if(kq.equals("add-success")) {
				System.out.print("them thanh cong \n");
			}
			else if(kq.equals("remove_success")) {
				System.out.print("xoa thanh cong \n");
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
