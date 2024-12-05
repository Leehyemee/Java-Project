package start;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;


public class MemMain extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	static Dto dto;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {//멀티스레드타입으로 진행.
				try {
					MemMain frame = new MemMain(dto);
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
	@SuppressWarnings({ "unchecked", "unchecked" })
	public MemMain(Dto dto) {
		setTitle("예매 창");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 932, 553);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("예매");
		lblNewLabel.setFont(new Font("돋움체", Font.BOLD, 20));
		lblNewLabel.setBounds(87, 33, 150, 55);
		contentPane.add(lblNewLabel);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"어른1명", "어른2명", "어른3명", "어른4명", "어른5명", "어른6명", "어른7명"}));
		comboBox.setBounds(90, 197, 125, 26);
		contentPane.add(comboBox);
		
		
		JLabel lblNewLabel_1 = new JLabel("인원정보");
		lblNewLabel_1.setFont(new Font("굴림체", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(90, 149, 100, 26);
		contentPane.add(lblNewLabel_1);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"6~12세", "어린이1명", "어린이2명", "어린이3명", "어린이4명", "어린이5명", "어린이6명", "어린이7명"}));
		comboBox_1.setBounds(90, 240, 125, 26);
		contentPane.add(comboBox_1);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"6세 미만", "유아 1명", "유아 2명", "유아 3명", "유아 4명", "유아 5명", "유아 6명", "유아 7명"}));
		comboBox_2.setBounds(90, 285, 125, 26);
		contentPane.add(comboBox_2);
		JRadioButton rdbtnNewRadioButton = new JRadioButton("전체");
		rdbtnNewRadioButton.setBounds(379, 100, 65, 22);
		
		//기본값으로 지정된 라디오버튼
		rdbtnNewRadioButton.setSelected(true);
		
		contentPane.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnKts = new JRadioButton("KTX/SRT");
		rdbtnKts.setBounds(465, 98, 100, 24);
		contentPane.add(rdbtnKts);
		
		JRadioButton rdbtnNewRadioButton_1_1 = new JRadioButton("새마을호");
		rdbtnNewRadioButton_1_1.setBounds(593, 98, 113, 24);
		contentPane.add(rdbtnNewRadioButton_1_1);
		
		ButtonGroup bg1 = new ButtonGroup();
		bg1.add(rdbtnNewRadioButton);
		bg1.add(rdbtnKts);
		bg1.add(rdbtnNewRadioButton_1_1);
		
		JLabel lblNewLabel_3 = new JLabel("여정경로");
		lblNewLabel_3.setFont(new Font("굴림체", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(379, 161, 74, 26);
		contentPane.add(lblNewLabel_3);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("직통");
		rdbtnNewRadioButton_1.setBounds(465, 163, 74, 23);
		
		//기본값으로 지정된 라디오버튼
		rdbtnNewRadioButton_1.setSelected(true);
		
		contentPane.add(rdbtnNewRadioButton_1);
		
		JRadioButton rdbtnNewRadioButton_1_3 = new JRadioButton("왕복");
		rdbtnNewRadioButton_1_3.setBounds(543, 163, 74, 23);
		contentPane.add(rdbtnNewRadioButton_1_3);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnNewRadioButton_1);
		bg.add(rdbtnNewRadioButton_1_3);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(465, 226, 133, 23);
		contentPane.add(textField);
		
		JLabel lblNewLabel_4 = new JLabel("출발역");
		lblNewLabel_4.setFont(new Font("굴림체", Font.PLAIN, 15));
		lblNewLabel_4.setBounds(379, 222, 74, 26);
		contentPane.add(lblNewLabel_4);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(465, 271, 133, 23);
		contentPane.add(textField_1);
		
		JLabel lblNewLabel_4_1 = new JLabel("도착역");
		lblNewLabel_4_1.setFont(new Font("굴림체", Font.PLAIN, 15));
		lblNewLabel_4_1.setBounds(379, 269, 74, 26);
		contentPane.add(lblNewLabel_4_1);
		
		JLabel lblNewLabel_4_1_1 = new JLabel("출발일");
		lblNewLabel_4_1_1.setFont(new Font("굴림체", Font.PLAIN, 15));
		lblNewLabel_4_1_1.setBounds(379, 323, 74, 26);
		contentPane.add(lblNewLabel_4_1_1);
		
		JComboBox<Object> comboBox_3 = new JComboBox<Object>();
		comboBox_3.setModel(new DefaultComboBoxModel<Object>(new String[] {"2024", "2025", "2026"}));
		comboBox_3.setBounds(465, 327, 51, 23);
		contentPane.add(comboBox_3);
		
		JLabel lblNewLabel_5 = new JLabel("년");
		lblNewLabel_5.setBounds(516, 331, 24, 15);
		contentPane.add(lblNewLabel_5);
		
		JComboBox<Object> comboBox_3_1 = new JComboBox<Object>();
		comboBox_3_1.setModel(new DefaultComboBoxModel<Object>(new String[] 
				{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		comboBox_3_1.setBounds(536, 327, 51, 23);
		contentPane.add(comboBox_3_1);
		
		JLabel lblNewLabel_5_1 = new JLabel("월");
		lblNewLabel_5_1.setBounds(593, 331, 24, 15);
		contentPane.add(lblNewLabel_5_1);
		
		JComboBox<Object> comboBox_3_2 = new JComboBox<Object>();
		comboBox_3_2.setModel(new DefaultComboBoxModel<Object>(new String[] 
				{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
						"17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		comboBox_3_2.setBounds(607, 327, 51, 23);
		contentPane.add(comboBox_3_2);
		
		JLabel lblNewLabel_5_2 = new JLabel("일");
		lblNewLabel_5_2.setBounds(661, 331, 24, 15);
		contentPane.add(lblNewLabel_5_2);
		
		JComboBox<Object> comboBox_3_3 = new JComboBox<Object>();
		comboBox_3_3.setModel(new DefaultComboBoxModel<Object>(new String[] 
				{"0 (오전00시)", "1 (오전01시)", "2 (오전02시)", "3 (오전03시)", "4 (오전04시)", "5 (오전05시)", "6 (오전06시)", 
						"7 (오전07시)", "8 (오전08시)", "9 (오전09시)", "10 (오전10시)", "11 (오전11시)", "12 (오전12시)", 
						"13 (오후1시)", "14 (오후2시)", "15 (오후3시)", "16 (오후4시)", "17 (오후5시)", "18 (오후6시)", 
						"19 (오후7시)", "20 (오후8시)", "21 (오후9시)", "22 (오후10시)", "23 (오후11시)"}));
		comboBox_3_3.setBounds(680, 328, 78, 23);
		contentPane.add(comboBox_3_3);
		
		JLabel lblNewLabel_5_3 = new JLabel("시");
		lblNewLabel_5_3.setBounds(764, 332, 24, 15);
		contentPane.add(lblNewLabel_5_3);
		
		JButton btnNewButton = new JButton("조회하기");
		btnNewButton.setFont(new Font("굴림체", Font.BOLD, 15));
		btnNewButton.setBounds(709, 402, 127, 44);
		contentPane.add(btnNewButton);
		
		JLabel label = new JLabel(dto.getId());
		label.setBounds(695, 55, 84, 15);
		contentPane.add(label);
		
		
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("start 데이터 확인 >>>" + textField.getText());
				
				dto.setStart(textField.getText());
				dto.setArrive(textField_1.getText());
				dto.setAdultCount((String)comboBox.getSelectedItem());
				dto.setChildCount((String)comboBox_1.getSelectedItem());
				dto.setBabyCount((String)comboBox_2.getSelectedItem());
				
				//선택한 기차 타입(라디오버튼)
				String selectedTrainType = "";
				if(rdbtnNewRadioButton.isSelected()) {
					selectedTrainType = rdbtnNewRadioButton.getText();
				}else if(rdbtnKts.isSelected()) {
					selectedTrainType = rdbtnKts.getText();
				}else if(rdbtnNewRadioButton_1_1.isSelected()) {
					selectedTrainType = rdbtnNewRadioButton_1_1.getText();
				}
				dto.setTrainType(selectedTrainType);;
				
				dto.setTrainType(selectedTrainType);
				//선택한 경로(라디오버튼)
				String selectedRoute = "";
				if(rdbtnNewRadioButton_1.isSelected()) {
					selectedRoute = rdbtnNewRadioButton_1.getText();
				}else if(rdbtnNewRadioButton_1_3.isSelected()) {
					selectedRoute = rdbtnNewRadioButton_1_3.getText();
				}
				
				dto.setRoute(selectedRoute);
				System.out.println("dtoroute값 : "+selectedRoute);
				
				String y =(String)comboBox_3.getSelectedItem();
				String m =(String)comboBox_3_1.getSelectedItem();
				String d =(String)comboBox_3_2.getSelectedItem();
				String time =(String)comboBox_3_3.getSelectedItem();
				
				String[] timeArr = time.split(" ");//공백을 구분으로 자름
				
				String t =timeArr[0];//0~24시간으로 구분한 시간
				
				dto.setStartDay(y+"/"+m+"/"+d);
								
				System.out.println("아이디 값을 보자(windowbuilder) : " + dto.getId());
				new SelectPage(dto);
				
				dispose();
			}
		});
	}
	
}