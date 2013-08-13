package com.treeapp;

import java.util.regex.Pattern;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.treeapp.exceptions.RegisterException;
import com.treeapp.helpers.GlobalHelper;
import com.treeapp.objects.ParseObjects;

public class RegisterActivity extends SherlockActivity {

	private EditText TXTUsername;
	private EditText TXTEmail;
	private EditText TXTCountryCode;
	private EditText TXTPhone;
	private EditText TXTPassword;
	private EditText TXTRetypePass;
	private String GOOGLE_CLASS;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		setContentView(R.layout.registration_activity);

		// google type
		GOOGLE_CLASS = getResources().getString(R.string.GOOGLE_CLASS);
		getViews();

		// get possible email
		Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
		Account[] accounts = AccountManager.get(getApplicationContext())
				.getAccounts();
		for (Account account : accounts) {
			if (emailPattern.matcher(account.name).matches()) {
				if (account.type.equals(GOOGLE_CLASS)) {
					String possibleEmail = account.name;
					TXTEmail.setText(possibleEmail);
					break;
				}
			}
		}

		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String mCountryCode = tm.getSimCountryIso();

		// get country code and set
		// String mCountryCode =
		// getResources().getConfiguration().locale.getCountry();
		if (mCountryCode == null || mCountryCode.equals("")) {
			// no hay country code
		} else {
			TXTPhone.setHint("");
			Log.i("mCountryCode", mCountryCode);
			String cod = GlobalHelper.getCodeCountry(mCountryCode);
			Log.i("cod", cod == null ? " null? wtf" : cod.toString());
			TXTCountryCode.setText(cod);
			TXTCountryCode.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {

				}

				@Override
				public void afterTextChanged(Editable s) {
					if (s != null) {
						Log.d("tag", s.toString());
						if (s.toString().equals(""))
							TXTPhone.setHint(getResources().getString(
									R.string.phone));
						else
							TXTPhone.setHint("");
					} else
						Log.d("tar", "Is null!!");
				}
			});
		}
	}

	// get references
	private void getViews() {
		TXTUsername = (EditText) findViewById(R.id.TXTUsername);
		TXTEmail = (EditText) findViewById(R.id.TXTEmail);
		TXTPhone = (EditText) findViewById(R.id.TXTPhone);
		TXTCountryCode = (EditText) findViewById(R.id.TXTCountryCode);
		TXTPassword = (EditText) findViewById(R.id.TXTPassword);
		TXTRetypePass = (EditText) findViewById(R.id.TXTRetypePass);
	}

	public void click(View view) {
		if (view.getId() == R.id.BTNRegister) {
			// register user. Validate
			if (view.getId() == R.id.BTNRegister) {
				String mUserName = TXTUsername.getText().toString();
				String mEmail = TXTEmail.getText().toString();
				String mCodePhone = TXTCountryCode.getText().toString();
				String mPhone = TXTPhone.getText().toString();
				String mPassword = TXTPassword.getText().toString();
				String mRetypePass = TXTRetypePass.getText().toString();
				
				RegisterException exception = null;
				if ((exception = GlobalHelper.isValidNewUser(this, mUserName, mEmail, mCodePhone,
						mPhone, mPassword, mRetypePass)) == null) {
					final AlertDialog alert = new AlertDialog.Builder(this).setPositiveButton(
							getResources().getString((R.string.Ok)), null).create();
					final ProgressDialog dialog = new ProgressDialog(this);
					dialog.setMessage(getResources().getString(R.string.MsgWaitSignUp));
					dialog.setTitle(getResources().getString(R.string.titleProgressDialogs));
					dialog.setIcon(R.drawable.ic_launcher);
					dialog.setCancelable(false);
					dialog.setCanceledOnTouchOutside(false);
					dialog.show();
					// intent create User in parse
					ParseUser user = new ParseUser();
					user.setEmail(mEmail);
					user.setUsername(mUserName);
					user.setPassword(mPassword);

					user.put(ParseObjects.User.codePhone, mCodePhone);
					user.put(ParseObjects.User.numberPhone, mPhone);
					
					user.signUpInBackground(new SignUpCallback() {
						
						@Override
						public void done(ParseException e) {
							dialog.dismiss();
							if (e == null) {
								Toast.makeText(getApplicationContext(), 
										getResources().getString(R.string.AccountCreated), 
										Toast.LENGTH_LONG).show();
								
							} else {
								alert.setMessage(GlobalHelper.getErrorMsg(e.getCode()));
								alert.setTitle(getResources().getString(
										R.string.titleErrors));
								alert.setIcon(R.drawable.ic_launcher);
								alert.setCancelable(true);
								alert.setCanceledOnTouchOutside(true);
								alert.show();				
							}
						}
					});
				} else {
					switch (exception.getCode()) {
					case GlobalHelper.ERR_REG_EMAIL:
						TXTEmail.setError(exception.getMessage());
						break;
					case GlobalHelper.ERR_REG_PASS:
						TXTPassword.setError(exception.getMessage());
						break;
					case GlobalHelper.ERR_REG_PHONE_CODE:
						TXTCountryCode.setError(exception.getMessage());
						break;
					case GlobalHelper.ERR_REG_PHONE_NUMBER:
						TXTPassword.setError(exception.getMessage());
						break;
					case GlobalHelper.ERR_REG_USER:
						TXTUsername.setError(exception.getMessage());
						break;
					}
				}
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
