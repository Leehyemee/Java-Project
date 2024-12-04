package start;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MasterLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField MId_TF;
	private JPasswordField MPwd_PF;
	
	Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = null;
	ImageIcon icon;
	
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
	
	public MasterLogin() {
		setTitle("관리자 로그인");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 335, 430);
		setResizable(false);
		contentPane = new JPanel() {
		
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
        	protected void paintComponent(Graphics g) {
            super.paintComponent(g);  // 기존의 paintComponent 기능을 유지하면서
            ImageIcon icon = new ImageIcon("images/master.jpg");
            Image img = icon.getImage();
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);  // 이미지를 패널 크기에 맞게 그린다.
        	}
		};
    	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel MId_JL = new JLabel("관리자 아이디");
		MId_JL.setBounds(36, 238, 116, 15);
		contentPane.add(MId_JL);
		MId_JL.setFont(new Font("Gothic", Font.BOLD, 16)); 
		MId_JL.setPreferredSize(new Dimension(100, 30));
        
		MId_TF = new JTextField();
		MId_TF.setBounds(167, 238, 116, 21);
		contentPane.add(MId_TF);
		MId_TF.setColumns(10);
		
		JLabel MPwd_JL = new JLabel("관리자 비밀번호");
		MPwd_JL.setBounds(36, 283, 116, 15);
		contentPane.add(MPwd_JL);
		MPwd_JL.setFont(new Font("Gothic", Font.BOLD, 16)); 
		MPwd_JL.setPreferredSize(new Dimension(100, 30));
        
		MPwd_PF = new JPasswordField();
		MPwd_PF.setBounds(167, 283, 116, 21);
		contentPane.add(MPwd_PF);
		
		JButton Login_BT = new JButton("로그인");
		Login_BT.setBounds(101, 328, 97, 23);
		contentPane.add(Login_BT);
		Login_BT.setBackground(Color.WHITE);  
		Login_BT.setForeground(Color.BLACK); 
		Login_BT.setFont(new Font("굴림", Font.PLAIN, 12));
		
	
		MId_JL.setBackground(Color.WHITE);  // 배경색 파란색
		MId_JL.setForeground(Color.BLACK);  // 텍스트 색 흰색
		MId_JL.setOpaque(true);  // 배경색이 보이도록 설정
		
		MPwd_JL.setBackground(Color.WHITE);  // 배경색 초록색
		MPwd_JL.setForeground(Color.BLACK);  // 텍스트 색 흰색
		MPwd_JL.setOpaque(true);  // 배경색이 보이도록 설정
		
		 // 로그인 버튼 클릭 시 아이디와 비밀번호 확인
        Login_BT.addActionListener(e -> {
        String ID = MId_TF.getText();  // 입력된 아이디
        String password = new String(MPwd_PF.getPassword());  // 입력된 비밀번호

         // 데이터베이스 연결
         connect();

         // 관리자 아이디가 "admin_"로 시작하는지 확인
         if (ID.startsWith("admin_")) {
                if (checkLogin(ID, password)) {
                    // 로그인 성공 시
                    JOptionPane.showMessageDialog(this, "로그인 성공!");
                    
                    // 로그인 성공 후 MasterMain 클래스 화면을 띄운다
                    MasterMain mainFrame = new MasterMain(); // MasterMain 클래스 인스턴스 생성
                    mainFrame.setVisible(true); // MasterMain 화면을 띄운다
                    this.dispose(); // 로그인 창은 종료
                } else {
                    // 로그인 실패 시
                    JOptionPane.showMessageDialog(this, "아이디 또는 비밀번호가 틀렸습니다.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "관리자 아이디로 로그인할 수 없습니다.", "로그인 실패", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);  // 로그인 화면을 보이도록 설정
    }

    // DB에서 아이디와 비밀번호를 확인하는 메서드
    public boolean checkLogin(String ID, String password) {
        try {
            sql = "SELECT * FROM users WHERE ID = ? AND password = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, ID);
            pstmt.setString(2, password);

            rs = pstmt.executeQuery();
            if (rs.next()) {  // 로그인 성공
                return true;
            } else {
                return false;  // 로그인 실패
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // DB 연결 오류 등의 예외가 발생하면 로그인 실패
        } finally {
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
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MasterLogin frame = new MasterLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}