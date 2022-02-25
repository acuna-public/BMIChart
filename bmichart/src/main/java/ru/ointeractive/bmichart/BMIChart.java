	package ru.ointeractive.bmichart;
	
	import android.content.Context;
	import android.graphics.Color;
	import android.support.v7.app.AppCompatDelegate;
	import android.util.AttributeSet;
	import android.view.View;
	import android.widget.ImageView;
	import android.widget.TextView;
	
	import java.math.BigDecimal;
	import java.math.RoundingMode;
	
	import ru.ointeractive.androdesign.widget.LinearLayout;
	import ru.ointeractive.androdesign.widget.RelativeLayout;
	import ru.ointeractive.andromeda.graphic.Graphic;
	import upl.core.Log;
	import upl.util.ArrayList;
	import upl.util.List;
	
	public class BMIChart extends LinearLayout {
		
		protected List<Value> items = new ArrayList<> ();
		
		public float value;
		public int layoutWidth;
		
		protected static class Value {
			
			public float value;
			public int length, width, padding;
			
			protected int color;
			
			public Value setColor (int color) {
				
				this.color = color;
				
				return this;
				
			}
			
			public Value setColor (String color) {
				return setColor (Color.parseColor (color));
			}
			
		}
		
		public BMIChart (Context context) {
			this (context, null);
		}
		
		public BMIChart (Context context, AttributeSet attrs) {
			this (context, attrs, 0);
		}
		
		public BMIChart (Context context, AttributeSet attrs, int defStyle) {
			super (context, attrs, defStyle);
		}
		
		public BMIChart addItem (Value value) {
			
			items.put (value);
			
			return this;
			
		}
		
		public BMIChart setValues (float weight, float height) {
			
			value = (weight / (float) Math.pow (height / 100, 2));
			return this;
			
		}
		
		public String[] getColors () {
			return getContext ().getResources ().getStringArray (R.array.colors);
		}
		
		public BMIChart build () {
			
			if (items.length () == 0) {
				
				String[] colors = getColors ();
				
				Value value = new Value ();
				
				value.value = 15f;
				value.setColor (colors[0]);
				value.width = 8;
				
				addItem (value);
				
				value = new Value ();
				
				value.value = 16f;
				value.setColor (colors[1]);
				value.width = 12;
				value.padding = 4;
				
				addItem (value);
				
				value = new Value ();
				
				value.value = 18.5f;
				value.setColor (colors[2]);
				value.width = 25;
				
				addItem (value);
				
				value = new Value ();
				
				value.value = 25f;
				value.setColor (colors[3]);
				value.width = 18;
				
				addItem (value);
				
				value = new Value ();
				
				value.value = 30f;
				value.setColor (colors[4]);
				value.width = 18;
				
				addItem (value);
				
				value = new Value ();
				
				value.value = 35f;
				value.setColor (colors[5]);
				value.width = 18;
				
				addItem (value);
				
				value = new Value ();
				
				value.value = 40f;
				
				addItem (value);
				
			}
			
			setOrientation (LinearLayout.VERTICAL);
			
			AppCompatDelegate.setCompatVectorFromResourcesEnabled (true);
			
			/*setListener (new Listener () {
				
				@Override
				public void onView (LinearLayout layout) {
					build (layout.getMeasuredWidth ());
				}
				
			});*/
			
			build (layoutWidth);
			
			return this;
			
		}
		
		protected void build (int layoutWidth) {
			
			removeAllViews ();
			
			LinearLayout ll1 = new LinearLayout (getContext ());
			LinearLayout ll2 = new LinearLayout (getContext ());
			
			RelativeLayout ll3 = new RelativeLayout (getContext ());
			
			ll1.setOrientation (LinearLayout.HORIZONTAL);
			
			ll1.setLayoutParams (new LayoutParams (LayoutParams.MATCH_PARENT, 15));
			
			ll1.setPadding (0, 8, 0, 0);
			
			ll2.setOrientation (LinearLayout.HORIZONTAL);
			
			ll2.setLayoutParams (new LayoutParams (LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			
			for (Value value : items) {
				
				int width = (value.width * layoutWidth) / 100;
				
				if (value.color != 0) {
					
					View view = new View (getContext ());
					
					view.setBackgroundColor (value.color);
					
					view.setLayoutParams (new LayoutParams (width, LayoutParams.MATCH_PARENT));
					
					ll1.addView (view);
					
					view = new View (getContext ());
					
					view.setLayoutParams (new LayoutParams (4, LayoutParams.MATCH_PARENT));
					
					ll1.addView (view);
					
					TextView textView = new TextView (getContext ());
					
					textView.setText (value.value + "");
					textView.setTextColor (getResources ().getColor (R.color.light_gray));
					textView.setTextSize (13);
					
					textView.setLayoutParams (new LayoutParams (width - value.padding, LayoutParams.WRAP_CONTENT));
					
					ll2.addView (textView);
					
				}
				
			}
			
			ll3.addView (ll1);
			
			float firstValue = items.get (0).value;
			float lastValue = items.get (items.endKey ()).value;
			
			float val = (lastValue - firstValue);
			val = (val * value) / lastValue;
			
			int width = (int) ((val * layoutWidth) / 40);
			
			TextView textView = new TextView (getContext ());
			
			textView.setText (String.valueOf (new BigDecimal (value).setScale (2, RoundingMode.HALF_EVEN).doubleValue ()));
			textView.setTextColor (getResources ().getColor (R.color.black));
			textView.setTextSize (16);
			textView.setPadding (width, 0, 0, 0);
			
			addView (textView);
			
			ImageView imageView = new ImageView (getContext ());
			
			imageView.setImageDrawable (Graphic.toDrawable (getContext (), R.drawable.ic_cursor));
			imageView.setLayoutParams (new LayoutParams (LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			imageView.setPadding (width + 20, 0, 0, 0);
			
			ll3.addView (imageView);
			
			addView (ll3);
			
			addView (ll2);
			
		}
		
		public int getLevel () {
			
			int level = 0;
			
			for (Value value : items)
				if (value.value < this.value)
					level++;
				else
					break;
			
			return level;
			
		}
		
	}