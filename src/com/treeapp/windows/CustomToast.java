package com.treeapp.windows;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.treeapp.R;

public class CustomToast {

	public static Toast makeInfoText(Activity context, String text, int duration) {
		return makeText(context, text, R.drawable.cust_toast_info,
				R.color.info, duration);
	}

	public static Toast makeWarningText(Activity context, String text,
			int duration) {
		return makeText(context, text, R.drawable.cust_toast_warnig,
				R.color.warning, duration);
	}

	public static Toast makeErrorText(Activity context, String text,
			int duration) {
		return makeText(context, text, R.drawable.cust_toast_error,
				R.color.error, duration);
	}

	private static Toast makeText(Activity context, String text, int image,
			int colorText, int duration) {
		Toast toast = new Toast(context);
		View view = context.getLayoutInflater().inflate(
				R.layout.cust_toast_layout, null);
		view.setBackgroundResource(image);
		((TextView) view.findViewById(R.id.textView2)).setText(text);
		((TextView) view.findViewById(R.id.textView2)).setTextColor(context
				.getResources().getColor(colorText));
		toast.setView(view);
		toast.setDuration(duration);
		return toast;
	}
}
