package com.treeapp.windows;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.treeapp.R;

public class DialogButtons extends Dialog {

	private LinearLayout buttonsContent;
	private LayoutInflater inflator;
	private Activity activity;
	
	public DialogButtons (Activity activity, String title, String message) {
		super (activity, R.style.CustomDialogTheme);
		initialize(activity, title, message);
	}
	
	public DialogButtons (Activity activity, int theme, String title, String message) {
		super (activity, theme);
		initialize(activity, title, message);
	}
	
	private void initialize(Activity activity, String title, String message) {
		// init 
		setContentView(R.layout.dialog_buttons_content);
		setCancelable(false);
		setCanceledOnTouchOutside(false);
		
		// get content of buttons
		buttonsContent = (LinearLayout) findViewById(R.id.LNLButtonsContent);
		((TextView) findViewById(R.id.TXTTitle)).setText(title);
		((TextView) findViewById(R.id.TXTMessage)).setText(message);
		inflator = activity.getLayoutInflater();
		this.activity = activity;
	}
	
	/**
	 * Set a button with text
	 * @param message text 
	 * @return reference to button
	 */
	public Button setButton(String message) {
		Button mButton = addButton(R.layout.possitive_button, message);
		return mButton;
	}
	
	/**
	 * Set a button with text, from a resource
	 * @param message text
	 * @param resource for inflate
	 * @return reference to button
	 */
	public Button setButton(String message, int resource) {
		Button mButton = addButton(resource, message);
		return mButton;
	}
	
	/**
	 * Set an imageButton with drawable
	 * @param drawable the image
	 * @return reference to ImageButton
	 */
	public ImageButton setImageButton(Drawable drawable) {
		ImageButton mImageButton = addImageButton(drawable, R.layout.possitive_image_button);
		return mImageButton;
	}
	
	/**
	 * Set an imageButton with drawable
	 * @param drawable image
	 * @param resource source to inflate
	 * @return the reference
	 */
	public ImageButton setImageButton(Drawable drawable, int resource) {
		ImageButton mImageButton = addImageButton(drawable, resource);
		return mImageButton;
	}
	
	/**
	 * set an imageButton with image
	 * @param resource id of image
	 * @return reference
	 */
	public ImageButton setImageButton(int resource) {
		ImageButton mImageButton = addImageButton(activity.getResources().getDrawable(resource), 
				R.layout.possitive_image_button);
		return mImageButton;
	}
	
	/**
	 * set an imageButton with icon
	 * @param resource resource to inflate
	 * @param icon id of icon 
	 * @return reference
	 */
	public ImageButton setImageButton(int resource, int icon) {
		ImageButton mImageButton = addImageButton(activity.getResources().getDrawable(icon), 
				resource);
		return mImageButton;
	}
	
	/// fix the layout params
	private Button addButton(int id, String msj) {
		Button mButton = (Button) inflator.inflate(id, null);
		buttonsContent.addView(mButton);
		LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		p.weight = 1;
		mButton.setLayoutParams(p);
		mButton.setText(msj);
		return mButton;
	}
	
	/// fix the layout params
	private ImageButton addImageButton(Drawable drawable, int resource) {
		ImageButton mImageButon = (ImageButton) inflator.inflate(resource, null);
		buttonsContent.addView(mImageButon);
		LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		mImageButon.setLayoutParams(p);
		mImageButon.setImageDrawable(drawable);
		return mImageButon;
	}
	
	public Button getCloseButton() {
		return (Button) findViewById(R.id.IMBClose);
	}
	
	public TextView getTextTitle() {
		return (TextView) findViewById(R.id.TXTTitle);
	}
	
	public TextView getTextMessage() {
		return (TextView) findViewById(R.id.TXTMessage);
	}
	
}
