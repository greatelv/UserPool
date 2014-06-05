package com.up;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextPane;
import java.awt.SystemColor;
import javax.swing.JMenu;
import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class UserPoolClient extends JFrame {

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
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserPoolClient frame = new UserPoolClient();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UserPoolClient() {
		setTitle("User Pool - \uC885\uD569 \uD68C\uC6D0\uAD00\uB9AC\uC2DC\uC2A4\uD15C");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 520);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("User Pool");
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setForeground(Color.PINK);
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 30));
		lblNewLabel.setBounds(23, 34, 142, 35);
		contentPane.add(lblNewLabel);
		
		JTextPane txtpnUserpool = new JTextPane();
		txtpnUserpool.setEditable(false);
		txtpnUserpool.setBackground(SystemColor.inactiveCaption);
		txtpnUserpool.setText("UserPool\uC774\uB780? \r\n\uC885\uD569\uD68C\uC6D0\uAD00\uB9AC \uAE30\uB2A5\uC744 \uC81C\uACF5\uD558\uB294\r\nSwing \uD504\uB85C\uADF8\uB7A8 \uC785\uB2C8\uB2E4.");
		txtpnUserpool.setBounds(188, 22, 234, 56);
		contentPane.add(txtpnUserpool);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 99, 434, 382);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("\uD68C\uC6D0\uC815\uBCF4", null, panel, null);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		
		table = new JTable();
		table.setEnabled(false);
		table.setRowSelectionAllowed(false);
		table.setBorder(null);
		table.setFillsViewportHeight(true);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"wjswjs2", "\uC804\uD0DC\uACBD", "\uB0A8\uC131", "010-5026-3671"},
				{"", null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"\uD68C\uC6D0\uC544\uC774\uB514", "\uD68C\uC6D0\uBA85", "\uC131\uBCC4", "\uC804\uD654\uBC88\uD638"
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
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("\uD68C\uC6D0\uB4F1\uB85D", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel label = new JLabel("\uD68C\uC6D0\uBA85");
		label.setBounds(12, 66, 57, 15);
		panel_1.add(label);
		
		JLabel label_1 = new JLabel("\uC131\uBCC4");
		label_1.setBounds(12, 109, 24, 15);
		panel_1.add(label_1);
		
		JLabel label_2 = new JLabel("\uC774\uBA54\uC77C");
		label_2.setBounds(12, 152, 57, 15);
		panel_1.add(label_2);
		
		textField = new JTextField();
		textField.setBounds(79, 63, 116, 21);
		panel_1.add(textField);
		textField.setColumns(10);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("\uC5EC\uC131");
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setBounds(79, 105, 57, 23);
		panel_1.add(rdbtnNewRadioButton);
		
		JRadioButton radioButton = new JRadioButton("\uB0A8\uC131");
		buttonGroup.add(radioButton);
		radioButton.setBounds(148, 105, 57, 23);
		panel_1.add(radioButton);
		
		textField_1 = new JTextField();
		textField_1.setBounds(79, 149, 184, 21);
		panel_1.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel label_3 = new JLabel("\uD734\uB300\uD3F0");
		label_3.setBounds(12, 197, 57, 15);
		panel_1.add(label_3);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(79, 194, 184, 21);
		panel_1.add(textField_2);
		
		JLabel label_4 = new JLabel("\uC8FC\uC18C");
		label_4.setBounds(12, 241, 57, 15); 
		panel_1.add(label_4);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(79, 238, 184, 21);
		panel_1.add(textField_3);
		
		JButton btnNewButton = new JButton("\uB4F1\uB85D");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(0, 292, 429, 61);
		panel_1.add(btnNewButton);
		
		JLabel label_5 = new JLabel("\uC544\uC774\uB514");
		label_5.setBounds(12, 26, 57, 15);
		panel_1.add(label_5);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(79, 23, 116, 21);
		panel_1.add(textField_4);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		tabbedPane.addTab("\uD68C\uC6D0\uAD00\uB9AC", null, panel_2, null);
		
		JLabel label_6 = new JLabel("\uD68C\uC6D0\uBA85");
		label_6.setBounds(12, 66, 57, 15);
		panel_2.add(label_6);
		
		JLabel label_7 = new JLabel("\uC131\uBCC4");
		label_7.setBounds(12, 109, 24, 15);
		panel_2.add(label_7);
		
		JLabel label_8 = new JLabel("\uC774\uBA54\uC77C");
		label_8.setBounds(12, 152, 57, 15);
		panel_2.add(label_8);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(79, 63, 116, 21);
		panel_2.add(textField_5);
		
		JRadioButton radioButton_1 = new JRadioButton("\uC5EC\uC131");
		radioButton_1.setBounds(79, 105, 57, 23);
		panel_2.add(radioButton_1);
		
		JRadioButton radioButton_2 = new JRadioButton("\uB0A8\uC131");
		radioButton_2.setBounds(148, 105, 57, 23);
		panel_2.add(radioButton_2);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(79, 149, 184, 21);
		panel_2.add(textField_6);
		
		JLabel label_9 = new JLabel("\uD734\uB300\uD3F0");
		label_9.setBounds(12, 197, 57, 15);
		panel_2.add(label_9);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(79, 194, 184, 21);
		panel_2.add(textField_7);
		
		JLabel label_10 = new JLabel("\uC8FC\uC18C");
		label_10.setBounds(12, 241, 57, 15);
		panel_2.add(label_10);
		
		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBounds(79, 238, 184, 21);
		panel_2.add(textField_8);
		
		JButton button = new JButton("\uC218\uC815");
		button.setBounds(234, 292, 195, 61);
		panel_2.add(button);
		
		JLabel label_11 = new JLabel("\uC544\uC774\uB514");
		label_11.setBounds(12, 26, 57, 15);
		panel_2.add(label_11);
		
		textField_9 = new JTextField();
		textField_9.setColumns(10);
		textField_9.setBounds(79, 23, 116, 21);
		panel_2.add(textField_9);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(319, 10, 98, 21);
		panel_2.add(comboBox);
		
		JButton button_1 = new JButton("\uC0AD\uC81C");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		button_1.setBounds(0, 292, 195, 61);
		panel_2.add(button_1);
		
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("\uB9CC\uB4E0\uC774", null, panel_3, null);
	}
}
