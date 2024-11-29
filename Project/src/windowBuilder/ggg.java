package windowBuilder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
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

public class ggg extends JFrame {
    
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Connection con = null; // DB와 연결하는 객체
    private PreparedStatement pstmt = null; // SQL문을 DB에 전송하는 객체
    private ResultSet rs = null; // SQL문 실행 결과를 가지고 있는 객체
    private String sql = null; // SQL문을 저장하는 문자열 변수.
    
    private JTextField numInput, strInput, arrInput, strDayInput, arrDayInput, typeInput, eco_seat_input, baby_seat_input, stand_seat_input;
    private DefaultTableModel model;
    private JTable table;

    // Launch the application
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ggg frame = new ggg();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Create the frame
    public ggg() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JPanel selectedPanel = new JPanel();

        String[] header = {"열차번호", "타입", "일정번호", "출발역", "도착역", "출발일", "도착일", "요금", "일반석", "유아석", "입석"};

        model = new DefaultTableModel(header, 0);
        selectedPanel.setLayout(null);
        table = new JTable(model);

        JScrollPane jsp = new JScrollPane(
                table, 
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jsp.setBounds(0, 74, 906, 430);

        selectedPanel.add(jsp);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(selectedPanel, BorderLayout.CENTER);
        
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(209, 252, 194));
        topPanel.setBounds(0, 0, 906, 74);
        selectedPanel.add(topPanel);
        topPanel.setLayout(null);
        
        JButton button2 = new JButton("삭제");
        button2.setFont(new Font("굴림", Font.BOLD, 12));
        button2.setBounds(628, 10, 97, 45);
        topPanel.add(button2);

        JButton Add_BT_1 = new JButton("조회");
        Add_BT_1.setFont(new Font("굴림", Font.BOLD, 12));
        Add_BT_1.setBackground(Color.WHITE);
        Add_BT_1.setBounds(370, 10, 97, 45);
        topPanel.add(Add_BT_1);

        // "조회" 버튼 클릭 이벤트
        Add_BT_1.addActionListener(e -> {
            String sql = "SELECT t.TRAIN_NUM, t.TRAIN_TYPE, t.STR_STATION, t.ARR_STATION, " +
                         "s.SCHEDULE_ID, s.START_DAY, s.ARRIVE_DAY, s.MONEY, s.ECONOMY_SEAT, s.BABY_SEAT, s.STAND_SEAT, s.SCHEDULE_NUM " +
                         "FROM train t JOIN schedule s ON t.TRAIN_NUM = s.TRAIN_NUM"; // SQL 쿼리

            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;

            try {
                conn = connect(); // DB 연결
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();

                // 테이블 모델 초기화
                model.setRowCount(0); // 테이블을 초기화합니다. (기존의 데이터 삭제)

                // 조회 결과를 테이블에 추가
                while (rs.next()) {
                    String trainNum = rs.getString("TRAIN_NUM");
                    String trainType = rs.getString("TRAIN_TYPE");
                    String strStation = rs.getString("STR_STATION");
                    String arrStation = rs.getString("ARR_STATION");
                    String scheduleId = rs.getString("SCHEDULE_ID");
                    String startDay = rs.getString("START_DAY");
                    String arriveDay = rs.getString("ARRIVE_DAY");
                    int money = rs.getInt("MONEY");
                    int economySeat = rs.getInt("ECONOMY_SEAT");
                    int babySeat = rs.getInt("BABY_SEAT");
                    String standSeat = rs.getString("STAND_SEAT");
                    int scheduleNum = rs.getInt("SCHEDULE_NUM");

                    // 데이터가 조회되면 테이블에 추가
                    model.addRow(new Object[]{trainNum, trainType, scheduleId, strStation, arrStation,  startDay, arriveDay, money, economySeat, babySeat, standSeat, scheduleNum});
                }

                // 조회 완료 메시지
                JOptionPane.showMessageDialog(this, "조회 완료.");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "DB 연결 실패.");
            } finally {
                closeConnection(conn); // DB 연결 종료
            }
        });

        JButton Add_BT = new JButton("추가");
        Add_BT.setFont(new Font("굴림", Font.BOLD, 12));
        Add_BT.setBackground(Color.WHITE);
        Add_BT.setBounds(499, 10, 97, 45);
        topPanel.add(Add_BT);

        JButton LogOut_BT = new JButton("로그아웃");
        LogOut_BT.setFont(new Font("굴림", Font.BOLD, 12));
        LogOut_BT.setBackground(Color.WHITE);
        LogOut_BT.setBounds(776, 10, 97, 45);
        topPanel.add(LogOut_BT);

        JLabel TM_JL = new JLabel("기차 관리");
        TM_JL.setFont(new Font("굴림", Font.BOLD, 22));
        TM_JL.setBounds(12, 10, 139, 32);
        topPanel.add(TM_JL);

        // "삭제" 버튼 클릭 이벤트
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

    // select 메서드: DB에서 기차 정보 조회
    private void select() {
        System.out.println("Admin select문 들어옴");

        sql = "SELECT * FROM train t JOIN schedule s ON t.train_num = s.train_num";
        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
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
                String schedule_id = rs.getString("SCHEDULE_ID");

                Object[] data = {train_num, train_type, schedule_id, str_station, arr_station, start_day, arrive_day, money, economy_seat, baby_seat, stand_seat};
                model.addRow(data);
            }
        } catch (SQLException e) {
            System.err.println("seat select() 에러 발생");
            System.out.println(e.getMessage());
        }
    }

    // delete 메서드: DB에서 선택된 기차 삭제
    private void delete() {
        try {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "삭제할 기차를 선택하세요.");
                return;
            }

            String trainNumStr = model.getValueAt(row, 0).toString();
            int trainNum = Integer.parseInt(trainNumStr);

            // DB 연결이 끊어진 경우 다시 연결을 시도
            if (con == null || con.isClosed()) {
                con = connect();
            }

            // 1. schedule 테이블에서 해당 기차 번호와 관련된 데이터 삭제
            String sql = "DELETE FROM schedule WHERE train_num = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, trainNum);
            int res = pstmt.executeUpdate();
            if (res > 0) {
                JOptionPane.showMessageDialog(null, "일정 삭제 성공");
            } else {
                JOptionPane.showMessageDialog(null, "일정 삭제 실패");
            }

            // 2. train 테이블에서 기차 삭제
            sql = "DELETE FROM train WHERE train_num = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, trainNum);
            res = pstmt.executeUpdate();
            if (res > 0) {
                JOptionPane.showMessageDialog(null, "기차 삭제 성공");
            } else {
                JOptionPane.showMessageDialog(null, "기차 삭제 실패");
            }

            // 테이블 UI에서 선택된 행 제거
            model.removeRow(row);

        } catch (SQLException e) {
            // 외래 키 제약 조건 오류를 처리합니다
            if (e.getErrorCode() == 2292) {
                JOptionPane.showMessageDialog(null, "삭제할 수 없습니다. 자식 레코드가 존재합니다.");
            } else {
                JOptionPane.showMessageDialog(null, "삭제 중 오류 발생: " + e.getMessage());
            }
            e.printStackTrace();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "기차 번호가 올바르지 않습니다.");
            e.printStackTrace();
        } finally {
            // 리소스 정리
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    // DB 연결 메서드
    private Connection connect() {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String user = "hyemee";
        String password = "1234";
        try {
            if (con == null || con.isClosed()) {
                Class.forName(driver);
                con = DriverManager.getConnection(url, user, password);
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "데이터베이스 연결 실패: " + e.getMessage());
            e.printStackTrace();
        }
        return con;
    }

    // DB 연결 종료 메서드
    private void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
