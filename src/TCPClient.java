import java.io.*;
import java.net.*;

public class TCPClient {
	public static String ip = "";
	public static int port = 8888; // Server port
	public static String str = "";

	public static void main(String[] args) {
		TCPClient tcp = new TCPClient();
		tcp.init();
	}

	public void init() {
		try {
			Socket socket = null;
			System.out.println("請輸入Server IP:\t");
			// ip = new BufferedReader(new InputStreamReader(System.in)).readLine();
			ip = "localhost";

			System.out.println("請輸入Server Port:\t");
			// port = Integer.parseInt(new BufferedReader(new
			// InputStreamReader(System.in)).readLine());
			port = 8888;
			while (true) {
				socket = new Socket(ip, port);
				// new RecieverThread(socket);
				new SenderThread(socket);
			}

		} catch (Exception e) {
			System.out.println("客戶端異常: " + e.getMessage());
		}
	}

	static String s = "";

	private class SenderThread implements Runnable {
		private Socket socket;

		public SenderThread(Socket s) {
			System.out.println("請輸入:\t");
			socket = s;
			new Thread(this).start();
		}

		public void run() {
			try {
				DataOutputStream output = new DataOutputStream(socket.getOutputStream());
				// 傳送一行
				String tmp;
				do {
					tmp = new BufferedReader(new InputStreamReader(System.in)).readLine();
					if ("go".equals(tmp))
						break;
					s += tmp + "\n";
				} while (true);

				// String s = new BufferedReader(new InputStreamReader(System.in)).readLine();
				System.out.println("Client: " + s);
				output.writeUTF(s);
				output.close();
				// input.close();
			} catch (IOException e) {
				System.out.println("客戶端 run 異常: " + e.getMessage());
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (Exception e) {
						socket = null;
						System.out.println("客戶端 finally 異常:" + e.getMessage());
					}
				}
			}

		}
	}

	private class RecieverThread implements Runnable {
		private Socket socket;

		public RecieverThread(Socket s) {
			socket = s;
			new Thread(this).start();
		}

		public void run() {
			try {
				// 讀取客戶端資料
				DataInputStream input = new DataInputStream(socket.getInputStream());
				String msg = input.readUTF();
				System.out.println("Server: " + msg);
				input.close();
			} catch (IOException e) {
				System.out.println("客戶端 run 異常: " + e.getMessage());  
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (Exception e) {
						socket = null;
						System.out.println("伺服器端 finally 異常:" + e.getMessage());  
					}
				}
			}
		}
	}
}
