package start;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class SeatSelect extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	Connection con = null;                  // DB와 연결하는 객체
	PreparedStatement pstmt = null;         // SQL문을 DB에 전송하는 객체
	ResultSet rs = null;                    // SQL문 실행 결과를 가지고 있는 객체
	String sql = null;                      // SQL문을 저장하는 문자열 변수.
	
	DefaultTableModel model2;
	JTable table2;
	JComboBox<String> jComboBox;
	JTextField buyCount;
	static Dto dto;//////////////////////

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SeatSelect frame = new SeatSelect(dto);
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
	public SeatSelect(Dto dto) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		

		JLabel selectTitle = new JLabel(dto.gettrainNum()+"기차의 "+dto.getSchedule_num()+"번 조회 화면");
		// String[] header = {"기차 번호", "1인실", "내측 좌석", "외측 좌석"};
		String[] header = {"좌석 유형", "남은 좌석 수"};
		
		JPanel selectedPanel = new JPanel();//layout 삭제함
		JPanel container1 = new JPanel();
		JPanel container2 = new JPanel(new BorderLayout());
		JPanel container3 = new JPanel();
		
		model2 = new DefaultTableModel(header, 0);
		
		table2 = new JTable(model2);

		JScrollPane jsp = new JScrollPane(
				table2, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		selectedPanel.add(jsp, BorderLayout.CENTER);
		container3.add(selectTitle);
		

		JButton buyButton = new JButton("예약하기");
		JLabel buyLable = new JLabel("좌석 수 입력 : ");
		buyCount = new JTextField(10);
		String[] type = {"1인석", "내측좌석", "외측좌석"};
		jComboBox = new JComboBox<>(type);

		container1.add(jComboBox);
		container1.add(buyLable);
		container1.add(buyCount);
		container1.add(buyButton);

		container2.add(container3,BorderLayout.NORTH);
		container2.add(selectedPanel, BorderLayout.CENTER);
		container2.add(container1, BorderLayout.SOUTH);

		add(container2);


		buyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int yes_no = JOptionPane.showConfirmDialog(null, "예약하시겠습니까", "확인", JOptionPane.YES_NO_OPTION);
				if(yes_no==1){//아니오 클릭
					System.out.println("아니오 들어옴");
				}else if(yes_no==0){//네 클릭
					System.out.println("네 들어옴");
					seatDeduct(dto);
				}
			}
		});

		
		connect();
		model2.setRowCount(0);
		select(dto);
		
		
		setBounds(100, 100, 932, 553);
		setVisible(true);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		
	}
	void connect() {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "basic";
		String password = "1234";
		try {
			Class.forName(driver);
			
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 

	
	void select(Dto dto) {
		
		System.out.println("seat select문 들어옴");
		System.out.println(dto.getSchedule_num());
		System.out.println(dto.gettrainNum());
		System.out.println("------------------");
		
		sql = "select * from seatinfo where train_num = ? and schedule_num = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.gettrainNum());
			pstmt.setString(2, dto.getSchedule_num());
			
			System.out.println(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				System.out.println(rs.getString("train_num"));
				System.out.println(rs.getString("inside_seat_count"));
				System.out.println(rs.getString("outside_seat_count"));
				
				String train_num = rs.getString("train_num");
				String one_seat_count = rs.getString("one_seat_count");
				String inside_seat_count = rs.getString("inside_seat_count");
				String outside_seat_count = rs.getString("outside_seat_count");
				
				Object[][] data = new Object[3][2];
				data[0][0] = "1인석";
				data[0][1] = one_seat_count;
				data[1][0] = "내측좌석";
				data[1][1] = inside_seat_count;
				data[2][0] = "외측좌석";
				data[2][1] = outside_seat_count;
				
				for(Object[] row : data){
					
					model2.addRow(row);
				}
			}
		} catch (SQLException e) {
			System.err.println("seat select() 에러 발생");
			System.out.println(e.getMessage());
		}
	}

	void seatDeduct(Dto dto){
		System.out.println("seatDeduct 진입");

		if(jComboBox.getSelectedItem().equals("1인석")){
			System.out.println("1인석 구매");
			connect();
			sql = "update seatinfo set one_seat_count = one_seat_count-? where train_num = ? and schedule_num = ?";
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, Integer.parseInt(buyCount.getText()));
				pstmt.setInt(2, Integer.parseInt(dto.gettrainNum()));
				pstmt.setInt(3, Integer.parseInt(dto.getSchedule_num()));

				int res = pstmt.executeUpdate();
				
				con.setAutoCommit(false);
				if(res>0){
					System.out.println("1인석 업데이트 실패");
				}else {
					JOptionPane.showMessageDialog(null, "구매에 실패하였습니다. 다시 시도해주세요.");
				}
			} catch (Exception e) {
				System.out.println("one_seat update 에서 실패");
				System.out.println(e.getMessage());
			}finally {
				
			}
			

		}else if(jComboBox.getSelectedItem().equals("내측좌석")){
			System.out.println("내측좌석 구매");
			sql = "update seatinfo set inside_seat_count = inside_seat_count - ? where train_num = ? and schedule_num = ?";
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, Integer.parseInt(buyCount.getText()));
				pstmt.setInt(2, Integer.parseInt(dto.gettrainNum()));
				pstmt.setInt(3, Integer.parseInt(dto.getSchedule_num()));

				int res = pstmt.executeUpdate();
				
				con.setAutoCommit(false);
				if(res>0){
					System.out.println("내측좌석 업데이트 실패");
				}else {
					JOptionPane.showMessageDialog(null, "구매에 실패하였습니다. 다시 시도해주세요.");
				}
			} catch (Exception e) {
				System.out.println("inside_seat update 에서 실패");
				System.out.println(e.getMessage());
			}finally {
		
			}
		}else if(jComboBox.getSelectedItem().equals("외측좌석")){
			System.out.println("외측좌석 구매");
			sql = "update seatinfo set outside_seat_count = outside_seat_count - ? where train_num = ? and schedule_num = ?";
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, Integer.parseInt(buyCount.getText()));
				pstmt.setInt(2, Integer.parseInt(dto.gettrainNum()));
				pstmt.setInt(3, Integer.parseInt(dto.getSchedule_num()));

				int res = pstmt.executeUpdate();
				
				con.setAutoCommit(false);
				if(res>0){
					System.out.println("외측좌석 업데이트 실패");
				}else {
					JOptionPane.showMessageDialog(null, "구매에 실패하였습니다. 다시 시도해주세요.");
				}
			} catch (Exception e) {
				System.out.println("inside_seat update 에서 실패");
				System.out.println(e.getMessage());
			}finally {

			}
		}

		sql = "insert into reservation values(reservation_seq.nextval, ?, ?, ?, to_timestamp(?,'YYYY-MM-DD HH24:MI'), ?, sysdate)";
		/* 순서 : 시퀀스, 아이디, 기차번호, 좌석종류, 예약자이름, 예약시간, 좌석수, 예약한 시간(sysdate) */
		try {
			System.out.println("-------좌석테이블에 넣어질 정보들------------");
			System.out.println(dto.getId());
			System.out.println(dto.gettrainNum());
			System.out.println((String)jComboBox.getSelectedItem());
//			System.out.println(dto.getName());
			System.out.println(dto.getStartDay());
			System.out.println(buyCount.getText());
			System.out.println("==========================================");
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.gettrainNum());
			pstmt.setString(3, (String)jComboBox.getSelectedItem());
//			pstmt.setString(4, dto.getName());
			pstmt.setString(4, dto.getStartDay());
			pstmt.setInt(5, Integer.parseInt(buyCount.getText()));

			int res = pstmt.executeUpdate();
			
			//모든 정보가 들어가면 commit 되게.
			con.commit();
			con.setAutoCommit(true);
			
			if(res>0){
				JOptionPane.showMessageDialog(null, "구매하셨습니다.");
			}else {
				JOptionPane.showMessageDialog(null, "구매에 실패하였습니다. 다시 시도해주세요.");
			}
			
			this.dispose();
			System.out.println("주문내역으로 넘어가!");
			new OrderPage(dto).setVisible(true);
			
		} catch (Exception e) {
			System.out.println("reservation update 에서 실패");
			System.out.println(e.getMessage());
		}finally {
			try {
				con.close();
				pstmt.close();
			} catch (SQLException e) {
				System.out.println("reservation finally 실패");
				System.out.println(e.getMessage());
			}
		}

	}
}