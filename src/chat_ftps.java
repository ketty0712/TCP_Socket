//chat_ftps
// import java.awt.*;
// import java.io.*;
// import java.net.*;
// import java.util.*;
// import javax.swing.filechooser.FileFilter;
// import java.awt.event.*;
// import javax.swing.*;
// import javax.swing.border.*;
// import javax.swing.text.*;

// public class chat_ftps {

// 	static JFrame frm = new JFrame("Client");

// 	static JPanel contentPane;
// 	static JTextField tfIP, tfPort;
// 	static JButton btnConnect, btnPic, btnSend, btnCancel;
// 	static JTextPane textPane;
// 	static JTextArea textArea;
// 	static boolean start_tcp = false;

// 	/**
// 	 * Launch the application.
// 	 */
// 	public static void main(String[] args) throws Exception {
// 		CreateFrame();
// 		frm.setVisible(true);
// 		// doc.insertString(doc.getLength(), "請輸入 Server IP 和 Server Port: ", null);
// 		// if (start_tcp)
// 		new chat_ftps().Start();
// 	}

// 	/**
// 	 * TCP Connect
// 	 */
// 	static DataOutputStream output;
// 	static DataOutputStream output2;
// 	static Socket socket1, socket2;
// 	static String IP, Port;
// 	static int port = 9000;

// 	public void Start() {
// 		try {

// 			socket1 = new Socket("localhost", port);

// 			DataInputStream input = new DataInputStream(socket1.getInputStream());
// 			tfIP.setText(socket1.getInetAddress().getHostAddress().toString());
// 			tfPort.setText(socket1.getPort() + "");
// 			output = new DataOutputStream(socket1.getOutputStream());

// 			socket2 = new Socket("localhost", port + 1);

// 			output2 = new DataOutputStream(socket2.getOutputStream());
// 			Receiver(input);
// 			// Receiver2();

// 			// 伺服器地址

// 		} catch (Exception e) {
// 			System.out.println("Client 異常: " + e.getMessage());
// 		}
// 	}

// 	/**
// 	 * 前面的操作都是傳送文字指令或資訊給連線的另一端。
// 	 * 現在需要適當的改寫一下，就可以作檔案傳送。
// 	 * 主要是使用PrintStream的write()方法，它負責將位元或 int 傳送給連線的另一端。
// 	 */
// 	public void Receiver(final DataInputStream input) {
// 		new Thread() {
// 			@Override
// 			public void run() {
// 				super.run();
// 				String msg = null;
// 				try {
// 					while ((msg = input.readUTF()) != null) {
// 						System.out.println("Server: " + msg);
// 						String[] sa = msg.split("\n");

// 						doc.insertString(doc.getLength(), "Server: \t", null);
// 						boolean f = false;
// 						for (String s : sa) {
// 							if (f)
// 								doc.insertString(doc.getLength(), "\t" + s + "\n", null);
// 							else {
// 								doc.insertString(doc.getLength(), s + "\n", null);
// 								f = true;
// 							}
// 						}

// 					}
// 				} catch (Exception e) {
// 					System.out.println("Client 端接收異常: " + e.getMessage());
// 				}
// 			}
// 		}.start();

// 	}

// 	public void Receiver2() {
// 		new Thread() {
// 			@Override
// 			public void run() {
// 				super.run();
// 				try {
// 					BufferedInputStream inputStream = new BufferedInputStream(socket2.getInputStream());
// 					BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream("D://4.jpg"));
// 					int readin;
// 					while ((readin = inputStream.read()) != -1) {
// 						outputStream.write(readin);
// 						Thread.yield();
// 					}
// 					outputStream.flush();
// 					outputStream.close();
// 					inputStream.close();
// 					InsertPic("Server");

// 				} catch (Exception e) {
// 					System.out.println("Client 端接收圖片異常: " + e.getMessage());
// 				}
// 			}
// 		}.start();

// 	}

// 	/**
// 	 * Create the frame.
// 	 */
// 	public static void CreateFrame() {
// 		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
// 		frm.setBounds(100, 100, 450, 570);
// 		contentPane = new JPanel();
// 		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
// 		frm.setContentPane(contentPane);
// 		contentPane.setLayout(null);

// 		JPanel panel = new JPanel();
// 		panel.setBorder(new CompoundBorder());
// 		panel.setBounds(10, 25, 235, 68);
// 		contentPane.add(panel);
// 		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

// 		JLabel jLabel1 = new JLabel("Server IP: ");
// 		jLabel1.setFont(new Font("Consolas", Font.PLAIN, 16));
// 		panel.add(jLabel1);

// 		tfIP = new JTextField();
// 		tfIP.setFont(new Font("Consolas", Font.PLAIN, 14));
// 		panel.add(tfIP);
// 		tfIP.setColumns(10);

// 		JLabel jLabel2 = new JLabel("Server Port: ");
// 		jLabel2.setFont(new Font("Consolas", Font.PLAIN, 16));
// 		panel.add(jLabel2);

// 		tfPort = new JTextField();
// 		tfPort.setFont(new Font("Consolas", Font.PLAIN, 14));
// 		panel.add(tfPort);
// 		tfPort.setColumns(10);

// 		JScrollPane scrollPane1 = new JScrollPane();
// 		scrollPane1.setBounds(10, 103, 414, 266);
// 		contentPane.add(scrollPane1);

// 		JLabel label = new JLabel("聊天室");
// 		label.setHorizontalAlignment(SwingConstants.CENTER);
// 		label.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
// 		scrollPane1.setColumnHeaderView(label);

// 		textPane = new JTextPane();
// 		textPane.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
// 		scrollPane1.setViewportView(textPane);

// 		JScrollPane scrollPane2 = new JScrollPane();
// 		scrollPane2.setBounds(10, 379, 414, 68);
// 		contentPane.add(scrollPane2);

// 		JLabel label1 = new JLabel("請輸入訊息");
// 		label1.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
// 		scrollPane2.setColumnHeaderView(label1);

// 		textArea = new JTextArea();
// 		scrollPane2.setViewportView(textArea);

// 		JPanel jPanel2 = new JPanel();
// 		FlowLayout flowLayout = (FlowLayout) jPanel2.getLayout();
// 		flowLayout.setHgap(75);
// 		jPanel2.setBounds(10, 467, 414, 39);
// 		contentPane.add(jPanel2);

// 		btnPic = new JButton("圖片");
// 		btnPic.setFont(new Font("微軟正黑體", Font.PLAIN, 15));
// 		jPanel2.add(btnPic);

// 		btnSend = new JButton("傳送");
// 		btnSend.setFont(new Font("微軟正黑體", Font.PLAIN, 15));
// 		jPanel2.add(btnSend);

// 		// 在建立傳輸之前，令其他功能失效
// 		// btnSend.setEnabled(false);
// 		// btnPic.setEnabled(false);

// 		btnSend.addActionListener(new act());
// 		btnPic.addActionListener(new act());

// 		btnCancel = new JButton("結束");
// 		btnCancel.setFont(new Font("微軟正黑體", Font.PLAIN, 15));
// 		jPanel2.add(btnCancel);

// 		btnConnect = new JButton("建立連線");
// 		btnConnect.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
// 		btnConnect.setBounds(298, 41, 101, 29);
// 		btnConnect.addActionListener(new act());
// 		contentPane.add(btnConnect);

// 		btnCancel.addActionListener(new ActionListener() {
// 			@Override
// 			public void actionPerformed(ActionEvent e) {
// 				frm.dispose();
// 			}
// 		});
// 		doc = textPane.getStyledDocument();

// 		style = doc.addStyle("StyleName", null);
// 		StyleConstants.setIcon(style, new ImageIcon("imagefile"));
// 	}

// 	static StyledDocument doc;
// 	static Style style;

// 	static class act implements ActionListener {
// 		public void actionPerformed(ActionEvent e) {
// 			// if (e.getSource() == btnConnect) {
// 			// // 如果還未建立連線
// 			// try {
// 			// // socket1 = new Socket(tfIP.getText(), Integer.parseInt(tfPort.getText()));

// 			// start_tcp = true;
// 			// btnSend.setEnabled(true);
// 			// btnPic.setEnabled(true);
// 			// btnConnect.setEnabled(false);
// 			// } catch (NumberFormatException | IOException e1) {
// 			// System.out.println("建立連線失敗!!" + e1. getMessage());
// 			// }
// 			// }
// 			if (e.getSource() == btnSend) {
// 				if (graph) {
// 					// 將選取的圖片顯示到自己的訊息欄中
// 					InsertIcon();

// 					// 傳送圖片
// 					try {
// 						PrintStream printStream = new PrintStream(socket2.getOutputStream());
// 						BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(path));
// 						int readin;
// 						while ((readin = inputStream.read()) != -1) {
// 							printStream.write(readin);
// 							Thread.yield();
// 						}
// 						printStream.flush();
// 						printStream.close();
// 						inputStream.close();
// 						System.out.println(path);
// 					} catch (Exception ex) {
// 						System.out.println("Client 傳送圖片異常: " + ex.getMessage());
// 					}
// 					System.out.println("success!!");
// 					graph = false;
// 					path = null;
// 				} else {
// 					try {
// 						String message = textArea.getText();
// 						output.writeUTF(message);

// 						String[] sa = message.split("\n");

// 						doc.insertString(doc.getLength(), "Client: \t", null);
// 						boolean f = false;
// 						for (String s : sa) {
// 							if (f)
// 								doc.insertString(doc.getLength(), "\t" + s + "\n", null);
// 							else {
// 								doc.insertString(doc.getLength(), s + "\n", null);
// 								f = true;
// 							}
// 						}

// 						textArea.setText("");
// 					} catch (Exception ex) {
// 						System.out.println("Client端傳送異常: " + ex.getMessage());
// 					}
// 				}

// 			}

// 			else if (e.getSource() == btnPic) {
// 				chooseFile();
// 			}
// 		}
// 	}

// 	static String path = null;
// 	static boolean graph = false;

// 	public static void InsertPic(String who) {
// 		try {
// 			doc.insertString(doc.getLength(), who + ": \t", null);
// 		} catch (Exception ex) {
// 			System.out.println(ex.getMessage());
// 		}
// 		ImageIcon image = new ImageIcon("D://4.jpg");
// 		textPane.setCaretPosition(doc.getLength());
// 		image.setImage(image.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
// 		textPane.insertIcon(image);
// 		try {
// 			doc.insertString(doc.getLength(), "\n", null);
// 		} catch (Exception ex) {
// 			System.out.println(ex.getMessage());
// 		}
// 	}

// 	public static void InsertIcon() {
// 		try {
// 			if (path == null)
// 				throw new Exception("您還未選取任何圖片: ");

// 			doc.insertString(doc.getLength(), "Client: \t", null);
// 			ImageIcon image = new ImageIcon(path);
// 			textPane.setCaretPosition(doc.getLength());
// 			image.setImage(image.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
// 			textPane.insertIcon(image);
// 			doc.insertString(doc.getLength(), "\n", null);

// 		} catch (Exception ex) {
// 			System.out.println("Client端匯入圖片異常: " + ex.getMessage()); 
// 		}
// 	}

// 	public static void chooseFile() {
// 		JFileChooser f = new JFileChooser("."); // 查詢檔案

// 		f.setCurrentDirectory(new File("D://xampp//htdocs//HW//product_pic"));

// 		ExtensionFileFilter filter = new ExtensionFileFilter();
// 		filter.addExtension("jpg");
// 		filter.addExtension("jpeg");
// 		filter.addExtension("gif");
// 		filter.addExtension("png");

// 		filter.setDescription("檔案圖片(*.jpg, *.jpeg, *.gif, *png)");
// 		// 隱藏下拉列表中的 "所有檔案" 選項

// 		f.addChoosableFileFilter(filter);
// 		// 為檔案選擇器指定一個預覽圖片的附件

// 		int res = f.showOpenDialog(null);

// 		if (res == JFileChooser.APPROVE_OPTION) {
// 			path = f.getSelectedFile().getAbsolutePath();
// 			graph = true;
// 		}
// 	}
// }

// class ExtensionFileFilter extends FileFilter {
// 	private String description;
// 	private ArrayList<String> extensions = new ArrayList<>();

// 	// 自定義方法，用於新增檔案字尾名
// 	public void addExtension(String extension) {
// 		if (!extension.startsWith("."))
// 			extension = "." + extension;
// 		extensions.add(extension.toLowerCase());
// 	}

// 	// 用於設定該檔案過濾器的描述文字
// 	public void setDescription(String description) {
// 		this.description = description;
// 	}

// 	public String getDescription() {
// 		return description;
// 	}

// 	public boolean accept(File file) {
// 		if (file.isDirectory())
// 			return true;
// 		String name = file.getName().toLowerCase();
// 		for (String extension : extensions) {
// 			if (name.endsWith(extension))
// 				return true;
// 		}
// 		return false;
// 	}
// }
