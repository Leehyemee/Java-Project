package start;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;

public class SelectPage extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static Dto dto = null;
	
	Connection con = null;                  
	PreparedStatement pstmt, pstmt2 = null;         
	ResultSet rs, rs2 = null;                   
	String sql, sql2 = null;   
	
	
	DefaultTableModel model;
	JTable table;

	
	String scheduel_id = null;
	static String id;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectPage frame = new SelectPage(dto);
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
	
	public SelectPage() {
		
	}
	public SelectPage(Dto dto) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		id = dto.getId();
		
		System.out.println("start >>> " + dto.getStart());
		
		
JPanel selectedPanel = new JPanel(new BorderLayout());    
		
		JLabel selectTitle = new JLabel("조회 화면");
		String[] header = 
			{"구 분", "열차번호", "고유번호", "출발시간", "출발", "도착", "일반실", "유아", "입석 가능 여부(Y/N)", "소요시간", "운임요금"};
		
		model = new DefaultTableModel(header, 0);
		
		table = new JTable(model);

		JScrollPane jsp = new JScrollPane(
				table, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		selectedPanel.add(selectTitle, BorderLayout.NORTH);
		selectedPanel.add(jsp, BorderLayout.CENTER);
		
        JPanel backPanel = new JPanel();
        JButton backBtn = new JButton("뒤로가기");

        backPanel.add(backBtn);

        setLayout(new BorderLayout());

		add(selectedPanel, BorderLayout.CENTER);
        add(backPanel, BorderLayout.SOUTH);
		
		setBounds(100, 100, 932, 553);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);
		
		connect();
		model.setRowCount(0);
		select(dto);

        /* 뒤로가기 버튼 */
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("뒤로가기 버튼 클릭!");
                
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run(){
                        new MemMain(dto).setVisible(true);
                        dispose();//뒤로가기 했을 때 기존 창 꺼짐
                    }
                });
            }
        });
	}
	
	Connection connect() {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "basic";
		String password = "1234";
		try {
			Class.forName(driver);
			
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	} 
	
	@SuppressWarnings("unused")
	void select(Dto dto) {
		System.out.println("select문 들어옴 :: " + dto.getStart());
		
		if(dto.getStart().equals("")) {
			JOptionPane.showMessageDialog(this, "출발역을 입력해주세요");
			new MemMain(dto);
			return;
		}
		
		try {
			
			System.out.println("---- select에 들어온 사용자가 입력한 정보 ----");
			System.out.println(dto.getStart());
			System.out.println(dto.getArrive());
			System.out.println(dto.getRoute());
			System.out.println(dto.getTrainType());
			System.out.println("---- ---- ----");
			//출발역만 입력했을 경우(직통 기본값)
			if(!dto.getStart().equals("")&&	dto.getArrive().equals("")&&
					dto.getRoute().equals("직통")&&dto.getTrainType().equals("전체")) {
				System.out.println("출발역만 입력");
				
				sql = "select t.TRAIN_NUM, schedule_num, t.STR_STATION, t.ARR_STATION, s.ECONOMY_SEAT, s.BABY_SEAT, s.STAND_SEAT, s.START_DAY, s.ARRIVE_DAY, s.MONEY, s.schedule_id from train t join SCHEDULE s on t.train_num = s.TRAIN_NUM where t.STR_STATION=?";
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, dto.getStart());
				
				rs = pstmt.executeQuery();
				
			//도착역과 출발역이 비어있지 않을 경우(직통 기본값)
			}else if(!dto.getStart().equals("")&&!dto.getArrive().equals("")&&
					dto.getRoute().equals("직통")&&dto.getTrainType().equals("전체")) {
				
				System.out.println("****출발역과 도착역을 입력했을 경우");
				sql = "select t.TRAIN_NUM, t.STR_STATION, schedule_num, t.ARR_STATION, s.ECONOMY_SEAT, s.BABY_SEAT, s.STAND_SEAT, s.START_DAY, s.ARRIVE_DAY, s.MONEY, s.schedule_id from train t join SCHEDULE s on t.train_num = s.TRAIN_NUM  where t.str_station=? and t.arr_station=?";
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, dto.getStart());
				pstmt.setString(2, dto.getArrive());
				
				rs = pstmt.executeQuery();
				
				//도착역와 출발역, 기차타입을 입력했을 경우
			}else if(!dto.getStart().equals("")&&!dto.getArrive().equals("")&&
					!dto.getTrainType().equals("전체")&&dto.getRoute().equals("직통")) {
				
				if(dto.getTrainType().equals("KTX/SRT")) {
				
				System.err.println(dto.getTrainType());
				
				sql = "select t.TRAIN_NUM, t.STR_STATION, schedule_num, t.ARR_STATION, s.ECONOMY_SEAT, s.BABY_SEAT, s.STAND_SEAT, s.START_DAY, s.ARRIVE_DAY, s.MONEY, s.schedule_id from train t join SCHEDULE s on t.train_num = s.TRAIN_NUM where str_station=? and arr_station=? and t.train_type in ('KTX','SRT')";
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, dto.getStart());
				pstmt.setString(2, dto.getArrive());
				
				rs = pstmt.executeQuery();
				
				}else if(dto.getTrainType().equals("새마을호")){
					
				System.out.println("****도착지와 출발역 기차타입을 입력했을 경우---------------");
				sql = "select t.TRAIN_NUM, t.STR_STATION, schedule_num, t.ARR_STATION, s.ECONOMY_SEAT, s.BABY_SEAT, s.STAND_SEAT, s.START_DAY, s.ARRIVE_DAY, s.MONEY, s.schedule_id from train t join SCHEDULE s on t.train_num = s.TRAIN_NUM where str_station=? and arr_station=? and t.train_type = '새마을호'";
				pstmt = con.prepareStatement(sql);
				
				String[] c = dto.getTrainType().split("/");
				
				pstmt.setString(1, dto.getStart());
				pstmt.setString(2, dto.getArrive());
				
				System.err.println(dto.getStart());
				System.err.println(dto.getArrive());
				
				rs = pstmt.executeQuery();
				
				}
				
			//출발역과 기차타입을 입력했을 경우
			}else if(!dto.getStart().equals("")&&dto.getArrive().equals("")&&
					!dto.getTrainType().equals("전체")&&dto.getRoute().equals("직통")) {
				
				System.out.println("기차타입과 출발역만 입력했을 경우.");
				sql = "select t.TRAIN_NUM, t.STR_STATION, schedule_num, t.ARR_STATION, s.ECONOMY_SEAT, s.BABY_SEAT, s.STAND_SEAT, s.START_DAY, s.ARRIVE_DAY, s.MONEY, s.schedule_id  from train t join schedule s on t.train_num = s.train_num where str_station = ? and train_type = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, dto.getStart());
				pstmt.setString(2, dto.getTrainType());
				
				rs = pstmt.executeQuery();
				
			//도착지와 출발역, 기차타입, 루트(환승,왕복)를 정했을 경우
			}else if(!dto.getStart().equals("")&&!dto.getArrive().equals("")&&
					dto.getRoute().equals("왕복")) {
				
				System.out.println("****도착지와 출발역, 기차타입, 루트(환승,왕복)를 정했을 경우");
				
					System.err.println("왕복쿼리문에 진입");

					sql = "select * from train t join schedule s on t.train_num = s.train_num where str_station = ? and arr_station = ?";
					sql2 = "select * from train t join schedule s on t.train_num = s.train_num where str_station = ? and arr_station = ?";
					
					System.out.println("--------"+sql);
					System.out.println("--------"+dto.getStart());
					System.out.println("--------"+dto.getArrive());
					
					pstmt = con.prepareStatement(sql);
					
					pstmt.setString(1, dto.getStart());
					pstmt.setString(2, dto.getArrive());
					rs = pstmt.executeQuery();
					
					pstmt2 = con.prepareStatement(sql2);
					pstmt2.setString(1, dto.getArrive());
					pstmt2.setString(2, dto.getStart());
					
					rs2 = pstmt2.executeQuery();
			}else {
				JOptionPane.showMessageDialog(null, "제대로 입력되지 않았습니다. 다시 입력해주세요");
				this.dispose();
				new MemMain(dto);
			}
			
trainresult : while(rs.next()) {
	
	System.out.println("select문 진입");
	
	String train_num = rs.getString("train_num");
	
	String scheduleId = rs.getString("schedule_id");
	
	dto.settrainNum(train_num);

	String strstation = rs.getString("str_station");
	String schedule_id = rs.getString("schedule_id");
	
	//java.sql.Date가 날짜 부분만 포함하고 시간 정보를 무시 timestemp 타입으로 가져와야
	Timestamp startday = rs.getTimestamp("start_day");
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String startdaytime = sdf.format(startday);
	
	Timestamp arriveday = rs.getTimestamp("arrive_day");
	String arrivedayttime = sdf.format(arriveday);
	
	//도착시간 - 출발시간
	Duration duration = Duration.between(startday.toInstant(), arriveday.toInstant());
	long hours = duration.toHours(); 
	long minutes = duration.minus(hours, ChronoUnit.HOURS).toMinutes();
	
    String schedule_num = rs.getString("schedule_num");

	System.out.println("duration ::: " + duration);
	System.out.println("hours ::: " + hours);
	System.out.println("minutes ::: " + minutes);
	
	String arrstation = rs.getString("arr_station");
	
	int economyseat = rs.getInt("economy_seat");
	int babyseat = rs.getInt("baby_seat");
	String standseat = rs.getString("stand_seat");
	int money = rs.getInt("money");
	
	String ecoseat="";
	if(economyseat>0) ecoseat="일반실";//좌석 수 > 원래는 seatinfo 연계되서 숫자가 입력되어야 함.
	
    int ecoseatColumn = 6;
    int spseatColumn = 7;

    // 링크 렌더러 설정
    table.getColumnModel().getColumn(ecoseatColumn).setCellRenderer(new LinkRenderer());
    table.getColumnModel().getColumn(spseatColumn).setCellRenderer(new LinkRenderer());

    // 링크 편집기 설정 (링크 클릭 시 새 창 열기)
    table.getColumnModel().getColumn(ecoseatColumn).setCellEditor(new LinkEditor(new JCheckBox()));
    table.getColumnModel().getColumn(spseatColumn).setCellEditor(new LinkEditor(new JCheckBox()));

    
    String d = dto.getStartDay();
    d = d.replace("/0/", "/1/");
    d = d.replace("/00", "/01"); 
    d += " 00:00";
    
    System.out.println("전달받은 시간 :::::: "+d);
    
    SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    Object[] data = null;
    try {
        // 문자열을 Date 객체로 파싱
        Date inputDate = sf.parse(d);

        // Date 객체를 원하는 형식으로 출력
        String formattedDate = sf.format(inputDate);
        
        System.out.println(formattedDate);  // 출력: 2024/01/01 00:00
        
        
    	if(startday.after(inputDate)) {

    		
    		data = new Object[]
    			{dto.getRoute() ,train_num, schedule_num, startdaytime.substring(0, 16), strstation, arrstation, ecoseat, babyseat, standseat, hours+"시"+minutes+"분",money};
    		
    	}	
    
    } catch (Exception e) {
        e.printStackTrace();
    }
	
   
    

	System.out.println("시간형식확인s"+startdaytime);
	System.out.println("시간형식확인a"+arrivedayttime);
	
	if(data!=null) {
		model.addRow(data);
	}
	
	table.setDefaultEditor(Object.class, null);
	
	table.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
	        int row = table.rowAtPoint(e.getPoint());
	        int column = table.columnAtPoint(e.getPoint());

	        if (column == ecoseatColumn || column == spseatColumn) {
	            LinkEditor editor = (LinkEditor) table.getCellEditor(row, column);
	            if (editor != null) {
	                editor.handlinkclick(column);
	            }
	        }
	    }
	});

}

	if(dto.getRoute().equals("왕복")) {
		
			while(rs2.next()) {//왕복인 경우 > 도착역과 출발역을 반대로 함.
				
				System.out.println("왕복일 경우 rs2.next() 진입");
				
				String train_num = rs2.getString("train_num");
				dto.settrainNum(train_num);
				
				String strstation = rs2.getString("str_station");
				String arrstation = rs2.getString("arr_station");
				String schedule_num = rs2.getString("schedule_num");
				Timestamp startday = rs2.getTimestamp("start_day");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String startdaytime = sdf.format(startday);
				Timestamp arriveday = rs2.getTimestamp("arrive_day");
				String arrivedayttime = sdf.format(arriveday);
				int economyseat = rs2.getInt("economy_seat");
				int babyseat = rs2.getInt("baby_seat");
				String standseat = rs2.getString("stand_seat");
				int money = rs2.getInt("money");
				
				String ecoseat="",baseat="";
				if(economyseat>0)ecoseat="일반실";
				if(babyseat>0)baseat="유아실";
				
				Duration duration = Duration.between(startday.toInstant(), arriveday.toInstant());
				long hours = duration.toHours(); 
				long minutes = duration.minus(hours, ChronoUnit.HOURS).toMinutes();
				
				
				
				Object[] data = 
					{dto.getRoute() ,train_num, schedule_num, startdaytime.substring(0, 16), strstation, arrstation, ecoseat, baseat, standseat, hours+"시"+minutes+"분",money};
				
				model.addRow(data);
				table.setDefaultEditor(Object.class, null);
				
				}
			}
			
		} catch (SQLException e) {
			System.err.println("select() 오류 발생함");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}finally {
			try {
				if(con!=null)con.close();	
				if(rs!=null)rs.close(); 
				if(pstmt!=null)pstmt.close();
				if(rs2!=null)rs2.close();
				if(pstmt2!=null)pstmt2.close(); 
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
    public static class LinkRenderer implements TableCellRenderer {
        private JLabel label;

        public LinkRenderer() {
            label = new JLabel();
            label.setForeground(Color.BLUE);  // 링크 색상 (파란색)
//            label.setText("<html><u>예약</u></html>");  // 링크처럼 보이게 하려면 HTML을 사용
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));  // 마우스 커서를 손 모양으로 변경
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        	if (value != null) {
                label.setText("<html><u>" + value.toString() + "</u></html>");  // 값이 있으면 링크처럼 보이게
            } else {
                label.setText("");  // 값이 없으면 빈 텍스트
            }
        	
        	//작동안됨.
        	if (isSelected) {
                label.setBackground(Color.YELLOW);  // 선택된 셀은 노란색 배경
            } else {
                label.setBackground(Color.WHITE);  // 선택되지 않은 셀은 흰색 배경
            }
            return label;
        }
    }
	
	
    public static class LinkEditor extends DefaultCellEditor {
    	/**
		 * 
		 */
		private static final long serialVersionUID = 1L;


		Dto dto;
    	
    	
    	private String trainNum;
        protected JLabel label;
        private String labelText;
		private String scheduleNum;
        private String startDatTime;
        private int col;
        private boolean isClicked = false;
        

        public LinkEditor(JCheckBox checkBox) {
            super(checkBox);
            
            label = new JLabel();
            label.setForeground(Color.BLUE);  // 링크 색상 (파란색)
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));  // 커서를 손 모양으로 설정

            //클릭
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                	handlinkclick(col);
                }
                
            });
        }
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            labelText = (value == null) ? "" : value.toString();
            label.setText(labelText);
            
            trainNum = (String) table.getValueAt(row, 1);// 기차 번호를 가져오기 위해 column 값을 1으로 지정
            scheduleNum = (String) table.getValueAt(row, 2);
            startDatTime = (String)table.getValueAt(row, 3);
            //label.setText("<html><u>예약</u></html>");
            
            col = column;
            System.out.println("선택한 column 인덱스 : " + col);
            
            //클릭했을 때 콘솔에 기차번호랑 특별실, 일반실 콘솔에 출력
            System.err.println("클릭한 기차번호 : "+trainNum + ", scheduleNum : " + scheduleNum );
            
//            new SeatSelect(dto);
            
            return label;
        }

        private void handlinkclick(int col) {//더블클릭해야 창 생성..
        	if (isClicked) {
                return; // 이미 클릭된 상태라면 아무 동작도 하지 않음
            }
            isClicked = true;
            

        	if(col==6) {
        		dto = new Dto();
            	dto.settrainNum(trainNum);
            	dto.setSchedule_num(scheduleNum);
                dto.setStartDay(startDatTime);
        		System.out.println("일반실 클릭");
        		dto.setId(id);
        		new SeatSelect(dto);
        		
        	}else if(col==7) {
        		System.out.println("유아실클릭");
        		String input = JOptionPane.showInputDialog("구매할 좌석 수량을 입력하세요");
        		//업데이트
        		if (input != null && !input.trim().isEmpty()) {
        			System.err.println("showInputMessege 값 :  " + input + "/ scheduleNum 값" + scheduleNum);
        			
        			SelectPage se = new SelectPage();
        			Connection con = se.connect();
        			
        			dto = new Dto();
        			
        			System.out.println("trainNum : " + trainNum);
        			System.out.println("scheduleNum : " + scheduleNum);
        			System.out.println("startDatTime : " + startDatTime);
        			System.out.println("id : " + id);
        			
        			dto.settrainNum(trainNum);
                	dto.setSchedule_num(scheduleNum);
                    dto.setStartDay(startDatTime);
                    dto.setId(id);
                    
                    
                    
        			String sql = "update schedule set baby_seat = baby_seat - ? where schedule_id = ?";
        			try {
        				
        	    		
        	    		PreparedStatement pstmt = con.prepareStatement(sql);
        				pstmt.setString(1, input);
        				pstmt.setString(2, scheduleNum);
        				
        				System.out.println(dto.getId());
        				
        				
        				int res = pstmt.executeUpdate();
        				
        				if(res>0) {
        					System.out.println("구매에 성공하였습니다");
        				}else {
        					System.out.println("구매에 실패하였습니다.");
        				}
        				
        				con.setAutoCommit(false);
        				System.out.println("어디까지 오니3");
        				
        				System.out.println();
        				
        				sql = "insert into reservation (reser_num, id, train_num, seat_type, reserve_time, seat_count, reserved_time)"
        						+ "values (reservation_seq.nextval, ?, ?, '유아석', ?, ?, sysdate)";
        				
        				pstmt = con.prepareStatement(sql);
        				
        				pstmt.setString(1, id);
        				pstmt.setString(2, trainNum);
        				pstmt.setString(3, startDatTime);
        				pstmt.setString(4, input);
        				
        				
        				System.out.println("어디까지 오니4");
        				System.out.println("reservation_seq.nextval");
        				System.out.println(dto.getId());
        				System.out.println(trainNum);
        				System.out.println(dto.getName());
        				System.out.println(startDatTime);
        				System.out.println(input);
        				
        				
        				res = pstmt.executeUpdate();
        				System.out.println("어디까지 오니5");
        				
        				con.commit();
        				con.setAutoCommit(true);
        				
        				
        				if(res>0) {
        					JOptionPane.showMessageDialog(null, "구매에 성공하였습니다");
        				}else {
        					JOptionPane.showMessageDialog(null, "구매에 실패하였습니다.");
        				}

        				new OrderPage(dto).setVisible(true);
        				
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
        					
        			
        		} else {
        		    // 입력값이 없거나 취소된 경우 처리
        		    System.out.println("입력값이 없거나 취소되었습니다.");
        		}
        	}
        	stopCellEditing();
        	isClicked = false; 
		}
        
        @Override
        public Object getCellEditorValue() {
            return labelText;
        }
        
        @Override
        public boolean stopCellEditing() {
        	fireEditingStopped();// 편집 중지 이벤트를 호출하여 테이블에 알림
        	return super.stopCellEditing();
        }
        
    }

}