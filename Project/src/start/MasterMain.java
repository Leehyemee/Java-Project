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
		Member_BT.setForeground(new Color(0, 0, 0));
		Member_BT.setFont(new Font("새굴림", Font.BOLD, 12));
		Member_BT.setBackground(new Color(255, 255, 255));
		Member_BT.setBounds(271, 10, 97, 35);
		panel.add(Member_BT);
		
		JButton Train_BT = new JButton("기차관리");
		Train_BT.setForeground(new Color(0, 0, 0));
		Train_BT.setFont(new Font("새굴림", Font.BOLD, 12));
		Train_BT.setBackground(new Color(255, 255, 255));
		Train_BT.setBounds(458, 10, 97, 35);
		panel.add(Train_BT);
		
		JButton Logout_BT = new JButton("로그아웃");
		Logout_BT.setForeground(new Color(0, 0, 0));
		Logout_BT.setFont(new Font("새굴림", Font.BOLD, 12));
		Logout_BT.setBackground(new Color(255, 255, 255));
		Logout_BT.setBounds(639, 10, 97, 35);
		panel.add(Logout_BT);
		
		// 회원관리 버튼 클릭 시
        Member_BT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // MemMenagement 클래스 실행
                MemMenagement memMenagementFrame = new MemMenagement();
                // MemMenagement 창만 닫히도록 설정
                memMenagementFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
                memMenagementFrame.setVisible(true); // 새 창을 보이도록 설정
                dispose();
            }
        });
        
        // 기차관리 버튼 클릭 시
        Train_BT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TrainManagement 클래스 실행
                TrainManagement trainManagementFrame = new TrainManagement();
                // TrainManagement 창만 닫히도록 설정
                trainManagementFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
                trainManagementFrame.setVisible(true); // 새 창을 보이도록 설정
                dispose();
            }
        });

        // 로그아웃 버튼 클릭 시
        Logout_BT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        MasterMain.this,
                        "종료하시겠습니까?",
                        "로그아웃",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

             // "Yes"를 누르면 로그인 화면 실행
                if (result == JOptionPane.YES_OPTION) {
                    // Login 클래스 실행
                    Login loginFrame = new Login();
                    loginFrame.setVisible(true);

                    // 현재 창 닫기
                    dispose();  // MasterMain 창을 닫음
                }                // "No"를 누르면 아무 동작도 하지 않음
                else if (result == JOptionPane.NO_OPTION) {
                    // 창 유지
                }
            }
        });
        
		 // 화면이 처음 로드될 때 repaint() 호출하여 배경 이미지를 그려주기
        contentPane.repaint();
	}
}