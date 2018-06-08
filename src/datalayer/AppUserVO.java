



package datalayer;

import java.io.Serializable;
import java.util.Date;

public class AppUserVO implements Serializable{
	
	

	
	//column name과 동일하게 가는 것이 제일 편함.
	String user_num,birthday,name;

	public AppUserVO() {
		super();
	}

	public AppUserVO(String user_num, String birthday, String name) {
		super();
		this.user_num = user_num;
		this.birthday = birthday;
		this.name = name;
	}

	@Override
	public String toString() {
		return "AppUserVO [user_num=" + user_num + ", birthday=" + birthday + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((user_num == null) ? 0 : user_num.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppUserVO other = (AppUserVO) obj;
		if (birthday == null) {
			if (other.birthday != null)
				return false;
		} else if (!birthday.equals(other.birthday))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (user_num == null) {
			if (other.user_num != null)
				return false;
		} else if (!user_num.equals(other.user_num))
			return false;
		return true;
	}

	public String getUser_num() {
		return user_num;
	}

	public void setUser_num(String user_num) {
		this.user_num = user_num;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	

	
	
	

}
