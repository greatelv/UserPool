package com.up;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListDataListener;
import javax.swing.table.DefaultTableModel;

import java.io.*;
import java.net.Socket;
import java.util.Enumeration;

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
	private final ButtonGroup userRegGenButtonGroup = new ButtonGroup();
	private final ButtonGroup userManGenButtonGroup = new ButtonGroup();
	private JTextField formMail;
	private JTextField formPhone;
	private JTextField formAddr;
	private JTextField formId;
	
	
	private JTextField formName2;
	private JTextField formMail2;
	private JTextField formPhone2;
	private JTextField formAddr2;
	private JTextField formId2;
	private final JRadioButton rBtnFemale2;
	private final JRadioButton rBtnmale2;
	final JComboBox<String> comboBox;
	
	final JButton btnDel;
	final JButton btnEdit;
	final JButton btnExport;

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
				isLoad =  false;
			}
		});

		try {
			//클라이언트 실행시 소켓 서버 커넥션 획득
			socket = new Socket("127.0.0.1", 9000);
			dataIn = new DataInputStream(new BufferedInputStream(socket
					.getInputStream()));
			dataOut = new DataOutputStream(new BufferedOutputStream(socket
					.getOutputStream()));

			//초기 회원 데이터 로드

			String[] userList = frame.getUserList();

			for(int i=0; i<userList.length; i++){
				UserPoolModel uModel = frame.parse.toModel(userList[i]);
				frame.model.addRow(new Object[]{uModel.getId(), uModel.getName(), uModel.getGender(), uModel.getPhone()});
				//comboBox.addItem(uModel.getId());
			}

			//최초 데이터 로딩 플래그
			isLoad = true;


		} catch (IOException ie) {
			ie.printStackTrace();
			stop();
		}
	}

	/*
	 * 회원정보를 서버로부터 조회 후
	 * 1. 회원정보의 jTable 동기화
	 * 2. 회원관리의 콤보박스 동기화
	 */
	public String[] getUserList(){
		String[] userList = {};
		try {

			//동기화 전 전체 테이블의 Row를 비워주는 로직
			int rows = model.getRowCount(); 
			for(int i = rows - 1; i >=0; i--)
			{
				model.removeRow(i); 
			}
			//comboBox.removeAll();
			//comboBox.addItem("선택하세요");


			String message = parse.joinMessage("getList","","","","","","");

			dataOut.writeUTF(message);
			dataOut.flush();

			userList = parse.toArray(dataIn.readUTF());

		} catch (IOException e) {
			e.printStackTrace();
		}

		return userList;
	}

	/**
	 * 회원관리에서 해당 아이디로 조회시 폼을 활성화 시키고 정보를 채워 넣는 메소드
	 * @param userId
	 */
	public void setUserInfo(UserPoolModel model){
		

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

		/**
		 * 탭 메뉴 패널 추가
		 */
		JPanel userRegTab = new JPanel();
		tabbedPane.addTab("\uD68C\uC6D0\uB4F1\uB85D", null, userRegTab, null);
		userRegTab.setLayout(null);
		
		JPanel userManTab = new JPanel();
		userManTab.setLayout(null);
		tabbedPane.addTab("\uD68C\uC6D0\uAD00\uB9AC", null, userManTab, null);
		
		JPanel copy = new JPanel();
		tabbedPane.addTab("만든이", null, copy, null);
		
		JLabel labelId = new JLabel("\uC544\uC774\uB514");
		labelId.setBounds(12, 26, 57, 15);
		userRegTab.add(labelId);

		formId = new JTextField();
		formId.setColumns(10);
		formId.setBounds(79, 23, 116, 21);
		userRegTab.add(formId);

		JLabel labelName = new JLabel("\uD68C\uC6D0\uBA85");
		labelName.setBounds(12, 66, 57, 15);
		userRegTab.add(labelName);
		
		
		JLabel labelGender = new JLabel("\uC131\uBCC4");
		labelGender.setBounds(12, 109, 37, 15);
		userRegTab.add(labelGender);

		JLabel labelMail = new JLabel("\uC774\uBA54\uC77C");
		labelMail.setBounds(12, 152, 57, 15);
		userRegTab.add(labelMail);

		formName = new JTextField();
		formName.setBounds(79, 63, 116, 21);
		userRegTab.add(formName);
		formName.setColumns(10);

		final JRadioButton rBtnFemale = new JRadioButton("여성");
		userRegGenButtonGroup.add(rBtnFemale);
		rBtnFemale.setBounds(79, 105, 57, 23);
		userRegTab.add(rBtnFemale);
		rBtnFemale.setSelected(true);

		final JRadioButton rBtnmale = new JRadioButton("남성");
		userRegGenButtonGroup.add(rBtnmale);
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
					//홈 유효성 검사
					if(formId.getText().equals("")){
						JOptionPane.showMessageDialog(null, "아이디를 입력해주세요.");
					}else if(formName.getText().equals("")){
						JOptionPane.showMessageDialog(null, "이름을 입력해주세요.");
					}else if(formMail.getText().equals("")){
						JOptionPane.showMessageDialog(null, "이메일을 입력해주세요.");
					}else if(formPhone.getText().equals("")){
						JOptionPane.showMessageDialog(null, "휴대폰번호를 입력해주세요.");
					}else if(formAddr.getText().equals("")){
						JOptionPane.showMessageDialog(null, "주소를 입력해주세요.");
					}else{
						//폼 유효성검사를 모두 통과 후

						String gender = getSelectedButtonText(userRegGenButtonGroup);
						String message = parse.joinMessage("post", 
								formId.getText(), formName.getText(), gender, formMail.getText(), formPhone.getText(), formAddr.getText()); 

						dataOut.writeUTF(message);
						dataOut.flush();

						boolean result = Boolean.parseBoolean(dataIn.readUTF());

						if(result){
							JOptionPane.showMessageDialog(null, "회원 등록이 완료되었습니다.");
							formId.setText("");
							formName.setText("");
							rBtnFemale.setSelected(true);
							formMail.setText("");
							formPhone.setText("");
							formAddr.setText("");
						}else{
							JOptionPane.showMessageDialog(null, "회원 등록에 문제가 발생했습니다.");
						}

					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}



			}
		});
		userRegTab.add(btnReg);
		

		JLabel labelName2 = new JLabel("\uD68C\uC6D0\uBA85");
		labelName2.setBounds(12, 66, 57, 15);
		userManTab.add(labelName2);
		

		JLabel labelGender2 = new JLabel("\uC131\uBCC4");
		labelGender2.setBounds(12, 109, 41, 15);
		userManTab.add(labelGender2);

		JLabel labelEmail2 = new JLabel("\uC774\uBA54\uC77C");
		labelEmail2.setBounds(12, 152, 57, 15);
		userManTab.add(labelEmail2);
		
		formId2 = new JTextField();
		formId2.setColumns(10);
		formId2.setBounds(79, 23, 116, 21);
		userManTab.add(formId2);
		formId2.setEditable(false);		//키값은 수정 불가능

		formName2 = new JTextField();
		formName2.setColumns(10);
		formName2.setBounds(79, 63, 116, 21);
		userManTab.add(formName2);

		rBtnFemale2 = new JRadioButton("\uC5EC\uC131");
		rBtnFemale2.setBounds(79, 105, 57, 23);
		userManGenButtonGroup.add(rBtnFemale2);
		userManTab.add(rBtnFemale2);
		rBtnFemale2.setSelected(true);

		rBtnmale2 = new JRadioButton("\uB0A8\uC131");
		rBtnmale2.setBounds(148, 105, 57, 23);
		userManGenButtonGroup.add(rBtnmale2);
		userManTab.add(rBtnmale2);

		formMail2 = new JTextField();
		formMail2.setColumns(10);
		formMail2.setBounds(79, 149, 184, 21);
		userManTab.add(formMail2);

		JLabel labelPhone2 = new JLabel("\uD734\uB300\uD3F0");
		labelPhone2.setBounds(12, 197, 57, 15);
		userManTab.add(labelPhone2);

		formPhone2 = new JTextField();
		formPhone2.setColumns(10);
		formPhone2.setBounds(79, 194, 184, 21);
		userManTab.add(formPhone2);

		JLabel labelAddr2 = new JLabel("\uC8FC\uC18C");
		labelAddr2.setBounds(12, 241, 57, 15);
		userManTab.add(labelAddr2);

		formAddr2 = new JTextField();
		formAddr2.setColumns(10);
		formAddr2.setBounds(79, 238, 184, 21);
		userManTab.add(formAddr2);



		JLabel labelId2 = new JLabel("\uC544\uC774\uB514");
		labelId2.setBounds(12, 26, 57, 15);
		userManTab.add(labelId2);

		
		/**
		 * 회원관리 - 회원 삭제
		 */
		btnDel = new JButton("삭제");
		btnDel.setBounds(0, 292, 195, 61);
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String message = parse.joinMessage("delete", formId2.getText(),"","","","","");

				try {
					dataOut.writeUTF(message);
					dataOut.flush();
					
					boolean result = Boolean.parseBoolean(dataIn.readUTF());

					if(result){
						JOptionPane.showMessageDialog(null, "회원 삭제가 완료되었습니다.");
						initUserManForm(comboBox);
					}else{
						JOptionPane.showMessageDialog(null, "회원 삭제에 문제가 발생했습니다.");
						initUserManForm(comboBox);
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnDel.setEnabled(false);
		userManTab.add(btnDel);

		/**
		 * 회원관리 - 회원 수정 
		 */
		
		btnEdit = new JButton("수정");
		btnEdit.setBounds(234, 292, 195, 61);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String gender = getSelectedButtonText(userManGenButtonGroup);
					String message = parse.joinMessage("edit", 
							formId2.getText(), formName2.getText(), gender, formMail2.getText(), formPhone2.getText(), formAddr2.getText()); 

					dataOut.writeUTF(message);
					dataOut.flush();
					
					boolean result = Boolean.parseBoolean(dataIn.readUTF());
					if(result){
						JOptionPane.showMessageDialog(null, "회원 수정이 완료되었습니다.");
					}else{
						JOptionPane.showMessageDialog(null, "회원 수정에 문제가 발생했습니다.");
						initUserManForm(comboBox);
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
		btnEdit.setEnabled(false);
		userManTab.add(btnEdit);

		/**
		 * 회원관리 - 회원 내보내기 
		 */
		btnExport = new JButton("\uB0B4\uBCF4\uB0B4\uAE30");
		btnExport.setBounds(319, 41, 98, 23);
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnExport.setEnabled(false);
		userManTab.add(btnExport);

		
		
		comboBox = new JComboBox();
		comboBox.setBounds(319, 10, 98, 21);
		userManTab.add(comboBox);
		comboBox.addItem("선택하세요");

		comboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {

				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					String selectedId = arg0.getItem().toString();

					if(!selectedId.equals("선택하세요")){
						String message = parse.joinMessage("getOne", selectedId,"","","","","");

						try {
							dataOut.writeUTF(message);
							dataOut.flush();

							UserPoolModel model = parse.toModel(dataIn.readUTF());
							
							if(model.getId() != null){
								//버튼 활성화
								btnDel.setEnabled(true);
								btnEdit.setEnabled(true);
								btnExport.setEnabled(true);
								
								System.out.println("mode get --> "+model.getId()+"||"+model.getName()+"||"+model.getEmail()+"||"+model.getPhone()+"||"+model.getAddress());
								
								//폼에 불러온 회원정보 설정
								formId2.setText(model.getId());
								formName2.setText(model.getName());
								formMail2.setText(model.getEmail());
								formPhone2.setText(model.getPhone());
								formAddr2.setText(model.getAddress());
								
								//성별 정보를 불러오기 전 Radio 버튼 초기화
								rBtnFemale2.setSelected(true);
								
								if(model.getGender().equals("남성")){
									rBtnmale2.setSelected(true);
								}else{
									rBtnFemale2.setSelected(true);
								}
							}else{
								JOptionPane.showMessageDialog(null, "회원정보를 불러오는데 실패했습니다.");
							}
							
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					} 

				}
			}
		});
		
		
		tabbedPane.addChangeListener(new ChangeListener() { //add the Listener

			@Override
			public void stateChanged(ChangeEvent arg0) {
				if(tabbedPane.getSelectedIndex()==0 && isLoad){	//회원정보의 테이블 초기화
					String[] userList = getUserList();

					for(int i=0; i<userList.length; i++){
						UserPoolModel uModel = parse.toModel(userList[i]);
						model.addRow(new Object[]{uModel.getId(), uModel.getName(), uModel.getGender(), uModel.getPhone()});
						//comboBox.addItem(uModel.getId());
					}					
					
				}else if(tabbedPane.getSelectedIndex()==2){	//회원관리의 콤보박스 초기화
					initUserManForm(comboBox);
				}

			}
		});
	}
	
	private void initUserManForm(JComboBox<String> combobox){
		combobox.removeAllItems();
		combobox.addItem("선택하세요");
		String[] userList = getUserList();

		for(int i=0; i<userList.length; i++){
			UserPoolModel uModel = parse.toModel(userList[i]);
			combobox.addItem(uModel.getId());
		}
		
		formId2.setText("");
		formName2.setText("");
		rBtnFemale2.setSelected(true);
		formMail2.setText("");
		formPhone2.setText("");
		formAddr2.setText("");
		
		btnDel.setEnabled(false);
		btnEdit.setEnabled(false);
		btnExport.setEnabled(false);
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

	/**
	 * Radio 버튼 중 어느것이 선택되었는가를 추출하는 외부 메소드
	 */

	public String getSelectedButtonText(ButtonGroup buttonGroup) {
		for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();

			if (button.isSelected()) {
				return button.getText();
			}
		}

		return null;
	}

}
