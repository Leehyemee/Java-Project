package start;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MyPage extends JFrame {

    private static final long serialVersionUID = 1L;
    UserDto userDto;

    public MyPage(UserDto dto) {
        setTitle("My Page");
        setSize(600, 504); // Adjusted size for better layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // First tab: My Info Update
        JPanel myInfoPanel = createMyInfoPanel(dto);
        tabbedPane.addTab("내 정보 수정", myInfoPanel);

        // Second tab: Reservation Lookup
        JPanel reservationPanel = new JPanel();
        reservationPanel.setBackground(new Color(243, 249, 198));
        tabbedPane.addTab("예약 조회", reservationPanel);
        reservationPanel.setLayout(null);

        // "내 예약 정보 불러오기" 버튼
        JButton Load_BT = new JButton("내 예약 정보 불러오기");
        Load_BT.setFont(new Font("굴림", Font.BOLD, 12));
        Load_BT.setBackground(new Color(255, 255, 255));
        Load_BT.setBounds(194, 10, 186, 44);
        reservationPanel.add(Load_BT);

        // 예약 정보를 표시할 테이블 설정
        String[] columnNames = {"고유번호", "아이디", "기차번호", "예약한 좌석", "좌석수", "출발시간", "예약한시간"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable reservationTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(reservationTable);
        scrollPane.setBounds(12, 64, 555, 296);
        reservationPanel.add(scrollPane);

        JButton Cansle_BT = new JButton("예약 취소");
        Cansle_BT.setFont(new Font("굴림", Font.BOLD, 12));
        Cansle_BT.setBackground(Color.WHITE);
        Cansle_BT.setBounds(194, 370, 186, 44);
        reservationPanel.add(Cansle_BT);

        // "내 예약 정보 불러오기" 버튼 클릭 시 예약 정보 불러오기
        Load_BT.addActionListener(e -> loadReservationInfo(dto.getId(), tableModel));

        // "예약 취소" 버튼 클릭 시 예약 취소
        Cansle_BT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 예약 테이블에서 선택된 행을 가져옴
                int selectedRow = reservationTable.getSelectedRow();

                // 선택된 예약이 없으면 경고 메시지 출력
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(MyPage.this, "취소할 예약을 선택해주세요.");
                    return;
                }

                // 선택된 예약의 고유번호(RESER_NUM)를 가져옴
                int resNum = (int) reservationTable.getValueAt(selectedRow, 0);

                // 예약 취소 쿼리 작성
                String deleteQuery = "DELETE FROM RESERVATION WHERE RESER_NUM = ?";

                try (Connection conn = connectToDb(); PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
                    pstmt.setInt(1, resNum);  // 고유번호로 예약을 삭제

                    int rowsAffected = pstmt.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(MyPage.this, "예약이 취소되었습니다.");
                        // 취소 후 테이블을 새로 고침
                        loadReservationInfo(dto.getId(), tableModel);
                    } else {
                        JOptionPane.showMessageDialog(MyPage.this, "예약 취소에 실패했습니다.");
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(MyPage.this, "예약 취소 중 오류가 발생했습니다.");
                }
            }
        });

        // Add tabbed pane to the frame
        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        setVisible(true);
    }

    // 예약 정보를 가져와서 테이블에 표시하는 메소드
    private void loadReservationInfo(String userId, DefaultTableModel tableModel) {
        String query = "SELECT RESER_NUM, ID, TRAIN_NUM, SEAT_TYPE, SEAT_COUNT, RESERVE_TIME, RESERVED_TIME FROM RESERVATION WHERE ID = ?";
        try (Connection conn = connectToDb(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            tableModel.setRowCount(0); // 기존 데이터 지우기
            while (rs.next()) {
                Object[] row = {
                        rs.getInt("RESER_NUM"),
                        rs.getString("ID"),
                        rs.getString("TRAIN_NUM"),
                        rs.getString("SEAT_TYPE"),
                        rs.getInt("SEAT_COUNT"),
                        rs.getTimestamp("RESERVE_TIME"),
                        rs.getTimestamp("RESERVED_TIME")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "예약 정보를 불러오는 데 실패했습니다.");
        }
    }

    // DB 연결 설정
    private Connection connectToDb() throws SQLException, ClassNotFoundException {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String user = "hyemee";
        String password = "1234";
        Class.forName(driver);
        return DriverManager.getConnection(url, user, password);
    }

    // 기존의 "내 정보 수정" 탭을 생성하는 메소드
    private JPanel createMyInfoPanel(UserDto dto) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(243, 249, 198));
        panel.setLayout(null);

        JLabel ID_LB = new JLabel("ID");
        ID_LB.setBounds(39, 62, 140, 30);
        JTextField ID__FD = new JTextField();
        ID__FD.setBounds(187, 62, 357, 30);
        ID__FD.setText(dto.getId());
        ID__FD.setEditable(false);

        JLabel NowPassword_LB = new JLabel("현재 비밀번호");
        NowPassword_LB.setBounds(39, 102, 140, 30);
        JTextField NowPassword_FD = new JTextField();
        NowPassword_FD.setBounds(187, 102, 357, 30);
        NowPassword_FD.setEditable(false);
        NowPassword_FD.setText("********");

        JLabel NewPassword_LB = new JLabel("새 비밀번호");
        NewPassword_LB.setBounds(39, 142, 140, 30);
        JTextField NewPassword_FD = new JTextField();
        NewPassword_FD.setBounds(187, 142, 357, 30);
        NewPassword_FD.setToolTipText("비밀번호는 8자리 이상, 영어와 숫자, 특수문자 모두 포함해야함");

        JLabel UserName_LB = new JLabel("이름");
        UserName_LB.setBounds(39, 22, 140, 30);
        JTextField UserName_FD = new JTextField();
        UserName_FD.setBounds(187, 22, 357, 30);
        UserName_FD.setText(dto.getName());
        UserName_FD.setEditable(false);

        JLabel address_LB = new JLabel("주소");
        address_LB.setBounds(39, 182, 140, 30);
        JTextField address_FD = new JTextField();
        address_FD.setBounds(187, 182, 357, 30);
        address_FD.setText(dto.getAddress());

        JLabel phone_LB = new JLabel("연락처");
        phone_LB.setBounds(39, 222, 140, 30);
        JTextField phone_FD = new JTextField();
        phone_FD.setBounds(187, 222, 357, 30);
        phone_FD.setText(dto.getPhone());

        JLabel Email_LB = new JLabel("Email");
        Email_LB.setBounds(39, 262, 140, 30);
        JTextField Email_FD = new JTextField();
        Email_FD.setBounds(187, 262, 357, 30);
        Email_FD.setText(dto.getEmail());

        JButton load_BT = new JButton("내 정보 불러오기");
        load_BT.setFont(new Font("굴림", Font.BOLD, 12));
        load_BT.setBackground(new Color(255, 255, 255));
        load_BT.setBounds(49, 322, 220, 30);

        JButton update_BT = new JButton("수정하기");
        update_BT.setFont(new Font("굴림", Font.BOLD, 12));
        update_BT.setBackground(new Color(255, 255, 255));
        update_BT.setBounds(303, 322, 220, 30);

        JLabel messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setBounds(59, 351, 470, 30);
        messageLabel.setForeground(Color.RED);

        // "내 정보 불러오기" 버튼 클릭 시 사용자 정보 불러오기
        load_BT.addActionListener(e -> loadUserInfoFromDb(dto, ID__FD, UserName_FD, address_FD, phone_FD, Email_FD));

        // "수정하기" 버튼 클릭 시 수정된 정보 업데이트
        update_BT.addActionListener(e -> updateUserInfo(dto, ID__FD, NewPassword_FD, UserName_FD, address_FD, phone_FD, Email_FD, messageLabel));

        // Add components to the panel
        panel.add(ID_LB);
        panel.add(ID__FD);
        panel.add(NowPassword_LB);
        panel.add(NowPassword_FD);
        panel.add(NewPassword_LB);
        panel.add(NewPassword_FD);
        panel.add(UserName_LB);
        panel.add(UserName_FD);
        panel.add(address_LB);
        panel.add(address_FD);
        panel.add(phone_LB);
        panel.add(phone_FD);
        panel.add(Email_LB);
        panel.add(Email_FD);
        panel.add(load_BT);
        panel.add(update_BT);
        panel.add(messageLabel);

        JButton Back_BT = new JButton("메인화면으로 돌아가기");
        Back_BT.setFont(new Font("굴림", Font.BOLD, 12));
        Back_BT.setBackground(new Color(255, 255, 255));
        Back_BT.setBounds(303, 372, 220, 30);
        panel.add(Back_BT);

        JButton LogOut_BT = new JButton("로그아웃");
        LogOut_BT.setFont(new Font("굴림", Font.BOLD, 12));
        LogOut_BT.setBackground(Color.WHITE);
        LogOut_BT.setBounds(49, 372, 220, 30);
        panel.add(LogOut_BT);

        // 로그아웃 버튼 클릭 시 종료 확인 메시지
        LogOut_BT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // "종료하시겠습니까?" 메시지 표시
                int result = JOptionPane.showConfirmDialog(
                        MyPage.this,    // 부모 창
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
                }                // "No"를 누르면 아무 동작도 하지 않음
                else if (result == JOptionPane.NO_OPTION) {
                    // 아무 동작도 하지 않음 (창을 유지)
                }
            }
        });

        Back_BT.addActionListener(e -> {
            // MemMain 창을 띄운 후, MyPage 창을 숨기기
            MemMain mainFrame = new MemMain(dto);
            mainFrame.setVisible(true);  // MemMain을 띄운다
            setVisible(false);  // 현재 MyPage 창을 숨긴다
        });

        return panel;
    }

    // 사용자 정보를 수정하는 메소드
    private void updateUserInfo(UserDto dto, JTextField idField, JTextField newPasswordField, JTextField usernameField,
                                 JTextField addressField, JTextField phoneField, JTextField emailField, JLabel messageLabel) {
        String newPassword = newPasswordField.getText().trim();
        String username = usernameField.getText().trim();
        String address = addressField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();

        // 유효성 검사
        if (newPassword.isEmpty() || username.isEmpty() || address.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            messageLabel.setText("모든 필드를 입력해주세요.");
            return;
        }

        // DB 업데이트 쿼리
        String updateQuery = "UPDATE USERS SET PASSWORD = ?, USERNAME = ?, ADDRESS = ?, PHONE = ?, EMAIL = ? WHERE ID = ?";

        try (Connection conn = connectToDb(); PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setString(1, newPassword);
            pstmt.setString(2, username);
            pstmt.setString(3, address);
            pstmt.setString(4, phone);
            pstmt.setString(5, email);
            pstmt.setString(6, dto.getId());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                messageLabel.setText("정보가 수정되었습니다.");
                dto.setName(username);
                dto.setAddress(address);
                dto.setPhone(phone);
                dto.setEmail(email);
            } else {
                messageLabel.setText("정보 수정에 실패했습니다.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            messageLabel.setText("정보 수정 중 오류가 발생했습니다.");
        }
    }

    // 사용자 정보를 DB에서 가져오는 메소드
    private void loadUserInfoFromDb(UserDto dto, JTextField idField, JTextField usernameField, JTextField addressField,
                                     JTextField phoneField, JTextField emailField) {
        String query = "SELECT USERNAME, ADDRESS, PHONE, EMAIL FROM USERS WHERE ID = ?";

        try (Connection conn = connectToDb(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, dto.getId());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                usernameField.setText(rs.getString("USERNAME"));
                addressField.setText(rs.getString("ADDRESS"));
                phoneField.setText(rs.getString("PHONE"));
                emailField.setText(rs.getString("EMAIL"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "정보를 불러오는 데 실패했습니다.");
        }
    }

    public static void main(String[] args) {
        // 로그인 후 사용자 정보를 넘겨 받아 MyPage 창을 띄움
        UserDto userDto = new UserDto("testuser", "John Doe", "1234", "123 Main St", "123-456-7890", "testuser@example.com");
        new MyPage(userDto);
    }
}
