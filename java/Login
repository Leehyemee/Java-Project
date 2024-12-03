package start;

import java.awt.*;
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

    // db연동시 필요
    // Jdbc jdbc;
    // Connection con = null;
    // PreparedStatement pstmt = null;
    // ResultSet rs = null;
    // String sql = null;
    // Sign sign = null;

    public Login() {
        in();
    }

    private void in() {
        getContentPane().setLayout(null);

        // 배경 이미지를 설정하는 JPanel 클래스를 만듭니다.
        JPanel backgroundPanel = new JPanel() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // 배경 이미지를 그립니다. 경로는 본인의 이미지 경로로 수정하세요.
                ImageIcon icon = new ImageIcon("images/train2.jpg");
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);  // 배경 이미지를 JFrame 크기에 맞게 그리기
            }
        };

        // 배경 패널의 크기를 JFrame 크기와 동일하게 설정
        backgroundPanel.setBounds(0, 0, 311, 475);  // 311 x 475로 수정
        backgroundPanel.setLayout(null);  // 배경 위에 컴포넌트를 추가할 수 있도록 설정

        // 배경 패널을 JFrame에 추가
        getContentPane().add(backgroundPanel);  // JFrame에 JPanel을 추가
        
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

    public static void main(String[] args) {
        new Login(); // 로그인 화면을 띄운다
    }
}
// 회원 로그인 누르면 회원의 메인화면 -- 민정님?
// 관리자 로그인 누르면 관리자 로그인 후 관리자 메인화면++
