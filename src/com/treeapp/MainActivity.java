package com.treeapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";

	private EditText TXTUsername;
	private EditText TXTPassword;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TXTUsername = (EditText) findViewById(R.id.TXTUsername);
		TXTPassword = (EditText) findViewById(R.id.TXTPassword);
	}

	/**
	 * Click on view 
	 * @param v the view
	 */
	public void click(View v) {
		switch (v.getId()) {
		case R.id.BTNFacebookLogin:
			facebookLogin();
			break;
		case R.id.BTNLogin:
			login();
			break;
		case R.id.BTNTwitterLogin:
			twitterLogin();
			break;
		case R.id.TXVForgotPass:
			forgotPass();
			break;
		case R.id.TXVSignUp:
			signUp();
			break;
		}
	}
	
	private void facebookLogin() {
		Log.d(TAG, "facebookLogin");
	}
	
	private void login() {
		Log.d(TAG, "login");
	}
	
	private void twitterLogin() {
		Log.d(TAG, "twitterLogin");
	}
	
	private void forgotPass() {
		Log.d(TAG, "forgotPass");
	}
	
	private void signUp() {
		startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
	}
}
