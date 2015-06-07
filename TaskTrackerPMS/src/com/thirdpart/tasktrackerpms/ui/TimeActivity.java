package com.thirdpart.tasktrackerpms.ui;

import java.util.Calendar;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.thirdpart.model.PMSManagerAPI;
import com.thirdpart.tasktrackerpms.R;

public class TimeActivity extends Activity {

	public static final int REQUEST_PICK_DATE = 0x11;
    private WheelView month;
	private WheelView day;
    private WheelView monthEnd;
	private WheelView dayEnd;
	private int curMonth;
	private boolean oneday;

    Calendar calendar = Calendar.getInstance();
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_layout);
        oneday = getIntent().getBooleanExtra("oneday", true);

        if (oneday) {
			findViewById(R.id.enddate).setVisibility(View.GONE);
			((TextView)findViewById(R.id.beginText)).setText("选择日期");
		}
        

        month = (WheelView) findViewById(R.id.month);
     //   final WheelView year = (WheelView) findViewById(R.id.year);
        day = (WheelView) findViewById(R.id.day);
        monthEnd = (WheelView) findViewById(R.id.month_end);
     //   final WheelView year = (WheelView) findViewById(R.id.year);
        dayEnd = (WheelView) findViewById(R.id.day_end);
        OnWheelChangedListener listener = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updateDays(month, day,monthEnd,dayEnd);
            }
        };

        // month
         curMonth = calendar.get(Calendar.MONTH);
        String months[] = new String[] {"一月", "二月", "三月", "四月", "五月",
                "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};
        month.setViewAdapter(new DateArrayAdapter(this, months, curMonth));
        month.setCurrentItem(curMonth);
        month.addChangingListener(listener);
        
        monthEnd.setViewAdapter(new DateArrayAdapter(this, months, curMonth));
        monthEnd.setCurrentItem(curMonth);
        monthEnd.addChangingListener(listener);
        
        findViewById(R.id.done).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				

				//check date is ok or not....
				
				if (monthEnd.getCurrentItem()<curMonth) {
					Toast.makeText(getBaseContext(), "呀灭地，结束日期必须是当月以及以后哟!", Toast.LENGTH_SHORT).show();
					return;
				}
				
				if (!oneday&&monthEnd.getCurrentItem()<month.getCurrentItem()) {
					Toast.makeText(getBaseContext(), "结束日期不能小于开始日期", Toast.LENGTH_SHORT).show();
					return;
				}
				if (!oneday&&monthEnd.getCurrentItem()==month.getCurrentItem()) {
					
					if (dayEnd.getCurrentItem()<day.getCurrentItem()) {
						Toast.makeText(getBaseContext(), "结束日期不能小于开始日期", Toast.LENGTH_SHORT).show();
						return;
					}
				}
				Intent intent = new Intent();
				intent.putExtra("month", month.getCurrentItem());
				intent.putExtra("day", day.getCurrentItem()+1);
				//intent.put
				intent.putExtra("monthEnd", monthEnd.getCurrentItem());
				intent.putExtra("dayEnd", dayEnd.getCurrentItem()+1);
				intent.putExtra("format", forSendMatDate(calendar.get(Calendar.YEAR), month.getCurrentItem(), day.getCurrentItem()+1));
				
				if (oneday) {
					intent.putExtra("time", forMatDate(month.getCurrentItem(), day.getCurrentItem()+1));
						
				}else {
					intent.putExtra("format1", forSendMatDate(calendar.get(Calendar.YEAR), monthEnd.getCurrentItem(), dayEnd.getCurrentItem()+1));
			intent.putExtra("time", forMatDate(month.getCurrentItem(), day.getCurrentItem()+1) + " - "
							+ forMatDate( monthEnd.getCurrentItem(),  dayEnd.getCurrentItem()+1));
					
				}
				
					setResult(RESULT_OK,intent);
					finish();
			}
		});
    
        
        //day
        updateDays(month, day,monthEnd,dayEnd);
        day.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1);
        dayEnd.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1);
    }
    
    /**
     * Updates day wheel. Sets max days according to selected month and year
     */
    void updateDays(WheelView month, WheelView day,WheelView monthEnd, WheelView dayEnd) {
     //   Calendar calendar = Calendar.getInstance();
     //   calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + year.getCurrentItem());
    
     //   calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) );
        calendar.set(Calendar.MONTH, month.getCurrentItem());   
        int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        day.setViewAdapter(new DateNumericAdapter(this, 1, maxDays, calendar.get(Calendar.DAY_OF_MONTH) - 1));
        int curDay = Math.min(maxDays, day.getCurrentItem() + 1);
        day.setCurrentItem(curDay - 1, true);
        
        //
        calendar.set(Calendar.MONTH, monthEnd.getCurrentItem());
         maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
         dayEnd.setViewAdapter(new DateNumericAdapter(this, 1, maxDays, calendar.get(Calendar.DAY_OF_MONTH) - 1));
         curDay = Math.min(maxDays, day.getCurrentItem() + 1);
         dayEnd.setCurrentItem(curDay - 1, true);
    }
    
    
    private String getTimeFormat(int val) {
		if (val < 10) {
			return "0" + val;
		} else {
			return "" + val;
		}
	}
    
    private String forSendMatDate(int year,int month, int day) {
		return year +"-"+ getTimeFormat(1 + month) + "-" + getTimeFormat(day);
	}

	private String forMatDate(int month, int day) {
		return getTimeFormat(1 + month) + "月" + getTimeFormat(day) + "日";
	}

	


	String getDefualtTimeFormat() {
		int yearVal = calendar.get(Calendar.YEAR);
		int hourVal = 10;
		if (hourVal > 24) {
			hourVal = 24;
		}
		int endHourVal = 18;
		if (endHourVal > 24) {
			endHourVal = 24;
		}
		int monthVal;
		int dayVal;
		int monthEndVal;
		int dayEndVal;
		return forMatDate(monthVal = calendar.get(Calendar.MONTH),
				dayVal = calendar.get(Calendar.DAY_OF_MONTH))
				+ " - "
				+ forMatDate(monthEndVal = calendar.get(Calendar.MONTH),
						dayEndVal = calendar.get(Calendar.DAY_OF_MONTH));
	}

    
    
    /**
     * Adapter for numeric wheels. Highlights the current value.
     */
    private class DateNumericAdapter extends NumericWheelAdapter {
        // Index of current item
        int currentItem;
        // Index of item to be highlighted
        int currentValue;
        
        /**
         * Constructor
         */
        public DateNumericAdapter(Context context, int minValue, int maxValue, int current) {
            super(context, minValue, maxValue);
            this.currentValue = current;
            setTextSize(16);
        }
        
        @Override
        protected void configureTextView(TextView view) {
            super.configureTextView(view);
            if (currentItem == currentValue) {
                view.setTextColor(0xFF0000F0);
            }
            view.setTypeface(Typeface.SANS_SERIF);
        }
        
        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            currentItem = index;
            return super.getItem(index, cachedView, parent);
        }
    }
    
    /**
     * Adapter for string based wheel. Highlights the current value.
     */
    private class DateArrayAdapter extends ArrayWheelAdapter<String> {
        // Index of current item
        int currentItem;
        // Index of item to be highlighted
        int currentValue;
        
        /**
         * Constructor
         */
        public DateArrayAdapter(Context context, String[] items, int current) {
            super(context, items);
            this.currentValue = current;
            setTextSize(16);
        }
        
        @Override
        protected void configureTextView(TextView view) {
            super.configureTextView(view);
            if (currentItem == currentValue) {
                view.setTextColor(0xFF0000F0);
            }
            view.setTypeface(Typeface.SANS_SERIF);
        }
        
        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            currentItem = index;
            return super.getItem(index, cachedView, parent);
        }
    }
}
