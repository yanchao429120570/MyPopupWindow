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
						Log.i("TAG", "popupWindow上面的按钮被点击了");
					}
				});
		popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		popupWindow.setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return false;
				// 这里如果返回true的话，touch事件将被拦截
				// 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
			}
		});
		// popupWindow.setTouchable(true);
		popupWindow.setOutsideTouchable(true);
		// 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
		// 我觉得这里是API的一个bug
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
		//旋转一圈需要多久时间
		rotateAnimation.setDuration(2000);
		//RESTART表示从结束继续开始,REVERSE表示反回去
		rotateAnimation.setRepeatMode(Animation.RESTART);
		//-1表示一直循环
		rotateAnimation.setRepeatCount(-1);
		//interpolator表示变化率，但不是运行速度。一个插补属性，可以将动画效果设置为加速，减速，反复，反弹等。默认为开始和结束慢中间快
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
				Log.i("TAG", "取消popupWindow");
			}else{
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
