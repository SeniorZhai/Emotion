package com.zoe.emotion.view;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zoe.emotion.view.model.FaceText;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

public class EmoticonsEditText extends EditText {
	public static List<FaceText> faceTexts = new ArrayList<FaceText>();
	static {
		faceTexts.add(new FaceText("\\ue056"));
		faceTexts.add(new FaceText("\\ue057"));
		faceTexts.add(new FaceText("\\ue058"));
		faceTexts.add(new FaceText("\\ue059"));
		faceTexts.add(new FaceText("\\ue105"));
		faceTexts.add(new FaceText("\\ue106"));
		faceTexts.add(new FaceText("\\ue107"));
		faceTexts.add(new FaceText("\\ue108"));
		faceTexts.add(new FaceText("\\ue401"));
		faceTexts.add(new FaceText("\\ue402"));
		faceTexts.add(new FaceText("\\ue403"));
		faceTexts.add(new FaceText("\\ue404"));
		faceTexts.add(new FaceText("\\ue405"));
		faceTexts.add(new FaceText("\\ue406"));
		faceTexts.add(new FaceText("\\ue407"));
		faceTexts.add(new FaceText("\\ue408"));
		faceTexts.add(new FaceText("\\ue409"));
		faceTexts.add(new FaceText("\\ue40a"));
		faceTexts.add(new FaceText("\\ue40b"));
		faceTexts.add(new FaceText("\\ue40d"));
		faceTexts.add(new FaceText("\\ue40e"));
		faceTexts.add(new FaceText("\\ue40f"));
		faceTexts.add(new FaceText("\\ue410"));
		faceTexts.add(new FaceText("\\ue411"));
		faceTexts.add(new FaceText("\\ue412"));
		faceTexts.add(new FaceText("\\ue413"));
		faceTexts.add(new FaceText("\\ue414"));
		faceTexts.add(new FaceText("\\ue415"));
		faceTexts.add(new FaceText("\\ue416"));
		faceTexts.add(new FaceText("\\ue417"));
		faceTexts.add(new FaceText("\\ue418"));
		faceTexts.add(new FaceText("\\ue41f"));
		faceTexts.add(new FaceText("\\ue00e"));
		faceTexts.add(new FaceText("\\ue421"));
	}
	public EmoticonsEditText(Context context) {
		super(context);
	}

	public EmoticonsEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public EmoticonsEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void setText(CharSequence text, BufferType type) {
		if (!TextUtils.isEmpty(text)) {
			super.setText(replace(text.toString()), type);
		} else {
			super.setText(text, type);
		}
	}
	
	private Pattern buildPattern() {
		// 匹配 \\uea09 这类的正则表达式
		return Pattern.compile("\\\\ue[a-z0-9]{3}", Pattern.CASE_INSENSITIVE);
	}
	
	private CharSequence replace(String text) {
		try {
			SpannableString spannableString = new SpannableString(text);
			int start = 0;
			Pattern pattern = buildPattern();
			Matcher matcher = pattern.matcher(text);
			while (matcher.find()) {
				// 获取匹配的字符
				String faceText = matcher.group();
				// 去除第一位
				String key = faceText.substring(1);
				BitmapFactory.Options options = new BitmapFactory.Options();
				// 根据字符找到图片资源
				Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(),
						getContext().getResources().getIdentifier(key, "drawable", getContext().getPackageName()), options);
				ImageSpan imageSpan = new ImageSpan(getContext(), bitmap);
				// 找到整个字符串匹配该表情字符串的起始
				int startIndex = text.indexOf(faceText, start);
				// 找到整个字符串匹配该表情字符串的截止
				int endIndex = startIndex + faceText.length();
				// 使用图案替换
				if (startIndex >= 0)
					spannableString.setSpan(imageSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				start = (endIndex - 1);
			}
			return spannableString;
		} catch (Exception e) {
			return text;
		}
	}
}
