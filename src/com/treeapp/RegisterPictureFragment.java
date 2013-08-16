package com.treeapp;

import java.util.ArrayList;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.treeapp.adapters.SelectStatusAdapter;
import com.treeapp.objects.Status;
import com.treeapp.windows.CustomToast;
import com.treeapp.windows.DialogButtons;

public class RegisterPictureFragment extends Fragment implements
		OnClickListener {

	private EditText TXTStatus;
	private ImageButton IMBStatus;
	private ImageView IMVProfile;
	private Button BTNNext;
	private View mView;
	private ArrayList<Status> list;
	private Dialog mDialog;
	private DialogButtons mButtons;
	// index of status in the ArrayList, default, state busy
	private int index = 2;
	private static final String TAG_index = "index";
	// if the dialog is showed
	private boolean show;
	private static final String TAG_show = "show";
	// if the dialog message showed
	private boolean messageShow;
	private static final String TAG_messageShow = "messageShow";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// the Activity only transforms
		if (savedInstanceState != null) {
			index = savedInstanceState.getInt(TAG_index);
			show = savedInstanceState.getBoolean(TAG_show);
			messageShow = savedInstanceState.getBoolean(TAG_messageShow);
		}
		// create list
		list = new ArrayList<Status>();
		list.add(new Status(getActivity().getResources().getString(
				R.string.Online), R.drawable.ic_status_online));
		list.add(new Status(getActivity().getResources().getString(
				R.string.Away), R.drawable.ic_status_away));
		list.add(new Status(getActivity().getResources().getString(
				R.string.Busy), R.drawable.is_status_busy));
		list.add(new Status(getActivity().getResources().getString(
				R.string.Invisible), R.drawable.is_status_invisible));
		list.add(new Status(getActivity().getResources().getString(
				R.string.Offline), R.drawable.ic_status_offline));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.register_picture_activity, null);
		initialize();
		setStatus(index);
		return mView;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (show)
			selectStatus();
		if (messageShow)
			getProfileImage();
	}

	private void initialize() {
		TXTStatus = (EditText) mView.findViewById(R.id.TXTStatus);
		IMBStatus = (ImageButton) mView.findViewById(R.id.IMBStatus);
		IMVProfile = (ImageView) mView.findViewById(R.id.IMVProfile);
		BTNNext = (Button) mView.findViewById(R.id.BTNNext);
		IMBStatus.setOnClickListener(this);
		IMVProfile.setOnClickListener(this);
		BTNNext.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.IMBStatus:
			selectStatus();
			break;
		case R.id.IMVProfile:
			getProfileImage();
			break;
		case R.id.BTNNext:
			nextStep();
			break;
		case R.id.IMBClose:
			mDialog.dismiss();
			show = false;
			break;  
		}
	}

	/**
	 * Set status
	 */
	private void selectStatus() {
		if (mDialog == null) {
			Log.i("WindowManager", "Dialog null");
			mDialog = new Dialog(getActivity(), R.style.CustomDialogTheme);
			mDialog.setContentView(R.layout.dialog_select_status);
			mDialog.setCancelable(false);
			mDialog.setCanceledOnTouchOutside(false);
		} else
			Log.i("WindowManager", "Dialog not null!!");
		show = true;
		mDialog.show();
		ListView mListView = (ListView) mDialog.findViewById(R.id.LTVStatus);
		SelectStatusAdapter adapter = new SelectStatusAdapter(list, this);
		mListView.setAdapter(adapter);
		((Button) mDialog.findViewById(R.id.IMBClose)).setOnClickListener(this);
	}

	/**
	 * get Profile Image
	 */
	private void getProfileImage() {
		mButtons  = new DialogButtons(getActivity(), "Selection method", 
				"That choose the image?");   
		Button bCamera = mButtons.setButton("Camera");
		Button bGalery = mButtons.setButton("Galery");
		bCamera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mButtons.dismiss();
				messageShow = false;
				CustomToast.makeInfoText(getActivity(), "You choose camera", Toast.LENGTH_SHORT).show();
			}
		});
		bGalery.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mButtons.dismiss();
				messageShow = false;
				CustomToast.makeInfoText(getActivity(), "You choose galery", Toast.LENGTH_SHORT).show();
			}
		});
		mButtons.getCloseButton().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mButtons.dismiss();
				messageShow = false;
			}
		});
		messageShow = true;
		mButtons.show();
	}

	/**
	 * continue to the next step
	 */
	private void nextStep() {

	}

	/**
	 * set status, call of ListView adapter
	 * 
	 * @param index
	 *            of status
	 */
	public void setStatus(int index) {
		this.index = index;
		if (mDialog != null) {
			mDialog.dismiss();
			show = false;
		}
		Status status = list.get(index);
		IMBStatus.setImageResource(status.getSourceImage());
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
		if(mDialog != null)
			mDialog.dismiss();
		if(mButtons != null)
			mButtons.dismiss();
	}

	// save the actual status, state, if the dialog is showed and the profile
	// picture

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putInt(TAG_index, index);
		outState.putBoolean(TAG_show, show);
		outState.putBoolean(TAG_messageShow, messageShow);
	}

}
