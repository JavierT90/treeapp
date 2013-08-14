package com.treeapp.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.treeapp.R;
import com.treeapp.RegisterPictureFragment;
import com.treeapp.objects.Status;

public class SelectStatusAdapter extends ArrayAdapter<Status> {

	private ArrayList<Status> list;
	private Activity context;
	private RegisterPictureFragment fragment;

	public SelectStatusAdapter(ArrayList<Status> list, RegisterPictureFragment fragment) {
		super(fragment.getActivity(), R.layout.status_list_item, list);

		this.list = list;
		this.context = fragment.getActivity();
		this.fragment = fragment;
	}

	@Override
	public View getView(final int index, View convertView, ViewGroup parent) {
		View view = null;
		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.status_list_item, null);
			PlaceHolder placeHolder = new PlaceHolder();
			placeHolder.mImageButton = (ImageButton) view.findViewById(R.id.IMBStatus);
			placeHolder.mTextView = (TextView) view.findViewById(R.id.TXTStatus);
			view.setTag(placeHolder);
		} else {
			view = convertView;
		}
		PlaceHolder holder = (PlaceHolder) view.getTag();
		holder.mImageButton.setBackgroundResource(list.get(index).getSourceImage());
		holder.mTextView.setText(list.get(index).getStatus());
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				fragment.setStatus(index);
			}
		});
		return view;
	}

	private class PlaceHolder {
		ImageButton mImageButton;
		TextView mTextView;
	}
}
