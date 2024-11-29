package start;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;

public class TrainManagement extends JFrame {
    private static final long serialVersionUID = 1L;
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = null;
    private JPanel contentPane;
    private DefaultTableModel model;
    private JTable table;

    public TrainManagement() {
        setTitle("기차 관리");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1105, 580);
        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // 상단 패널
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 1089, 92);
        panel.setBackground(new Color(219, 254, 205));
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
        JButton Search_BT = new JButton("조회");
        Search_BT.setFont(new Font("굴림", Font.BOLD, 12));
        Search_BT.setBackground(Color.WHITE);
        Search_BT.setBounds(451, 23, 97, 45);
        panel.add(Search_BT);

        // 테이블 생성 및 설정
        String[] header = {"기차 번호", "기차 유형", "운행 회차", "출발역", "도착역", "출발일시", "도착일시", "금액", 
                           "일반석 좌석수", "유아석 좌석수", "입석 수", "운행 고유번호"};
        model = new DefaultTableModel(header, 0);
        table = new JTable(model);
        JScrollPane jsp = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jsp.setBounds(0, 94, 1089, 447);
        contentPane.add(jsp);

        // 가운데 정렬을 위한 렌더러 설정
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // 모든 열에 대해 가운데 정렬 적용
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // "추가" 버튼 클릭 이벤트
        Add_BT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 기차 추가
                String trainType = JOptionPane.showInputDialog("기차 종류 (ex: KTX, SRT, 새마을호): ");
                String strStation = JOptionPane.showInputDialog("출발역 ");
                String arrStation = JOptionPane.showInputDialog("도착역 ");
                
                String scheduleId = JOptionPane.showInputDialog("스케줄 종류(1, 2, 3 선택) ");
                String startDay = JOptionPane.showInputDialog("출발일 (YYYY-MM-DD HH:MM) ");
                String arriveDay = JOptionPane.showInputDialog("도착일 (YYYY-MM-DD HH:MM) ");
                String money = JOptionPane.showInputDialog("요금 ");
                String economySeat = JOptionPane.showInputDialog("일반석 좌석 수 ");
                String babySeat = JOptionPane.showInputDialog("유아석 좌석 수 ");
                String standSeat = JOptionPane.showInputDialog("입석 가능 여부 (Y/N) ");
                
                if (trainType == null || strStation == null || arrStation == null || 
                    scheduleId == null || startDay == null || arriveDay == null || 
                    money == null || economySeat == null || babySeat == null || standSeat == null) {
                    JOptionPane.showMessageDialog(null, "모든 정보를 입력해야 합니다.");
                    return;
                }

                if (!isValidDateTimeFormat(startDay) || !isValidDateTimeFormat(arriveDay)) {
                    JOptionPane.showMessageDialog(null, "날짜 형식이 잘못되었습니다. 'YYYY-MM-DD HH:MM' 형식을 사용해주세요.");
                    return;
                }

                try {
                    Connection con = connect();
                    String trainSql = "INSERT INTO train (train_num, train_type, str_station, arr_station) VALUES (train_seq.NEXTVAL, ?, ?, ?)";
                    pstmt = con.prepareStatement(trainSql);
                    pstmt.setString(1, trainType);
                    pstmt.setString(2, strStation);
                    pstmt.setString(3, arrStation);
                    int trainRes = pstmt.executeUpdate();

                    if (trainRes > 0) {
                        JOptionPane.showMessageDialog(null, "기차 정보 추가 성공.");
                    } else {
                        JOptionPane.showMessageDialog(null, "기차 정보 추가 실패.");
                        return;
                    }

                    String scheduleSql = "INSERT INTO schedule (schedule_num, schedule_id, train_num, start_day, arrive_day, money, economy_seat, baby_seat, stand_seat) VALUES (schedule_seq.NEXTVAL, ?, train_seq.CURRVAL, ?, ?, ?, ?, ?, ?)";
                    pstmt = con.prepareStatement(scheduleSql);
                    pstmt.setString(1, scheduleId);
                    pstmt.setString(2, startDay);
                    pstmt.setString(3, arriveDay);
                    pstmt.setString(4, money);
                    pstmt.setString(5, economySeat);
                    pstmt.setString(6, babySeat);
                    pstmt.setString(7, standSeat);
                    pstmt.executeUpdate();

                    updateTable(); // 테이블 갱신
                    JOptionPane.showMessageDialog(null, "기차 일정 추가 성공.");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "데이터베이스 오류 발생.");
                }
            }
        });

        // "삭제" 버튼 클릭 이벤트
        Del_BT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete();
            }
        });

        // "조회" 버튼 클릭 이벤트
        Search_BT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable(); // 테이블 갱신
            }
        });

        // "로그아웃" 버튼 이벤트
        LogOut_BT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        TrainManagement.this,
                        "종료하시겠습니까?",
                        "로그아웃",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );
                if (result == JOptionPane.YES_OPTION) {
                    Login loginFrame = new Login();
                    loginFrame.setVisible(true);
                    dispose(); // 현재 창 종료
                }
            }
        });
    }

    // 날짜 형식 검증
    public boolean isValidDateTimeFormat(String dateTime) {
        String regex = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$";
        return dateTime.matches(regex);
    }

    // DB 연결 메서드
    public Connection connect() {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String user = "hyemee";
        String password = "1234";

        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(this, "DB 연결 실패.");
            e.printStackTrace();
            return null;
        }
    }

    // 테이블 갱신
    public void updateTable() {
        String sql = "SELECT * FROM train t JOIN schedule s ON t.train_num = s.train_num";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            model.setRowCount(0);  // 기존 데이터 삭제

            while (rs.next()) {
                String train_num = rs.getString("TRAIN_NUM");
                String train_type = rs.getString("TRAIN_TYPE");
                String schedule_id = rs.getString("SCHEDULE_ID");
                String str_station = rs.getString("STR_STATION");
                String arr_station = rs.getString("ARR_STATION");
                String start_day = rs.getString("START_DAY");
                String arrive_day = rs.getString("ARRIVE_DAY");
                String money = rs.getString("MONEY");
                String economy_seat = rs.getString("ECONOMY_SEAT");
                String baby_seat = rs.getString("BABY_SEAT");
                String stand_seat = rs.getString("STAND_SEAT");
                String schedule_num = rs.getString("SCHEDULE_NUM");

                Object[] data = {train_num, train_type, schedule_id, str_station, arr_station, start_day, arrive_day, money, economy_seat, baby_seat, stand_seat, schedule_num};
                model.addRow(data);
            }

            //JOptionPane.showMessageDialog(this, "테이블 갱신 완료.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "DB 연결 실패.");
        }
    }

    // 삭제 기능 (삭제 버튼 클릭 시 실행)
    public void delete() {
        String trainNum = JOptionPane.showInputDialog("삭제할 기차 번호를 입력하세요:");
        if (trainNum != null && !trainNum.isEmpty()) {
            try (Connection conn = connect()) {
                String deleteSchedule = "DELETE FROM schedule WHERE train_num = ?";
                pstmt = conn.prepareStatement(deleteSchedule);
                pstmt.setString(1, trainNum);
                pstmt.executeUpdate();

                String deleteTrain = "DELETE FROM train WHERE train_num = ?";
                pstmt = conn.prepareStatement(deleteTrain);
                pstmt.setString(1, trainNum);
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "기차 정보 삭제 완료.");
                updateTable();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "삭제 실패.");
            }
        }
    }

    // 메인 메서드
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                TrainManagement frame = new TrainManagement();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
