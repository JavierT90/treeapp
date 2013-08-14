package com.treeapp;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.astuetz.viewpager.extensions.PagerSlidingTabStrip;
import com.treeapp.adapters.TestAdapter;

public class RegisterActivity extends SherlockFragmentActivity implements
		OnPageChangeListener {

	private RegisterBasicFragment mRegisterBasicFragment;
	private RegisterPictureFragment mRegisterPictureFragment;
	public static ViewPager mViewPager;
	private PagerSlidingTabStrip mPagerSlidingTabStrip;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		setContentView(R.layout.register_page);

		// Set the pager with an adapter
		mViewPager = (ViewPager) findViewById(R.id.viewPager);
		TestAdapter adapter = new TestAdapter(getSupportFragmentManager());
		mRegisterBasicFragment = adapter.getRegisterBasicFragment();
		mRegisterPictureFragment = adapter.getRegisterPictureFragment();
		mViewPager.setAdapter(adapter);
		// Bind the widget to the adapter
		mPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		mPagerSlidingTabStrip.setOnPageChangeListener(this);
		mPagerSlidingTabStrip.setViewPager(mViewPager);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub  
//		Log.d("TAG", "arg0: " + arg0);
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
//		Log.d("TAG", "arg0: " + arg0 + " - arg1: " + arg1 + " - arg2: " + arg2);
	}

	@Override
	public void onPageSelected(int position) {
		if(position == 0) {
			// restaurate errors
			mRegisterBasicFragment.restErrors();
		} else if (position == 1){
			// quit errors
			mRegisterBasicFragment.clearErrors();
		}
	}

}
