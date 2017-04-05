package com.thirdpart.tasktrackerpms.ui;

import java.util.Calendar;

import com.thirdpart.tasktrackerpms.R;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelClickedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TimePicker;
import android.widget.Toast;

public class TimeActivity extends DateActivity {
	// Time changed flag
	private boolean timeChanged = false;
	
	// Time scrolled flag
	private boolean timeScrolled = false;
	 WheelView hours ;
	 WheelView mins;
	// WheelView endhours ;
	// WheelView endmins;
	 boolean sameday=true;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.time_date_layout);
		initDate();
		 hours = (WheelView) findViewById(R.id.hour);
		hours.setViewAdapter(new NumericWheelAdapter(this, 0, 23));
	
		 mins = (WheelView) findViewById(R.id.mins);
		mins.setViewAdapter(new NumericWheelAdapter(this, 0, 59, "%02d"));
		mins.setCyclic(true);
	
//		 endhours = (WheelView) findViewById(R.id.endhour);
//		 endhours.setViewAdapter(new NumericWheelAdapter(this, 0, 23));
//		
//			 endmins = (WheelView) findViewById(R.id.endmins);
//			 endmins.setViewAdapter(new NumericWheelAdapter(this, 0, 59, "%02d"));
//			 endmins.setCyclic(true);
		
		
//		final TimePicker picker = (TimePicker) findViewById(R.id.time);
//		picker.setIs24HourView(true);
//	
		// set current time
		Calendar c = Calendar.getInstance();
		int curHours = c.get(Calendar.HOUR_OF_DAY);
		int curMinutes = c.get(Calendar.MINUTE);
	//
		hours.setCurrentItem(10);
		mins.setCurrentItem(0);
	
	//	endhours.setCurrentItem(18);
		mins.setCurrentItem(0);
		
//		picker.setCurrentHour(curHours);
//		picker.setCurrentMinute(curMinutes);
	
		// add listeners
		addChangingListener(mins, "min");
		addChangingListener(hours, "hour");
	
		OnWheelChangedListener wheelListener = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (!timeScrolled) {
					timeChanged = true;
//					picker.setCurrentHour(hours.getCurrentItem());
//					picker.setCurrentMinute(mins.getCurrentItem());
					timeChanged = false;
				}
			}
		};
		hours.addChangingListener(wheelListener);
		mins.addChangingListener(wheelListener);
		
		OnWheelClickedListener click = new OnWheelClickedListener() {
            public void onItemClicked(WheelView wheel, int itemIndex) {
                wheel.setCurrentItem(itemIndex, true);
            }
        };
        hours.addClickingListener(click);
        mins.addClickingListener(click);

		OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
			public void onScrollingStarted(WheelView wheel) {
				timeScrolled = true;
			}
			public void onScrollingFinished(WheelView wheel) {
				timeScrolled = false;
				timeChanged = true;
//				picker.setCurrentHour(hours.getCurrentItem());
//				picker.setCurrentMinute(mins.getCurrentItem());
				timeChanged = false;
			}
		};
		
		hours.addScrollingListener(scrollListener);
		mins.addScrollingListener(scrollListener);
		
//		picker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
//			public void onTimeChanged(TimePicker  view, int hourOfDay, int minute) {
//				if (!timeChanged) {
//					hours.setCurrentItem(hourOfDay, true);
//					mins.setCurrentItem(minute, true);
//				}
//			}
//		});
	  
	}

	
	@Override
	protected void commitDateTime() {

		Intent intent = new Intent();
		intent.putExtra("month", month.getCurrentItem());
		intent.putExtra("day", day.getCurrentItem()+1);
		//intent.put
		intent.putExtra("format", forSendMatDate(calendar.get(Calendar.YEAR), month.getCurrentItem(), day.getCurrentItem()+1)+
				forSendMatTime(hours.getCurrentItem(), mins.getCurrentItem()));
		
			intent.putExtra("time", forMatDate(month.getCurrentItem(), day.getCurrentItem()+1)+
					forMatTime(hours.getCurrentItem(), mins.getCurrentItem())
					);
		
		
			setResult(RESULT_OK,intent);
			finish();
	
	
	}
	
	
	 protected String forSendMatTime(int hour, int min) {
			return " "+getTimeFormat(hour) + ":" + getTimeFormat(min) + ":00";
		}

	    protected String forMatTime(int hour, int min) {
			return " "+getTimeFormat(0 + hour) + "时" + getTimeFormat(min) + "分";
		}
	
	/**
	 * Adds changing listener for wheel that updates the wheel label
	 * @param wheel the wheel
	 * @param label the wheel label
	 */
	private void addChangingListener(final WheelView wheel, final String label) {
		wheel.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				//wheel.setLabel(newValue != 1 ? label + "s" : label);
			}
		});
	}
}
