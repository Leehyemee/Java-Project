package start;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTextField;

public class TrainMenagement extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField LineName_TF;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrainMenagement frame = new TrainMenagement();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TrainMenagement() {
		setTitle("기차관리 화면");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 679, 549);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel TrainTypee_JL = new JLabel("기차 종류");
		TrainTypee_JL.setFont(new Font("굴림", Font.BOLD, 14));
		TrainTypee_JL.setBounds(25, 65, 70, 34);
		contentPane.add(TrainTypee_JL);
		
		JRadioButton KTX_RB = new JRadioButton("KTX");
		KTX_RB.setBackground(new Color(253, 255, 225));
		KTX_RB.setFont(new Font("굴림", Font.PLAIN, 14));
		KTX_RB.setBounds(120, 71, 51, 23);
		contentPane.add(KTX_RB);
		
		JRadioButton SRT_RB = new JRadioButton("SRT");
		SRT_RB.setBackground(new Color(253, 255, 225));
		SRT_RB.setFont(new Font("굴림", Font.PLAIN, 14));
		SRT_RB.setBounds(194, 71, 53, 23);
		contentPane.add(SRT_RB);
		
		JRadioButton ITX_RB = new JRadioButton("ITX-새마을");
		ITX_RB.setBackground(new Color(253, 255, 225));
		ITX_RB.setFont(new Font("굴림", Font.PLAIN, 14));
		ITX_RB.setBounds(268, 71, 97, 23);
		contentPane.add(ITX_RB);
		
		// 라디오 버튼들을 하나의 그룹으로 묶기 위해 ButtonGroup 사용
        ButtonGroup group = new ButtonGroup();
        group.add(KTX_RB);
        group.add(SRT_RB);
        group.add(ITX_RB);
		
		JLabel seate_JL = new JLabel("좌석수");
		seate_JL.setFont(new Font("굴림", Font.BOLD, 14));
		seate_JL.setBounds(25, 142, 45, 15);
		contentPane.add(seate_JL);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBackground(new Color(255, 255, 255));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"선택", "[KTX - 56석]", "[KTX - 60석]", "[SRT - 360석]", "[SRT - 410석]", "[ITX - 376석]", "[ITX - 392석]"}));
		comboBox.setBounds(120, 138, 82, 23);
		contentPane.add(comboBox);
		
		 // 라디오 버튼 선택에 따른 콤보박스 항목 변경
        KTX_RB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (KTX_RB.isSelected()) {
                    comboBox.setModel(new DefaultComboBoxModel<>(new String[] {"선택", "[KTX - 56석]", "[KTX - 60석]"}));
                }
            }
        });

        SRT_RB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (SRT_RB.isSelected()) {
                    comboBox.setModel(new DefaultComboBoxModel<>(new String[] {"선택", "[SRT - 360석]", "[SRT - 410석]"}));
                }
            }
        });

        ITX_RB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ITX_RB.isSelected()) {
                    comboBox.setModel(new DefaultComboBoxModel<>(new String[] {"선택", "[ITX - 376석]", "[ITX - 392석]"}));
                }
            }
        });
		
   
        
		JPanel panel = new JPanel();
		panel.setBackground(new Color(242, 253, 221));
		panel.setBounds(0, 0, 663, 56);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton Trainadd_BT = new JButton("기차 추가");
		Trainadd_BT.setBackground(new Color(255, 255, 255));
		Trainadd_BT.setBounds(259, 10, 97, 37);
		panel.add(Trainadd_BT);
		
		JButton TrainMo_BT = new JButton("기차 수정");
		TrainMo_BT.setBackground(new Color(255, 255, 255));
		TrainMo_BT.setBounds(381, 10, 97, 37);
		panel.add(TrainMo_BT);
		
		JButton LineMo_BT = new JButton("노선 수정");
		LineMo_BT.setBackground(Color.WHITE);
		LineMo_BT.setBounds(134, 10, 97, 37);
		panel.add(LineMo_BT);
		
		JButton Lineadd_BT = new JButton("노선 추가");
		Lineadd_BT.setBackground(Color.WHITE);
		Lineadd_BT.setBounds(12, 10, 97, 37);
		panel.add(Lineadd_BT);
		
		JButton Reset_BT = new JButton("초기화");
		Reset_BT.setBackground(Color.WHITE);
		Reset_BT.setBounds(507, 10, 97, 37);
		panel.add(Reset_BT);
		
		JButton regist_BT = new JButton("등록");
		regist_BT.setBackground(new Color(255, 255, 255));
		regist_BT.setBounds(291, 442, 97, 37);
		contentPane.add(regist_BT);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 500, 663, -445);
		contentPane.add(panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(253, 255, 225));
		panel_2.setBounds(0, 54, 663, 456);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel Linename_JL = new JLabel("노선명");
		Linename_JL.setFont(new Font("굴림", Font.BOLD, 14));
		Linename_JL.setBounds(27, 155, 64, 15);
		panel_2.add(Linename_JL);
		
		LineName_TF = new JTextField();
		LineName_TF.setBounds(118, 152, 116, 21);
		panel_2.add(LineName_TF);
		LineName_TF.setColumns(10);
	}
}
// 등록하면 DB에 추가되게
