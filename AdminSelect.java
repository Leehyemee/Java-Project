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

//    Dto dto;

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
            {"열차번호", "타입", "일정번호", "출발역", "도착역", "출발일", "도착일", "요금", "일반석", "유아석", "입석"};

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
        JButton button2 = new JButton("삭제");
        
        topPanel.add(button2);
        
        add(topPanel, BorderLayout.NORTH);
        
        
        button2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				connect();
				delete();
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
                
                String schedule_id = rs.getString("schedule_id");
                
                Object[] data = {train_num, train_type, schedule_id, str_station, arr_station, start_day, arrive_day, money, economy_seat, baby_seat, stand_seat};
                model.addRow(data);
            }
        } catch (SQLException e) {
            System.err.println("seat select() 에러 발생");
            System.out.println(e.getMessage());
        }
    }
    
    void delete() {
        try {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "삭제할 기차를 선택하세요.");
                return; 
            }

            String trainNumStr = model.getValueAt(row, 0).toString();
            int trainNum = Integer.parseInt(trainNumStr);

            // 1. schedule 테이블에서 먼저 삭제
            sql = "delete from schedule where train_num = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, trainNum); 
            int res = pstmt.executeUpdate();
            if (res > 0) {
                JOptionPane.showMessageDialog(null, "일정 삭제 성공");
            } else {
                JOptionPane.showMessageDialog(null, "일정 삭제 실패");
            }



            // 테이블 UI에서 선택된 행 제거
            model.removeRow(row);

            // 3. 리소스 정리
            pstmt.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "기차 번호가 올바르지 않습니다.");
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
