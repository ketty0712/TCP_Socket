import java.awt.*;
import java.io.*;
import java.net.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;

public class Server {

	public static int port = 9000;

	static JFrame frm = new JFrame("Server");
	static JPanel contentPane;
	static JScrollPane scrollPane, scrollPane2;
	static JTextPane textPane;
	static JLabel label;
	static JButton btnSend, btnPic;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		// �إ� JFrame ����
		CreateFrame();
		frm.setVisible(true);

		// �Ұ� Server �ݪ��\��
		new Server().Start();
	}

	/**
	 * 
	 * TCP Part
	 * 
	 */

	static ServerSocket ss, ss2;
	static Socket socket1 = null;
	static Socket socket2 = null;
	static DataOutputStream output, output2;
	static PrintStream printStream;

	public void Start() {
		try {
			ServerSocket ss = new ServerSocket(port);
			ServerSocket ss2 = new ServerSocket(port + 1);
			System.out.println("Server is listening..."); // : Server �P Client ���\�s�u
			String socketName;
			while (true) {
				// �@��������, �h��ܦ��A���P�Ȥ����o�F�s�u
				Socket socket1 = ss.accept(); // �B�z�o���s�u

				System.out.println("Client connected!!");
				textPane.setText(null);
				socketName = socket1.getInetAddress().getHostAddress().toString(); // get client host IP address
				tfIP.setText(socketName);

				// �쥻�p�G������ Server �ݳs�u Port �����ܡAclientPort �i�H���s�� Port
				// int clientPort = socket1.getPort();

				output = new DataOutputStream(socket1.getOutputStream());

				// �}�Ұ�����B�z�q�T�ACommunication(socket) �O�@���ΨӺ�ť client �ݦ^�Ъ��T��
				new Communication(socket1).start();

				// socket2�O�t�@���Ψ� �����Ϥ� �� Socket�Awith Port #10001
				Socket socket2 = ss2.accept();
				output2 = new DataOutputStream(socket2.getOutputStream());
				printStream = new PrintStream(socket2.getOutputStream());

				new RcvPic(socket2).start(); // �P�B�}�ҲĤT�������: RcvPic ()
			}
		} catch (Exception e) {
			System.out.println("Server �ݳ]�w���`: " + e.getMessage());
		}
	}

	public class RcvPic extends Thread {
		Socket socket;
		// BufferedInputStream inputStream;
		DataInputStream input;
		FileOutputStream fos;

		public RcvPic(Socket socket) {
			this.socket = socket;
			try {
				input = new DataInputStream(socket.getInputStream());
				// Ū���Ȥ�ݸ��
				// inputStream = new BufferedInputStream(socket.getInputStream());
			} catch (Exception e) {
				System.out.println("Server�ݱ����Ϥ����`: " + e.getMessage());
			}
		}

		@Override
		public void run() {
			super.run();
			try {
				try {
					/** �N�ɮץ]�˨��ɮ׿�X�y����*/       
					int length = 0;
					byte[] inputByte = new byte[1024 * 4];

					fos = new FileOutputStream(new File("D://5.jpg"));
					while ((length = input.read(inputByte, 0, inputByte.length)) > 0) {
						fos.write(inputByte, 0, length);
						fos.flush();
					}
					InsertPic("Client");

				} finally {
					if (fos != null)
						fos.close();
					if (input != null)
						input.close();
					if (socket2 != null)
						socket2.close();
				}
			} catch (Exception e) {
				System.out.println("Server�ݱ����Ϥ����`: " + e.getMessage());
			}
			System.out.println("success!");
		}
	}

	/**
	 * 
	 * Communication is a thread that works on listening text message from client
	 * 
	 */
	public class Communication extends Thread {

		// Due to every time connection between client and server closed
		// when a message is transmitted successfully
		// Each Communication need a new connection with a rebuild socket
		Socket socket;

		//
		DataInputStream input;

		public Communication(Socket socket) {
			this.socket = socket;
			try {
				input = new DataInputStream(socket.getInputStream()); // Ū���Ȥ�ݸ��

			} catch (Exception e) {
				System.out.println("Server �ݲ��`: " + e.getMessage());
			}
		}

		@Override
		public void run() {
			super.run();
			String msg = null;
			try {
				while ((msg = input.readUTF()) != null) { // �o�̭n�`�N�M�Ȥ�ݿ�X�y���g��k����,�_�h�|�� EOFException
					System.out.println("Client: " + msg);

					/**
					 * �B�z�Ȥ�ݸ�� *
					 */
					String[] sa = msg.split("\n"); // ����B�z => �ন�}�Csa[]

					doc.insertString(doc.getLength(), "Client: \t", null);
					boolean f = false;
					// ���r�@��@��K��textPane�W��
					for (String s : sa) {
						if (f)
							doc.insertString(doc.getLength(), "\t" + s + "\n", null);
						else {
							doc.insertString(doc.getLength(), s + "\n", null);
							f = true;
						}
					}
				}
			} catch (Exception e) {
				System.out.println("Server �ݱ������`: " + e.getMessage());
			}
		}
	}

	static StyledDocument doc;
	static Style style;
	private static JTextField tfIP;
	private static JTextField tfPort;
	private static JTextArea textArea;

	static class act implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// �ǰe���s
			if (e.getSource() == btnSend) {
				if (graph) {
					InsertIcon();
					try {
						try {
							// output2 = new DataOutputStream(socket2.getOutputStream());
							File file = new File(path);
							FileInputStream fis = new FileInputStream(file);
							byte[] sendBytes = new byte[1024 * 4];
							int length = 0;
							while ((length = fis.read(sendBytes, 0, sendBytes.length)) > 0) {
								output2.write(sendBytes, 0, length);
								output2.flush();
							}
						} finally {
							output2.close();
							socket2.close();
						}
						// BufferedInputStream inputStream = new BufferedInputStream(new
						// FileInputStream(path));
						// int readin;
						// while ((readin = inputStream.read()) != -1) {
						// printStream.write(readin);
						// Thread.yield();
						// }
						// printStream.flush();
						// printStream.close();
						// inputStream.close();

					} catch (Exception ex) {
						System.out.println("Server �ǰe�Ϥ����`: " + ex.getMessage());
					}
					graph = false;
					path = null;
				} else {
					try {
						String message = textArea.getText();
						// �V�Ȥ��Send��T  
						output.writeUTF(message);

						String[] sa = message.split("\n");

						doc.insertString(doc.getLength(), "\nServer: \t", null);
						boolean f = false;
						for (String s : sa) {
							if (f)
								doc.insertString(doc.getLength(), "\t" + s + "\n", null);
							else {
								doc.insertString(doc.getLength(), s + "\n", null);
								f = true;
							}
						}
						textArea.setText("");
					} catch (Exception ex) {
						System.out.println("Server �ݶǰe���`: " + ex.getMessage());
					}
				}

			} else if (e.getSource() == btnPic) {
				chooseFile();
			}
		}
	}

	// static String path = "D://xampp//htdocs//HW//product_pic//about_pic3.jpg";
	static String path = null;

	public static void InsertPic(String who) {
		try {
			doc.insertString(doc.getLength(), who + ": \t", null);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		ImageIcon image = new ImageIcon("D://5.jpg");
		textPane.setCaretPosition(doc.getLength());
		image.setImage(image.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		textPane.insertIcon(image);
		try {
			doc.insertString(doc.getLength(), "\n", null);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public static void InsertIcon() {
		try {
			if (path == null)
				throw new Exception("�z�٥��������Ϥ�: ");

			doc.insertString(doc.getLength(), "\nServer: \t", null);
			ImageIcon image = new ImageIcon(path);
			textPane.setCaretPosition(doc.getLength());
			image.setImage(image.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
			textPane.insertIcon(image);
			doc.insertString(doc.getLength(), "\n", null);

		} catch (Exception ex) {
			System.out.println("Client �ݶפJ�Ϥ����`: " + ex.getMessage()); 
		}
	}

	public static void chooseFile() {
		JFileChooser f = new JFileChooser("."); // �d���ɮ�

		f.setCurrentDirectory(new File("D://xampp//htdocs//HW//product_pic"));

		ExtensionFileFilter filter = new ExtensionFileFilter();
		filter.addExtension("jpg");
		filter.addExtension("jpeg");
		filter.addExtension("gif");
		filter.addExtension("png");

		filter.setDescription("�ɮ׹Ϥ�(*.jpg, *.jpeg, *.gif, *png)");
		// ���äU�ԦC���� "�Ҧ��ɮ�" �ﶵ

		f.addChoosableFileFilter(filter);
		// ���ɮ׿�ܾ����w�@�ӹw���Ϥ�������

		int res = f.showOpenDialog(null);

		if (res == JFileChooser.APPROVE_OPTION) {
			path = f.getSelectedFile().getAbsolutePath();
			graph = true;
		}
	}

	static boolean graph = false;

	/**
	 * Create the frame.
	 */
	public static void CreateFrame() {
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.setBounds(100, 100, 450, 570);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frm.setContentPane(contentPane);
		contentPane.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 69, 414, 301);
		contentPane.add(scrollPane);

		textPane = new JTextPane();
		// textPane.setText("�o�O��ѫ�");
		textPane.setFont(new Font("�L�n������", Font.PLAIN, 14));
		scrollPane.setViewportView(textPane);

		label = new JLabel("��ѫ�");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("�L�n������", Font.PLAIN, 16));
		scrollPane.setColumnHeaderView(label);

		scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(10, 380, 414, 80);
		contentPane.add(scrollPane2);

		textArea = new JTextArea();
		scrollPane2.setViewportView(textArea);

		JLabel lblNewLabel_1 = new JLabel("�п�J�T��");
		lblNewLabel_1.setFont(new Font("�L�n������", Font.PLAIN, 14));
		scrollPane2.setColumnHeaderView(lblNewLabel_1);

		btnPic = new JButton("�Ϥ�");
		btnPic.setFont(new Font("�L�n������", Font.PLAIN, 14));
		btnPic.setBounds(20, 482, 70, 33);
		contentPane.add(btnPic);

		btnSend = new JButton("�ǰe");
		btnSend.setFont(new Font("�L�n������", Font.PLAIN, 14));
		btnSend.setBounds(130, 482, 70, 33);
		contentPane.add(btnSend);

		JPanel panel = new JPanel();
		panel.setBounds(10, 21, 414, 38);
		contentPane.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel = new JLabel("Client IP: ");
		lblNewLabel.setFont(new Font("Consolas", Font.PLAIN, 16));
		panel.add(lblNewLabel);

		tfIP = new JTextField();
		tfIP.setFont(new Font("Consolas", Font.PLAIN, 14));
		tfIP.setColumns(10);
		panel.add(tfIP);

		JLabel lblServerPort = new JLabel("Client Port: ");
		lblServerPort.setFont(new Font("Consolas", Font.PLAIN, 16));
		panel.add(lblServerPort);

		tfPort = new JTextField();
		tfPort.setText(port + "");
		tfPort.setFont(new Font("Consolas", Font.PLAIN, 14));
		tfPort.setColumns(10);
		panel.add(tfPort);

		btnSend.addActionListener(new act());
		btnPic.addActionListener(new act());

		doc = textPane.getStyledDocument();

		style = doc.addStyle("StyleName", null);
		StyleConstants.setIcon(style, new ImageIcon("imagefile"));
	}

}
