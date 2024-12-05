package start;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Font;
import javax.swing.JButton;

public class OrderPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;

	DefaultTableModel model3 = null;
	JTable table3 = null;
	
	static Dto dto;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Dto dto = new Dto();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OrderPage frame = new OrderPage(dto);
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
	public OrderPage(Dto dto) {
		setTitle("주문내역");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 932, 553);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JPanel view = new JPanel();
		view.setBounds(64, 80, 765, 344);
		
		String[] header = {"고유번호", "아이디", "기차번호", "예약한 좌석", "좌석 수", "출발시간", "예약한 시간"};
		
		model3 = new DefaultTableModel(header, 0);
		contentPane.setLayout(null);
		view.setLayout(null);
		
		table3 = new JTable(model3);
		
		JScrollPane jsp = new JScrollPane(table3, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setBounds(5, 5, 748, 329);
		
		view.add(jsp);
		
		getContentPane().add(view);
		
		JLabel userId = new JLabel(dto.getId()+ " 님의 주문 내역");
		userId.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 15));
		userId.setHorizontalAlignment(SwingConstants.CENTER);
		userId.setBounds(313, 22, 230, 48);
		contentPane.add(userId);
		
		JButton backBtn = new JButton("뒤로가기");
		backBtn.setBounds(82, 453, 97, 23);
		contentPane.add(backBtn);
		
		backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("뒤로가기 버튼 클릭!");
                
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run(){
                        new MemMain(dto).setVisible(true);
                        dispose();//뒤로가기 했을 때 기존 창 꺼짐
                    }
                });
            }
        });
		
		JButton deleteBtn = new JButton("삭제");
		deleteBtn.setBounds(707, 453, 97, 23);
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
		String user = "basic";
		String password = "1234";
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	void select(Dto dto) {
		System.out.println("OderPage에서 select() 로 진입");
		
		sql = "select * from reservation where id = ?";//user 에서 id 가 primary key
		
		System.out.println("select 문 진입");
		System.out.println("+++++++++++++"+dto.getId());
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
			System.err.println("OrderPage에서 select() 에러");
			System.out.println(e.getMessage());
		}finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
			} catch (Exception e2) {
				System.out.println("OrderPage select() finally 에러");
				System.out.println(e2.getMessage());
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
			System.out.println("OderPage delete() err");
			System.out.println(e.getMessage());
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
}
