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
            System.out.println("Client ���`: " + e.getMessage());
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
                    System.out.println("Client ���`: " + e.getMessage());
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
// client = new Socket("localhost", port); // �ھ� args[0] �� TCP Socket.
// OutputStream out = client.getOutputStream(); // ����@�ӿ�X�y�A�V�A�Ⱥݶǰe��T
// PrintWriter printWriter = new PrintWriter(out);// �N��X�y�]�˦��C�L�y
// printWriter.println(msg);
// printWriter.flush();
// client.shutdownOutput();
// printWriter.close();
// out.close();
// client.close();
// }

// public static String rev() throws UnknownHostException, IOException {
// client = new Socket("localhost", port); // �ھ� args[0] �� TCP Socket.

// InputStream in = client.getInputStream(); // ���o��J�T������y

// InputStreamReader inputStreamReader = new InputStreamReader(in); //
// �]�˦��r���y�A�����Ĳv
// // StringBuffer buf = new StringBuffer(); // �إ�Ū���r��C
// BufferedReader br = new BufferedReader(inputStreamReader); // �w�İ�

// // StringBuffer buf = new StringBuffer(); // �إ�Ū���r��C
// String msg = "";
// String tmp = null;
// while ((tmp = br.readLine()) != null) {
// msg += tmp;
// System.out.println(msg);
// }

// client.shutdownInput();
// br.close();
// in.close();
// client.close(); // ���� TcpSocket.

// // inputStreamReader.close();
// return msg;
// }