package com.thirdpart.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;


public class TouchImage extends ImageView{

	public TouchImage(Context context, AttributeSet attrs) {
		super(context, attrs);
		setScaleType(ScaleType.CENTER_INSIDE);
		// TODO Auto-generated constructor stub
		buttonEffect(this);
	}
	
	
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public static void ViewEffect(View bubbleView) {
	
		StateListDrawable states = new StateListDrawable(){
			@Override
			protected boolean onStateChange(int[] stateSet) {
				
				 boolean isStatePressedInArray = false;
				 for (int state : stateSet) {
			            if (state == android.R.attr.state_selected) {
			            	   isStatePressedInArray = true;
			            }
			            
			            if (state == android.R.attr.state_pressed) {
			            	   isStatePressedInArray = true;
			            }
			        }
				 
				 if (isStatePressedInArray) {
					  super.setColorFilter(0x99999999,Mode.MULTIPLY);
			        } else {
			            super.clearColorFilter();
			        }
				 
				return super.onStateChange(stateSet);
			}
			@Override
		    public boolean isStateful() {
		        return true;
		    }
		};
		
			if (bubbleView.getBackground()!=null) {
				states.addState(new int[] {android.R.attr.state_pressed}, bubbleView.getBackground());	   
	    		states.addState(new int[] {}, bubbleView.getBackground());	    		
	    		if (Build.VERSION.SDK_INT>=16) {
	    			bubbleView.setBackground(states);
				}else {
					bubbleView.setBackgroundDrawable(states);
				}
			}
		
	
	//states.addState(new int[] {}, r.getDrawable(R.drawable.normal));
		
	}
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public static void buttonEffect(View bubbleView) {
	
		StateListDrawable states = new StateListDrawable(){
			@Override
			protected boolean onStateChange(int[] stateSet) {
				
				 boolean isStatePressedInArray = false;
				 for (int state : stateSet) {
			            if (state == android.R.attr.state_selected) {
			            	   isStatePressedInArray = true;
			            }
			            
			            if (state == android.R.attr.state_pressed) {
			            	   isStatePressedInArray = true;
			            }
			        }
				 
				 if (isStatePressedInArray) {
					  super.setColorFilter(0x99999999,Mode.MULTIPLY);
			        } else {
			            super.clearColorFilter();
			        }
				 
				return super.onStateChange(stateSet);
			}
			@Override
		    public boolean isStateful() {
		        return true;
		    }
		};
		
		if (bubbleView instanceof ImageView) {
			ImageView imageView = (ImageView) bubbleView;
			if ((imageView).getDrawable()!=null) {
				states.addState(new int[] {android.R.attr.state_pressed},(imageView).getDrawable());	   
	    		states.addState(new int[] {},(imageView).getDrawable());	   
	    			imageView.setImageDrawable(states);
				
			}
		}else {
			if (bubbleView.getBackground()!=null) {
				states.addState(new int[] {android.R.attr.state_pressed}, bubbleView.getBackground());	   
	    		states.addState(new int[] {}, bubbleView.getBackground());	    		
	    		if (Build.VERSION.SDK_INT>=16) {
	    			bubbleView.setBackground(states);
				}else {
					bubbleView.setBackgroundDrawable(states);
				}
			}
		}
	
	//states.addState(new int[] {}, r.getDrawable(R.drawable.normal));
		
	}
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public static void ImageViewEffect(ImageView bubbleView,Bitmap bitmap) {
		
		StateListDrawable states = new StateListDrawable(){
			@Override
			protected boolean onStateChange(int[] stateSet) {
				
				 boolean isStatePressedInArray = false;
				 for (int state : stateSet) {
			            if (state == android.R.attr.state_selected) {
			            	   isStatePressedInArray = true;
			            }
			        }
				 
				 if (isStatePressedInArray) {
					  super.setColorFilter(0x99999999,Mode.MULTIPLY);
			        } else {
			            super.clearColorFilter();
			        }
				 
				return super.onStateChange(stateSet);
			}
			@Override
		    public boolean isStateful() {
		        return true;
		    }
		};
   
		states.addState(new int[] {}, new BitmapDrawable(bubbleView.getResources(),bitmap));	    		
		bubbleView.setImageDrawable(states);
	//states.addState(new int[] {}, r.getDrawable(R.drawable.normal));
		
	}
}
