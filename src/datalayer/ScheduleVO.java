



package datalayer;

import java.io.Serializable;
import java.util.Date;

public class ScheduleVO implements Serializable{
	
	

	
	//column name과 동일하게 가는 것이 제일 편함.
	String dates,menu1,menu2,event;

	public ScheduleVO() {
		super();
	}

	public ScheduleVO(String dates, String menu1, String menu2, String event) {
		super();
		this.dates = dates;
		this.menu1 = menu1;
		this.menu2 = menu2;
		this.event = event;
	}

	@Override
	public String toString() {
		return "ScheduleVO [dates=" + dates + ", menu1=" + menu1 + ", menu2=" + menu2 + ", event=" + event + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dates == null) ? 0 : dates.hashCode());
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + ((menu1 == null) ? 0 : menu1.hashCode());
		result = prime * result + ((menu2 == null) ? 0 : menu2.hashCode());
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
		ScheduleVO other = (ScheduleVO) obj;
		if (dates == null) {
			if (other.dates != null)
				return false;
		} else if (!dates.equals(other.dates))
			return false;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		if (menu1 == null) {
			if (other.menu1 != null)
				return false;
		} else if (!menu1.equals(other.menu1))
			return false;
		if (menu2 == null) {
			if (other.menu2 != null)
				return false;
		} else if (!menu2.equals(other.menu2))
			return false;
		return true;
	}

	public String getDates() {
		return dates;
	}

	public void setDates(String dates) {
		this.dates = dates;
	}

	public String getMenu1() {
		return menu1;
	}

	public void setMenu1(String menu1) {
		this.menu1 = menu1;
	}

	public String getMenu2() {
		return menu2;
	}

	public void setMenu2(String menu2) {
		this.menu2 = menu2;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	
	
	

	
	
	

}
