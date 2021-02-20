package Dictionary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Keyer
 */
public class Server {
	private Socket socket = null;
	private ServerSocket server = null;
	BufferedWriter out = null;
	BufferedReader in = null;
	FileInputStream fi;
	Scanner inp;
	StringTokenizer st;
	LinkedHashMap<String, String> map1;
	LinkedHashMap<String, String> map2;
	public Server(int port) throws IOException {
		server = new ServerSocket(port);
		System.out.println("Server started");
		System.out.println("Waiting for a client ...");
		socket = server.accept();
		out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	public void read_dictionary() throws FileNotFoundException {
		map1 = new LinkedHashMap<String, String>();
		map2 = new LinkedHashMap<String, String>();
		fi = new FileInputStream("src\\dictionary.txt");
		inp = new Scanner(fi, "UTF-8");
		while (inp.hasNextLine()) {
			String line = inp.nextLine();
			st = new StringTokenizer(line, ";");
			while (st.hasMoreTokens()) {
				String key = st.nextToken();
				String word = st.nextToken();
				map1.put(key, word);
				map2.put(word, key);

			}
		}
	}

	public void receive_andSent() throws IOException {
		String sent_to_client="";
		String line = "";		
		while (!line.equals("bye")) {
			try {								
				line = in.readLine();								
				Pattern p = Pattern.compile("[^A-Za-zÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ ]", Pattern.CASE_INSENSITIVE);
				Matcher m = p.matcher(line);
				boolean b = m.find();
				if (b)
				{
					String[] words = line.split(";");
					if(words.length>2 && words[0].equals("Add") && words[1]!=null && words[2]!=null) {								 
					String x= words[1];  
					String y= words[2];  
					sent_to_client = Add(x,y);					
					}
					else
					if(words.length>1 && words[0].equals("Del") && words[1]!=null)	
					{
						String x= words[1]; 
						sent_to_client = Del(x);
					}					
									
				else {					
					sent_to_client="lỗi";
				}
				}
					else 
					{
					sent_to_client = Transfer(line);
					}
					out.write(sent_to_client);
					out.newLine();
					out.flush();							
			} catch (IOException i) {
				System.err.println(i);
			}
		}		
		 File file = new File("src//dictionary.txt");
	        OutputStream outputStream = new FileOutputStream(file);
	        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
		for (Map.Entry<String, String> entry : map1.entrySet()) {
           
           outputStreamWriter.write(entry.getKey()+";"+entry.getValue());
           // Dùng để xuống hàng
           outputStreamWriter.write("\n");
        }
		outputStreamWriter.flush();
		in.close();
		out.close();
		socket.close();
		server.close();		        
	}

	public String Transfer(String word) {
		String word_transfer = "";
		if ((map1.get(word) != null) || (map1.get(word.toLowerCase()) != null)) {
			word_transfer = map1.get(word.toLowerCase());

		}
		else
		if((map2.get(word) != null) || (map2.get(word.toLowerCase()) != null))
		{
			word_transfer = map2.get(word.toLowerCase());
		}

		return word_transfer;
	}
	
	
	public String Add(String key,String value) {
		String alert = "";
		if (map1.get(key) != null) {
			alert = "Already exists";
		}
		else
		{
			map1.put(key, value);
			map2.put(value, key);
			alert = "add-success";
		}
		

		return alert;
	}
	
	public String Del(String word) {
		String alert="";
		String word_transfer = "";
		if (map1.get(word) != null) {
			word_transfer =map1.get(word);
			
			map1.remove(word);
			map2.remove(word_transfer);
            alert="remove_success";
		}
		else
		if (map2.get(word) != null) {
				word_transfer =map2.get(word);
				
				map2.remove(word);
				map1.remove(word_transfer);
	            alert="remove_success";
			}
		
		else
		{
			alert="từ cần xóa không có trong từ điển";
		}

		return alert;
	}

	/**
	 * @param args the command line arguments
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		Server sv = new Server(5000);
		sv.read_dictionary();
		sv.receive_andSent();

	}

}
