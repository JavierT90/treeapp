package com.treeapp;

import java.util.ArrayList;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.treeapp.adapters.SelectStatusAdapter;
import com.treeapp.objects.Status;

public class RegisterPictureFragment extends Fragment implements
		OnClickListener, OnItemSelectedListener, OnItemClickListener {

	private EditText TXTStatus;
	private ImageButton IMBStatus;
	private ImageView IMVProfile;
	private Button BTNNext;
	private View mView;
	private ArrayList<Status> list;
	private Dialog mDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// create list
		list = new ArrayList<Status>();
		list.add(new Status(getActivity().getResources().getString(R.string.Online), 
				R.drawable.ic_status_online));
		list.add(new Status(getActivity().getResources().getString(R.string.Away), 
				R.drawable.ic_status_away));
		list.add(new Status(getActivity().getResources().getString(R.string.Busy), 
				R.drawable.is_status_busy));
		list.add(new Status(getActivity().getResources().getString(R.string.Invisible), 
				R.drawable.is_status_invisible));
		list.add(new Status(getActivity().getResources().getString(R.string.Offline), 
				R.drawable.ic_status_offline));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.register_picture_activity, null);
		initialize();
		return mView;
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
		}
	}

	/**
	 * Set status
	 */
	private void selectStatus() {
		mDialog = new Dialog(getActivity(), R.style.CustomDialogTheme);
		mDialog.setContentView(R.layout.status_dialog);
		mDialog.setCancelable(true);
		mDialog.setCanceledOnTouchOutside(false);
		mDialog.show();
		ListView mListView = (ListView) mDialog.findViewById(R.id.LTVStatus);
		mListView.setOnItemSelectedListener(this);
		mListView.setOnItemClickListener(this);
		SelectStatusAdapter adapter = new SelectStatusAdapter(list, this);
		mListView.setAdapter(adapter);
	}

	/**
	 * get Profile Image
	 */
	private void getProfileImage() {

	}

	/**
	 * continue to the next step
	 */
	private void nextStep() {

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		mDialog.dismiss();
		IMBStatus.setBackgroundResource(list.get(position).getSourceImage());
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		mDialog.dismiss();
		IMBStatus.setBackgroundResource(list.get(position).getSourceImage());
	}
	
	/**
	 * set status, call of ListView adapter 
	 * @param index of status
	 */
	public void setStatus(int index) {
		mDialog.dismiss();
		Status status = list.get(index);
		IMBStatus.setImageResource(status.getSourceImage());
	}
}
