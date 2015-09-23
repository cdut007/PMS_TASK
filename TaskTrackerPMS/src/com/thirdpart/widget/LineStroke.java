package com.thirdpart.widget;

import android.R.integer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

public class LineStroke extends View{

	public LineStroke(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		setLayerType(View.LAYER_TYPE_SOFTWARE, null);
	}
	
	 @Override      
	    protected void onDraw(Canvas canvas) {      
	        // TODO Auto-generated method stub      
	        super.onDraw(canvas);              
	        Paint paint = new Paint();
	        paint.setStyle(Paint.Style.STROKE);
	        paint.setColor(Color.rgb(0x00, 0x8b, 0x00));
	        int w = getWidth();
	        int sepcW = 8;
	        int count = 2 * w /sepcW;
	        for (int i = 0; i < count; i+=2) {
	        	int end = (i+1) * sepcW;
	        	if (end<=w) {
	        	//	canvas.restore();
	        		canvas.drawLine( i*sepcW,0, end, 1, paint);
	        	//	canvas.save();
				}else {
					break;
				}
	        	
			}
	    }   
	

}
