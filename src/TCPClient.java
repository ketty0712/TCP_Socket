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
			System.out.println("�п�JServer IP:\t");
			// ip = new BufferedReader(new InputStreamReader(System.in)).readLine();
			ip = "localhost";

			System.out.println("�п�JServer Port:\t");
			// port = Integer.parseInt(new BufferedReader(new
			// InputStreamReader(System.in)).readLine());
			port = 8888;
			while (true) {
				socket = new Socket(ip, port);
				// new RecieverThread(socket);
				new SenderThread(socket);
			}

		} catch (Exception e) {
			System.out.println("�Ȥ�ݲ��`: " + e.getMessage());
		}
	}

	static String s = "";

	private class SenderThread implements Runnable {
		private Socket socket;

		public SenderThread(Socket s) {
			System.out.println("�п�J:\t");
			socket = s;
			new Thread(this).start();
		}

		public void run() {
			try {
				DataOutputStream output = new DataOutputStream(socket.getOutputStream());
				// �ǰe�@��
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
				System.out.println("�Ȥ�� run ���`: " + e.getMessage());
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (Exception e) {
						socket = null;
						System.out.println("�Ȥ�� finally ���`:" + e.getMessage());
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
				// Ū���Ȥ�ݸ��
				DataInputStream input = new DataInputStream(socket.getInputStream());
				String msg = input.readUTF();
				System.out.println("Server: " + msg);
				input.close();
			} catch (IOException e) {
				System.out.println("�Ȥ�� run ���`: " + e.getMessage());  
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (Exception e) {
						socket = null;
						System.out.println("���A���� finally ���`:" + e.getMessage());  
					}
				}
			}
		}
	}
}
