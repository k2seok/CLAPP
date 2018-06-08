



package datalayer;

import java.io.Serializable;
import java.util.Date;

public class RegisterVO implements Serializable{
	
	

	
	//column name과 동일하게 가는 것이 제일 편함.
	String dates,user_num,enter,departure,loc,tastegrade,taste,reason;

	public RegisterVO() {
		super();
	}

	public RegisterVO(String dates, String user_num, String enter, String departure, String loc, String tastegrade,
			String taste, String reason) {
		super();
		this.dates = dates;
		this.user_num = user_num;
		this.enter = enter;
		this.departure = departure;
		this.loc = loc;
		this.tastegrade = tastegrade;
		this.taste = taste;
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "RegisterVO [dates=" + dates + ", user_num=" + user_num + ", enter=" + enter + ", departure=" + departure
				+ ", loc=" + loc + ", tastegrade=" + tastegrade + ", taste=" + taste + ", reason=" + reason + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dates == null) ? 0 : dates.hashCode());
		result = prime * result + ((departure == null) ? 0 : departure.hashCode());
		result = prime * result + ((enter == null) ? 0 : enter.hashCode());
		result = prime * result + ((loc == null) ? 0 : loc.hashCode());
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
		result = prime * result + ((taste == null) ? 0 : taste.hashCode());
		result = prime * result + ((tastegrade == null) ? 0 : tastegrade.hashCode());
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
		RegisterVO other = (RegisterVO) obj;
		if (dates == null) {
			if (other.dates != null)
				return false;
		} else if (!dates.equals(other.dates))
			return false;
		if (departure == null) {
			if (other.departure != null)
				return false;
		} else if (!departure.equals(other.departure))
			return false;
		if (enter == null) {
			if (other.enter != null)
				return false;
		} else if (!enter.equals(other.enter))
			return false;
		if (loc == null) {
			if (other.loc != null)
				return false;
		} else if (!loc.equals(other.loc))
			return false;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		if (taste == null) {
			if (other.taste != null)
				return false;
		} else if (!taste.equals(other.taste))
			return false;
		if (tastegrade == null) {
			if (other.tastegrade != null)
				return false;
		} else if (!tastegrade.equals(other.tastegrade))
			return false;
		if (user_num == null) {
			if (other.user_num != null)
				return false;
		} else if (!user_num.equals(other.user_num))
			return false;
		return true;
	}

	public String getDates() {
		return dates;
	}

	public void setDates(String dates) {
		this.dates = dates;
	}

	public String getUser_num() {
		return user_num;
	}

	public void setUser_num(String user_num) {
		this.user_num = user_num;
	}

	public String getEnter() {
		return enter;
	}

	public void setEnter(String enter) {
		this.enter = enter;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public String getTastegrade() {
		return tastegrade;
	}

	public void setTastegrade(String tastegrade) {
		this.tastegrade = tastegrade;
	}

	public String getTaste() {
		return taste;
	}

	public void setTaste(String taste) {
		this.taste = taste;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	

	

	
	
	

}
