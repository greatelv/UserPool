package com.up;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import java.io.*;
import java.net.Socket;

public class UserPoolClient extends JFrame{

	private static Socket socket;
	private static DataInputStream dataIn;
	private static DataOutputStream dataOut;
	private static boolean isLoad = false;

	UserPoolParser parse = new UserPoolParser();


	private JPanel contentPane;
	private JTable table;

	private DefaultTableModel model = new DefaultTableModel(new Object[][] {}, new String[]{"회원아이디", "회원명", "성별", "전화번호"}) {

		Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class
		};

		public Class getColumnClass(int columnIndex) {
			return columnTypes[columnIndex];
		}
	};

	private JTextField formName;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField formMail;
	private JTextField formPhone;
	private JTextField formAddr;
	private JTextField formId;
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

			//초기 회원 데이터 로드
			frame.loadUserList();

			//최초 데이터 로딩 플래그
			isLoad = true;


		} catch (IOException ie) {
			ie.printStackTrace();
			stop();
		}
	}

	/*
	 * 회원정보를 서버로부터 조회해서 jTable에 동기화하는 메소드
	 */
	public void loadUserList(){
		try {
			
			//동기화 전 전체 테이블의 Row를 비워주는 로직
			int rows = model.getRowCount(); 
			for(int i = rows - 1; i >=0; i--)
			{
				model.removeRow(i); 
			}

			String message = parse.joinMessage("getList","","","","","","");

			dataOut.writeUTF(message);
			dataOut.flush();

			String[] userList = parse.toArray(dataIn.readUTF());

			for(int i=0; i<userList.length; i++){
				UserPoolModel uModel = parse.toModel(userList[i]);
				model.addRow(new Object[]{uModel.getId(), uModel.getName(), uModel.getGender(), uModel.getPhone()});
			}

		} catch (IOException e) {
			e.printStackTrace();
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

		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 99, 434, 382);
		contentPane.add(tabbedPane);


		tabbedPane.addChangeListener(new ChangeListener() { //add the Listener

			@Override
			public void stateChanged(ChangeEvent arg0) {
				System.out.println(tabbedPane.getSelectedIndex());

				if(tabbedPane.getSelectedIndex()==0 && isLoad) //Index starts at 0, so Index 2 = Tab3
				{
					loadUserList();
				}

			}
		});

		JPanel userInfoTab = new JPanel();
		tabbedPane.addTab("\uD68C\uC6D0\uC815\uBCF4", null, userInfoTab, null);
		userInfoTab.setLayout(new BoxLayout(userInfoTab, BoxLayout.X_AXIS));

		JScrollPane scrollPane = new JScrollPane();
		userInfoTab.add(scrollPane);

		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(false);
		table.setBorder(null);
		table.setModel(model);
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

		JLabel labelName = new JLabel("\uD68C\uC6D0\uBA85");
		labelName.setBounds(12, 66, 57, 15);
		userRegTab.add(labelName);

		JLabel labelGender = new JLabel("\uC131\uBCC4");
		labelGender.setBounds(12, 109, 24, 15);
		userRegTab.add(labelGender);

		JLabel labelMail = new JLabel("\uC774\uBA54\uC77C");
		labelMail.setBounds(12, 152, 57, 15);
		userRegTab.add(labelMail);

		formName = new JTextField();
		formName.setBounds(79, 63, 116, 21);
		userRegTab.add(formName);
		formName.setColumns(10);

		JRadioButton rBtnFemale = new JRadioButton("여성");
		buttonGroup.add(rBtnFemale);
		rBtnFemale.setBounds(79, 105, 57, 23);
		userRegTab.add(rBtnFemale);

		JRadioButton rBtnmale = new JRadioButton("남성");
		buttonGroup.add(rBtnmale);
		rBtnmale.setBounds(148, 105, 57, 23);
		userRegTab.add(rBtnmale);

		formMail = new JTextField();
		formMail.setBounds(79, 149, 184, 21);
		userRegTab.add(formMail);
		formMail.setColumns(10);

		JLabel labelPhone = new JLabel("\uD734\uB300\uD3F0");
		labelPhone.setBounds(12, 197, 57, 15);
		userRegTab.add(labelPhone);

		formPhone = new JTextField();
		formPhone.setColumns(10);
		formPhone.setBounds(79, 194, 184, 21);
		userRegTab.add(formPhone);

		JLabel labelAddr = new JLabel("\uC8FC\uC18C");
		labelAddr.setBounds(12, 241, 57, 15); 
		userRegTab.add(labelAddr);

		formAddr = new JTextField();
		formAddr.setColumns(10);
		formAddr.setBounds(79, 238, 184, 21);
		userRegTab.add(formAddr);

		/**
		 * 회원등록 - 회원 등록
		 */
		JButton btnReg = new JButton("등록");
		btnReg.setBounds(0, 292, 429, 61);
		btnReg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					String message = parse.joinMessage("post", 
							formId.getText(), formName.getText(), "남성", formMail.getText(), formPhone.getText(), formAddr.getText()); 

					dataOut.writeUTF(message);
					dataOut.flush();

					boolean result = Boolean.parseBoolean(dataIn.readUTF());

					if(result){
						JOptionPane.showMessageDialog(null, "회원 등록이 완료되었습니다.");
					}else{
						JOptionPane.showMessageDialog(null, "회원 등록에 문제가 발생했습니다.");
					}

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		userRegTab.add(btnReg);


		JLabel labelId = new JLabel("\uC544\uC774\uB514");
		labelId.setBounds(12, 26, 57, 15);
		userRegTab.add(labelId);

		formId = new JTextField();
		formId.setColumns(10);
		formId.setBounds(79, 23, 116, 21);
		userRegTab.add(formId);

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


		/**
		 * 회원관리 - 회원 삭제
		 */
		JButton btnDel = new JButton("삭제");
		btnDel.setBounds(0, 292, 195, 61);
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		userManTab.add(btnDel);

		/**
		 * 회원관리 - 회원 수정 
		 */
		JButton btnEdit = new JButton("수정");
		btnEdit.setBounds(234, 292, 195, 61);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String message = parse.joinMessage("getOne", "","","","","","");

					dataOut.writeUTF(message);
					dataOut.flush();
					String userData = dataIn.readUTF();

					System.out.println("userData : "+userData);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
		userManTab.add(btnEdit);

		/**
		 * 회원관리 - 회원 내보내기 
		 */
		JButton btnExport = new JButton("\uB0B4\uBCF4\uB0B4\uAE30");
		btnExport.setBounds(319, 41, 98, 23);
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		userManTab.add(btnExport);


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
