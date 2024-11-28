package start;

import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrainMenagement extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    // DB와 연결하는 객체
    private Connection con = null;
    private PreparedStatement pstmt = null; // SQL문을 DB에 전송하는 객체
    private ResultSet rs = null; // SQL문 실행 결과를 가지고 있는 객체
    private String sql = null; // SQL문을 저장하는 문자열 변수

    private DefaultTableModel model;
    private JTable table;

    public TrainMenagement() {
        setTitle("기차 관리");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1105, 580);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // 상단 패널
        JPanel panel = new JPanel();
        panel.setBackground(new Color(219, 254, 205));
        panel.setBounds(0, 0, 1089, 92);
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel TM_JL = new JLabel("기차 관리");
        TM_JL.setFont(new Font("굴림", Font.BOLD, 22));
        TM_JL.setBounds(12, 23, 139, 32);
        panel.add(TM_JL);

        // 추가 버튼
        JButton Add_BT = new JButton("추가");
        Add_BT.setBackground(new Color(255, 255, 255));
        Add_BT.setFont(new Font("굴림", Font.BOLD, 12));
        Add_BT.setBounds(575, 23, 97, 45);
        panel.add(Add_BT);

        // 삭제 버튼
        JButton Del_BT = new JButton("삭제");
        Del_BT.setFont(new Font("굴림", Font.BOLD, 12));
        Del_BT.setBackground(Color.WHITE);
        Del_BT.setBounds(710, 23, 97, 45);
        panel.add(Del_BT);

        // 로그아웃 버튼
        JButton LogOut_BT = new JButton("로그아웃");
        LogOut_BT.setFont(new Font("굴림", Font.BOLD, 12));
        LogOut_BT.setBackground(Color.WHITE);
        LogOut_BT.setBounds(965, 23, 97, 45);
        panel.add(LogOut_BT);

        // 조회 버튼
        JButton Add_BT_1 = new JButton("조회");
        Add_BT_1.setFont(new Font("굴림", Font.BOLD, 12));
        Add_BT_1.setBackground(Color.WHITE);
        Add_BT_1.setBounds(451, 23, 97, 45);
        panel.add(Add_BT_1);

        // 테이블 생성 및 설정
        String[] header = {"TRAIN_NUM", "TRAIN_TYPE", "STR_STATION", "ARR_STATION", "START_DAY", "ARRIVE_DAY", "MONEY", "ECONOMY_SEAT", "BABY_SEAT", "STAND_SEAT"};
        model = new DefaultTableModel(header, 0);
        table = new JTable(model);
        JScrollPane jsp = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jsp.setBounds(30, 94, 1024, 366);  // 테이블 위치 및 크기 설정
        contentPane.add(jsp);

        // 가운데 정렬을 위한 렌더러 설정
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // 모든 열에 대해 가운데 정렬 적용
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

     // "조회" 버튼 클릭 이벤트
        Add_BT_1.addActionListener(e -> {
            String sql = "SELECT t.TRAIN_NUM, t.TRAIN_TYPE, t.STR_STATION, t.ARR_STATION, " +
                         "s.START_DAY, s.ARRIVE_DAY, s.MONEY, s.ECONOMY_SEAT, s.BABY_SEAT, s.STAND_SEAT " +
                         "FROM train t JOIN schedule s ON t.TRAIN_NUM = s.TRAIN_NUM"; // SQL 쿼리

            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;

            try {
                conn = connect(); // DB 연결
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();

                // 테이블 모델 초기화
                model.setRowCount(0);

                // 조회 결과를 테이블에 추가
                while (rs.next()) {
                    String trainNum = rs.getString("TRAIN_NUM");
                    String trainType = rs.getString("TRAIN_TYPE");
                    String strStation = rs.getString("STR_STATION");
                    String arrStation = rs.getString("ARR_STATION");
                    String startDay = rs.getString("START_DAY");
                    String arriveDay = rs.getString("ARRIVE_DAY");
                    int money = rs.getInt("MONEY");
                    int economySeat = rs.getInt("ECONOMY_SEAT");
                    int babySeat = rs.getInt("BABY_SEAT");
                    String standSeat = rs.getString("STAND_SEAT");

                    model.addRow(new Object[]{trainNum, trainType, strStation, arrStation, startDay, arriveDay, money, economySeat, babySeat, standSeat});
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



        // 로그아웃 버튼 이벤트 처리 (기능은 예시로 비워두었습니다)
        LogOut_BT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // "종료하시겠습니까?" 메시지 표시
                int result = JOptionPane.showConfirmDialog(
                		TrainMenagement.this,    // 부모 창
                        "종료하시겠습니까?", // 메시지
                        "로그아웃",         // 제목
                        JOptionPane.YES_NO_OPTION, // Y/N 옵션
                        JOptionPane.QUESTION_MESSAGE // 질문 아이콘
                );

             // "Yes"를 누르면 로그인 화면 실행
                if (result == JOptionPane.YES_OPTION) {
                    // Login 클래스 실행
                    Login loginFrame = new Login();  // Login 클래스를 인스턴스화
                    loginFrame.setVisible(true);      // 로그인 창을 보이도록 설정

                    // 현재 창을 닫기
                    dispose();  // MasterMain 창을 닫음
                }
                // "No"를 누르면 아무 동작도 하지 않음
                else if (result == JOptionPane.NO_OPTION) {
                    // 아무 동작도 하지 않음 (창을 유지)
                }
            }
        });
    }

    // DB 연결 메서드
    public Connection connect() {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String user = "hyemee";
        String password = "1234";

        try {
            Class.forName(driver); // 드라이버 로드
            return DriverManager.getConnection(url, user, password);  // DB 연결 반환
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC 드라이버를 찾을 수 없습니다.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("DB 연결에 실패했습니다.");
            e.printStackTrace();
        }

        return null; // 연결 실패 시 null 반환
    }

    // DB 연결 해제 메서드
    private void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TrainMenagement frame = new TrainMenagement();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

