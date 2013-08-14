package com.treeapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.treeapp.RegisterBasicFragment;
import com.treeapp.RegisterPictureFragment;

public class TestAdapter extends FragmentPagerAdapter {
	
	private String [] titles = {"...", "..."};
	private RegisterBasicFragment mRegisterBasicFragment = 
			new RegisterBasicFragment();
	private RegisterPictureFragment mRegisterPictureFragment =
			new RegisterPictureFragment();
	
	public TestAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case 0:
			return mRegisterBasicFragment;
		case 1:
			return mRegisterPictureFragment;
		}
		return null;
	}

	@Override
	public int getCount() {
		return titles.length;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		return	titles [position];
	}
	
	public RegisterBasicFragment getRegisterBasicFragment() {
		return mRegisterBasicFragment;
	}

	public RegisterPictureFragment getRegisterPictureFragment() {
		return mRegisterPictureFragment;
	}

}
