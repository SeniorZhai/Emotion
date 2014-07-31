package com.zoe.emotion;

import java.util.ArrayList;
import java.util.List;

import com.zoe.emotion.view.EmoticonsEditText;
import com.zoe.emotion.view.adapter.EmoViewPagerAdapter;
import com.zoe.emotion.view.adapter.EmoteAdapter;
import com.zoe.emotion.view.model.FaceText;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Button btn_chat_emo, btn_chat_keyboard, btn_chat_send;
	private EmoticonsEditText edit_user_comment;
	private LinearLayout layout_emo;
	private ViewPager pager_emo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_bottom_bar);
		btn_chat_emo = (Button) findViewById(R.id.btn_chat_emo);
		btn_chat_keyboard = (Button) findViewById(R.id.btn_chat_keyboard);
		btn_chat_send = (Button) findViewById(R.id.btn_chat_send);
		layout_emo = (LinearLayout) findViewById(R.id.layout_emo);
		edit_user_comment = (EmoticonsEditText) findViewById(R.id.edit_user_comment);
		initEmoView();
	}

	List<FaceText> emos;

	private void initEmoView() {
		pager_emo = (ViewPager) findViewById(R.id.pager_emo);
		emos = EmoticonsEditText.faceTexts;
		List<View> views = new ArrayList<View>();
		// 分为两页
		for (int i = 0; i < 2; ++i) {
			views.add(getGridView(i));
		}
		pager_emo.setAdapter(new EmoViewPagerAdapter(views));
	}

	public void toAction(View view) {
		switch (view.getId()) {
		case R.id.btn_chat_emo:
			btn_chat_keyboard.setVisibility(View.VISIBLE);
			btn_chat_emo.setVisibility(View.GONE);
			layout_emo.setVisibility(View.VISIBLE);
			break;
		case R.id.btn_chat_keyboard:
			btn_chat_keyboard.setVisibility(View.GONE);
			btn_chat_emo.setVisibility(View.VISIBLE);
			layout_emo.setVisibility(View.GONE);
			break;
		case R.id.btn_chat_send:
			Toast.makeText(this, edit_user_comment.getText(), Toast.LENGTH_LONG)
					.show();
		}
	}

	private View getGridView(final int i) {
		View view = View.inflate(this, R.layout.include_emo_gridview, null);
		GridView gridview = (GridView) view.findViewById(R.id.gridview);
		List<FaceText> list = new ArrayList<FaceText>();
		if (i == 0) {
			list.addAll(emos.subList(0, 21));
		} else if (i == 1) {
			list.addAll(emos.subList(21, emos.size()));
		}
		final EmoteAdapter gridAdapter = new EmoteAdapter(MainActivity.this,
				list);
		gridview.setAdapter(gridAdapter);
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				FaceText name = (FaceText) gridAdapter.getItem(position);
				String key = name.text.toString();
				try {
					if (edit_user_comment != null && !TextUtils.isEmpty(key)) {
						int start = edit_user_comment.getSelectionStart();
						CharSequence content = edit_user_comment.getText()
								.insert(start, key);
						edit_user_comment.setText(content);
						// 定位光标位置
						CharSequence info = edit_user_comment.getText();
						if (info instanceof Spannable) {
							Spannable spanText = (Spannable) info;
							Selection.setSelection(spanText,
									start + key.length());
						}
					}
				} catch (Exception e) {

				}

			}
		});
		return view;
	}

	// 显示软键盘
	public void showSoftInputView() {
		if (getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (getCurrentFocus() != null)
				((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
						.showSoftInput(edit_user_comment, 0);
		}
	}

	// 隐藏软键盘
	public void hideSoftInputView() {
		InputMethodManager manager = ((InputMethodManager) this
				.getSystemService(Activity.INPUT_METHOD_SERVICE));
		if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (getCurrentFocus() != null)
				manager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

}
