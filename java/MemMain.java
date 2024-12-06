
package start;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;


public class MemMain extends JFrame {

	private static final long serialVersionUID = 1L;
	Dto dto;
	private JPanel contentPane;
	private JTextField Start_TF;
	private JTextField Arr_TF;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {//멀티스레드타입으로 진행.
				try {
					// MemMain frame = new MemMain(userdto);
					// frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	//@SuppressWarnings({ "unchecked", "unchecked" })
	public MemMain(UserDto userdto) {
		setTitle("예매");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 932, 553);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(243, 249, 198));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel Reservation_LB = new JLabel("[예매]");
		Reservation_LB.setFont(new Font("굴림", Font.BOLD, 24));
		Reservation_LB.setBounds(87, 33, 92, 55);
		contentPane.add(Reservation_LB);

		JComboBox Adult_CB = new JComboBox();
		Adult_CB.setModel(new DefaultComboBoxModel(new String[] {"어른 선택","어른1명", "어른2명", "어른3명", "어른4명", "어른5명", "어른6명", "어른7명"}));
		Adult_CB.setBounds(204, 119, 125, 26);
		contentPane.add(Adult_CB);
		
		
		JLabel lblNewLabel_1 = new JLabel("인원정보");
		lblNewLabel_1.setFont(new Font("굴림체", Font.BOLD, 16));
		lblNewLabel_1.setBounds(87, 118, 100, 26);
		contentPane.add(lblNewLabel_1);
		
		JComboBox Kid_CB = new JComboBox();
		Kid_CB.setModel(new DefaultComboBoxModel(new String[] {"6~12세 선택", "어린이1명", "어린이2명", "어린이3명", "어린이4명", "어린이5명", "어린이6명", "어린이7명"}));
		Kid_CB.setBounds(363, 119, 125, 26);
		contentPane.add(Kid_CB);
		
		JComboBox Kid2_CB = new JComboBox();
		Kid2_CB.setModel(new DefaultComboBoxModel(new String[] {"6세 미만 선택", "유아 1명", "유아 2명", "유아 3명", "유아 4명", "유아 5명", "유아 6명", "유아 7명"}));
		Kid2_CB.setBounds(522, 119, 125, 26);
		contentPane.add(Kid2_CB);
		JRadioButton total_LB = new JRadioButton("전체");
		total_LB.setFont(new Font("굴림", Font.PLAIN, 14));
		total_LB.setBackground(new Color(243, 249, 198));
		total_LB.setBounds(204, 174, 65, 22);
		
		//기본값으로 지정된 라디오버튼
		total_LB.setSelected(true);
		
		contentPane.add(total_LB);
		
		JRadioButton KtxSrt_LB = new JRadioButton("KTX/SRT");
		KtxSrt_LB.setFont(new Font("굴림", Font.PLAIN, 14));
		KtxSrt_LB.setBackground(new Color(243, 249, 198));
		KtxSrt_LB.setBounds(290, 172, 100, 24);
		contentPane.add(KtxSrt_LB);
		
		JRadioButton NewTown_LB = new JRadioButton("새마을호");
		NewTown_LB.setFont(new Font("굴림", Font.PLAIN, 14));
		NewTown_LB.setBackground(new Color(243, 249, 198));
		NewTown_LB.setBounds(418, 172, 113, 24);
		contentPane.add(NewTown_LB);
		
		ButtonGroup bg1 = new ButtonGroup();
		bg1.add(total_LB);
		bg1.add(KtxSrt_LB);
		bg1.add(NewTown_LB);
		
		JLabel lblNewLabel_3 = new JLabel("여정경로");
		lblNewLabel_3.setFont(new Font("굴림체", Font.BOLD, 15));
		lblNewLabel_3.setBounds(87, 225, 74, 26);
		contentPane.add(lblNewLabel_3);
		
		JRadioButton OneWay_RB = new JRadioButton("직통");
		OneWay_RB.setFont(new Font("굴림", Font.PLAIN, 14));
		OneWay_RB.setBackground(new Color(243, 249, 198));
		OneWay_RB.setBounds(207, 227, 74, 23);
		
		//기본값으로 지정된 라디오버튼
		OneWay_RB.setSelected(true);
		
		contentPane.add(OneWay_RB);
		
		JRadioButton RoundT_RB = new JRadioButton("왕복");
		RoundT_RB.setFont(new Font("굴림", Font.PLAIN, 14));
		RoundT_RB.setBackground(new Color(243, 249, 198));
		RoundT_RB.setBounds(285, 227, 74, 23);
		contentPane.add(RoundT_RB);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(OneWay_RB);
		bg.add(RoundT_RB);
		
		Start_TF = new JComboBox<String>();;
		Start_TF.setBackground(new Color(255, 255, 255));
		Start_TF.setModel(new DefaultComboBoxModel<String>(new String[] 
        		{"선택", "서울", "행신", "영등포", "광명", "수원", "천안아산", "오송", "대전", "김천구미", "서대구", "동대구", 
        				"포항", "경주", "밀양", "울산", "구포", "부산"}));
		Start_TF.setBounds(204, 274, 133, 23);
		contentPane.add(Start_TF);
		
		JLabel lblNewLabel_4 = new JLabel("출발역");
		lblNewLabel_4.setFont(new Font("굴림체", Font.BOLD, 15));
		lblNewLabel_4.setBounds(87, 272, 74, 26);
		contentPane.add(lblNewLabel_4);
		
		Arr_TF = new JComboBox<String>();
		Arr_TF.setBackground(new Color(255, 255, 255));
		Arr_TF.setModel(new DefaultComboBoxModel<String>(new String[] 
        		{"선택", "서울", "행신", "영등포", "광명", "수원", "천안아산", "오송", "대전", "김천구미", "서대구", "동대구", 
        				"포항", "경주", "밀양", "울산", "구포", "부산"}));
		Arr_TF.setBounds(204, 328, 133, 23);
		contentPane.add(Arr_TF);
		
		JLabel lblNewLabel_4_1 = new JLabel("도착역");
		lblNewLabel_4_1.setFont(new Font("굴림체", Font.BOLD, 15));
		lblNewLabel_4_1.setBounds(87, 326, 74, 26);
		contentPane.add(lblNewLabel_4_1);
		
		JLabel lblNewLabel_4_1_1 = new JLabel("출발일");
		lblNewLabel_4_1_1.setFont(new Font("굴림체", Font.BOLD, 15));
		lblNewLabel_4_1_1.setBounds(87, 380, 74, 26);
		contentPane.add(lblNewLabel_4_1_1);
		
		JComboBox<Object> Year_CB = new JComboBox<Object>();
		Year_CB.setModel(new DefaultComboBoxModel<Object>(new String[] {"2024", "2025", "2026"}));
		Year_CB.setBounds(200, 383, 57, 23);
		contentPane.add(Year_CB);
		
		JLabel lblNewLabel_5 = new JLabel("년");
		lblNewLabel_5.setBounds(258, 390, 24, 15);
		contentPane.add(lblNewLabel_5);
		
		JComboBox<Object> Month_CB = new JComboBox<Object>();
		Month_CB.setModel(new DefaultComboBoxModel<Object>(new String[] 
				{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		Month_CB.setBounds(286, 383, 51, 23);
		contentPane.add(Month_CB);
		
		JLabel lblNewLabel_5_1 = new JLabel("월");
		lblNewLabel_5_1.setBounds(339, 390, 24, 15);
		contentPane.add(lblNewLabel_5_1);
		
		JComboBox<Object> Day_CB = new JComboBox<Object>();
		Day_CB.setModel(new DefaultComboBoxModel<Object>(new String[] 
				{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
						"17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		Day_CB.setBounds(375, 381, 51, 23);
		contentPane.add(Day_CB);
		
		JLabel lblNewLabel_5_2 = new JLabel("일");
		lblNewLabel_5_2.setBounds(430, 390, 24, 15);
		contentPane.add(lblNewLabel_5_2);
		
		JComboBox<Object> Hour_CB = new JComboBox<Object>();
		Hour_CB.setModel(new DefaultComboBoxModel<Object>(new String[] 
				{"0 (오전00시)", "1 (오전01시)", "2 (오전02시)", "3 (오전03시)", "4 (오전04시)", "5 (오전05시)", "6 (오전06시)", 
						"7 (오전07시)", "8 (오전08시)", "9 (오전09시)", "10 (오전10시)", "11 (오전11시)", "12 (오전12시)", 
						"13 (오후1시)", "14 (오후2시)", "15 (오후3시)", "16 (오후4시)", "17 (오후5시)", "18 (오후6시)", 
						"19 (오후7시)", "20 (오후8시)", "21 (오후9시)", "22 (오후10시)", "23 (오후11시)"}));
		Hour_CB.setBounds(462, 381, 100, 23);
		contentPane.add(Hour_CB);
		
		JLabel lblNewLabel_5_3 = new JLabel("시");
		lblNewLabel_5_3.setBounds(563, 389, 24, 15);
		contentPane.add(lblNewLabel_5_3);
		
		JButton Search_BT = new JButton("조회하기");
		Search_BT.setFont(new Font("굴림체", Font.BOLD, 15));
		Search_BT.setBounds(754, 446, 127, 44);
		contentPane.add(Search_BT);
		
		JButton Mypage_BT = new JButton(userdto.getId() + "님의 마이페이지로");
		Mypage_BT.setFont(new Font("굴림", Font.BOLD, 12));
		Mypage_BT.setBounds(692, 56, 189, 15);
		contentPane.add(Mypage_BT);
		
		// JLabel에 MouseListener 추가
			Mypage_BT.addMouseListener(new MouseAdapter() {
				    @Override
				    public void mouseClicked(MouseEvent e) {
				        // 마이페이지 클래스 열기
				        new MyPage(userdto); // MyPage는 새로 생성해야 할 클래스
				        dispose(); // 현재 창 닫기
				    }
				});
		
		JLabel lblNewLabel_3_1 = new JLabel("열차 종류");
		lblNewLabel_3_1.setFont(new Font("굴림체", Font.BOLD, 15));
		lblNewLabel_3_1.setBounds(87, 172, 74, 26);
		contentPane.add(lblNewLabel_3_1);
		
		JComboBox<Object> Minute_CB = new JComboBox<Object>();
		Minute_CB.setModel(new DefaultComboBoxModel(new String[] {"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"}));
		Minute_CB.setBounds(601, 381, 87, 23);
		contentPane.add(Minute_CB);
		
		JLabel lblNewLabel_5_3_1 = new JLabel("분");
		lblNewLabel_5_3_1.setBounds(692, 389, 24, 15);
		contentPane.add(lblNewLabel_5_3_1);
		
		
		Search_BT.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("start 데이터 확인 >>>" + (String)Start_TF.getSelectedItem());
				dto = new Dto();
				dto.setStart((String)Start_TF.getSelectedItem());
				dto.setArrive((String)Arr_TF.getSelectedItem());
				dto.setAdultCount((String)Adult_CB.getSelectedItem());
				dto.setChildCount((String)Kid_CB.getSelectedItem());
				dto.setBabyCount((String)Kid2_CB.getSelectedItem());
				dto.setId(userdto.getId());
				
				//선택한 기차 타입(라디오버튼)
				String selectedTrainType = "";
				if(total_LB.isSelected()) {
					selectedTrainType = total_LB.getText();
				}else if(KtxSrt_LB.isSelected()) {
					selectedTrainType = KtxSrt_LB.getText();
				}else if(NewTown_LB.isSelected()) {
					selectedTrainType = NewTown_LB.getText();
				}
				dto.setTrainType(selectedTrainType);;
				
				dto.setTrainType(selectedTrainType);
				//선택한 경로(라디오버튼)
				String selectedRoute = "";
				if(OneWay_RB.isSelected()) {
					selectedRoute = OneWay_RB.getText();
				}else if(RoundT_RB.isSelected()) {
					selectedRoute = RoundT_RB.getText();
				}
				
				dto.setRoute(selectedRoute);
				System.out.println("dtoroute값 : "+selectedRoute);
				
				String y =(String)Year_CB.getSelectedItem();
				String m =(String)Month_CB.getSelectedItem();
				String d =(String)Day_CB.getSelectedItem();
				String time =(String)Hour_CB.getSelectedItem();
				
				String[] timeArr = time.split(" ");//공백을 구분으로 자름
				
				String t =timeArr[0];//0~24시간으로 구분한 시간
				
				dto.setStartDay(y+"/"+m+"/"+d);
								
				if(dto.getAdultCount().contains("선택")||dto.getChildCount().contains("선택")||dto.getBabyCount().contains("선택")) {
					JOptionPane.showMessageDialog(null, "인원이 선택되지 않았습니다. 다시 시도해주세요.");
					return;
				}
				if(dto.getArrive().equals("")) {
					JOptionPane.showMessageDialog(null, "도착역을 입력해주세요");
					return;
				}
				new SelectPage(dto);
				
				dispose();
			}
		});
	}
}
