import java.net.*;
import java.util.*;
import java.io.*;

public class TCPServer {
    public static int port = 8888;

    public static void main(String args[]) {

        TCPServer tcp = new TCPServer();
        tcp.init();

    }

    public void init() {
        try {
            ServerSocket ss = new ServerSocket(port);
            while (true) {
                Socket sc = ss.accept();
                new HandlerThread(sc);
                new SenderThread(sc);
            }
        } catch (Exception e) {
            System.out.println("���A���ݲ��`: " + e.getMessage());
        }
    }

    private class SenderThread implements Runnable {
        private Socket socket;
        private String s = "";

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

                output.writeUTF(s);
                output.close();
                System.out.println("Client: " + s);

                // input.close();
            } catch (IOException e) {
                System.out.println("���A�� run ���`: " + e.getMessage());
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (Exception e) {
                        socket = null;
                        System.out.println("�A�Ⱥ� finally ���`:" + e.getMessage());
                    }
                }
            }
        }
    }

    private class HandlerThread implements Runnable {
        private Socket socket;

        public HandlerThread(Socket s) {
            socket = s;
            new Thread(this).start();
        }

        public void run() {
            try {
                // Ū���Ȥ�ݸ��
                DataInputStream input = new DataInputStream(socket.getInputStream());
                String msg = input.readUTF();
                System.out.println("Client: " + msg);
                input.close();
            } catch (IOException e) {
                System.out.println("���A�� run ���`: " + e.getMessage());  
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (Exception e) {
                        socket = null;
                        System.out.println("�A�Ⱥ� finally ���`:" + e.getMessage());  
                    }
                }
            }
            // try {
            //     Thread.sleep(7000);
            // } catch (Exception e) {

            // }
        }
    }

    // public static void send(String msg) throws IOException {
    // OutputStream os = sc.getOutputStream(); // ���o��X��y�C
    // os.write(msg.getBytes("UTF-8"));// �e�T���� Client �ݡC
    // os.close(); // ������X��y�C
    // sc.close(); // ���� TCP ���A���C
    // }

    // public static void rev() throws IOException {

    // }
}
