package com.up;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.io.*;
import java.net.Socket;

public class UserPoolClient extends JFrame{

	private static Socket socket;
	private static DataInputStream dataIn;
	private static DataOutputStream dataOut;


	private JPanel contentPane;
	private JTable table;
	private JTextField textField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		UserPoolClient frame = new UserPoolClient();
		frame.setVisible(true);
		
		//클라이언트 창이 닫혀질 시 이벤트 정의
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				stop();
			}
		});

		try {
			//클라이언트 실행시 소켓 서버 커넥션 획득
			System.out.println("해피");

			socket = new Socket("127.0.0.1", 9000);
			dataIn = new DataInputStream(new BufferedInputStream(socket
					.getInputStream()));
			dataOut = new DataOutputStream(new BufferedOutputStream(socket
					.getOutputStream()));

		} catch (IOException ie) {
			ie.printStackTrace();
			stop();
		}
	}

	/**
	 * Create the frame.
	 */
	public UserPoolClient() {
		setTitle("User Pool - 종합 회원관리시스템 ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 520);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel logoLabel = new JLabel("User Pool");
		logoLabel.setVerticalAlignment(SwingConstants.TOP);
		logoLabel.setForeground(Color.PINK);
		logoLabel.setFont(new Font("Arial", Font.PLAIN, 30));
		logoLabel.setBounds(23, 34, 142, 35);
		contentPane.add(logoLabel);

		JTextPane descLabel = new JTextPane();
		descLabel.setEditable(false);
		descLabel.setBackground(SystemColor.inactiveCaption);
		descLabel.setText("UserPool\uC774\uB780? \r\n\uC885\uD569\uD68C\uC6D0\uAD00\uB9AC \uAE30\uB2A5\uC744 \uC81C\uACF5\uD558\uB294\r\nSwing \uD504\uB85C\uADF8\uB7A8 \uC785\uB2C8\uB2E4.");
		descLabel.setBounds(188, 22, 234, 56);
		contentPane.add(descLabel);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 99, 434, 382);
		contentPane.add(tabbedPane);

		JPanel userInfoTab = new JPanel();
		tabbedPane.addTab("\uD68C\uC6D0\uC815\uBCF4", null, userInfoTab, null);
		userInfoTab.setLayout(new BoxLayout(userInfoTab, BoxLayout.X_AXIS));

		JScrollPane scrollPane = new JScrollPane();
		userInfoTab.add(scrollPane);

		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(false);
		table.setBorder(null);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"wjswjs2", "\uC804\uD0DC\uACBD", "\uB0A8\uC131", "010-5026-3671"},
			},
			new String[] {
				"회원아이디", "\uD68C\uC6D0\uBA85", "\uC131\uBCC4", "\uC804\uD654\uBC88\uD638"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(60);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(3).setPreferredWidth(90);
		scrollPane.setViewportView(table);

		JPanel userRegTab = new JPanel();
		tabbedPane.addTab("\uD68C\uC6D0\uB4F1\uB85D", null, userRegTab, null);
		userRegTab.setLayout(null);

		JLabel label = new JLabel("\uD68C\uC6D0\uBA85");
		label.setBounds(12, 66, 57, 15);
		userRegTab.add(label);

		JLabel label_1 = new JLabel("\uC131\uBCC4");
		label_1.setBounds(12, 109, 24, 15);
		userRegTab.add(label_1);

		JLabel label_2 = new JLabel("\uC774\uBA54\uC77C");
		label_2.setBounds(12, 152, 57, 15);
		userRegTab.add(label_2);

		textField = new JTextField();
		textField.setBounds(79, 63, 116, 21);
		userRegTab.add(textField);
		textField.setColumns(10);

		JRadioButton rBtnFemale = new JRadioButton("\uC5EC\uC131");
		buttonGroup.add(rBtnFemale);
		rBtnFemale.setBounds(79, 105, 57, 23);
		userRegTab.add(rBtnFemale);

		JRadioButton rBtnmale = new JRadioButton("\uB0A8\uC131");
		buttonGroup.add(rBtnmale);
		rBtnmale.setBounds(148, 105, 57, 23);
		userRegTab.add(rBtnmale);

		textField_1 = new JTextField();
		textField_1.setBounds(79, 149, 184, 21);
		userRegTab.add(textField_1);
		textField_1.setColumns(10);

		JLabel label_3 = new JLabel("\uD734\uB300\uD3F0");
		label_3.setBounds(12, 197, 57, 15);
		userRegTab.add(label_3);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(79, 194, 184, 21);
		userRegTab.add(textField_2);

		JLabel label_4 = new JLabel("\uC8FC\uC18C");
		label_4.setBounds(12, 241, 57, 15); 
		userRegTab.add(label_4);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(79, 238, 184, 21);
		userRegTab.add(textField_3);

		JButton btnNewButton = new JButton("등록");
		
		//유저 등록 버튼 이벤트 핸들러
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					dataOut.writeUTF("1");
					dataOut.flush();
					
					//System.out.println(dataIn.readUTF());
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton.setBounds(0, 292, 429, 61);
		userRegTab.add(btnNewButton);

		JLabel label_5 = new JLabel("\uC544\uC774\uB514");
		label_5.setBounds(12, 26, 57, 15);
		userRegTab.add(label_5);

		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(79, 23, 116, 21);
		userRegTab.add(textField_4);

		JPanel userManTab = new JPanel();
		userManTab.setLayout(null);
		tabbedPane.addTab("\uD68C\uC6D0\uAD00\uB9AC", null, userManTab, null);

		JLabel label_6 = new JLabel("\uD68C\uC6D0\uBA85");
		label_6.setBounds(12, 66, 57, 15);
		userManTab.add(label_6);

		JLabel label_7 = new JLabel("\uC131\uBCC4");
		label_7.setBounds(12, 109, 24, 15);
		userManTab.add(label_7);

		JLabel label_8 = new JLabel("\uC774\uBA54\uC77C");
		label_8.setBounds(12, 152, 57, 15);
		userManTab.add(label_8);

		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(79, 63, 116, 21);
		userManTab.add(textField_5);

		JRadioButton radioButton_1 = new JRadioButton("\uC5EC\uC131");
		radioButton_1.setBounds(79, 105, 57, 23);
		userManTab.add(radioButton_1);

		JRadioButton radioButton_2 = new JRadioButton("\uB0A8\uC131");
		radioButton_2.setBounds(148, 105, 57, 23);
		userManTab.add(radioButton_2);

		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(79, 149, 184, 21);
		userManTab.add(textField_6);

		JLabel label_9 = new JLabel("\uD734\uB300\uD3F0");
		label_9.setBounds(12, 197, 57, 15);
		userManTab.add(label_9);

		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(79, 194, 184, 21);
		userManTab.add(textField_7);

		JLabel label_10 = new JLabel("\uC8FC\uC18C");
		label_10.setBounds(12, 241, 57, 15);
		userManTab.add(label_10);

		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBounds(79, 238, 184, 21);
		userManTab.add(textField_8);

		JButton button = new JButton("삭제");
		button.setBounds(234, 292, 195, 61);
		userManTab.add(button);

		JLabel label_11 = new JLabel("\uC544\uC774\uB514");
		label_11.setBounds(12, 26, 57, 15);
		userManTab.add(label_11);

		textField_9 = new JTextField();
		textField_9.setColumns(10);
		textField_9.setBounds(79, 23, 116, 21);
		userManTab.add(textField_9);

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(319, 10, 98, 21);
		userManTab.add(comboBox);

		JButton button_1 = new JButton("수정");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				try {
					dataOut.writeUTF("2");
					dataOut.flush();
					String userData = dataIn.readUTF();
					
					System.out.println("userData : "+userData);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
		button_1.setBounds(0, 292, 195, 61);
		userManTab.add(button_1);

		JPanel copy = new JPanel();
		tabbedPane.addTab("\uB9CC\uB4E0\uC774", null, copy, null);
	}

	/*
	 * method : stop() 
	 * 모든 연결을 끊고 소켓을 닫음 
	 * 위에서 IOException을 처리하기 위해서 호출 가능
	 */
	public static void stop() {
		try {
			dataIn.close();
			dataOut.close();
			socket.close();

		} catch (IOException e) {
			//label1.setText("Error : " + e.toString());
		}
	}
}
