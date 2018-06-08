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

public class AppUserDAO_jdbc {

	
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	int count=1;
	
	
	
	
	public void DropUser() {

		String sql = "DROP TABLE AppUser";

		try {
			con = JDBCutil.getConnection();

			
			//사용자 모두 삭제
			ps = con.prepareStatement(sql);
			rs=ps.executeQuery();

			if (rs==null)	System.out.println("user 테이블이 삭제되지 않았습니다.");
			else	System.out.println("user 테이블이 삭제되었습니다.");

		} catch (Exception e) {	e.printStackTrace();} 
		finally {JDBCutil.close(con, ps,rs);}

	}
	
	public void CreatUser() {
		StringBuilder sb=new StringBuilder();

		sb.append(" CREATE TABLE AppUser(");
		sb.append(" User_Num VARCHAR(50) PRIMARY KEY,");
		sb.append(" birthday VARCHAR(50), ");
		sb.append(" name VARCHAR(50))");
		
		
		
		String sql =  sb.toString();

		try {
			con = JDBCutil.getConnection();

			//create
			ps = con.prepareStatement(sql);
			rs=ps.executeQuery();
			

			if (rs == null)	System.out.println("user 테이블이 만들어 지지 않았습니다.");
			else	System.out.println("user 테이블이 만들어 졌습니다.");

		} catch (Exception e) {	e.printStackTrace();} 
		finally {JDBCutil.close(con, ps, rs);}

	}

	public void check() {
		
		String sql = " select * from AppUser";

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
	
	//사용자 입력받기
	public int userInsert(AppUserVO user) {
		String sql = "insert into AppUser (user_num,birthday,name) values (?,?,?)";
		int result=0;
		
		try {
			con = JDBCutil.getConnection();
			ps = con.prepareStatement(sql);

			// ? 값 바인딩
			ps.setString(1, Integer.toString(count));
			ps.setString(2, user.getBirthday());
			ps.setString(3, user.getName());

			// ps 실행
			result = ps.executeUpdate();

		} catch (Exception e) {e.printStackTrace();} 
		finally {JDBCutil.close(con, ps, rs);count++;}
		return result;

	}
	
	

	// 사용자 정보 수정. user_num은 pk로 변경불가

	public int userUpdate(AppUserVO user,String birthday,String name) {
		String sql = "update AppUser set birthday=?, name=? where user_num=?";
		
		AppUserVO temp_user = userCheck(user);
		
		
		int result=0;
		try {
			con = JDBCutil.getConnection();
			ps = con.prepareStatement(sql);

			// ? 값 바인딩
			ps.setString(1, birthday);
			ps.setString(2, name);
			ps.setString(3, temp_user.getUser_num());
			// ps 실행
			result = ps.executeUpdate();

		} catch (Exception e) {e.printStackTrace();} 
		finally {JDBCutil.close(con, ps, rs);}
		return result;
	}
	
	// 책 삭제 user_num 기준.
	public int userDelete() {
		
		AppUserVO user = null;
		user.setName(JOptionPane.showInputDialog("삭제할 사용자 이름을 입력하세요 ex:홍길동"));
		user.setBirthday(JOptionPane.showInputDialog("삭제할 사용자의 생년월일를 입력하세요 ex:1998-11-23"));
		
		AppUserVO list = userCheck(user);
		int result=0;
		
		if (list == null)System.out.println("Delete sql 실행 실패");
		else {
			System.out.println("삭제할 사용자를 찾았습니다.");
			result += userDelete(user);
			System.out.println(" 삭제 완료");
		}
		
		return result;
	}
	public int userDelete(AppUserVO user) {
		String AppUsersql = "delete from AppUser where user_num=?";
		String Registersql = "delete from Register where user_num=?";
		
		int result=0;
		

		try {
			con = JDBCutil.getConnection();
			
			//출석 테이블의 해당 학생 정보 삭제
			ps = con.prepareStatement(Registersql);
			ps.setString(1, user.getUser_num());
			result+=ps.executeUpdate();
			//회원 테이블의 해당 학생정보 삭제
			ps = con.prepareStatement(AppUsersql);
			ps.setString(1, user.getUser_num());
			result+=ps.executeUpdate();
			if(result!=0) System.out.println(user.getName()+"  "+user.getBirthday()+"삭제성공!");
			else		  System.out.println(user.getName()+"  "+user.getBirthday()+"삭제실패!");
		} catch (Exception e) {e.printStackTrace();} 
		finally {JDBCutil.close(con, ps,rs);}
		return result;
	}
	
	
	
public AppUserVO userCheck (AppUserVO user) {
		
		int temp_count=0;
		String sql = "Select * from AppUser where name =? and birthday =?";
		AppUserVO temp_user=null;
		
		try {
			con = JDBCutil.getConnection();
			ps = con.prepareStatement(sql);

			// ? 값 바인딩
			ps.setString(1, user.getName());
			ps.setString(2, user.getBirthday());
			// ps 실행
			rs = ps.executeQuery();
			if(rs==null)System.out.println("Check sql 실행 실패");
			else {

				// 결과값 핸들링.
	
				while (rs.next()) {
					temp_user = new AppUserVO();
					temp_user.setUser_num(rs.getString("user_num"));
					temp_user.setBirthday(rs.getString("Birthday"));
					temp_user.setName(rs.getString("Name"));
					temp_count++;
				}
			}
		} catch (Exception e) {e.printStackTrace();} 
		finally {JDBCutil.close(con, ps,rs);
			if(temp_count==0) { 
				temp_user=null;
				System.out.println("검색된 사용자가 없습니다.");
			}
		}
		return temp_user;
	}

	
	
	
	
	public AppUserVO userSearch(AppUserVO user) {
		
		String sql = "Select * from AppUser where name=? and birthday=?";
		List<AppUserVO> list = new ArrayList();
		AppUserVO temp_user=null;
		
		try {
			con = JDBCutil.getConnection();
			ps = con.prepareStatement(sql);

			// ? 값 바인딩
			ps.setString(1, user.getName());
			ps.setString(2, user.getBirthday());
			// ps 실행
			rs = ps.executeQuery();
			if(rs==null)System.out.println("Update sql 실행 실패");
			else {

				// 결과값 핸들링.
				list = new ArrayList();
	
				while (rs.next()) {
					temp_user = new AppUserVO();
					temp_user.setUser_num(rs.getString("user_num"));
					temp_user.setBirthday(rs.getString("Birthday"));
					temp_user.setName(rs.getString("Name"));
				}
			}
		} catch (Exception e) {e.printStackTrace();} 
		finally {JDBCutil.close(con, ps,rs);}
		return temp_user;
	}
	
	//리스트 출력
	public List<AppUserVO> userList() {
		String sql = "select * from AppUser order by user_num";

		List<AppUserVO> list = new ArrayList<>();
		AppUserVO temp_user=null;


		try {
			con = JDBCutil.getConnection();
			ps = con.prepareStatement(sql);

			// ps 실행
			rs = ps.executeQuery();

			// 결과값 핸들링.
			while (rs.next()) {
				temp_user = new AppUserVO();
				temp_user.setUser_num(rs.getString("user_num"));
				temp_user.setName(rs.getString("name"));
				temp_user.setBirthday(rs.getString("birthday"));
				list.add(temp_user);
			}
		} catch (Exception e) {e.printStackTrace();} 
		finally {JDBCutil.close(con, ps, rs);}
		return list;
	}
	
	
	
	
}
