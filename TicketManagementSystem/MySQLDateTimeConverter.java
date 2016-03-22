import java.util.Date;

public class MySQLDateTimeConverter {	
	// This method will return a DATE as medium date format from MySQL date time format. (Example: '2010-12-25 10:39:47' to 25 Dec, 2010)
	public Date getConvertedDate(String mysqlDateTime) {
		int monthNo = Integer.parseInt(String.valueOf(mysqlDateTime.substring(5, 7)));
		String month = new String();
			
		if(monthNo == 1)
			month = "Jan";
		else if(monthNo == 2)
			month = "Feb";
		else if(monthNo == 3)
			month = "Mar";
		else if(monthNo == 4)
			month = "Apr";
		else if(monthNo == 5)
			month = "May";
		else if(monthNo == 6)
			month = "Jun";
		else if(monthNo == 7)
			month = "Jul";
		else if(monthNo == 8)
			month = "Aug";
		else if(monthNo == 9)
			month = "Sep";
		else if(monthNo == 10)
			month = "Oct";
		else if(monthNo == 11)
			month = "Nov";
		else if(monthNo == 12)
			month = "Dec";
			
		Date convertedDate = new Date(month + " " + mysqlDateTime.substring(8, 10) + ", " + mysqlDateTime.substring(0, 4));
			
		return convertedDate;
	}
	
	// This method will return a DATE as time format from MySQL date time format. (Example: '2010-12-25 10:39:47' to 10:39)
	public Date getConvertedTime(String mySqlDateTime) {
		Date convertedDate = new Date();
		convertedDate.setHours(Integer.parseInt(String.valueOf(mySqlDateTime.substring(11, 13))));
		convertedDate.setMinutes(Integer.parseInt(String.valueOf(mySqlDateTime.substring(14, 16))));
		
		return convertedDate;
	}
}
