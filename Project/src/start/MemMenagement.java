package start;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;


public class MemMenagement extends JFrame {

	Connection con = null;                  // DB와 연결하는 객체
	PreparedStatement pstmt = null;         // SQL문을 DB에 전송하는 객체
	ResultSet rs = null;                    // SQL문 실행 결과를 가지고 있는 객체
	String sql = null;                      // SQL문을 저장하는 문자열 변수.
	
    DefaultTableModel model;
    JTable table;
    JComboBox<String> Choice_CB; // 검색 조건 ComboBox
    JTextField Search_TF; // 검색 텍스트 필드
    private JPanel contentPane;
    private ImageIcon backgroundImage;

    public MemMenagement() {
        setTitle("회원관리");
        backgroundImage = new ImageIcon("images/train3.jpg");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1105, 580);
        setResizable(false);
        contentPane = new JPanel() {
        	@Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
            g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), null);
        
            }
        }
    };
        contentPane.setBackground(new Color(218, 245, 203));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton Logout_BT = new JButton("로그아웃");
        Logout_BT.setBackground(new Color(255, 255, 255));
        Logout_BT.setBounds(965, 470, 89, 25);
        contentPane.add(Logout_BT);

        // 테이블 생성 및 설정
        String[] header = {"ID", "PASSWORD", "BIRTHDATE", "USERNAME", "ADDRESS", "PHONE", "EMAIL", "CREATED_AT", "MEMBER_ID"};
        model = new DefaultTableModel(header, 0);
        table = new JTable(model);
        JScrollPane jsp = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jsp.setBounds(30, 94, 1024, 366);  // 테이블 위치 및 크기 설정
        contentPane.add(jsp);

        // 검색 관련 ComboBox와 JTextField 설정
        Choice_CB = new JComboBox<String>();
        Choice_CB.setBackground(new Color(255, 255, 255));
        Choice_CB.setModel(new DefaultComboBoxModel(new String[] {"선택", "전체", "ID", "PASSWORD", "BIRTHDATE", "USERNAME", "ADDRESS", "PHONE", "EMAIL", "CREATED_AT", "MEMBER_ID"}));
        Choice_CB.setBounds(30, 470, 89, 25);  // ComboBox 위치 설정
        contentPane.add(Choice_CB);

        Search_TF = new JTextField();
        Search_TF.setForeground(new Color(0, 0, 0));
        Search_TF.setBackground(new Color(255, 255, 255));
        Search_TF.setBounds(157, 470, 150, 25);  // 텍스트 필드 위치 설정
        contentPane.add(Search_TF);

        JButton Search_BT = new JButton("검색");
        Search_BT.setBackground(new Color(255, 255, 255));
        Search_BT.setBounds(342, 471, 97, 23);  // 검색 버튼 위치 설정
        contentPane.add(Search_BT);
        
        JButton Delet_BT = new JButton("삭제");
        Delet_BT.setBackground(new Color(255, 255, 255));
        Delet_BT.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        Delet_BT.setBounds(652, 471, 76, 23);
        contentPane.add(Delet_BT);
        
        JButton Change_BT = new JButton("수정");
        Change_BT.setBackground(new Color(255, 255, 255));
        Change_BT.setBounds(756, 470, 76, 23);
        contentPane.add(Change_BT);
        
        JButton Reset_BT = new JButton("초기화");
        Reset_BT.setBackground(new Color(255, 255, 255));
        Reset_BT.setBounds(456, 471, 97, 23);
        contentPane.add(Reset_BT);
        
        JLabel lblNewLabel = new JLabel("회원관리");
        lblNewLabel.setFont(new Font("굴림", Font.BOLD, 24));
        lblNewLabel.setBounds(30, 33, 107, 34);
        contentPane.add(lblNewLabel);

        // 가운데 정렬을 위한 렌더러 설정
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // 모든 열에 대해 가운데 정렬 적용
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        
        // 검색 버튼 클릭 시 동작하는 이벤트 리스너
        Search_BT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchType = (String) Choice_CB.getSelectedItem(); // 선택한 검색 조건
                String searchText = Search_TF.getText(); // 텍스트 필드에 입력한 값

                // 검색 조건에 따른 처리
                if (!searchType.equals("선택") && !searchText.isEmpty()) {
                    if (searchType.equals("전체")) {
                        searchData("전체", "");  // "전체" 선택 시 조건 없이 검색
                    } else {
                        searchData(searchType, searchText);  // 선택한 조건에 맞게 검색
                    }
                } else {
                    // "전체"를 선택하거나 검색 조건이 잘못되었을 때
                    if (searchType.equals("전체")) {
                        searchData("전체", ""); // "전체" 선택 시 모든 데이터를 조회
                    } else {
                        JOptionPane.showMessageDialog(null, "검색 조건이 잘못되었거나 텍스트가 비어있습니다.");
                    }
                }
            }
        });

     // 삭제 버튼 클릭 시 동작
        Delet_BT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow(); // 선택된 행
                if (selectedRow >= 0) {
                    String memberId = (String) model.getValueAt(selectedRow, 8); // MEMBER_ID
                    deleteData(memberId);  // 선택한 회원 ID로 삭제
                } else {
                    JOptionPane.showMessageDialog(null, "삭제할 항목을 선택하세요.");
                }
            }
        });

        // 수정 버튼 클릭 시 동작
        Change_BT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow(); // 선택된 행
                if (selectedRow >= 0) {
                    // 선택된 행에서 MEMBER_ID를 가져오기
                    String memberId = (String) model.getValueAt(selectedRow, 8); // MEMBER_ID

                    // 수정할 항목 선택을 위한 입력 다이얼로그
                    String[] options = {"ID", "PASSWORD", "BIRTHDATE", "USERNAME", "ADDRESS", "PHONE", "EMAIL"};
                    String selectedColumn = (String) JOptionPane.showInputDialog(
                        null,
                        "수정할 항목을 선택하세요:",
                        "수정 항목 선택",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0] // 기본값 설정
                    );

                    if (selectedColumn != null) {
                        // 각 컬럼에 맞는 입력을 받는 입력창 띄우기
                        String newValue = JOptionPane.showInputDialog("새로운 값을 입력하세요 (" + selectedColumn + "):");

                        if (newValue != null && !newValue.isEmpty()) {
                            updateData(memberId, selectedColumn, newValue);  // 수정 메서드 호출
                        } else {
                            JOptionPane.showMessageDialog(null, "입력 값이 비어있습니다.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "수정할 항목을 선택하세요.");
                }
            }
        });
    
     // 초기화 버튼 클릭 시 동작
        Reset_BT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 검색 조건 ComboBox 초기화
                Choice_CB.setSelectedIndex(0);  // "선택"으로 초기화
                
                // 검색 텍스트 필드 초기화
                Search_TF.setText("");  // 텍스트 필드 비우기
                
                // 테이블 초기화
                model.setRowCount(0);  // 테이블 내용 삭제
            }
        });
        
     // 로그아웃 버튼 클릭 시 종료 확인 메시지
        Logout_BT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // "종료하시겠습니까?" 메시지 표시
                int result = JOptionPane.showConfirmDialog(
                        MemMenagement.this,    // 부모 창
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
        // 검색 메서드
    public void searchData(String searchType, String searchText) {
        try {
            con = connect();
            if (con != null) {
                String query = "";
                
                // "전체"가 선택되면 모든 회원을 조회
                if (searchType.equals("전체")) {
                    query = "SELECT * FROM USERS WHERE ID NOT LIKE 'admin_%'"; // 모든 회원 정보를 조회하되 관리자는 제외한 쿼리
                } else {
                    // 사용자가 선택한 검색 조건에 맞는 SQL 쿼리 작성
                    query = "SELECT * FROM USERS WHERE " + searchType + " LIKE ?";
                }
                
                pstmt = con.prepareStatement(query);
                // "전체" 검색 시 searchText는 사용하지 않음
                if (!searchType.equals("전체")) {
                    pstmt.setString(1, "%" + searchText + "%"); // 검색어에 와일드카드 추가
                }
                rs = pstmt.executeQuery();

                // 기존 테이블 내용 삭제
                model.setRowCount(0);

                // 검색된 결과 테이블에 추가
                while (rs.next()) {
                    Object[] row = {
                        rs.getString("ID"),          // ID 컬럼
                        rs.getString("PASSWORD"),    // PASSWORD 컬럼
                        rs.getString("BIRTHDATE"),   // BIRTHDATE 컬럼
                        rs.getString("USERNAME"),    // USERNAME 컬럼
                        rs.getString("ADDRESS"),     // ADDRESS 컬럼
                        rs.getString("PHONE"),       // PHONE 컬럼
                        rs.getString("EMAIL"),       // EMAIL 컬럼
                        rs.getString("CREATED_AT"),  // CREATED_AT 컬럼
                        rs.getString("MEMBER_ID")    // MEMBER_ID 컬럼
                    };
                    model.addRow(row);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


        // 삭제 메서드
    public void deleteData(String member_Id) {
        try {
            con = connect();
            if (con != null) {
                // ID로 회원 삭제
                String query = "DELETE FROM USERS WHERE MEMBER_ID = ?";
                pstmt = con.prepareStatement(query);
                pstmt.setString(1, member_Id); // ID로 삭제
                int result = pstmt.executeUpdate();
                
                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "삭제 성공");
                    
                 // 삭제된 항목을 테이블에서 제거
                 // 테이블에서 해당 MEMBER_ID에 해당하는 행을 제거
                    for (int i = 0; i < model.getRowCount(); i++) {
                        if (model.getValueAt(i, 8).equals(member_Id)) { // MEMBER_ID 컬럼 비교
                            model.removeRow(i);  // 해당 행 제거
                            break;  // 한 번 찾으면 종료
                        }
                    }
                } else {
                    // 삭제 실패 시 메시지 표시
                    JOptionPane.showMessageDialog(this, "삭제 실패");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
        // 수정 메서드
    public void updateData(String memberId, String column, String newValue) {
        try {
            con = connect();
            if (con != null) {
                // MEMBER_ID로 사용자를 찾아, 선택된 컬럼만 수정
                String query = "UPDATE USERS SET " + column + " = ? WHERE MEMBER_ID = ?";
                pstmt = con.prepareStatement(query);
                pstmt.setString(1, newValue);    // 새로운 값
                pstmt.setString(2, memberId);    // MEMBER_ID로 업데이트
                int result = pstmt.executeUpdate();
                
                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "수정 성공");
                    
                    // 수정된 내용을 테이블에 반영
                    for (int i = 0; i < model.getRowCount(); i++) {
                        if (model.getValueAt(i, 8).equals(memberId)) { // MEMBER_ID 컬럼 비교
                            // 선택된 컬럼에 맞게 해당 값 수정
                            switch (column) {
                                case "ID":
                                    model.setValueAt(newValue, i, 0);
                                    break;
                                case "PASSWORD":
                                    model.setValueAt(newValue, i, 1);
                                    break;
                                case "BIRTHDATE":
                                    model.setValueAt(newValue, i, 2);
                                    break;
                                case "USERNAME":
                                    model.setValueAt(newValue, i, 3);
                                    break;
                                case "ADDRESS":
                                    model.setValueAt(newValue, i, 4);
                                    break;
                                case "PHONE":
                                    model.setValueAt(newValue, i, 5);
                                    break;
                                case "EMAIL":
                                    model.setValueAt(newValue, i, 6);
                                    break;
                            }
                            break;  // 수정된 항목 찾으면 종료
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "수정 실패");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MemMenagement frame = new MemMenagement();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

// 관리자 메인화면
// 회원관리 버튼???
// 로그아웃 버튼 - 종료하시겠습니까? Y/N 선택 후 Y-종료, N - 화면 유지++
// 기차관리 - 민정님