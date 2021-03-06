import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;

public class Client {

	static JFrame frm = new JFrame("Client");

	static JPanel contentPane;
	static JTextField tfIP, tfPort;
	static JButton btnPic, btnSend, btnCancel;
	static JTextPane textPane;
	static JTextArea textArea;
	static boolean start_tcp = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws Exception {
		CreateFrame();
		frm.setVisible(true);
		doc.insertString(doc.getLength(), "請輸入 Server IP 和 Server Port(9000): ", null);
		// Scanner scanner = new Scanner(System.in);
		// IP = scanner.nextLine();
		// port = scanner.nextInt();
	}

	/**
	 * TCP Connect
	 */
	static DataOutputStream output, output2;
	static PrintStream printStream;

	// static DataOutputStream output2;
	static Socket socket1, socket2;
	static String IP = "";
	static int port = 9000;

	public void Start() {
		try {
			socket1 = new Socket(IP, port);
			DataInputStream input = new DataInputStream(socket1.getInputStream());
			// tfIP.setText(socket1.getInetAddress().getHostAddress().toString());
			// tfPort.setText(socket1.getPort() + "");
			output = new DataOutputStream(socket1.getOutputStream());

			socket2 = new Socket(IP, port + 1);
			// BufferedInputStream inputStream = new
			// BufferedInputStream(socket2.getInputStream());
			printStream = new PrintStream(socket2.getOutputStream());
			output2 = new DataOutputStream(socket2.getOutputStream());
			DataInputStream input2 = new DataInputStream(socket2.getInputStream());
			Receiver(input);
			Receiver2(input2);

			// 伺服器地址

		} catch (Exception e) {
			System.out.println("Client 異常: " + e.getMessage());
		}
	}

	// public void Start2() {
	// try {
	// socket2 = new Socket(IP, port + 1);
	// printStream = new PrintStream(socket2.getOutputStream());
	// output2 = new DataOutputStream(socket2.getOutputStream());

	// // output2 = new DataOutputStream(socket2.getOutputStream());
	// Receiver2();
	// input2 = new DataInputStream(socket2.getInputStream());

	// // 伺服器地址

	// } catch (Exception e) {
	// System.out.println("Client 異常: " + e.getMessage());
	// }
	// }

	/**
	 * 前面的操作都是傳送文字指令或資訊給連線的另一端。
	 * 現在需要適當的改寫一下，就可以作檔案傳送。
	 * 主要是使用PrintStream的write()方法，它負責將位元或 int 傳送給連線的另一端。
	 */
	public void Receiver(final DataInputStream input) {
		new Thread() {
			@Override
			public void run() {
				super.run();

				String msg = null;
				try {
					while ((msg = input.readUTF()) != null) {
						System.out.println("Server: " + msg);
						String[] sa = msg.split("\n");

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
					}
				} catch (Exception e) {
					System.out.println("Client 端接收異常: " + e.getMessage());
				}
			}
		}.start();
	}

	public void Receiver2(final DataInputStream input) {
		new Thread() {
			@Override
			public void run() {
				super.run();
				try {
					try {
						FileOutputStream fos = new FileOutputStream(new File("D://4.jpg"));
						byte[] inputByte = new byte[1024 * 4];
						System.out.println("開始接收資料...");
						int length = 0;
						while ((length = input.read(inputByte, 0, inputByte.length)) > 0) {
							fos.write(inputByte, 0, length);
							fos.flush();
						}
						InsertPic("Server");

					} finally {
						if (input != null)
							input.close();
						if (socket2 != null)
							socket2.close();
					}
					// BufferedOutputStream outputStream = new BufferedOutputStream(new
					// FileOutputStream("D://4.jpg"));
					// int readin;
					// while ((readin = inputStream.read()) != -1) {
					// outputStream.write(readin);
					// Thread.yield();
					// }
					// outputStream.flush();
					// outputStream.close();
					// inputStream.close();

				} catch (Exception e) {
					System.out.println("Client 端接收圖片異常: " + e.getMessage());
					e.getStackTrace();
				}

				System.out.println("success!");
			}
		}.start();
	}

	static StyledDocument doc;
	static Style style;

	// /**
	// * Button's Action
	// * btnSend: Send message to server
	// * btnPic: Send picture to server
	// * btnCancel: Exit program
	// */
	static class act implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == btnSend) {
				if (graph) {
					// 將選取的圖片顯示到自己的訊息欄中
					InsertIcon();

					// 傳送圖片
					try {
						try {
							socket2 = new Socket(IP, port + 1);
							// BufferedInputStream inputStream = new BufferedInputStream(new
							// FileInputStream(path));
							// int readin;
							// while ((readin = inputStream.read()) != -1) {
							// printStream.write(readin);
							// Thread.yield();
							// }
							// socket2.shutdownOutput();

							// printStream.flush();
							// printStream.close();
							// inputStream.close();
							FileInputStream fis = new FileInputStream(new File(path));
							byte[] sendBytes = new byte[1024 * 4];
							int length = 0;
							while ((length = fis.read(sendBytes, 0, sendBytes.length)) > 0) {
								output2.write(sendBytes, 0, length);
								output2.flush();
							}
							// System.out.println(path);
						} finally {
							if (output2 != null)
								output2.close();
							if (socket2 != null)
								socket2.close();
						}
					} catch (Exception ex) {
						System.out.println("Client 傳送圖片異常: " + ex.getMessage());
					}
					System.out.println("success!!");
					graph = false;
					path = null;
				} else {
					try {
						String message = textArea.getText();
						output.writeUTF(message);

						String[] sa = message.split("\n");

						doc.insertString(doc.getLength(), "\nClient: \t", null);
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
						System.out.println("Client端傳送異常: " + ex.getMessage());
					}
				}

			}

			else if (e.getSource() == btnPic) {
				chooseFile();
			}
		}
	}

	static class KeyLis implements KeyListener {
		public void keyPressed(KeyEvent e) {

			if (e.getKeyCode() == KeyEvent.VK_ENTER) // 判斷是否按Enter鑑
			{
				if (e.getComponent() == tfIP) {
					IP = tfIP.getText();
					tfPort.requestFocus();
				} else {
					port = Integer.parseInt(tfPort.getText());
					new Client().Start();
				}
			}
		}

		public void keyReleased(KeyEvent e) // 當按鍵放開時
		{
		}

		public void keyTyped(KeyEvent e) // 鍵入文字時
		{
		}

	}

	static String path = null;
	static boolean graph = false;

	public static void InsertPic(String who) {
		try {
			doc.insertString(doc.getLength(), "\n" + who + ": \t", null);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		ImageIcon image = new ImageIcon("D://4.jpg");
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
				throw new Exception("您還未選取任何圖片: ");

			doc.insertString(doc.getLength(), "\nClient: \t", null);
			ImageIcon image = new ImageIcon(path);
			textPane.setCaretPosition(doc.getLength());
			image.setImage(image.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
			textPane.insertIcon(image);
			doc.insertString(doc.getLength(), "\n", null);

		} catch (Exception ex) {
			System.out.println("Client端匯入圖片異常: " + ex.getMessage()); 
		}
	}

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

		JPanel panel = new JPanel();
		panel.setBorder(new CompoundBorder());
		panel.setBounds(10, 25, 416, 43);
		contentPane.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel jLabel1 = new JLabel("Server IP: ");
		jLabel1.setFont(new Font("Consolas", Font.PLAIN, 16));
		panel.add(jLabel1);

		tfIP = new JTextField();
		tfIP.setFont(new Font("Consolas", Font.PLAIN, 14));
		panel.add(tfIP);
		tfIP.setColumns(10);

		JLabel jLabel2 = new JLabel("Server Port: ");
		jLabel2.setFont(new Font("Consolas", Font.PLAIN, 16));
		panel.add(jLabel2);

		tfPort = new JTextField();
		tfPort.setFont(new Font("Consolas", Font.PLAIN, 14));
		panel.add(tfPort);
		tfPort.setColumns(10);
		tfIP.addKeyListener(new KeyLis());
		tfPort.addKeyListener(new KeyLis());

		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setBounds(10, 73, 414, 296);
		contentPane.add(scrollPane1);

		JLabel label = new JLabel("聊天室");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		scrollPane1.setColumnHeaderView(label);

		textPane = new JTextPane();
		textPane.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
		scrollPane1.setViewportView(textPane);

		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(10, 379, 414, 68);
		contentPane.add(scrollPane2);

		JLabel label1 = new JLabel("請輸入訊息");
		label1.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
		scrollPane2.setColumnHeaderView(label1);

		textArea = new JTextArea();
		scrollPane2.setViewportView(textArea);

		JPanel jPanel2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) jPanel2.getLayout();
		flowLayout.setHgap(65);
		jPanel2.setBounds(10, 467, 414, 39);
		contentPane.add(jPanel2);

		btnPic = new JButton("圖片");
		btnPic.setFont(new Font("微軟正黑體", Font.PLAIN, 15));
		jPanel2.add(btnPic);

		btnSend = new JButton("傳送");
		btnSend.setFont(new Font("微軟正黑體", Font.PLAIN, 15));
		jPanel2.add(btnSend);

		btnSend.addActionListener(new act());
		btnPic.addActionListener(new act());

		btnCancel = new JButton("結束");
		btnCancel.setFont(new Font("微軟正黑體", Font.PLAIN, 15));
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frm.dispose();
			}
		});
		jPanel2.add(btnCancel);

		doc = textPane.getStyledDocument();

		style = doc.addStyle("StyleName", null);
		StyleConstants.setIcon(style, new ImageIcon("imagefile"));
	}

	public static void chooseFile() {
		JFileChooser f = new JFileChooser("."); // 查詢檔案

		f.setCurrentDirectory(new File("D://xampp/htdocs/HW/product_pic"));

		ExtensionFileFilter filter = new ExtensionFileFilter();
		filter.addExtension("jpg");
		filter.addExtension("jpeg");
		filter.addExtension("gif");
		filter.addExtension("png");

		filter.setDescription("檔案圖片(*.jpg, *.jpeg, *.gif, *png)");
		// 隱藏下拉列表中的 "所有檔案" 選項

		f.addChoosableFileFilter(filter);
		// 為檔案選擇器指定一個預覽圖片的附件

		int res = f.showOpenDialog(null);

		if (res == JFileChooser.APPROVE_OPTION) {
			path = f.getSelectedFile().getAbsolutePath();
			graph = true;
		}
	}
}

class ExtensionFileFilter extends FileFilter {
	private String description;
	private ArrayList<String> extensions = new ArrayList<>();

	// 自定義方法，用於新增檔案字尾名
	public void addExtension(String extension) {
		if (!extension.startsWith("."))
			extension = "." + extension;
		extensions.add(extension.toLowerCase());
	}

	// 用於設定該檔案過濾器的描述文字
	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public boolean accept(File file) {
		if (file.isDirectory())
			return true;
		String name = file.getName().toLowerCase();
		for (String extension : extensions) {
			if (name.endsWith(extension))
				return true;
		}
		return false;
	}
}