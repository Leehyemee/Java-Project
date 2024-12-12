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
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;
	
	DefaultTableModel model2;
	JTable table2;
	JComboBox<String> SeatChose_CB;
	JTextField buyCount;
	Dto dto;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
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
		setTitle("좌석 선택");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 932, 587);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(243, 249, 198));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		

		JLabel Title_LB = new JLabel(dto.gettrainNum()+"기차 조회 화면");
		Title_LB.setFont(new Font("굴림", Font.BOLD, 13));
		String[] header = {"좌석 유형", "남은 좌석 수"};
		
		JPanel selectedPanel = new JPanel();
		selectedPanel.setBackground(new Color(243, 249, 198));
		selectedPanel.setBounds(0, 35, 691, 413);
		JPanel container1 = new JPanel();
		container1.setBackground(new Color(243, 249, 198));
		container1.setBounds(158, 453, 403, 33);
		JPanel container2 = new JPanel();
		container2.setBackground(new Color(243, 249, 198));
		container2.setBounds(114, 10, 703, 496);
		JPanel container3 = new JPanel();
		container3.setBackground(new Color(243, 249, 198));
		container3.setBounds(194, 10, 283, 25);
		
		model2 = new DefaultTableModel(header, 0);
		selectedPanel.setLayout(null);
		
		table2 = new JTable(model2);

		JScrollPane jsp = new JScrollPane(
				table2, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setBounds(24, 0, 667, 398);
		
		selectedPanel.add(jsp);
		container3.add(Title_LB);
		

		JButton Buy_BT = new JButton("예약하기");
		Buy_BT.setFont(new Font("굴림", Font.BOLD, 12));
		Buy_BT.setBackground(new Color(255, 255, 255));
		JLabel buyLable = new JLabel("좌석 수 입력 : ");
		buyLable.setFont(new Font("굴림", Font.BOLD, 12));
		buyCount = new JTextField(10);
		String[] type = {"1인석", "내측좌석", "외측좌석"};
		contentPane.setLayout(null);
		SeatChose_CB = new JComboBox<>(type);
		SeatChose_CB.setFont(new Font("굴림", Font.BOLD, 12));
		SeatChose_CB.setBackground(new Color(255, 255, 255));

		container1.add(SeatChose_CB);
		container1.add(buyLable);
		container1.add(buyCount);
		container1.add(Buy_BT);
		container2.setLayout(null);

		container2.add(container3);
		container2.add(selectedPanel);
		container2.add(container1);

		getContentPane().add(container2);


		Buy_BT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int yes_no = JOptionPane.showConfirmDialog(null, "예약하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
				if(yes_no==1){	// 아니오
				}else if(yes_no==0){	// 네
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
		String user = "hyemee";
		String password = "1234";
		try {
			Class.forName(driver);
			
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 

	
	void select(Dto dto) {

		sql = "select * from seatinfo where train_num = ? and schedule_num = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.gettrainNum());
			pstmt.setString(2, dto.getSchedule_num());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
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
		}
	}

	void seatDeduct(Dto dto){

		if(SeatChose_CB.getSelectedItem().equals("1인석")){
			connect();
			sql = "update seatinfo set one_seat_count = one_seat_count-? "
					+ "where train_num = ? and schedule_num = ?";
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, Integer.parseInt(buyCount.getText()));
				pstmt.setInt(2, Integer.parseInt(dto.gettrainNum()));
				pstmt.setInt(3, Integer.parseInt(dto.getSchedule_num()));

				int res = pstmt.executeUpdate();
				
				con.setAutoCommit(false);
				if(res>0){
				}else {
					JOptionPane.showMessageDialog(null, "구매에 실패하였습니다. 다시 시도해주세요.");
				}
			} catch (Exception e) {

			}finally {
				
			}
			

		}else if(SeatChose_CB.getSelectedItem().equals("내측좌석")){
			sql = "update seatinfo set inside_seat_count = inside_seat_count - ? where train_num = ? and schedule_num = ?";
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, Integer.parseInt(buyCount.getText()));
				pstmt.setInt(2, Integer.parseInt(dto.gettrainNum()));
				pstmt.setInt(3, Integer.parseInt(dto.getSchedule_num()));

				int res = pstmt.executeUpdate();
				
				con.setAutoCommit(false);
				if(res>0){
				}else {
					JOptionPane.showMessageDialog(null, "구매에 실패하였습니다. 다시 시도해주세요.");
				}
			} catch (Exception e) {
			}finally {
		
			}
		}else if(SeatChose_CB.getSelectedItem().equals("외측좌석")){
			sql = "update seatinfo set outside_seat_count = outside_seat_count - ? "
					+ "where train_num = ? and schedule_num = ?";
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, Integer.parseInt(buyCount.getText()));
				pstmt.setInt(2, Integer.parseInt(dto.gettrainNum()));
				pstmt.setInt(3, Integer.parseInt(dto.getSchedule_num()));

				int res = pstmt.executeUpdate();
				
				con.setAutoCommit(false);
				if(res>0){
				}else {
					JOptionPane.showMessageDialog(null, "구매에 실패하였습니다. 다시 시도해주세요.");
				}
			} catch (Exception e) {
			}finally {
			}
		}

		sql = "insert into reservation values(reservation_seq.nextval, ?, ?, ?, to_timestamp(?,'YYYY-MM-DD HH24:MI'), ?, sysdate)";

		try {
//			System.out.println("-------좌석테이블에 넣어질 정보들------------");
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.gettrainNum());
			pstmt.setString(3, (String)SeatChose_CB.getSelectedItem());
			pstmt.setString(4, dto.getStartDay());
			pstmt.setInt(5, Integer.parseInt(buyCount.getText()));

			int res = pstmt.executeUpdate();
			
			//모든 정보가 들어가면 commit 되게.
			con.commit();
			con.setAutoCommit(true);
			
			if(res>0){
				JOptionPane.showMessageDialog(null, "구매하셨습니다.");
				this.dispose();
				new OrderPage(dto).setVisible(true);
			}else {
				JOptionPane.showMessageDialog(null, "구매에 실패하였습니다. 다시 시도해주세요.");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				con.close();
				pstmt.close();
			} catch (SQLException e) {
			}
		}

	}
}