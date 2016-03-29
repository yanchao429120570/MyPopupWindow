package com.example.mypopupwindow;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button bt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bt = (Button) findViewById(R.id.bt);
	}

	public void click(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.bt:
			// showPopWindow();
			showCircleViewPopupWindow();
			break;
		}
	}

	LayoutInflater layoutInflater;
	PopupWindow popupWindow;

	public void showPopWindow() {
		layoutInflater = LayoutInflater.from(this);
		View view = layoutInflater.inflate(R.layout.popwindow_layout, null);
		view.findViewById(R.id.popupBt).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Log.i("TAG", "popupWindow����İ�ť�������");
					}
				});
		popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		popupWindow.setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return false;
				// �����������true�Ļ���touch�¼���������
				// ���غ� PopupWindow��onTouchEvent�������ã���������ⲿ�����޷�dismiss
			}
		});
		// popupWindow.setTouchable(true);
		popupWindow.setOutsideTouchable(true);
		// ���������PopupWindow�ı����������ǵ���ⲿ������Back�����޷�dismiss����
		// �Ҿ���������API��һ��bug
		popupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.hrbj));
		// popupWindow.showAsDropDown(bt);

		View parentView = ((Activity) this).getWindow().findViewById(
				Window.ID_ANDROID_CONTENT);
		popupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);
	}

	View circleView;

	public void showCircleViewPopupWindow() {
		layoutInflater = LayoutInflater.from(this);
		View view = layoutInflater.inflate(R.layout.circleview_layout, null);
		circleView = view.findViewById(R.id.circleView);
		popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		//��תһȦ��Ҫ���ʱ��
		rotateAnimation.setDuration(2000);
		//RESTART��ʾ�ӽ���������ʼ,REVERSE��ʾ����ȥ
		rotateAnimation.setRepeatMode(Animation.RESTART);
		//-1��ʾһֱѭ��
		rotateAnimation.setRepeatCount(-1);
		//interpolator��ʾ�仯�ʣ������������ٶȡ�һ���岹���ԣ����Խ�����Ч������Ϊ���٣����٣������������ȡ�Ĭ��Ϊ��ʼ�ͽ������м��
		rotateAnimation.setInterpolator(new LinearInterpolator());
		circleView.startAnimation(rotateAnimation);
		
		View parentView = ((Activity) this).getWindow().findViewById(
				Window.ID_ANDROID_CONTENT);
		popupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (popupWindow.isShowing()) {
				circleView.clearAnimation();
				popupWindow.dismiss();
				Log.i("TAG", "ȡ��popupWindow");
			}else{
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
