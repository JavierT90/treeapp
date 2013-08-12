package com.treeapp;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;

public class TreeApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		
		Parse.initialize(getApplicationContext(), 
				getResources().getString(R.string.ParseApplicationId), 
				getResources().getString(R.string.ParseClientKey));
		
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
		//enable public write and read
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
        
    	//Initialize Facebook Utils
        ParseFacebookUtils.initialize(getResources().getString(R.string.FbAppId));
        
        //Initialize Twitter Utils
        ParseTwitterUtils.initialize(getResources().getString(R.string.TwConsumerKey),
        		getResources().getString(R.string.TwConsumerSecret));

	}
}
