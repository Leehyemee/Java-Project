package start;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class FindId extends JFrame {

    JLabel Name_JL, Phone_JL;
    JTextField Name_TF, Phone_TF;
    JButton FindId_BT;

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    private JLabel Caution_JL;

    public FindId() {
    	getContentPane().setBackground(new Color(255, 255, 255));
        // 기본적인 레이아웃 설정
        setTitle("아이디 찾기");
        getContentPane().setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // 창을 닫을 때 dispose()
        setBounds(100, 100, 340, 250);
        setResizable(false);
        
        // 이름 입력란
        Name_JL = new JLabel("이름");
        Name_JL.setBounds(65, 40, 50, 20);
        getContentPane().add(Name_JL);

        Name_TF = new JTextField();
        Name_TF.setBounds(125, 40, 120, 20);
        getContentPane().add(Name_TF);

        // 전화번호 입력란
        Phone_JL = new JLabel("전화번호");
        Phone_JL.setBounds(65, 80, 50, 20);
        getContentPane().add(Phone_JL);

        Phone_TF = new JTextField();
        Phone_TF.setBounds(125, 80, 120, 20);
        getContentPane().add(Phone_TF);

        // 아이디 찾기 버튼
        FindId_BT = new JButton("아이디 찾기");
        FindId_BT.setBounds(101, 149, 120, 30);
        getContentPane().add(FindId_BT);
        FindId_BT.setForeground(Color.BLACK);
        FindId_BT.setBackground(Color.WHITE);
        
        Caution_JL = new JLabel("※ 전화번호는 \"-\" 까지 입력");
        Caution_JL.setForeground(new Color(255, 128, 128));
        Caution_JL.setBounds(95, 110, 150, 15);
        getContentPane().add(Caution_JL);
        
        // 버튼 클릭 이벤트 (아이디 찾기 로직)
        FindId_BT.addActionListener(e -> {
            // 실제로 아이디 찾기 로직을 추가해야 합니다.
            // 예시로 메시지를 표시하는 코드
            String name = Name_TF.getText();
            String phone = Phone_TF.getText();

            if (!name.isEmpty() && !phone.isEmpty()) {
                // DB에서 아이디 찾기 메서드 호출
                findId(name, phone);
            } else {
                JOptionPane.showMessageDialog(this, "이름과 전화번호를 입력하세요.");
            }
        });

        setVisible(true);  // 화면을 표시
    }

    // 데이터베이스 연결
    void connect() {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String user = "hyemee";
        String password = "1234";

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
            if (con != null) {
                System.out.println("오라클 데이터베이스와 연결 성공!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 아이디 찾기 로직
    private void findId(String name, String phone) {
        try {
            // DB 연결
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hyemee", "1234");

            // SQL 쿼리 작성 (이름과 전화번호로 아이디를 찾음)
            String sql = "SELECT id FROM users WHERE username = ? AND phone = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, phone);

            // 쿼리 실행
            rs = pstmt.executeQuery();

            // 결과 처리
            if (rs.next()) {
                // 일치하는 사용자 정보가 있으면 아이디 출력
                String id = rs.getString("id");
                JOptionPane.showMessageDialog(this, "찾은 아이디: " + id);
                
                // 아이디 찾기가 성공하면 로그인 화면을 표시
                new Login(); // 로그인 화면 실행
                dispose();   // 현재 아이디 찾기 창은 닫기
            } else {
                // 일치하는 데이터가 없으면 오류 메시지 출력
                JOptionPane.showMessageDialog(this, "이름과 전화번호가 일치하지 않습니다.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "DB 오류가 발생했습니다.");
        } finally {
            // 자원 해제
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new FindId();
    }
}

// 확인 누르면 login 클래스 실행 되게++