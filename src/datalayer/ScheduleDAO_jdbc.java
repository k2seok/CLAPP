/*Book 생성
UserVO 생성

BookDAO 작성
   Book 등록 메소드
   Book 삭제 메소드
   Book 수정 메소드
   Book 목록 메소드
   Book 검색 메소드  => 
   검색 조건 컬럼을 타이틀별 혹은 저자별로 검색 가능 하도록 만든다.



클래스  create book
클래스 UserVO
userdao가져오고
나머지는 코드만 바꾸고, 조건 검색만 따로 만들기.

title,price,author,pubdate*/

//date를 입력하는게 따로 없는데 어떻게 될것인지..

package datalayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import datalayer.JDBCutil;

public class ScheduleDAO_jdbc {

	
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	int count=1;
	
	
	
	
	public void DropSchedule() {

		String sql = "DROP TABLE Schedule";

		try {
			con = JDBCutil.getConnection();

			
			//사용자 모두 삭제
			ps = con.prepareStatement(sql);
			rs=ps.executeQuery();

			if (rs==null)	System.out.println("Schedule 테이블이 삭제되지 않았습니다.");
			else	System.out.println("Schedule 테이블이 삭제되었습니다.");

		} catch (Exception e) {	e.printStackTrace();} 
		finally {JDBCutil.close(con, ps,rs);}

	}
	
	public void CreatSchedule() {
		StringBuilder sb=new StringBuilder();

		sb.append(" CREATE TABLE Schedule(");
		sb.append(" dates VARCHAR(50) PRIMARY KEY,");
		sb.append(" menu1 VARCHAR(50), ");
		sb.append(" menu2 VARCHAR(50), ");
		sb.append(" event VARCHAR(500))");
		
		
		String sql =  sb.toString();

		try {
			con = JDBCutil.getConnection();

			//create
			ps = con.prepareStatement(sql);
			rs=ps.executeQuery();
			

			if (rs == null)	System.out.println("Schedule 테이블이 만들어 지지 않았습니다.");
			else	System.out.println("Schedule 테이블이 만들어 졌습니다.");

		} catch (Exception e) {	e.printStackTrace();} 
		finally {JDBCutil.close(con, ps, rs);}

	}

	public void CheckScheduleTable() {
		
		String sql = " select * from Schedule";

		try {
			con = JDBCutil.getConnection();
			ps = con.prepareStatement(sql);

			// ps 실행
			rs = ps.executeQuery();
			// 결과값 핸들링.
			if (rs.next() == false)	System.out.println("테이블이 비어 있습니다.");
			else	System.out.println("테이블에 한 레코드 이상의 정보가 있습니다.");
			

		} catch (Exception e) {e.printStackTrace();} 
		finally {JDBCutil.close(con, ps, rs);}		
	}
	public ScheduleVO CheckSchedule (ScheduleVO schedule) {
		
		int temp_count=0;
		String sql = "Select * from Schedule where dates=?";
		ScheduleVO temp_schedule=null;
		
		try {
			con = JDBCutil.getConnection();
			ps = con.prepareStatement(sql);

			// ? 값 바인딩
			ps.setString(1, schedule.getDates());
			// ps 실행
			rs = ps.executeQuery();
			if(rs==null)System.out.println("Check sql 실행 실패");
			else {

				// 결과값 핸들링.
	
				while (rs.next()) {
					temp_schedule = new ScheduleVO();
					temp_schedule.setDates("Dates");
					temp_schedule.setMenu1("Menu1");
					temp_schedule.setMenu2("Menu2");
					temp_schedule.setEvent("Event");
					temp_count++;
				}
			}
		}
		catch (Exception e) {e.printStackTrace();} 
		finally {JDBCutil.close(con, ps,rs);
			if(temp_count==0) { 
				temp_schedule=null;
				System.out.println("검색된 일정이 없습니다.");
			}
		}
		return temp_schedule;
	}
	
	//사용자 입력받기
	public int InsertSchedule(ScheduleVO schedule) {
		String sql = "insert into Schedule (dates, menu1, menu2, event) values (?,?,?,?)";
		int result=0;
		
		try {
			con = JDBCutil.getConnection();
			ps = con.prepareStatement(sql);

			// ? 값 바인딩
			ps.setString(1, schedule.getDates());
			ps.setString(2, schedule.getMenu1());
			ps.setString(3, schedule.getMenu2());
			ps.setString(4, schedule.getEvent());

			// ps 실행
			result = ps.executeUpdate();

		} catch (Exception e) {e.printStackTrace();} 
		finally {JDBCutil.close(con, ps, rs);}
		return result;

	}
	
	

	// 사용자 정보 수정. user_num은 pk로 변경불가

	public int UpdateSchedule(ScheduleVO presentSchedule, String menu1,String menu2, String event) {
		String sql = "update Schedule set menu1=?, menu2=?, event=? where dates=?";
		
		ScheduleVO temp_schedule = CheckSchedule(presentSchedule);
		
		if(temp_schedule==null) {
			System.out.println("변경할 일정이 목록에 없습니다. 정확히 확인 후 진행해주세요.");
			return 0;
		}
		
		int result=0;
		try {
			con = JDBCutil.getConnection();
			ps = con.prepareStatement(sql);

			// ? 값 바인딩
			ps.setString(1, menu1);
			ps.setString(2, menu2);
			ps.setString(3, event);
			ps.setString(4, presentSchedule.getDates());
			// ps 실행
			result = ps.executeUpdate();

		} catch (Exception e) {e.printStackTrace();} 
		finally {JDBCutil.close(con, ps, rs);}
		return result;
	}
	
	// 책 삭제 user_num 기준.
	public int DeleteSchedule() {
		
		
		
		ScheduleVO schedule = null;
		schedule.setDates(JOptionPane.showInputDialog("삭제할 일정 날짜 입력하세요 ex:1998-11-23"));
		
		int result=0;
		
		result += DeleteSchedule(schedule);
		
		
		if (result == 0)System.out.println("Delete sql 실행 실패");
		else {
			System.out.println(" 삭제 완료");
		}
		
		return result;
	}
	public int DeleteSchedule(ScheduleVO schedule) {
		int result=0;
		
		String Schedulesql = "delete from Schedule where dates=?";
		String Registersql = "delete from Register where dates=?";
		
		try {
			con = JDBCutil.getConnection();
			
			//출석 테이블의 해당 학생 정보 삭제
			ps = con.prepareStatement(Registersql);
			ps.setString(1, schedule.getDates());
			result+=ps.executeUpdate();
			//회원 테이블의 해당 학생정보 삭제
			ps = con.prepareStatement(Schedulesql);
			ps.setString(1, schedule.getDates());
			result+=ps.executeUpdate();
			if(result!=0) System.out.println("해당되는 Register정보, Schedule 정보 삭제성공!");
			else		  System.out.println("해당되는 Register정보, Schedule 정보 삭제실패!");
		} catch (Exception e) {e.printStackTrace();} 
		finally {JDBCutil.close(con, ps,rs);}
		return result;
	}
	
	
	


	
	
	
	
	public ScheduleVO SearchSchedule(ScheduleVO schedule) {
		
		String sql = "Select * from Schedule where dates=?";
		List<ScheduleVO> list = new ArrayList();
		ScheduleVO temp_schedule=null;
		
		try {
			con = JDBCutil.getConnection();
			ps = con.prepareStatement(sql);

			// ? 값 바인딩
			ps.setString(1, schedule.getDates());
			// ps 실행
			rs = ps.executeQuery();
			if(rs==null)System.out.println("Search sql 실행 실패");
			else {

				// 결과값 핸들링.
				list = new ArrayList();
	
				while (rs.next()) {
					
					temp_schedule = new ScheduleVO();
					temp_schedule.setDates(rs.getString("Dates"));
					temp_schedule.setMenu1(rs.getString("Menu1"));
					temp_schedule.setMenu2(rs.getString("Menu2"));
					temp_schedule.setEvent(rs.getString("Event"));
					list.add(temp_schedule);
				}
			}
		} catch (Exception e) {e.printStackTrace();} 
		finally {JDBCutil.close(con, ps,rs);
		}
		return temp_schedule;
	}
	
	//리스트 출력
	public List<ScheduleVO> ScheduleList() {
		String sql = "select * from Schedule order by dates";

		List<ScheduleVO> list = new ArrayList<>();
		ScheduleVO temp_schedule=null;


		try {
			con = JDBCutil.getConnection();
			ps = con.prepareStatement(sql);

			// ps 실행
			rs = ps.executeQuery();

			// 결과값 핸들링.
			while (rs.next()) {
				temp_schedule = new ScheduleVO();
				temp_schedule.setDates(rs.getString("Dates"));
				temp_schedule.setMenu1(rs.getString("Menu1"));
				temp_schedule.setMenu2(rs.getString("Menu2"));
				temp_schedule.setEvent(rs.getString("Event"));
				list.add(temp_schedule);
			}
		} catch (Exception e) {e.printStackTrace();} 
		finally {JDBCutil.close(con, ps, rs);}
		return list;
	}
	
	
	public void AutoSchedule()
	{
		String start = "2018-06-01";
	    String end = "2018-07-04";
	    StringBuilder sb=new StringBuilder();
	    String out;
	    
	    ScheduleVO schedule=new ScheduleVO();

		ScheduleDAO_jdbc dao = new ScheduleDAO_jdbc();

		dao.DropSchedule();
		dao.CreatSchedule();
	    
	    try {
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        Date beginDate = formatter.parse(start);
	        Date endDate = formatter.parse(end);
	        
	         
	        // 시간차이를 시간,분,초를 곱한 값으로 나누면 하루 단위가 나옴
	        long diffMilisecond = endDate.getTime() - beginDate.getTime();
	        long diffDays = diffMilisecond / (24 * 60 * 60 * 1000);
	        int difday=(int)diffDays;
	 
	        sb.append(start);
	        sb.append("부터 ");
	        sb.append(end);
	        sb.append("까지의 식단 생성 중 ...");
	        
	        out=sb.toString();
	        
	        System.out.println(out);
	        
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(beginDate);
	        System.out.println(formatter.format(cal.getTime()));
	        for(int i=0;i<difday;i++) {
		        cal.add(Calendar.DATE, 1);
		        schedule.setDates(formatter.format(cal.getTime()));
   		        dao.InsertSchedule(schedule);
	        }
	        System.out.println(formatter.format(cal.getTime()));
	         
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	}
	
	
	
}
