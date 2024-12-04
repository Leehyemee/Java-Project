package start;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;


public class MasterMain extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ImageIcon backgroundImage;
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MasterMain frame = new MasterMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public MasterMain() {
		setTitle("관리자 메인화면");
		backgroundImage = new ImageIcon("images/trainbg.jpg");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 780, 547);
		setResizable(false);
		
		contentPane = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), null);
            
                }
            }
        };
		contentPane.setBackground(new Color(240, 240, 240));
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(218, 245, 203));
		panel.setBounds(0, 0, 764, 55);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton Member_BT = new JButton("회원관리");
		Member_BT.setFont(new Font("새굴림", Font.PLAIN, 12));
		Member_BT.setBackground(new Color(255, 255, 255));
		Member_BT.setBounds(271, 10, 97, 35);
		panel.add(Member_BT);
		
		JButton Train_BT = new JButton("기차관리");
		Train_BT.setFont(new Font("새굴림", Font.PLAIN, 12));
		Train_BT.setBackground(new Color(255, 255, 255));
		Train_BT.setBounds(458, 10, 97, 35);
		panel.add(Train_BT);
		
		JButton Logout_BT = new JButton("로그아웃");
		Logout_BT.setFont(new Font("새굴림", Font.PLAIN, 12));
		Logout_BT.setBackground(new Color(255, 255, 255));
		Logout_BT.setBounds(639, 10, 97, 35);
		panel.add(Logout_BT);
		
		// 회원관리 버튼 클릭 시 MemMenagement 클래스 실행
        Member_BT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // MemMenagement 클래스 실행
                MemMenagement memMenagementFrame = new MemMenagement();
                memMenagementFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // MemMenagement 창만 닫히도록 설정
                memMenagementFrame.setVisible(true); // 새 창을 보이도록 설정
            }
        });
        
        
        // 기차관리 버튼 클릭 시 TrainManagement 클래스 실행
        Train_BT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TrainManagement 클래스 실행
                TrainManagement trainManagementFrame = new TrainManagement();
                trainManagementFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // TrainManagement 창만 닫히도록 설정
                trainManagementFrame.setVisible(true); // 새 창을 보이도록 설정
            }
        });

        
        // 로그아웃 버튼 클릭 시 종료 확인 메시지
        Logout_BT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // "종료하시겠습니까?" 메시지 표시
                int result = JOptionPane.showConfirmDialog(
                        MasterMain.this,    // 부모 창
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
        
		 // 화면이 처음 로드될 때 repaint() 호출하여 배경 이미지를 그려주기
        contentPane.repaint();
	}
}

// 회원관리 버튼 클릭 시 MemMenagement 실행 ++
// 로그아웃 버튼 - 종료하시겠습니까? Y/N 선택 후 Y-종료, N - 화면 유지++
// 기차관리 -- 나
// 회원관리 창을 닫으면 관리자메인도 같이 닫힘 해결하기