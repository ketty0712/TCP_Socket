import java.io.*;
import java.net.*;
import java.util.*;

public class Server_t {
    public static int port = 8888;

    public static void main(String[] args) {
        new Server_t().Start();

    }

    public void Start() {
        try {
            ServerSocket ss = new ServerSocket(port);
            System.out.println("Server is listening...");
            String socketName;
            int portName;

            while (true) {
                Socket socket = ss.accept();
                System.out.println("Client connected!!");
                socketName = socket.getInetAddress().getHostAddress().toString();
                portName = socket.getPort();

                DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                Scanner scanner = new Scanner(System.in);
                String str = null;
                String message = "";
                new Communication(socket).start();
                while ((str = scanner.nextLine()) != null) {
                    if ("OK".equals(str)) {
                        output.writeUTF(message);
                        System.out.println("Client: " + message);
                        message = "";
                    } else
                        message += str + "\n";
                }
            }
        } catch (Exception e) {
            System.out.println("ServerºÝ²§±`: " + e.getMessage());
        }
    }

    public class Communication extends Thread {
        Socket socket;

        DataInputStream input;

        public Communication(Socket socket) {
            this.socket = socket;
            try {
                input = new DataInputStream(socket.getInputStream());

            } catch (Exception e) {
                System.out.println("ServerºÝ²§±`: " + e.getMessage());
            }
        }

        @Override
        public void run() {
            super.run();
            String msg = null;
            try {
                while ((msg = input.readUTF()) != null) {
                    System.out.println("Client: " + msg);
                }
            } catch (Exception e) {
                System.out.println("ClientºÝ²§±`: " + e.getMessage());
            }
        }
    }
}
