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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import datalayer.JDBCutil;

public class RegisterDAO_jdbc {

	
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	int count=1;
	
	
	
	
	public void DropRegister() {

		String sql = "DROP TABLE Register";

		try {
			con = JDBCutil.getConnection();

			
			//사용자 모두 삭제
			ps = con.prepareStatement(sql);
			rs=ps.executeQuery();

			if (rs==null)	System.out.println("register 테이블이 삭제되지 않았습니다.");
			else	System.out.println("register 테이블이 삭제되었습니다.");

		} catch (Exception e) {	e.printStackTrace();} 
		finally {JDBCutil.close(con, ps,rs);}

	}
	
	public void CreatRegister() {
		StringBuilder sb=new StringBuilder();

		sb.append(" CREATE TABLE Register(");
		sb.append(" dates VARCHAR(50) REFERENCES Schedule (dates),");
		sb.append(" User_Num VARCHAR(50) REFERENCES AppUser (User_Num), ");
		sb.append(" enter VARCHAR(50), ");
		sb.append(" departure VARCHAR(50),");
		sb.append(" loc VARCHAR(50),");
		sb.append(" tastegrade VARCHAR(50),");
		sb.append(" taste VARCHAR(500),");
		sb.append(" reason VARCHAR(500))");
		
		
		
		
		String sql =  sb.toString();

		try {
			con = JDBCutil.getConnection();

			//create
			ps = con.prepareStatement(sql);
			rs=ps.executeQuery();
			

			if (rs == null)	System.out.println("register 테이블이 만들어 지지 않았습니다.");
			else	System.out.println("register 테이블이 만들어 졌습니다.");

		} catch (Exception e) {	e.printStackTrace();} 
		finally {JDBCutil.close(con, ps, rs);}

	}

	public void CheckRegisterTable() {
		
		String sql = " select * from Register";

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
	public RegisterVO CheckRegister (RegisterVO register) {
		
		int temp_count=0;
		String sql = "Select * from Register where dates=? and User_Num=?";
		RegisterVO temp_register=null;
		
		try {
			con = JDBCutil.getConnection();
			ps = con.prepareStatement(sql);

			// ? 값 바인딩
			ps.setString(1, register.getDates());
			ps.setString(2, register.getUser_num());
			// ps 실행
			rs = ps.executeQuery();
			rs = ps.executeQuery();
			if(rs==null)System.out.println("Check sql 실행 실패");
			else {

				// 결과값 핸들링.
	
				while (rs.next()) {
					temp_register = new RegisterVO();
					temp_register.setDates("Dates");
					temp_register.setUser_num("User_Num");
					temp_register.setEnter("Enter");
					temp_register.setDeparture("Departure");
					temp_register.setLoc("Loc");
					temp_register.setTastegrade("Tastegrade");
					temp_register.setTaste("Taste");
					temp_register.setReason("Reason");
					temp_count++;
				}
			}
		}
		catch (Exception e) {e.printStackTrace();} 
		finally {JDBCutil.close(con, ps,rs);
			if(temp_count==0) { 
				temp_register=null;
				System.out.println("해당 날짜에 검색된 학생이 없습니다.");
			}
		}
		return temp_register;
	}
	
	//사용자 입력받기
	public int InsertRegister(RegisterVO register) {
		String sql = "insert into Register (dates, User_Num,enter,departure,loc,tastegrade,taste,reason) values (?,?,?,?,?,?,?,?)";
		int result=0;
		//FK로 입력하려는 값이 AppUser의 PK에 존재하는지 확인하는 용도
		int user_count=0;
		int schedule_count=0;
		
		AppUserDAO_jdbc dao=new AppUserDAO_jdbc();
		List<AppUserVO> list=dao.userList();
		Iterator<AppUserVO> it=list.iterator();
		while(it.hasNext()) {
			AppUserVO user=it.next();
			if(user.getUser_num().equals(register.getUser_num()))	user_count++;
		}
		
		if(user_count==0) {
			System.out.println("등록되지 않은 사용자가 출첵을 시도 했습니다. 회원가입 후 시도하세요.");
			return 0;
		}
		
		
		ScheduleDAO_jdbc dao2=new ScheduleDAO_jdbc();
		List<ScheduleVO> list2=dao2.ScheduleList();
		Iterator<ScheduleVO> it2=list2.iterator();
		while(it2.hasNext()) {
			ScheduleVO user=it2.next();
			if(user.getDates().equals(register.getDates()))	schedule_count++;
		}
		
		if(schedule_count==0) {
			System.out.println("해당 날이 스케줄에 등록되어 있지 않습니다. 일정을 확인해주세요.");
			return 0;
		}
		
		
		
		
		try {
			con = JDBCutil.getConnection();
			ps = con.prepareStatement(sql);

			// ? 값 바인딩
			ps.setString(1, register.getDates());
			ps.setString(2, register.getUser_num());
			ps.setString(3, register.getEnter());
			ps.setString(4, register.getDeparture());
			ps.setString(5, register.getLoc());
			ps.setString(6, register.getTastegrade());
			ps.setString(7, register.getTaste());
			ps.setString(8, register.getReason());

			// ps 실행
			result = ps.executeUpdate();

		} catch (Exception e) {e.printStackTrace();} 
		finally {JDBCutil.close(con, ps, rs);count++;}
		return result;

	}
	
	

	// 사용자 정보 수정. user_num은 pk로 변경불가

	public int UpdateRegister(RegisterVO presentRegister,RegisterVO afterRegister) {
		String sql = "update Register set enter=?, departure=?, loc=?,tastegrade=?, taste=?, reason=? "
				+ " where user_num=? and dates=?";
		int result=0;
		
		
		//FK로 입력하려는 값이 AppUser의 PK에 존재하는지 확인하는 용도
		int user_count=0;
		int schedule_count=0;
		
		AppUserDAO_jdbc dao=new AppUserDAO_jdbc();
		List<AppUserVO> list=dao.userList();
		Iterator<AppUserVO> it=list.iterator();
		while(it.hasNext()) {
			AppUserVO user=it.next();
			if(user.getUser_num().equals(afterRegister.getUser_num()))	user_count++;
		}
		
		if(user_count==0) {
			System.out.println("등록되지 않은 사용자가 출첵을 시도 했습니다. 회원가입 후 시도하세요.");
			return 0;
		}
		
		
		ScheduleDAO_jdbc dao2=new ScheduleDAO_jdbc();
		List<ScheduleVO> list2=dao2.ScheduleList();
		Iterator<ScheduleVO> it2=list2.iterator();
		while(it2.hasNext()) {
			ScheduleVO user=it2.next();
			if(user.getDates().equals(afterRegister.getDates()))	schedule_count++;
		}
		
		
		
		RegisterVO temp_register = CheckRegister(presentRegister);
		
		if(temp_register==null) {
			System.out.println("변경할 사용자가 목록에 없습니다. 정확히 확인 후 진행해주세요.");
			return 0;
		}
		
		
		try {
			con = JDBCutil.getConnection();
			ps = con.prepareStatement(sql);

			// ? 값 바인딩
			ps.setString(1, temp_register.getEnter());
			ps.setString(2, temp_register.getDeparture());
			ps.setString(3, temp_register.getLoc());
			ps.setString(4, temp_register.getTastegrade());
			ps.setString(5, temp_register.getTaste());
			ps.setString(6, temp_register.getReason());
			ps.setString(7, temp_register.getUser_num());
			ps.setString(8, temp_register.getDates());
			// ps 실행
			result = ps.executeUpdate();

		} catch (Exception e) {e.printStackTrace();} 
		finally {JDBCutil.close(con, ps, rs);}
		return result;
	}
	
	// 책 삭제 user_num 기준.
	public int DeleteRegister() {
		
		
		
		RegisterVO register = null;
		register.setDates(JOptionPane.showInputDialog("삭제할 출석날짜 입력하세요 ex:1998-11-23"));
		register.setUser_num(JOptionPane.showInputDialog("삭제할 고유 사용자번호를 입력하세요. ex:54"));
		register.setEnter(JOptionPane.showInputDialog("삭제할 출근 시간을 입력하세요. ex:09:05"));
		register.setDeparture(JOptionPane.showInputDialog("삭제할 퇴근 시간을 입력하세요. ex:18:02"));
		register.setLoc(JOptionPane.showInputDialog("삭제할 퇴근 장소를 입력하세요. ex:18:02"));
		register.setTastegrade(JOptionPane.showInputDialog("삭제할 맛점을 입력하세요. ex:18:02"));
		register.setTaste(JOptionPane.showInputDialog("삭제할 후기를 입력하세요. ex:18:02"));
		register.setReason(JOptionPane.showInputDialog("삭제할 이유를 입력하세요. ex:18:02"));
		
		RegisterVO list = CheckRegister(register);
		int result=0;
		
		if (list == null)System.out.println("Delete sql 실행 실패");
		else {
			System.out.println("삭제할 사용자를 찾았습니다.");
			result += DeleteRegister(register);
			System.out.println(" 삭제 완료");
		}
		
		return result;
	}
	public int DeleteRegister(RegisterVO register) {
		int result=0;
		
		String sql = "Delete Register where dates=?, User_Num=?";
		RegisterVO temp_register = CheckRegister(register);
		
		if(temp_register==null) {
			System.out.println("변경할 사용자가 목록에 없습니다. 정확히 확인 후 진행해주세요.");
			return 0;
		}

		try {
			con = JDBCutil.getConnection();
			ps = con.prepareStatement(sql);

			// ? 값 바인딩
			ps.setString(1, temp_register.getDates());
			ps.setString(2, temp_register.getUser_num());
			
			// ps 실행
			result += ps.executeUpdate();
			
			
			if(result!=0) System.out.println(temp_register.getUser_num()+"삭제성공!");
			else		  System.out.println(temp_register.getUser_num()+"삭제실패!");
		} catch (Exception e) {e.printStackTrace();} 
		finally {JDBCutil.close(con, ps,rs);}
		return result;
	}
	
	
	


	
	
	
	
	public RegisterVO SearchRegister(RegisterVO register) {
		
		String sql = "Select * from Register where dates=? and User_Num=? order by dates";
		List<RegisterVO> list = new ArrayList();
		RegisterVO temp_register=null;
		
		try {
			con = JDBCutil.getConnection();
			ps = con.prepareStatement(sql);

			// ? 값 바인딩
			ps.setString(1, register.getDates());
			ps.setString(2, register.getUser_num());
			// ps 실행
			rs = ps.executeQuery();
			if(rs==null)System.out.println("Select sql 실행 실패");
			else {

				// 결과값 핸들링.
				list = new ArrayList();
	
				while (rs.next()) {
					temp_register = new RegisterVO();
					temp_register.setDates(rs.getString("Dates"));
					temp_register.setUser_num(rs.getString("User_num"));
					temp_register.setEnter(rs.getString("Enter"));
					temp_register.setDeparture(rs.getString("Departure"));
					temp_register.setLoc(rs.getString("Loc"));
					temp_register.setTastegrade(rs.getString("Tastegrade"));
					temp_register.setTaste(rs.getString("Taste"));
					temp_register.setReason(rs.getString("Reason"));
				}
			}
		} catch (Exception e) {e.printStackTrace();} 
		finally {JDBCutil.close(con, ps,rs);}
		return temp_register;
	}
	
	//리스트 출력
	public List<RegisterVO> userList() {
		String sql = "select * from Register order by user_num";

		List<RegisterVO> list = new ArrayList<>();
		RegisterVO temp_register=null;


		try {
			con = JDBCutil.getConnection();
			ps = con.prepareStatement(sql);

			// ps 실행
			rs = ps.executeQuery();

			// 결과값 핸들링.
			while (rs.next()) {
				temp_register = new RegisterVO();
				temp_register.setDates(rs.getString("Dates"));
				temp_register.setUser_num(rs.getString("User_num"));
				temp_register.setEnter(rs.getString("Enter"));
				temp_register.setDeparture(rs.getString("Departure"));
				temp_register.setLoc(rs.getString("Loc"));
				temp_register.setTastegrade(rs.getString("Tastegrade"));
				temp_register.setTaste(rs.getString("Taste"));
				temp_register.setReason(rs.getString("Reason"));
				list.add(temp_register);
			}
		} catch (Exception e) {e.printStackTrace();} 
		finally {JDBCutil.close(con, ps, rs);}
		return list;
	}
	
	
	
	
}
