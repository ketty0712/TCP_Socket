import java.io.*;
import java.net.*;
import java.util.*;

public class Client_t1 {
    public static void main(String[] args) throws Exception {
        new Client_t1().Start();
    }

    public void Start() {
        try {
            Socket socket = new Socket("localhost", 8888);
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            Socket socket2 = new Socket("localhost", 8889);

            Scanner scanner = new Scanner(System.in);
            String str = null;
            Receiver(input);
            String message = "";
            while ((str = scanner.nextLine()) != null) {
                if ("OK".equals(str)) {
                    output.writeUTF(message);
                    System.out.println("Client: " + message);
                    message = "";

                } else if ("PIC".equals(str)) {
                    System.out.println("HELLO");

                    DataOutputStream output2 = new DataOutputStream(socket2.getOutputStream());
                    FileInputStream fis = new FileInputStream(new File("D://workspace//TCP_Socket//about_pic4.jpg"));
                    // byte[] b = new byte[1024];
                    // fis.read(b);
                    byte[] b = new byte[1024];
                    int len;
                    while ((len = fis.read(b)) != -1) {
                        output2.write(b, 0, len);
                    }

                    // output2.writeUTF("HELLO");
                } else
                    message += str + "\n";
            }
        } catch (Exception e) {
            System.out.println("Client 異常: " + e.getMessage());
        }
    }

    public void Receiver(final DataInputStream input) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                String str = null;
                try {
                    while ((str = input.readUTF()) != null) {
                        System.out.println("Server: " + str);
                    }
                } catch (IOException e) {
                    System.out.println("Client 異常: " + e.getMessage());
                }
            }
        }.start();
    }
}
// public static int port = 8888;
// public static Socket client;

// public static void main(String args[]) throws Exception {

// for (int i = 0; i < 50; i++) {
// rev();
// }
// }

// public static void send(String msg) throws Exception {
// client = new Socket("localhost", port); // 根據 args[0] 的 TCP Socket.
// OutputStream out = client.getOutputStream(); // 獲取一個輸出流，向服務端傳送資訊
// PrintWriter printWriter = new PrintWriter(out);// 將輸出流包裝成列印流
// printWriter.println(msg);
// printWriter.flush();
// client.shutdownOutput();
// printWriter.close();
// out.close();
// client.close();
// }

// public static String rev() throws UnknownHostException, IOException {
// client = new Socket("localhost", port); // 根據 args[0] 的 TCP Socket.

// InputStream in = client.getInputStream(); // 取得輸入訊息的串流

// InputStreamReader inputStreamReader = new InputStreamReader(in); //
// 包裝成字元流，提高效率
// // StringBuffer buf = new StringBuffer(); // 建立讀取字串。
// BufferedReader br = new BufferedReader(inputStreamReader); // 緩衝區

// // StringBuffer buf = new StringBuffer(); // 建立讀取字串。
// String msg = "";
// String tmp = null;
// while ((tmp = br.readLine()) != null) {
// msg += tmp;
// System.out.println(msg);
// }

// client.shutdownInput();
// br.close();
// in.close();
// client.close(); // 關閉 TcpSocket.

// // inputStreamReader.close();
// return msg;
// }