package start;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;

public class OrderPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;

	DefaultTableModel model3 = null;
	JTable table3 = null;
	
	Dto dto;
	UserDto userDto;
	
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
	public OrderPage(Dto dto) {
		setTitle("주문내역");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 842, 534);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(243, 249, 198));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JPanel view = new JPanel();
		view.setBackground(new Color(243, 249, 198));
		view.setBounds(35, 81, 765, 344);
		
		String[] header = {"고유번호", "아이디", "기차번호", "예약한 좌석", "좌석 수", "출발시간", "예약한 시간"};
		
		model3 = new DefaultTableModel(header, 0);
		contentPane.setLayout(null);
		view.setLayout(null);
		
		table3 = new JTable(model3);
		
		JScrollPane jsp = new JScrollPane(table3, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setBounds(5, 5, 748, 329);
		
		view.add(jsp);
		
		getContentPane().add(view);
		
		JLabel UserName_LB = new JLabel(dto.getId()+ " 님의 주문 내역");
		UserName_LB.setFont(new Font("굴림", Font.BOLD, 18));
		UserName_LB.setHorizontalAlignment(SwingConstants.CENTER);
		UserName_LB.setBounds(261, 23, 253, 48);
		contentPane.add(UserName_LB);
		
		JButton backBtn = new JButton("뒤로가기");
		backBtn.setFont(new Font("굴림", Font.BOLD, 13));
		backBtn.setBackground(new Color(255, 255, 255));
		backBtn.setBounds(53, 435, 97, 42);
		contentPane.add(backBtn);
		
		backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("뒤로가기 버튼 클릭!");
                
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run(){
                    	userDto = new UserDto();
                    	userDto.setId(dto.getId());
                        new MemMain(userDto).setVisible(true);
                        dispose();	//뒤로가기 했을 때 기존 창 꺼짐
                    }
                });
            }
        });
		
		JButton deleteBtn = new JButton("예약 취소");
		deleteBtn.setFont(new Font("굴림", Font.BOLD, 13));
		deleteBtn.setBackground(new Color(255, 255, 255));
		deleteBtn.setBounds(678, 435, 97, 42);
		contentPane.add(deleteBtn);
		
		deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("삭제 버튼 클릭!");
                delete(dto);
                
            }
        });
		
		connect();
		model3.setRowCount(0);
		select(dto);
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
			System.out.println(e.getMessage());
		}
	}
	
	void select(Dto dto) {
		sql = "select * from reservation where id = ?";//user 에서 id 가 primary key
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String reser_num = rs.getString("reser_num");
				String id = rs.getString("id");
				String train_num = rs.getString("train_num");
				String seat_type = rs.getString("seat_type");
				String reserve_time = rs.getString("reserve_time");
				String seat_count = rs.getString("seat_count");
				String reserved_time = rs.getString("reserved_time");
				
				Object[] date = {reser_num, id, train_num, seat_type, seat_count, reserve_time, reserved_time};
				model3.addRow(date);
			}
		} catch (Exception e) {
		}finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
			} catch (Exception e2) {
			}
		}
	}
	
	void delete(Dto dto) {
		sql = "delete from reservation where id = ? and reser_num = ? ";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			
			int row = table3.getSelectedRow();
			
			pstmt.setInt(2, Integer.parseInt((String) model3.getValueAt(row, 0)));
			
			int res = pstmt.executeUpdate();
			if(res>0) {
				JOptionPane.showMessageDialog(null, "예약이 취소되었습니다");
			}else {
				JOptionPane.showMessageDialog(null, "예약 취소에 실패하였습니다. 다시 시도해주세요");
			}
			
			model3.setRowCount(0);
			select(dto);
		} catch (Exception e) {
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
			} catch (Exception e2) {
			}
		}
	}
}