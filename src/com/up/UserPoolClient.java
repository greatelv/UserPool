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
			System.out.println("userList : "+userList.length);

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

			String userListMessage = dataIn.readUTF();

			if(!userListMessage.equals("")){
				userList = parse.toArray(userListMessage);
			}
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
		
		setBounds(100, 100, 450, 520);	//스윙 윈도우 사이즈
		setTitle("User Pool - 종합 회원관리시스템 ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		
		setContentPane(contentPane);

		JLabel logoLabel = new JLabel("User Pool");
		JTextPane descLabel = new JTextPane();
		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		logoLabel.setVerticalAlignment(SwingConstants.TOP);
		logoLabel.setForeground(Color.PINK);
		logoLabel.setFont(new Font("Arial", Font.PLAIN, 30));

		descLabel.setEditable(false);
		descLabel.setBackground(SystemColor.inactiveCaption);
		descLabel.setText(""
				+ "UserPool이란? \r\n"
				+ "종합회원관리 기능을 제공하는\r\n"
				+ "Swing 프로그램 입니다.");
		
		
		logoLabel.setBounds(23, 34, 142, 35);		
		descLabel.setBounds(188, 22, 234, 56);
		tabbedPane.setBounds(0, 99, 434, 382);

		contentPane.add(logoLabel);
		contentPane.add(descLabel);
		contentPane.add(tabbedPane);

		/**
		 * 탭 메뉴 패널 추가
		 */
		JPanel userInfoTab = new JPanel();
		JPanel userRegTab = new JPanel();
		JPanel userManTab = new JPanel();
		JPanel copy = new JPanel();

		userInfoTab.setLayout(new BoxLayout(userInfoTab, BoxLayout.X_AXIS));
		userRegTab.setLayout(null);
		userManTab.setLayout(null);
		copy.setLayout(null);
		
		tabbedPane.addTab("회원정보", userInfoTab);
		tabbedPane.addTab("회원등록", userRegTab);
		tabbedPane.addTab("회원관리", userManTab);
		tabbedPane.addTab("만든이", copy);
		
		/**
		 * ######### 회원정보 UI 컴포넌트 
		 */
		
		JScrollPane scrollPane = new JScrollPane();
		
		//회원정보의 테이블 프로퍼티 설정
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
		
		userInfoTab.add(scrollPane);

		//회원정보  - 라벨 초기화
		JLabel labelId = new JLabel("아이디");
		JLabel labelName = new JLabel("회원명");
		JLabel labelGender = new JLabel("성별");
		JLabel labelMail = new JLabel("이메일");
		JLabel labelPhone = new JLabel("휴대폰");
		JLabel labelAddr = new JLabel("주소");
		
		labelId.setBounds(12, 26, 57, 15);
		labelName.setBounds(12, 66, 57, 15);
		labelGender.setBounds(12, 109, 37, 15);
		labelMail.setBounds(12, 152, 57, 15);
		labelPhone.setBounds(12, 197, 57, 15);
		labelAddr.setBounds(12, 241, 57, 15);
		
		userRegTab.add(labelId);
		userRegTab.add(labelName);
		userRegTab.add(labelGender);
		userRegTab.add(labelMail);
		userRegTab.add(labelPhone);
		userRegTab.add(labelAddr);
		
		//회원정보 - 필드 초기화
		formId = new JTextField();
		formName = new JTextField();
		final JRadioButton rBtnFemale = new JRadioButton("여성");
		final JRadioButton rBtnmale = new JRadioButton("남성");
		formMail = new JTextField();
		formPhone = new JTextField();
		formAddr = new JTextField();
		JButton btnReg = new JButton("등록");
		
		formId.setColumns(10);
		formName.setColumns(10);
		formMail.setColumns(10);
		formPhone.setColumns(10);
		formAddr.setColumns(10);
		
		formId.setBounds(79, 23, 116, 21);
		formName.setBounds(79, 63, 116, 21);
		rBtnFemale.setBounds(79, 105, 57, 23);
		rBtnmale.setBounds(148, 105, 57, 23);
		formMail.setBounds(79, 149, 184, 21);
		formPhone.setBounds(79, 194, 184, 21);
		formAddr.setBounds(79, 238, 184, 21);
		btnReg.setBounds(0, 292, 429, 61);
		
		userRegTab.add(formId);
		userRegTab.add(formName);
		userRegTab.add(rBtnFemale);
		userRegTab.add(rBtnmale);
		userRegTab.add(formMail);
		userRegTab.add(formPhone);
		userRegTab.add(formAddr);

		userRegGenButtonGroup.add(rBtnFemale);
		userRegGenButtonGroup.add(rBtnmale);
		
		rBtnFemale.setSelected(true);

		/**
		 * 등록 버튼 핸들러
		 */
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
		
		/**
		 * ######### 회원등록 UI 컴포넌트 
		 */

		JLabel labelId2 = new JLabel("아이디");
		JLabel labelName2 = new JLabel("회원명");
		JLabel labelGender2 = new JLabel("성별");
		JLabel labelEmail2 = new JLabel("이메일");
		JLabel labelPhone2 = new JLabel("휴대폰");
		JLabel labelAddr2 = new JLabel("주소");
		
		
		labelId2.setBounds(12, 26, 57, 15);
		labelName2.setBounds(12, 66, 57, 15);
		labelGender2.setBounds(12, 109, 41, 15);
		labelEmail2.setBounds(12, 152, 57, 15);
		labelPhone2.setBounds(12, 197, 57, 15);
		labelAddr2.setBounds(12, 241, 57, 15);
		
		userManTab.add(labelId2);
		userManTab.add(labelName2);
		userManTab.add(labelGender2);
		userManTab.add(labelEmail2);
		userManTab.add(labelPhone2);
		userManTab.add(labelAddr2);

		formId2 = new JTextField();
		formName2 = new JTextField();
		rBtnFemale2 = new JRadioButton("여성");
		rBtnmale2 = new JRadioButton("남성");
		formMail2 = new JTextField();
		formPhone2 = new JTextField();
		formAddr2 = new JTextField();
		btnDel = new JButton("삭제");
		btnEdit = new JButton("수정");
		comboBox = new JComboBox();
		
		formId2.setColumns(10);
		formName2.setColumns(10);
		formMail2.setColumns(10);
		formPhone2.setColumns(10);
		formAddr2.setColumns(10);
		
		formId2.setBounds(79, 23, 116, 21);
		formName2.setBounds(79, 63, 116, 21);
		rBtnFemale2.setBounds(79, 105, 57, 23);
		rBtnmale2.setBounds(148, 105, 57, 23);
		formMail2.setBounds(79, 149, 184, 21);
		formPhone2.setBounds(79, 194, 184, 21);
		formAddr2.setBounds(79, 238, 184, 21);
		btnDel.setBounds(0, 292, 195, 61);
		btnEdit.setBounds(234, 292, 195, 61);
		comboBox.setBounds(319, 10, 98, 21);
		
		userManTab.add(formId2);
		userManTab.add(formName2);
		userManTab.add(rBtnFemale2);
		userManTab.add(rBtnmale2);
		userManTab.add(formMail2);		
		userManTab.add(formPhone2);
		userManTab.add(formAddr2);
		
		userManGenButtonGroup.add(rBtnFemale2);
		userManGenButtonGroup.add(rBtnmale2);

		
		formId2.setEditable(false);		//키값은 수정 불가능
		rBtnFemale2.setSelected(true);
		btnDel.setEnabled(false);
		btnEdit.setEnabled(false);
		comboBox.addItem("선택하세요");


		/**
		 * 삭제 버튼 핸들러
		 */
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
		
		userManTab.add(btnDel);

		/**
		 * 수정 버튼 핸들러 
		 */
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
		
		userManTab.add(btnEdit);

		/**
		 * 회원 선택 콤보박스 핸들러
		 */
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
		userManTab.add(comboBox);
		
		/**
		 * ######### 만든이 UI 컴포넌트 
		 */
		
		JLabel member1 = new JLabel("전태경 08109369");
		JLabel member1Pic = new JLabel("");
		JLabel member2 = new JLabel("조재영 08109375");
		JLabel member2Pic = new JLabel("");
		
		member1.setFont(new Font("굴림", Font.BOLD, 18));
		member2.setFont(new Font("굴림", Font.BOLD, 18));
		
		member1Pic.setIcon(new ImageIcon(this.getClass().getResource("/com/resource/jpeg.jpg")));
		member2Pic.setIcon(new ImageIcon(this.getClass().getResource("/com/resource/wrg.jpg")));
		
		member1.setBounds(32, 246, 160, 38);
		member1Pic.setBounds(32, 67, 160, 160);
		member2.setBounds(234, 246, 160, 38);
		member2Pic.setBounds(234, 67, 160, 160);
		
		copy.add(member1);
		copy.add(member1Pic);
		copy.add(member2);
		copy.add(member2Pic);


		tabbedPane.addChangeListener(new ChangeListener() { //add the Listener

			@Override
			public void stateChanged(ChangeEvent arg0) {
				if(tabbedPane.getSelectedIndex()==0 && isLoad){	//회원정보의 테이블 초기화
					String[] userList = getUserList();
					for(int i=0; i<userList.length; i++){
						UserPoolModel uModel = parse.toModel(userList[i]);
						model.addRow(new Object[]{uModel.getId(), uModel.getName(), uModel.getGender(), uModel.getPhone()});
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
