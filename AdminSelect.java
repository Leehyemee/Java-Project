package windowBuilder;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class AdminSelect extends JFrame {
    
    Connection con = null;                  // DB와 연결하는 객체
    PreparedStatement pstmt = null;         // SQL문을 DB에 전송하는 객체
    ResultSet rs = null;                    // SQL문 실행 결과를 가지고 있는 객체
    String sql = null;                      // SQL문을 저장하는 문자열 변수.

    JTextField numInput, strInput, arrInput, strDayInput, arrDayInput, typeInput, eco_seat_input, baby_seat_input, stand_seat_input;

    DefaultTableModel model;
    JTable table;

    Dto dto;

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AdminSelect frame = new AdminSelect();
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
    public AdminSelect() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);

        JPanel selectedPanel = new JPanel(new BorderLayout());

        String[] header = 
            {"열차번호", "타입", "출발역", "도착역", "출발일", "도착일", "요금", "일반석", "유아석", "입석"};

        model = new DefaultTableModel(header, 0);
        table = new JTable(model);

        JScrollPane jsp = new JScrollPane(
                table, 
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        selectedPanel.add(jsp, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        
        add(selectedPanel, BorderLayout.CENTER);
        
        
        JPanel topPanel = new JPanel();
        JButton button1 = new JButton("수정");
        JButton button2 = new JButton("삭제");
        JButton button3 = new JButton("추가");
        
        topPanel.add(button1);
        topPanel.add(button2);
        topPanel.add(button3);
        
        add(topPanel, BorderLayout.NORTH);
        
        button1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				connect();
				update();
			}
		});
        
        button2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				connect();
				delete();
			}
		});
        
        button3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				insert();
			}
		});
        
        

        setBounds(100, 100, 932, 553);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // 데이터베이스 연결 및 조회
        connect();
        model.setRowCount(0);
        select();
    }

    private void select() {
        System.out.println("Admin select문 들어옴");
        
        sql = "select * from train t join schedule s on t.train_num = s.train_num";
        try {
            pstmt = con.prepareStatement(sql);
            
            rs = pstmt.executeQuery();
            while(rs.next()) {
                
                String train_num = rs.getString("TRAIN_NUM");
                String train_type = rs.getString("TRAIN_TYPE");
                String str_station = rs.getString("STR_STATION");
                String arr_station = rs.getString("ARR_STATION");
                String start_day = rs.getString("START_DAY");
                String arrive_day = rs.getString("ARRIVE_DAY");
                String money = rs.getString("MONEY");
                String economy_seat = rs.getString("ECONOMY_SEAT");
                String baby_seat = rs.getString("BABY_SEAT");
                String stand_seat = rs.getString("STAND_SEAT");
                
                Object[] data = {train_num, train_type, str_station, arr_station, start_day, arrive_day, money, economy_seat, baby_seat, stand_seat};
                model.addRow(data);
            }
        } catch (SQLException e) {
            System.err.println("seat select() 에러 발생");
            System.out.println(e.getMessage());
        }
    }
    
    void update() {
    	try {
			// 1. 오라클 데이터베이스로 전송할 SQL문을 작성.
			sql = "update emp set job = ?, mgr = ?, sal = ?, "
					+ " comm = ?, deptno = ? where empno = ?";
//			
//			pstmt = con.prepareStatement(sql);
//			
//			pstmt.setString(1, jcb1.getSelectedItem().toString());
//			pstmt.setInt
//				(2, Integer.parseInt(jcb2.getSelectedItem().toString().substring(0, 4)));
//			pstmt.setInt(3, Integer.parseInt(jtf3.getText()));
//			pstmt.setInt(4, Integer.parseInt(jtf4.getText()));
//			pstmt.setInt
//			    (5, Integer.parseInt(jcb3.getSelectedItem().toString().substring(0, 2)));
//			pstmt.setInt(6, Integer.parseInt(jtf1.getText()));
//			
			// 2. 오라클 데이터베이스에 SQL문 전송 및 SQL문 실행.
			int res = pstmt.executeUpdate();
			
			if(res > 0) {
				JOptionPane.showMessageDialog(null, "사원 정보 수정 성공!!!");
			}else {
				JOptionPane.showMessageDialog(null, "사원 정보 수정 실패~~~");
			}
			
			// 3. 오라클 데이터베이스에 연결되어 있던 자원 종료
			pstmt.close(); // con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    void delete() {

		try {
			// 1. 오라클 데이터베이스로 전송할 SQL문을 작성.
			sql = "delete from train where train_num = ?";
			
			pstmt = con.prepareStatement(sql);
			
			// 테이블의 특정 행을 클릭했을 때 해당 테이블의 값을 가져오는 메서드.
			int row = table.getSelectedRow();
			
			// 해당 행의 값을 가져올 때 해당 행의 0번째 열의 값을 가져오는 방법.
			pstmt.setInt(1, (int)model.getValueAt(row, 0));
			
			
			// 2. 오라클 데이터베이스에 SQL문 전송 및 SQL문 실행.
			int res = pstmt.executeUpdate();
			
			if(res > 0) {
				JOptionPane.showMessageDialog(null, "삭제 성공");
			}else {
				JOptionPane.showMessageDialog(null, "삭제 실패");
			}
			
			// 실제로 테이블 상의 클릭한 한 레코드를 삭제.
			model.removeRow(row);
			
			// 3. 오라클 데이터베이스에 연결되어 있던 자원 종료.
			pstmt.close(); con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    void insert() {

		try {
			// 1. 오라클 데이터베이스에 전송할 SQL문 작성.
			sql = "insert into train values(?, ?, ?, ?)";
			
			pstmt = con.prepareStatement(sql);
			
//			pstmt.setInt(1, Integer.parseInt(jtf1.getText()));
//			pstmt.setString(2, jtf2.getText());
//			pstmt.setString(3, jcb1.getSelectedItem().toString());
//			pstmt.setInt
//				(4, Integer.parseInt(jcb2.getSelectedItem().toString().substring(0, 4)));
//			pstmt.setInt(5, Integer.parseInt(jtf3.getText()));
//			pstmt.setInt(6, Integer.parseInt(jtf4.getText()));
//			pstmt.setInt
//			    (7, Integer.parseInt(jcb3.getSelectedItem().toString().substring(0, 2)));
//			
			// 2. 오라클 데이터베이스에 SQL문 전송 및 SQL문 실행.
			int res = pstmt.executeUpdate();
			
			if(res > 0) {
				JOptionPane.showMessageDialog(null, "사원 등록 성공");
			}else {
				JOptionPane.showMessageDialog(null, "사원 등록 실패");
			}
			
			// 3. 오라클 데이터베이스에 연결되어 있던 자원 종료.
			pstmt.close(); // con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private void connect() {
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
}
