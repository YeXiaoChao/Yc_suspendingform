package com.yc.yc_suspendingform;

import com.yc.yc_floatingform.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class DesktopLayout extends LinearLayout {

	public DesktopLayout(Context context) {
		super(context);
		setOrientation(LinearLayout.VERTICAL);// ˮƽ����
		

		//���ÿ��
		this.setLayoutParams( new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		
		View view = LayoutInflater.from(context).inflate(  
                R.layout.desklayout, null); 
		this.addView(view);
	}

}
