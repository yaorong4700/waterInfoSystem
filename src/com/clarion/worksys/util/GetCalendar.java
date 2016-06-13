
package com.clarion.worksys.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 根据某天算出某个月的日历
 * @author guo_renpeng
 *
 */
public class GetCalendar {
	private Calendar mCalendar;
	private int iDay = 0;
	private int dayCount = 0;
	private int totalDay = 0;
	public GetCalendar(String currentDate) {
		String[] date      = currentDate.split("-");
		int      mYear     = Integer.parseInt(date[0]);
		int      mMonth    = Integer.parseInt(date[1]);
		int      mDay      = Integer.parseInt(date[2]);
		mCalendar = Calendar.getInstance();
		mCalendar.clear();
		mCalendar.set(Calendar.YEAR, mYear);
		mCalendar.set(Calendar.MONTH, mMonth-1);
		mCalendar.set(Calendar.DAY_OF_MONTH, 1);
		mCalendar.setFirstDayOfWeek(Calendar.SUNDAY);
		iDay = 0;
		dayCount = 0;
		int firstDay = mCalendar.get(Calendar.DAY_OF_WEEK);
		iDay = firstDay - 1;
		iDay = mCalendar.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
		if(mMonth == 2){
			if ((mMonth % 400 == 0) || (mMonth % 4 == 0) && (mMonth % 100 != 0))  {
                dayCount = 29;
			}else{
				dayCount = 28;
			}
		}else{
			switch (mMonth) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				dayCount = 31;
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				dayCount = 30;
				break;
			}	
		}
		totalDay = dayCount+iDay;
		if(totalDay<=35){
			totalDay =34;
		}else{
			totalDay = 41;
		}
	}
	
	public Calendar getFullCalendar() {
		return mCalendar;
	}
	
	public String getStartDate(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
		calendar.add(Calendar.DAY_OF_WEEK, -iDay);
		String startDate= calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
		return startDate;
	}

	public String getEndDate(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
		calendar.add(Calendar.DAY_OF_WEEK, totalDay);
		String endDate = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
		return endDate;
	}
	//工數入力，月末截止日
	public String getEndtimeDate(){
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		calendar.set(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)+1);
		calendar.set(Calendar.DATE, 1);
		calendar.roll(Calendar.DATE, -1);
		//String endDate  = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
		String endDate  = df.format(calendar.getTime());
		return endDate;
	}
	public String getMonthEndDate(){
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		calendar.set(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
		calendar.set(Calendar.DATE, 1);
		calendar.roll(Calendar.DATE, -1);
		//String endDate  = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
		String endDate  = df.format(calendar.getTime());
		return endDate;
	}
}
