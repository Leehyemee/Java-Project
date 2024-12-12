package start;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class Login extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel Id_JL, Pwd_JL;
    JTextField Id_TF;
    JPasswordField Pwd_TF;
    JButton jbjoin, Login_BT, Join_BT, FindId_BT, FindPwd_BT;
    ImageIcon icon;

     Connection con = null;
     PreparedStatement pstmt = null;
     ResultSet rs = null;
     String sql = null;
     Dto dto;

    public Login() {
        in();
    }

    private void in() {
        getContentPane().setLayout(null);

        
        JPanel backgroundPanel = new JPanel() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("images/train2.jpg");
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);  // 배경 이미지를 JFrame 크기에 맞게 그리기
            }
        };

        // 배경 패널의 크기를 JFrame 크기와 동일하게 설정
        backgroundPanel.setBounds(0, 0, 311, 475);  
        backgroundPanel.setLayout(null); 

        // 배경 패널을 JFrame에 추가
        getContentPane().add(backgroundPanel);  
        
        // UI 구성 요소들 추가
        Id_JL = new JLabel("아이디");
        Id_JL.setBackground(new Color(255, 255, 255));
        Id_JL.setBounds(55, 149, 48, 20);
        Id_JL.setForeground(Color.BLACK);
        Id_JL.setFont(new Font("새굴림", Font.BOLD, 15)); 
        backgroundPanel.add(Id_JL);

        Pwd_JL = new JLabel("비밀번호");
        Pwd_JL.setBounds(39, 186, 64, 20);
        Pwd_JL.setForeground(Color.BLACK);
        Pwd_JL.setFont(new Font("새굴림", Font.BOLD, 15)); 
        backgroundPanel.add(Pwd_JL);

        Id_TF = new JTextField();
        Id_TF.setBounds(115, 149, 120, 20);
        backgroundPanel.add(Id_TF);

        Pwd_TF = new JPasswordField();
        Pwd_TF.setBounds(115, 189, 120, 20);
        backgroundPanel.add(Pwd_TF);

        Join_BT = new JButton("회원가입");
        Join_BT.setFont(new Font("새굴림", Font.BOLD, 12));
        Join_BT.setBackground(Color.WHITE);
        Join_BT.setForeground(Color.BLACK);
        Join_BT.setBounds(42, 231, 100, 39);
        backgroundPanel.add(Join_BT);

        Login_BT = new JButton("로그인");
        Login_BT.setFont(new Font("새굴림", Font.BOLD, 12));
        Login_BT.setBackground(Color.WHITE);
        Login_BT.setForeground(Color.BLACK);
        Login_BT.setBounds(155, 231, 80, 39);
        backgroundPanel.add(Login_BT);

        FindId_BT = new JButton("아이디 찾기");
        FindId_BT.setFont(new Font("새굴림", Font.BOLD, 12));
        FindId_BT.setBackground(Color.WHITE);
        FindId_BT.setForeground(Color.BLACK);
        FindId_BT.setBounds(42, 280, 193, 34);
        backgroundPanel.add(FindId_BT);

        FindPwd_BT = new JButton("비밀번호 찾기");
        FindPwd_BT.setFont(new Font("새굴림", Font.BOLD, 12));
        FindPwd_BT.setBackground(Color.WHITE);
        FindPwd_BT.setForeground(Color.BLACK);
        FindPwd_BT.setBounds(42, 324, 193, 34);
        backgroundPanel.add(FindPwd_BT);

        JButton MasterLogin_BT = new JButton("관리자 로그인");
        MasterLogin_BT.setBounds(42, 383, 193, 23);
        MasterLogin_BT.setFont(new Font("새굴림", Font.BOLD, 12));
        MasterLogin_BT.setForeground(Color.BLACK);
        MasterLogin_BT.setBackground(Color.WHITE);
        backgroundPanel.add(MasterLogin_BT);

	// 로그인 버튼 클릭 시
        Login_BT.addActionListener(e -> {
            String Id = Id_TF.getText();  // 입력된 아이디
            String password = new String(Pwd_TF.getPassword());  // 입력된 비밀번호

            // DB에서 아이디와 비밀번호가 일치하는지 확인
            if (isValidLogin(Id, password)) {
                JOptionPane.showMessageDialog(this, "로그인 성공");
                UserDto userdto = new UserDto();
                userdto.setId(Id);  // 아이디 설정
                new MemMain(userdto).setVisible(true);  // MemMain 화면 띄우기
                this.dispose();  // 현재 로그인 창 닫기
                
            } else {
                JOptionPane.showMessageDialog(this, "아이디나 비밀번호가 일치하지 않습니다.", "로그인 실패", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        
        // 아이디 찾기 이벤트
        FindId_BT.addActionListener(e -> {
            new FindId();  // 아이디 찾기 화면을 띄운다
            setVisible(false);  // 현재 로그인 창 숨기기
        });

        // 비밀번호 찾기 버튼 클릭 시 이벤트 처리
        FindPwd_BT.addActionListener(e -> {
            new FindPwd();  // 비밀번호 찾기 화면을 띄운다
            setVisible(false);  // 현재 로그인 창 숨기기
        });

        // 회원가입 버튼 클릭 시 이벤트 처리
        Join_BT.addActionListener(e -> {
            new SignUp(); // 회원가입 창을 띄운다.
            setVisible(false);
        });

        // 관리자 로그인 버튼 이벤트
        MasterLogin_BT.addActionListener(e -> {
        	SwingUtilities.invokeLater(() -> {
            new MasterLogin();  // 관리자 로그인 창을 띄운다.
            setVisible(false);  // 로그인 화면을 숨긴다.
        });
        // 로그인 창 숨기기
        setVisible(false);  // 로그인 화면을 숨긴다.
        });
        	
        	
        setTitle("로그인");
        setBounds(100, 100, 311, 475);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // 데이터베이스 연결 및 로그인 검증 메소드
    private boolean isValidLogin(String userId, String password) {
        boolean isValid = false;
        
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String user = "hyemee";
        String dbpassword = "1234";
        
     // 공백 제거 및 소문자로 변환
        userId = userId.trim().toLowerCase();
        password = password.trim();
        
        try {
            // JDBC 드라이버 로드
            Class.forName(driver);  // Oracle JDBC 드라이버 로드

            // DB 연결
            con = DriverManager.getConnection(url, user, dbpassword);

            // 로그인 검증 쿼리
            sql = "SELECT * FROM USERS WHERE ID = ? AND PASSWORD = ?";  
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userId);  // 입력된 아이디
            pstmt.setString(2, password);  // 입력된 비밀번호

            rs = pstmt.executeQuery();

            // 아이디와 비밀번호가 일치하면 로그인 성공
            if (rs.next()) {
                isValid = true;  // 일치하는 레코드가 있으면 로그인 성공
            }

        } catch (SQLException e) {
            e.printStackTrace();  // SQL 오류 발생 시 출력
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  // 드라이버 클래스를 찾지 못할 경우
        } finally {
            try {
                if (rs != null) rs.close();  // ResultSet 닫기
                if (pstmt != null) pstmt.close();  // PreparedStatement 닫기
                if (con != null) con.close();  // Connection 닫기
            } catch (SQLException e) {
                e.printStackTrace();  // 예외 처리
            }
        }

        return isValid;  // 로그인 유효성 반환
    }
    
    public static void main(String[] args) {
        new Login(); // 로그인 화면을 띄운다
    }
}