package com.yc.yc_suspendingform;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;

import com.yc.yc_floatingform.R;

public class MainActivity extends Activity {
	private WindowManager mWindowManager;
	private WindowManager.LayoutParams mLayout;
	private DesktopLayout mDesktopLayout;
	private long startTime;
	// ������Ļ�Ŀ��
	float x, y;
	int top;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		createWindowManager();
		createDesktopLayout();
		Button btn = (Button) findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showDesk();
			}
		});
	}
	/**
	 * ������������
	 */
	private void createDesktopLayout() {
		mDesktopLayout = new DesktopLayout(this);
		mDesktopLayout.setOnTouchListener(new OnTouchListener() {
			float mTouchStartX;
			float mTouchStartY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// ��ȡ�����Ļ�����꣬������Ļ���Ͻ�Ϊԭ��
				x = event.getRawX();
				y = event.getRawY() - top; // 25��ϵͳ״̬���ĸ߶�
				Log.i("startP", "startX" + mTouchStartX + "====startY"
						+ mTouchStartY);
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// ��ȡ���View�����꣬���Դ�View���Ͻ�Ϊԭ��
					mTouchStartX = event.getX();
					mTouchStartY = event.getY();
					Log.i("startP", "startX" + mTouchStartX + "====startY"
							+ mTouchStartY);
					long end = System.currentTimeMillis() - startTime;
					// ˫���ļ���� 200ms �� 500ms ֮��
					if (end > 200 && end < 500) {
						closeDesk();
					}
					startTime = System.currentTimeMillis();
					break;
				case MotionEvent.ACTION_MOVE:
					// ���¸�������λ�ò���
					mLayout.x = (int) (x - mTouchStartX);
					mLayout.y = (int) (y - mTouchStartY);
					mWindowManager.updateViewLayout(v, mLayout);
					break;
				case MotionEvent.ACTION_UP:

					// ���¸�������λ�ò���
					mLayout.x = (int) (x - mTouchStartX);
					mLayout.y = (int) (y - mTouchStartY);
					mWindowManager.updateViewLayout(v, mLayout);

					// �����ڴ˼�¼���һ�ε�λ��

					mTouchStartX = mTouchStartY = 0;
					break;
				}
				return true;
			}
		});
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		Rect rect = new Rect();
		// /ȡ��������ͼ����,ע�⣬�����Ҫ���ñ�����ʽ�������������ڱ�����ʽ֮�󣬷�������
		getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
		top = rect.top;//״̬���ĸ߶ȣ�����rect.height,rect.width�ֱ���ϵͳ�ĸ߶ȵĿ��

		Log.i("top",""+top);
	}

	/**
	 * ��ʾDesktopLayout
	 */
	private void showDesk() {
		mWindowManager.addView(mDesktopLayout, mLayout);
		finish();
	}

	/**
	 * �ر�DesktopLayout
	 */
	private void closeDesk() {
		mWindowManager.removeView(mDesktopLayout);
		finish();
	}

	/**
	 * ����WindowManager
	 */
	private void createWindowManager() {
		// ȡ��ϵͳ����
		mWindowManager = (WindowManager) getApplicationContext()
				.getSystemService("window");

		// ����Ĳ�����ʽ
		mLayout = new WindowManager.LayoutParams();

		// ���ô�����ʾ���͡���TYPE_SYSTEM_ALERT(ϵͳ��ʾ)
		mLayout.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

		// ���ô��役�㼰������
		// FLAG_NOT_FOCUSABLE(���ܻ�ð������뽹��)
		mLayout.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

		// ������ʾ��ģʽ
		mLayout.format = PixelFormat.RGBA_8888;

		// ���ö���ķ���
		mLayout.gravity = Gravity.TOP | Gravity.LEFT;

		// ���ô����Ⱥ͸߶�
		mLayout.width = WindowManager.LayoutParams.WRAP_CONTENT;
		mLayout.height = WindowManager.LayoutParams.WRAP_CONTENT;

	}

}
