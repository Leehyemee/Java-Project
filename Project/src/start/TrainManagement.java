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
    private JPanel cardPanel, tablePanel, addPanel;
    
    // 입력 필드
    private JTextField TrainNum_TF;
    private JComboBox<String> StandSeat_CB, Count_CB, TrainType_CB, year_CB, month_CB, Day_CB, 
    						  arrYear_CB, arrMonth_CB, arrDay_CB, Start_CB, Arr_CB, Charge_CB, 
    						  babySeat_CB, EconomySeat_CB, Hour_CB, Minute_CB, arrHour_CB, arrMinute_CB;  
    private JLabel Month_LB, arrMonth_LB, Day_LB, arrDay_LB, Hour_LB, arrHour_LB, Minute_LB, arrMinute_LB,
    			   Seat_LB, seat_LB, seat_LB_1, Won_LB;
    
    public TrainManagement() {
        setTitle("기차 관리");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1105, 580);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // 카드 레이아웃 패널
        cardPanel = new JPanel();
        cardPanel.setBounds(0, 94, 1089, 450);
        contentPane.add(cardPanel);

        // 카드 레이아웃 설정
        CardLayout cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        // 테이블 패널
        tablePanel = new JPanel();
        cardPanel.add(tablePanel, "table");

        // 추가 패널
        addPanel = new JPanel();
        addPanel.setBackground(new Color(255, 255, 255));
        cardPanel.add(addPanel, "add");
        addPanel.setLayout(null);

        // 입력 폼 구성
        JLabel TrainNum_LB = new JLabel("기차 번호");
        TrainNum_LB.setBounds(50, 40, 60, 25);
        addPanel.add(TrainNum_LB);

        TrainNum_TF = new JTextField();
        TrainNum_TF.setBounds(162, 40, 150, 25);
        addPanel.add(TrainNum_TF);
        
        JLabel Count_LB = new JLabel("운행 회차");
        Count_LB.setBounds(350, 40, 60, 25);
        addPanel.add(Count_LB);

        JLabel TrainType_LB = new JLabel("기차 종류");
        TrainType_LB.setBounds(693, 40, 60, 25);
        addPanel.add(TrainType_LB);

        JLabel StandSeat_LB = new JLabel("입석 가능 여부 (Y/N):");
        StandSeat_LB.setBounds(693, 233, 123, 25);
        addPanel.add(StandSeat_LB);

        // 추가 버튼
        JButton addTrainButton = new JButton("기차 추가");
        addTrainButton.setBackground(new Color(255, 255, 255));
        addTrainButton.setFont(new Font("굴림", Font.BOLD, 20));
        addTrainButton.setBounds(449, 343, 157, 62);
        addPanel.add(addTrainButton);
        
        TrainType_CB = new JComboBox<String>();
        TrainType_CB.setBackground(new Color(255, 255, 255));
        TrainType_CB.setModel(new DefaultComboBoxModel<String>(new String[] {"선택", "KTX", "SRT", "새마을호"}));
        TrainType_CB.setBounds(855, 41, 100, 23);
        addPanel.add(TrainType_CB);
        
        String[] trainNo = {"선택", "1", "2", "3"};
        Count_CB = new JComboBox<String>(trainNo);
        Count_CB.setModel(new DefaultComboBoxModel<String>(new String[] {"", "1", "2", "3"}));
        Count_CB.setBackground(new Color(255, 255, 255));
        Count_CB.setForeground(new Color(0, 0, 0));
        Count_CB.setBounds(441, 41, 123, 23);
        addPanel.add(Count_CB);
        
        JLabel StratDate_LB = new JLabel("출발 날짜");
        StratDate_LB.setBounds(350, 112, 60, 25);
        addPanel.add(StratDate_LB);
        
        JLabel ArrDate_LB = new JLabel("도착 날짜");
        ArrDate_LB.setBounds(354, 171, 60, 25);
        addPanel.add(ArrDate_LB);
        
        JLabel StratStation_LB = new JLabel("출발역");
        StratStation_LB.setBounds(50, 112, 43, 25);
        addPanel.add(StratStation_LB);
        
        arrYear_CB = new JComboBox<String>();
        arrYear_CB.setBackground(new Color(255, 255, 255));
        arrYear_CB.setModel(new DefaultComboBoxModel<String>(new String[] {"2024", "2025", "2026", "2027"}));
        arrYear_CB.setBounds(441, 172, 70, 23);
        addPanel.add(arrYear_CB);
        
        JLabel ArrStation_LB = new JLabel("도착역");
        ArrStation_LB.setBounds(50, 171, 43, 25);
        addPanel.add(ArrStation_LB);
        
        String[] year = {"2024", "2025", "2026", "2027"};
        year_CB = new JComboBox<String>(year);
        year_CB.setBackground(new Color(255, 255, 255));
        year_CB.setBounds(441, 113, 70, 23);
        addPanel.add(year_CB);
        
        String[] month = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        month_CB = new JComboBox<String>(month);
        month_CB.setBackground(new Color(255, 255, 255));
        month_CB.setModel(new DefaultComboBoxModel<String>(new String[] 
        		{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
        month_CB.setBounds(523, 113, 50, 23);
        addPanel.add(month_CB);
        
        Day_CB = new JComboBox<String>();
        Day_CB.setBackground(new Color(255, 255, 255));
        Day_CB.setModel(new DefaultComboBoxModel<String>(new String[] 
        		{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", 
        				"16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", 
        				"30", "31"}));
        Day_CB.setBounds(596, 112, 50, 23);
        addPanel.add(Day_CB);
        
        arrMonth_CB = new JComboBox<String>();
        arrMonth_CB.setBackground(new Color(255, 255, 255));
        arrMonth_CB.setModel(new DefaultComboBoxModel<String>(new String[] 
        		{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
        arrMonth_CB.setBounds(523, 172, 50, 23);
        addPanel.add(arrMonth_CB);
        
        arrDay_CB = new JComboBox<String>();
        arrDay_CB.setBackground(new Color(255, 255, 255));
        arrDay_CB.setModel(new DefaultComboBoxModel<String>(new String[] 
        		{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", 
        				"16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", 
        				"30", "31"}));
        arrDay_CB.setBounds(596, 171, 50, 23);
        addPanel.add(arrDay_CB);
        
        Arr_CB = new JComboBox<String>();
        Arr_CB.setBackground(new Color(255, 255, 255));
        Arr_CB.setModel(new DefaultComboBoxModel<String>(new String[] 
        		{"선택", "서울", "행신", "영등포", "광명", "수원", "천안아산", "오송", "대전", "김천구미", "서대구", "동대구", 
        				"포항", "경주", "밀양", "울산", "구포", "부산"}));
        Arr_CB.setBounds(162, 172, 150, 23);
        addPanel.add(Arr_CB);
        
        Start_CB = new JComboBox<String>();
        Start_CB.setBackground(new Color(255, 255, 255));
        Start_CB.setModel(new DefaultComboBoxModel<String>(new String[] 
        		{"선택", "서울", "행신", "영등포", "광명", "수원", "천안아산", "오송", "대전", "김천구미", "서대구", "동대구", 
        				"포항", "경주", "밀양", "울산", "구포", "부산"}));
        Start_CB.setBounds(162, 113, 150, 23);
        addPanel.add(Start_CB);
        
        StandSeat_CB = new JComboBox<String>();
        StandSeat_CB.setBackground(new Color(255, 255, 255));
        StandSeat_CB.setModel(new DefaultComboBoxModel<String>(new String[] {"선택", "Y", "N"}));
        StandSeat_CB.setBounds(855, 234, 100, 23);
        addPanel.add(StandSeat_CB);
        
        JLabel babySeat_LB = new JLabel("유아 좌석수");
        babySeat_LB.setBounds(355, 233, 70, 25);
        addPanel.add(babySeat_LB);
        
        babySeat_CB = new JComboBox<String>();
        babySeat_CB.setBackground(new Color(255, 255, 255));
        babySeat_CB.setModel(new DefaultComboBoxModel<String>(new String[] 
        		{"", "6", "8", "10", "12", "14", "16", "18", "20", "22", "24", "26", "28", "30"}));
        babySeat_CB.setBounds(449, 234, 123, 23);
        addPanel.add(babySeat_CB);
        
        JLabel EconomySeat_LB = new JLabel("일반석 좌석수");
        EconomySeat_LB.setBounds(51, 233, 83, 25);
        addPanel.add(EconomySeat_LB);
        
        EconomySeat_CB = new JComboBox<String>();
        EconomySeat_CB.setBackground(new Color(255, 255, 255));
        EconomySeat_CB.setModel(new DefaultComboBoxModel<String>(new String[] 
        		{"", "30", "35", "40", "45", "50", "55", "60", "65", "70", "75"}));
        EconomySeat_CB.setBounds(213, 234, 100, 23);
        addPanel.add(EconomySeat_CB);
        
        JLabel Charge_LB = new JLabel("요금");
        Charge_LB.setBounds(50, 290, 30, 25);
        addPanel.add(Charge_LB);
        
        Charge_CB = new JComboBox<String>();
        Charge_CB.setBackground(new Color(255, 255, 255));
        Charge_CB.setModel(new DefaultComboBoxModel<String>(new String[] 
        		{"", "30000", "31000", "32000", "33000", "40000", "41000", "42000", "43000", "50000", 
        				"51000", "52000", "53000"}));
        Charge_CB.setBounds(212, 291, 100, 23);
        addPanel.add(Charge_CB);
        
        Hour_CB = new JComboBox<String>();
        Hour_CB.setBackground(new Color(255, 255, 255));
        Hour_CB.setModel(new DefaultComboBoxModel<String>(new String[] 
        		{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
        				"15", "16", "17", "18", "19", "20", "21", "22", "23"}));
        Hour_CB.setBounds(693, 113, 50, 23);
        addPanel.add(Hour_CB);
        
        Minute_CB = new JComboBox<String>();
        Minute_CB.setBackground(new Color(255, 255, 255));
        Minute_CB.setModel(new DefaultComboBoxModel<String>(new String[] 
        		{"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"}));
        Minute_CB.setBounds(788, 113, 50, 23);
        addPanel.add(Minute_CB);
        
        arrHour_CB = new JComboBox<String>();
        arrHour_CB.setBackground(new Color(255, 255, 255));
        arrHour_CB.setModel(new DefaultComboBoxModel<String>(new String[] 
        		{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", 
        				"15", "16", "17", "18", "19", "20", "21", "22", "23"}));
        arrHour_CB.setBounds(693, 172, 50, 23);
        addPanel.add(arrHour_CB);
        
        arrMinute_CB = new JComboBox<String>();
        arrMinute_CB.setBackground(new Color(255, 255, 255));
        arrMinute_CB.setModel(new DefaultComboBoxModel<String>(new String[] 
        		{"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"}));
        arrMinute_CB.setBounds(788, 172, 50, 23);
        addPanel.add(arrMinute_CB);
        
        Month_LB = new JLabel("월");
        Month_LB.setFont(new Font("굴림", Font.PLAIN, 13));
        Month_LB.setBounds(574, 121, 21, 15);
        addPanel.add(Month_LB);
        
        arrMonth_LB = new JLabel("월");
        arrMonth_LB.setFont(new Font("굴림", Font.PLAIN, 13));
        arrMonth_LB.setBounds(574, 180, 21, 15);
        addPanel.add(arrMonth_LB);
        
        Day_LB = new JLabel("일");
        Day_LB.setFont(new Font("굴림", Font.PLAIN, 13));
        Day_LB.setBounds(648, 120, 21, 15);
        addPanel.add(Day_LB);
        
        arrDay_LB = new JLabel("일");
        arrDay_LB.setFont(new Font("굴림", Font.PLAIN, 13));
        arrDay_LB.setBounds(648, 179, 21, 15);
        addPanel.add(arrDay_LB);
        
        Hour_LB = new JLabel("시");
        Hour_LB.setFont(new Font("굴림", Font.PLAIN, 13));
        Hour_LB.setBounds(743, 122, 21, 15);
        addPanel.add(Hour_LB);
        
        arrHour_LB = new JLabel("시");
        arrHour_LB.setFont(new Font("굴림", Font.PLAIN, 13));
        arrHour_LB.setBounds(743, 181, 21, 15);
        addPanel.add(arrHour_LB);
        
        Minute_LB = new JLabel("분");
        Minute_LB.setFont(new Font("굴림", Font.PLAIN, 13));
        Minute_LB.setBounds(839, 122, 21, 15);
        addPanel.add(Minute_LB);
        
        arrMinute_LB = new JLabel("분");
        arrMinute_LB.setFont(new Font("굴림", Font.PLAIN, 13));
        arrMinute_LB.setBounds(839, 181, 21, 15);
        addPanel.add(arrMinute_LB);
        
        Seat_LB = new JLabel("석");
        Seat_LB.setFont(new Font("굴림", Font.PLAIN, 13));
        Seat_LB.setBounds(574, 243, 21, 15);
        addPanel.add(Seat_LB);
        
        seat_LB = new JLabel("회차");
        seat_LB.setFont(new Font("굴림", Font.PLAIN, 13));
        seat_LB.setBounds(565, 50, 30, 15);
        addPanel.add(seat_LB);
        
        seat_LB_1 = new JLabel("석");
        seat_LB_1.setFont(new Font("굴림", Font.PLAIN, 13));
        seat_LB_1.setBounds(313, 243, 21, 15);
        addPanel.add(seat_LB_1);
        
        Won_LB = new JLabel("원");
        Won_LB.setFont(new Font("굴림", Font.PLAIN, 13));
        Won_LB.setBounds(313, 300, 21, 15);
        addPanel.add(Won_LB);


        // 추가 버튼 클릭 이벤트
        addTrainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTrain();
            }
        });

        // 상단 패널
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 1089, 92);
        panel.setBackground(new Color(219, 254, 205));
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel TM_JL = new JLabel("기차 관리");
        TM_JL.setFont(new Font("휴먼엑스포", Font.BOLD, 28));
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
        JButton movement_BT = new JButton("메인화면으로..");
        movement_BT.setFont(new Font("굴림", Font.BOLD, 12));
        movement_BT.setBackground(Color.WHITE);
        movement_BT.setBounds(940, 36, 122, 32);
        panel.add(movement_BT);

        // 조회 버튼
        JButton Search_BT = new JButton("조회");
        Search_BT.setFont(new Font("굴림", Font.BOLD, 12));
        Search_BT.setBackground(Color.WHITE);
        Search_BT.setBounds(451, 23, 97, 45);
        panel.add(Search_BT);

        // 테이블 생성 및 설정
        String[] header = {"기차 번호", "기차 유형", "운행 회차", "출발역", "도착역", "출발일시", "도착일시", "금액", 
                           "일반석 좌석수", "유아석 좌석수", "입석 여부", "운행 고유번호"};
        model = new DefaultTableModel(header, 0);
        tablePanel.setLayout(null);
        table = new JTable(model);
        JScrollPane jsp = new JScrollPane
        		(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jsp.setBounds(0, 0, 1089, 446);
        tablePanel.add(jsp);

        // 가운데 정렬을 위한 렌더러 설정
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // 모든 열에 대해 가운데 정렬 적용
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

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
                cardLayout.show(cardPanel, "table"); // 테이블 카드로 전환
            }
        });

        // "추가" 버튼 클릭 이벤트
        Add_BT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "add"); // 추가 화면 카드로 전환
            }
        });

     // "메인으로.." 버튼 이벤트
        movement_BT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        TrainManagement.this,
                        "관리자 메인화면으로 이동하시겠습니까??",
                        "로그아웃",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );
                if (result == JOptionPane.YES_OPTION) {
                    MasterMain masterMainFrame = new MasterMain(); // MasterMain 창 생성
                    masterMainFrame.setVisible(true); // MasterMain 창을 보여줌
                    dispose(); // 현재 TrainManagement 창 종료
                }
            }
        });
    }

    // 기차 추가 메서드
    public void addTrain() {
        String trainNum = TrainNum_TF.getText();
        String scheduleId = Count_CB.getSelectedItem().toString();
        String tt = TrainType_CB.getSelectedItem().toString();
        String startStation = Start_CB.getSelectedItem().toString();
        String endStation = Arr_CB.getSelectedItem().toString();
        String start_day = String.format("%4d-%2d-%2d %2d:%2d", 
                Integer.parseInt(year_CB.getSelectedItem().toString()),
                Integer.parseInt(month_CB.getSelectedItem().toString()),
                Integer.parseInt(Day_CB.getSelectedItem().toString()),
                Integer.parseInt(Hour_CB.getSelectedItem().toString()),  // 시
                Integer.parseInt(Minute_CB.getSelectedItem().toString()));  // 분
        String arrive_day = String.format("%4d-%2d-%2d %2d:%2d", 
                Integer.parseInt(arrYear_CB.getSelectedItem().toString()),
                Integer.parseInt(arrMonth_CB.getSelectedItem().toString()),
                Integer.parseInt(arrDay_CB.getSelectedItem().toString()),
                Integer.parseInt(arrHour_CB.getSelectedItem().toString()),  // 시
                Integer.parseInt(arrMinute_CB.getSelectedItem().toString()));  // 분
        String money = Charge_CB.getSelectedItem().toString(); // 요금
        String standSeat = StandSeat_CB.getSelectedItem().toString();
        String baby_seat = babySeat_CB.getSelectedItem().toString();
        String economy_seat  = EconomySeat_CB.getSelectedItem().toString();

        // 출발날짜 선택값 가져오기
        int year = Integer.parseInt(year_CB.getSelectedItem().toString());
        String month = month_CB.getSelectedItem().toString();
        int day = Integer.parseInt(Day_CB.getSelectedItem().toString());
        
        // 도착날짜 선택값 가져오기
        int arryear = Integer.parseInt(arrYear_CB.getSelectedItem().toString());
        String arrmonth = arrMonth_CB.getSelectedItem().toString();
        int arrday = Integer.parseInt(arrDay_CB.getSelectedItem().toString());
        
        // 날짜 형식으로 변환
        String selectedDate = year + "-" + month + "-" + day;
        String arrivedDate = arryear + "-" + arrmonth + "-" + arrday;
        
        if (trainNum.isEmpty() || scheduleId.isEmpty() || tt.isEmpty() || startStation.isEmpty() || endStation.isEmpty() ||
        	start_day.isEmpty() || arrive_day.isEmpty() ||	money.isEmpty() || standSeat.isEmpty() || baby_seat.isEmpty() ||
        	economy_seat.isEmpty()) {
            JOptionPane.showMessageDialog(this, "모든 항목을 입력해주세요.");
            System.out.println(trainNum + scheduleId + startStation + endStation + selectedDate + arrivedDate + standSeat);
            return;  
        }

        try (Connection conn = connect()) {
        	// 기존 데이터의 기차번호와 일치하는지 확인
			String checkTrainSql = "SELECT * FROM train WHERE train_num = ?";//train > 기차번호 유뮤 확인
			pstmt = conn.prepareStatement(checkTrainSql);
			pstmt.setInt(1, Integer.parseInt(trainNum));
			rs = pstmt.executeQuery();

			if (rs.next()) {
				JOptionPane.showMessageDialog(null, "동일한 기차번호가 존재합니다.");
				return;
			}
			
            // 기차 추가 SQL
            String insertTrain = "INSERT INTO TRAIN VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(insertTrain);
            pstmt.setString(1, trainNum);
            pstmt.setString(2, tt);
            pstmt.setString(3, startStation);
            pstmt.setString(4, endStation);
            pstmt.executeUpdate();

            // 일정 추가 SQL
            String insertSchedule = "INSERT INTO schedule "
            		+ "(schedule_num, SCHEDULE_ID, train_num, START_DAY, ARRIVE_DAY, MONEY, ECONOMY_SEAT, BABY_SEAT, "
            		+ "STAND_SEAT) VALUES (schedule_seq.nextval,?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(insertSchedule); 
            pstmt.setString(1, scheduleId);
            pstmt.setString(2, trainNum);
            pstmt.setString(3, start_day);
            pstmt.setString(4, arrive_day);
            pstmt.setString(5, money);
            pstmt.setString(6, economy_seat);
            pstmt.setString(7, baby_seat);
            pstmt.setString(8, standSeat);
            pstmt.executeUpdate();
            
            // 시트 추가 SQL
            String seattablesql = "insert into seatinfo(train_num, schedule_num, one_seat_count, inside_seat_count, outside_seat_count) values(?,schedule_seq.currval,floor(?/3),floor(?/3),(FLOOR(?/3)+MOD(?,3)))";
            //train_num / schedule_num / one_seat_count / inside_seat_count / outside_seat_count
            //EconomySeat_CB
            
            pstmt = conn.prepareStatement(seattablesql); 
            pstmt.setString(1, trainNum);
            pstmt.setInt(2, Integer.parseInt((String) EconomySeat_CB.getSelectedItem()));
            pstmt.setInt(3, Integer.parseInt((String) EconomySeat_CB.getSelectedItem()));
            pstmt.setInt(4, Integer.parseInt((String) EconomySeat_CB.getSelectedItem()));
            pstmt.setInt(5, Integer.parseInt((String) EconomySeat_CB.getSelectedItem()));
            
            pstmt.executeUpdate();
            
            JOptionPane.showMessageDialog(this, "기차 정보 추가 완료.");
            clearAddFields(); // 입력 필드 초기화
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "추가 실패.");
        }
    }

 // 입력 필드 초기화
    public void clearAddFields() {
        // 모든 컴포넌트를 하나의 배열로 묶기 
        JComponent[] components = new JComponent[]{
            TrainNum_TF, Count_CB, TrainType_CB, Start_CB, year_CB, month_CB, Day_CB, Hour_CB, Minute_CB,
            Arr_CB, arrYear_CB, arrMonth_CB, arrDay_CB, arrHour_CB, arrMinute_CB, EconomySeat_CB, babySeat_CB, StandSeat_CB, Charge_CB
        };
        
        // 각 컴포넌트 초기화
        for (JComponent component : components) {
            if (component instanceof JTextField) {
                ((JTextField) component).setText("");  // JTextField는 텍스트 초기화
            } else if (component instanceof JComboBox) {
                ((JComboBox<?>) component).setSelectedIndex(0);  // JComboBox는 첫 번째 항목 선택
            } 
        }
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