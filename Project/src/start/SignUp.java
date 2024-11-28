package start;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignUp extends JFrame {

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = null;
    ImageIcon icon;
    
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField Id_TF;
    private JPasswordField Pwd_TF;
    private JPasswordField PwdCheck_TF;
    private JTextField Email_TF;
    private JTextField Name_TF;
    private JTextField Address_TF;
    private JTextField Address_TF2;
    private JTextField Phone_TF, Phone_TF2;  // phoneField_2를 인스턴스 변수로 변경
    private JTextField BirthField;
    private JComboBox<String> Email_CB, Phone_CB;
    private JButton idchb;
    private boolean isIdChecked = false;  // 아이디 중복확인 여부를 저장하는 변수
    
    
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

    
    
    // 회원가입 정보 insert
    void insert() {
        try {
            sql = "insert into USERS(ID, PASSWORD, BIRTHDATE, USERNAME, ADDRESS, PHONE, EMAIL) values (?, ?, ?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(sql);

            // 수정: 인스턴스 변수들을 사용
            pstmt.setString(1, Id_TF.getText());
            pstmt.setString(2, new String(Pwd_TF.getPassword()));  // 비밀번호는 getPassword()로 가져오므로 String으로 변환
            pstmt.setString(3, BirthField.getText());
            pstmt.setString(4, Name_TF.getText());
            pstmt.setString(5, Address_TF.getText()+ " " + Address_TF2.getText());
            pstmt.setString(6, Phone_CB.getSelectedItem() + "-" + Phone_TF.getText() + "-" + Phone_TF2.getText());
            pstmt.setString(7, Email_TF.getText() + "@" + Email_CB.getSelectedItem());

            int result = pstmt.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(null, "회원가입 성공!");
                
                // 회원가입 성공 시 Login 화면을 띄운다.
                dispose();  // 현재 SignUp 창을 닫는다.
                Login loginFrame = new Login();  // Login 창을 생성
                loginFrame.setVisible(true);  // Login 창을 보이게 한다.
            } else {
                JOptionPane.showMessageDialog(null, "회원가입 실패~");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    } // insert() end

    void checkDuplicateID() {
        String userId = Id_TF.getText();  // 아이디 입력 필드에서 텍스트 가져오기
        if (userId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "아이디를 입력해주세요.");
            return;
        }

        String sql = "SELECT COUNT(*) FROM USERS WHERE ID = ?";
        try {
            // 데이터베이스 연결이 되어 있는지 확인
            if (con == null || con.isClosed()) {
                connect(); // 연결이 끊어진 경우 연결 시도
            }

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userId);  // 아이디를 쿼리 파라미터로 설정
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);  // 결과로 나온 카운트 값 가져오기
                if (count > 0) {
                    // 아이디가 이미 존재하면
                    JOptionPane.showMessageDialog(null, "이미 존재하는 아이디입니다.");
                } else {
                    // 아이디가 존재하지 않으면
                    JOptionPane.showMessageDialog(null, "사용 가능한 아이디입니다.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "아이디 중복 확인 중 오류가 발생했습니다.");
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private ImageIcon backgroundImage;
    
    public SignUp() {
        backgroundImage = new ImageIcon("images/welcome.jpg");

        setTitle("회원가입");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 797);
        setResizable(false);
        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel Id_JL = new JLabel("아이디");
		Id_JL.setBounds(67, 218, 57, 15);
		contentPane.add(Id_JL);
		
		JLabel Pwd_JL = new JLabel("비밀번호");
		Pwd_JL.setBounds(67, 264, 57, 15);
		contentPane.add(Pwd_JL);
		
		JLabel PwdCheck_JL = new JLabel("비밀번호 확인");
		PwdCheck_JL.setBounds(67, 306, 89, 15);
		contentPane.add(PwdCheck_JL);
		
        // 필드 초기화
        Id_TF = new JTextField();
        Id_TF.setBounds(205, 215, 116, 21);
        contentPane.add(Id_TF);

        Pwd_TF = new JPasswordField();
        Pwd_TF.setBounds(205, 261, 116, 21);
        contentPane.add(Pwd_TF);

        PwdCheck_TF = new JPasswordField();
        PwdCheck_TF.setBounds(205, 303, 116, 21);
        contentPane.add(PwdCheck_TF);

        Pwd_TF.setToolTipText("특수문자: ! @ # $ % ^ & * ( ) _ + - = [ ] { } ; : ' \" \\ | , . < > / ?");
        PwdCheck_TF.setToolTipText("특수문자: ! @ # $ % ^ & * ( ) _ + - = [ ] { } ; : ' \" \\ | , . < > / ?");
        
        JButton idchb = new JButton("중복확인");
		idchb.setBounds(376, 214, 97, 23);
		contentPane.add(idchb);
		idchb.setForeground(Color.BLACK);
		idchb.setBackground(Color.WHITE);
		
		// 중복확인 버튼 클릭 시 checkDuplicateID() 호출
        idchb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	 String userId = Id_TF.getText();  // 아이디 입력 필드에서 텍스트 가져오기
            	 
            	// 아이디가 비어 있거나 6자 미만이면 경고 메시지 출력
                if (userId.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "아이디를 입력해주세요.");
                    return;
                }
                
                if (userId.length() < 6) {
                    JOptionPane.showMessageDialog(null, "아이디는 최소 6자 이상이어야 합니다.");
                    return;
                }
            	
             // 중복확인 후 isIdChecked를 true로 설정
                checkDuplicateID(); // 아이디 중복 확인 메서드 실행
                isIdChecked = true; // 중복 확인을 마쳤으므로 isIdChecked를 true로 설정
            }
       });
		
		JLabel Email_JL = new JLabel("이메일");
		Email_JL.setBounds(67, 567, 42, 17);
		contentPane.add(Email_JL);
		
        Email_TF = new JTextField();
        Email_TF.setBounds(205, 565, 116, 20);
        contentPane.add(Email_TF);

        Name_TF = new JTextField();
        Name_TF.setColumns(10);
        Name_TF.setBounds(205, 389, 116, 21);
        contentPane.add(Name_TF);

        Address_TF = new JTextField();
        Address_TF.setColumns(10);
        Address_TF.setBounds(205, 437, 268, 21);
        contentPane.add(Address_TF);

        Address_TF2 = new JTextField();
        Address_TF2.setColumns(10);
        Address_TF2.setBounds(205, 480, 268, 21);
        contentPane.add(Address_TF2);

        Phone_TF = new JTextField();
        Phone_TF.setColumns(10);
        Phone_TF.setBounds(312, 522, 62, 21);
        contentPane.add(Phone_TF);

        Phone_TF2 = new JTextField();
        Phone_TF2.setColumns(10);
        Phone_TF2.setBounds(411, 522, 62, 21);
        contentPane.add(Phone_TF2);

        BirthField = new JTextField();
        BirthField.setColumns(10);
        BirthField.setBounds(205, 345, 116, 21);
        contentPane.add(BirthField);

        // JComboBox 설정
        Email_CB = new JComboBox<>();
        Email_CB.setBackground(new Color(255, 255, 255));
        Email_CB.setModel(new DefaultComboBoxModel<>(new String[] {"이메일 선택", "naver.com", "gmail.com", "daum.net"}));
        Email_CB.setBounds(357, 563, 116, 21);
        contentPane.add(Email_CB);

        JLabel x2 = new JLabel("*");
		x2.setForeground(new Color(255, 0, 0));
		x2.setBounds(51, 218, 16, 15);
		contentPane.add(x2);
		
		JLabel x3 = new JLabel("*");
		x3.setForeground(Color.RED);
		x3.setBounds(51, 264, 16, 15);
		contentPane.add(x3);
		
		JLabel x4 = new JLabel("*");
		x4.setForeground(Color.RED);
		x4.setBounds(51, 306, 16, 15);
		contentPane.add(x4);
		
		JLabel x1 = new JLabel("*");
		x1.setForeground(Color.RED);
		x1.setBounds(442, 189, 16, 15);
		contentPane.add(x1);
		
		JLabel xx_JL = new JLabel("필수입력사항");
		xx_JL.setBounds(460, 189, 72, 15);
		contentPane.add(xx_JL);
        
		JLabel x5 = new JLabel("*");
		x5.setForeground(Color.RED);
		x5.setBounds(51, 351, 16, 15);
		contentPane.add(x5);
		
		JLabel Birth_JL = new JLabel("생년월일");
		Birth_JL.setBounds(67, 351, 62, 15);
		contentPane.add(Birth_JL);
		
		JLabel x6 = new JLabel("*");
		x6.setForeground(Color.RED);
		x6.setBounds(51, 392, 16, 15);
		contentPane.add(x6);
		
		JLabel Name_JL = new JLabel("이름");
		Name_JL.setBounds(67, 392, 42, 15);
		contentPane.add(Name_JL);
		
		JLabel Addres_JL = new JLabel("주소");
		Addres_JL.setBounds(67, 437, 32, 15);
		contentPane.add(Addres_JL);
        
		JLabel Phone_JL = new JLabel("휴대전화");
		Phone_JL.setBounds(67, 525, 57, 15);
		contentPane.add(Phone_JL);
		
		JLabel x8 = new JLabel("*");
		x8.setForeground(Color.RED);
		x8.setBounds(51, 525, 16, 15);
		contentPane.add(x8);
		
        Phone_CB = new JComboBox<>();
        Phone_CB.setBackground(new Color(255, 255, 255));
        Phone_CB.setModel(new DefaultComboBoxModel(new String[] {"선택", "010", "011", "016", "017", "018", "019"}));
        Phone_CB.setBounds(205, 521, 62, 23);
        contentPane.add(Phone_CB);

        // 회원가입 버튼
        JButton SignUp_BT = new JButton("회원가입");
        SignUp_BT.setFont(new Font("굴림", Font.BOLD, 22));
        SignUp_BT.setBounds(205, 657, 169, 56);
        contentPane.add(SignUp_BT);
        SignUp_BT.setForeground(Color.BLACK);
        SignUp_BT.setBackground(Color.WHITE);

        JLabel lblNewLabel_6_2 = new JLabel("-");
		lblNewLabel_6_2.setBounds(289, 525, 16, 15);
		contentPane.add(lblNewLabel_6_2);
		
		JLabel lblNewLabel_6_2_1 = new JLabel("-");
		lblNewLabel_6_2_1.setBounds(383, 525, 16, 15);
		contentPane.add(lblNewLabel_6_2_1);
		
		JLabel lblNewLabel_6_2_2 = new JLabel("@");
		lblNewLabel_6_2_2.setBounds(333, 567, 16, 17);
		contentPane.add(lblNewLabel_6_2_2);
		
		JLabel lblNewLabel = new JLabel("※ 숫자, 영어 포함 6자리 이상");
		lblNewLabel.setForeground(new Color(255, 128, 128));
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 11));
		lblNewLabel.setBounds(205, 236, 150, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("※ 숫자, 영어 포함 8자리 이상");
		lblNewLabel_1.setForeground(new Color(255, 128, 128));
		lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(205, 281, 150, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("※ 숫자, 영어 포함 8자리 이상");
		lblNewLabel_1_1.setForeground(new Color(255, 128, 128));
		lblNewLabel_1_1.setFont(new Font("굴림", Font.PLAIN, 11));
		lblNewLabel_1_1.setBounds(205, 323, 150, 15);
		contentPane.add(lblNewLabel_1_1);
		
		setVisible(true); // 화면에 보이도록 설정
		
        // 회원가입 버튼 클릭 시 DB 연결 및 insert 실행
        SignUp_BT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            	// 아이디 중복확인 여부 체크
                if (!isIdChecked) {
                    JOptionPane.showMessageDialog(null, "아이디 중복확인을 해주세요.");
                    return; // 아이디 중복확인 없으면 회원가입 진행 안함
                }
                
            	// 필수 입력 사항 체크
                if (Id_TF.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "아이디를 입력해주세요.");
                    return;  // 입력되지 않으면 회원가입 진행하지 않음
                }
                if (!isValidID(Id_TF.getText())) {  // 아이디 유효성 검사
                    return;
                }
                
                if (Pwd_TF.getPassword().length == 0) {
                    JOptionPane.showMessageDialog(null, "비밀번호를 입력해주세요.");
                    return;
                }
                if (!isValidPassword(new String(Pwd_TF.getPassword()))) {  // 비밀번호 유효성 검사
                    return;
                }
                
                if (PwdCheck_TF.getPassword().length == 0) {
                    JOptionPane.showMessageDialog(null, "비밀번호 확인을 입력해주세요.");
                    return;
                }
                if (!new String(Pwd_TF.getPassword()).equals(new String(PwdCheck_TF.getPassword()))) {
                    JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다.");
                    return;
                }
                if (BirthField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "생년월일을 입력해주세요.");
                    return;
                }
                if (Name_TF.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "이름을 입력해주세요.");
                    return;
                }
                
                if (Phone_TF.getText().isEmpty() || Phone_TF2.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "전화번호를 입력해주세요.");
                    return;
                }
                
                // 전화번호 유효성 검사 (010-0000-0000 형태)
                if (!isValidPhone(Phone_TF.getText(), Phone_TF2.getText(), Phone_CB.getSelectedItem().toString())) {
                    return; // 전화번호가 올바르지 않으면 진행하지 않음
                }
                

                // 모든 필드가 유효한 경우 DB에 저장
                connect();
                insert();

                // 폼 초기화
                Id_TF.setText("");
                Pwd_TF.setText("");
                PwdCheck_TF.setText("");
                BirthField.setText("");
                Name_TF.setText("");
                
                Address_TF.setText("");
                Address_TF2.setText("");
                Phone_CB.setSelectedItem("선택");
                Phone_TF.setText("");
                Phone_TF2.setText("");
                Email_CB.setSelectedItem("이메일 선택");	
            	
                
            }
        });  
    }
    // 아이디 유효성 검사 (6자 이상)
    private boolean isValidID(String id) {
        if (id.length() < 6) {
            JOptionPane.showMessageDialog(null, "아이디는 6자리 이상이어야 합니다.");
            return false;
        }
        // 아이디가 영어와 숫자를 모두 포함했는지 확인 (영어와 숫자 포함 여부)
        if (!id.matches(".*[a-zA-Z].*") || !id.matches(".*\\d.*")) {
            JOptionPane.showMessageDialog(null, "아이디는 영어와 숫자를 모두 포함해야 합니다.");
            return false;
        }
        return true;
    }

    // 비밀번호 유효성 검사 (영어 + 숫자 포함, 8자리 이상)
    private boolean isValidPassword(String password) {
        // 정규식: 8자 이상, 영어와 숫자 포함
    	String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-\\[\\]{};':\"\\\\|,.<>/?]).{8,}$";
    	
    	if (!password.matches(passwordPattern)) {
            JOptionPane.showMessageDialog(null, "비밀번호는 8자리 이상이어야 하며, 영어와 숫자, 특수문자를 모두 포함해야 합니다.");
            return false;
        }
        return true;
    }
    
 // 전화번호 유효성 검사
    private boolean isValidPhone(String phoneField1, String phoneField2, String phoneCB) {
        // 전화번호가 "010-0000-0000" 형태로 되어 있는지 체크
        String phone = phoneCB + "-" + phoneField1 + "-" + phoneField2;
        String phonePattern = "^010-\\d{4}-\\d{4}$";  // 010-0000-0000 형태

        if (!phone.matches(phonePattern)) {
            JOptionPane.showMessageDialog(null, "전화번호 형식이 올바르지 않습니다. 예시: 010-1234-5678");
            return false;
        }
        return true;
    }
    
    
    public static void main(String[] args) { 
    	EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SignUp frame = new SignUp();  // SignUp 클래스의 인스턴스를 생성
                    frame.setVisible(true);       // JFrame을 화면에 보이게 설정
                } catch (Exception e) {
                    e.printStackTrace();  // 예외 발생 시 스택 트레이스를 출력
                }
            }
        });
    } 
}

// 필수 입력사항 ++
// 주소검색 ++
// email 텍스트 필드 초기화++
// 010 콤보박스 초기화++
// 아이디 중복확인 안하면 회원가입 불가하게++
// 전화번호 형식 설정 ++
