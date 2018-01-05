package data.streaming.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KeywordDTO {

	String key;
	Double statistic;
	String date;

	public KeywordDTO(String key, Double statistic) {
		super();
		this.key = key;
		this.statistic = statistic;
		
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		this.date = formatter.format(new Date());
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Double getStatistic() {
		return statistic;
	}

	public void setStatistic(Double statistic) {
		this.statistic = statistic;
	}

	public void addOneStatisticMore(Double statistic) {
		this.statistic += statistic;
	}

	public void addOneStatisticMore() {
		this.statistic += 1.0;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
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
		KeywordDTO other = (KeywordDTO) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "KeywordDTO [key=" + key + ", statistic=" + statistic + "]";
	}

}
