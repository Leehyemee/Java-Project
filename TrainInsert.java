package windowBuilder;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TrainInsert extends JFrame {

    Connection con = null;                  // DB와 연결하는 객체
	PreparedStatement pstmt = null;         // SQL문을 DB에 전송하는 객체
	ResultSet rs = null;                    // SQL문 실행 결과를 가지고 있는 객체
	String sql = null;                      // SQL문을 저장하는 문자열 변수.
    
    JTextField numInput,strInput,arrInput,strDayInput,arrDayInput,typeInput,eco_seat_input,baby_seat_input,stand_seat_input,schedule_id_input;

    public TrainInsert() {
        super();
        setTitle("관리자 - 기차 추가 창");

        JPanel total = new JPanel(new GridBagLayout());
        JPanel container1 = new JPanel(new GridBagLayout());
        JPanel container2 = new JPanel(new GridBagLayout());
        JPanel container3 = new JPanel(new GridBagLayout());
        JPanel container4 = new JPanel(new GridBagLayout());
        JPanel container5 = new JPanel(new GridBagLayout());
        JPanel container6 = new JPanel(new GridBagLayout());
        JPanel container7 = new JPanel(new GridBagLayout());
        JPanel container8 = new JPanel(new GridBagLayout());
        JPanel container9 = new JPanel(new GridBagLayout());
        JPanel container0 = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.ipadx = 100;
        gbc.ipady = 5;
        /* gbc.anchor = GridBagConstraints.WEST; // 이 부분을 추가하여 왼쪽 정렬을 강제합니다. */
        Dimension textFieldSize = new Dimension(200, 30);

        JLabel trainNum = new JLabel("기차번호");
        numInput = new JTextField(10);

        JLabel str_station = new JLabel("출발역");
        strInput = new JTextField(10);

        JLabel arr_station = new JLabel("도착역");
        arrInput = new JTextField(10);

        JLabel start_day= new JLabel("출발날짜");
        strDayInput = new JTextField(10);

        JLabel arrive_day = new JLabel("도착날짜");
        arrDayInput = new JTextField(10);
        
        JLabel train_Type = new JLabel("기차종류");
        typeInput = new JTextField(10);

        JLabel economy_seat = new JLabel("일반석 좌석 수");
        eco_seat_input = new JTextField(10);

        JLabel baby_seat = new JLabel("유아실 좌석 수");
        baby_seat_input = new JTextField(10);

        JLabel stand_seat = new JLabel("입석 가능 유무(Y/N)");
        stand_seat_input = new JTextField(10);
        
        JLabel schedule_id = new JLabel("일정번호");
        schedule_id_input = new JTextField(10);
        

        gbc.gridx=0;
        container1.add(trainNum, gbc);
        container2.add(str_station, gbc);
        container3.add(arr_station, gbc);
        container4.add(start_day, gbc);
        container5.add(arrive_day, gbc);
        container6.add(train_Type, gbc);
        // container7.add(economy_seat, gbc);
        // container8.add(baby_seat, gbc);
        container9.add(stand_seat, gbc);
        container0.add(schedule_id, gbc);
        
        gbc.gridx=1;
        /* gbc.gridwidth = GridBagConstraints.REMAINDER; */
        container1.add(numInput, gbc);
        container2.add(strInput, gbc);
        container3.add(arrInput, gbc);
        container4.add(strDayInput, gbc);
        container5.add(arrDayInput, gbc);
        container6.add(typeInput, gbc);
        // container7.add(eco_seat_input, gbc);
        // container8.add(baby_seat_input, gbc);
        container9.add(stand_seat_input, gbc);
        container0.add(schedule_id_input, gbc);

        gbc.gridy=0;
        total.add(container1, gbc);
        
        gbc.gridy=1;
        total.add(container0, gbc);

        gbc.gridy=2;
        total.add(container2, gbc);
        
        gbc.gridy=3;
        total.add(container3, gbc);

        gbc.gridy=4;
        total.add(container4, gbc);

        gbc.gridy=5;
        total.add(container5, gbc);

        gbc.gridy=6;
        total.add(container6, gbc);
        
        gbc.gridy=7;
        total.add(container8, gbc);
        
        gbc.gridy=8;
        total.add(container9, gbc);
        

        JButton traininsert = new JButton("저장");

        gbc.gridy=0;
        setLayout(new GridBagLayout());
        add(total, gbc);
        gbc.gridy=1;
        add(traininsert, gbc);

        // 요금은 일단 값 지정 해놓기 > ktx 일반석이면 5 유아석 2 입석 1 나중에 수정할 수 있으면 할 것.
        
        traininsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connect();
                insert();
            }
        });

        setVisible(true);
        setBounds(1000, 500, 500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    void connect(){
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
    void insert(){
        
		try {
		    // 1. 기차 번호와 일정 번호가 이미 존재하는지 확인
		    String checkSql = "SELECT COUNT(*) FROM schedule WHERE train_num = ? AND schedule_id = ?";
		    pstmt = con.prepareStatement(checkSql);
		    pstmt.setInt(1, Integer.parseInt(numInput.getText()));  // 입력받은 기차 번호
		    pstmt.setInt(2, Integer.parseInt(schedule_id_input.getText()));  // 입력받은 일정 번호

		    ResultSet rs = pstmt.executeQuery();
		    
		    // 이미 동일한 기차 번호와 일정 번호가 존재하는지 확인
		    if (rs.next() && rs.getInt(1) > 0) {
		        JOptionPane.showMessageDialog(null, "이미 동일한 기차 번호와 일정 번호가 존재합니다.");
		        return;  // 동일한 데이터가 있으면 메서드 종료 (INSERT 건너뛰기)
		    }
		    			
			
			sql = "insert into train values(?, ?, ?, ?)";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, Integer.parseInt(numInput.getText()));
			pstmt.setString(2, typeInput.getText());
			pstmt.setString(3, strInput.getText());
			pstmt.setString(4, arrInput.getText());
			
			int res = pstmt.executeUpdate();
			
			if(res > 0) {
				JOptionPane.showMessageDialog(null, "등록 성공");
			}else {
				JOptionPane.showMessageDialog(null, "등록 실패");
			}

            con.setAutoCommit(false);

            // 조회화면에 해당 기차번호의 일정번호를 보여줘야 할 듯.
            sql = "insert into schedule values(schedule_seq.nextval, ?, ?, ?, ?, 50000, 60, 30, ?)";
            pstmt = con.prepareStatement(sql);
            
            pstmt.setInt(1, Integer.parseInt(schedule_id_input.getText()));

            pstmt.setInt(2, Integer.parseInt(numInput.getText()));
            //String inputString = "2024-11-27 19:46:00";으로 입력
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date parsedDate = dateFormat.parse(strDayInput.getText());
            Timestamp timestamp = new Timestamp(parsedDate.getTime());
            pstmt.setTimestamp(3, timestamp);

            parsedDate = dateFormat.parse(arrDayInput.getText());
            timestamp = new Timestamp(parsedDate.getTime());
            pstmt.setTimestamp(4, timestamp);

            pstmt.setString(5, stand_seat_input.getText());

            res = pstmt.executeUpdate();

            if(res>0){
                System.out.println("schedule update 성공");
            }else{
                System.out.println("schedule update 실패");
            }

            /* 좌석 수 지정해 놓음 >> (ktx, 새마을호)기차 타입에 따라 다르게 삽입되게 하기 */
            sql = "insert into seatinfo values(?, schedule_seq.currval, 20, 20, 20)";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(numInput.getText()));

			res = pstmt.executeUpdate();

            if(res>0){
                System.out.println("seatinfo update 성공");
            }else {
                System.out.println("seatinfo update 실패");
            }

            con.setAutoCommit(true);

			pstmt.close(); 
            con.close();

		} catch (Exception e) {
            System.err.println("train insert() 에러");
            System.out.println(e.getMessage());
            e.getStackTrace();
		}

    }
    public static void main(String[] args) {
        new TrainInsert();
    }
}
