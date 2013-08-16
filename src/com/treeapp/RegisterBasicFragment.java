package com.treeapp;

import java.util.regex.Pattern;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.treeapp.exceptions.RegisterException;
import com.treeapp.helpers.GlobalHelper;
import com.treeapp.objects.ParseObjects;
import com.treeapp.windows.CustomToast;

public class RegisterBasicFragment extends Fragment implements OnClickListener {

	private EditText TXTUsername;
	private EditText TXTEmail;
	private EditText TXTCountryCode;
	private EditText TXTPhone;
	private EditText TXTPassword;
	private EditText TXTRetypePass;
	private ImageView IMVComplete;
	// with error:
	private EditText mEditText;
	private String GOOGLE_CLASS;
	// the actual error:
	private String error;
	// account Created?
	private boolean created;
	private static final String CREATED = "created";
	private int mEditTextId;
	private final String TXTERROR = "mEdit";

	private View mView;
	private final String TAG = "Register Basic";
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		created = false;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(savedInstanceState != null) {
			created = savedInstanceState.getBoolean(CREATED);
			mEditTextId = savedInstanceState.getInt(TXTERROR);
		} else 
			created = false;
		Log.d(TAG, "onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.registration_activity, null);
		onCreate();
		Log.d(TAG, "onCreateView");
		return mView;
	}

	private void onCreate() {

		// google type
		GOOGLE_CLASS = getResources().getString(R.string.GOOGLE_CLASS);
		getViews();

		// get possible email
		Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
		Account[] accounts = AccountManager.get(getActivity()).getAccounts();
		for (Account account : accounts) {
			if (emailPattern.matcher(account.name).matches()) {
				if (account.type.equals(GOOGLE_CLASS)) {
					String possibleEmail = account.name;
					TXTEmail.setText(possibleEmail);
					break;
				}
			}
		}

		TelephonyManager tm = (TelephonyManager) getActivity()
				.getSystemService(Context.TELEPHONY_SERVICE);
		String mCountryCode = tm.getSimCountryIso();

		// listener for hint code and phone
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

		// get country code and set
		// String mCountryCode =
		// getResources().getConfiguration().locale.getCountry();
		if (mCountryCode == null || mCountryCode.equals("")) {
			// no hay country code
		} else {
			// hay country code
			TXTPhone.setHint("");
			Log.i("mCountryCode", mCountryCode);
			String cod = GlobalHelper.getCodeCountry(mCountryCode);
			Log.i("cod", cod == null ? " null? wtf" : cod.toString());
			TXTCountryCode.setText(cod);
		}
	}

	// get references
	private void getViews() {
		((Button) mView.findViewById(R.id.BTNRegister))
				.setOnClickListener(this);
		TXTUsername = (EditText) mView.findViewById(R.id.TXTUsername);
		TXTUsername.addTextChangedListener(new CleanErrorWatcher(TXTUsername));
		TXTEmail = (EditText) mView.findViewById(R.id.TXTEmail);
		TXTEmail.addTextChangedListener(new CleanErrorWatcher(TXTEmail));
		TXTPhone = (EditText) mView.findViewById(R.id.TXTPhone);
		TXTPhone.addTextChangedListener(new CleanErrorWatcher(TXTPhone));
		TXTCountryCode = (EditText) mView.findViewById(R.id.TXTCountryCode);
		TXTCountryCode.addTextChangedListener(new CleanErrorWatcher(
				TXTCountryCode));
		TXTPassword = (EditText) mView.findViewById(R.id.TXTPassword);
		TXTPassword.addTextChangedListener(new CleanErrorWatcher(TXTPassword));
		TXTRetypePass = (EditText) mView.findViewById(R.id.TXTRetypePass);
		TXTRetypePass.addTextChangedListener(new CleanErrorWatcher(
				TXTRetypePass));
		IMVComplete = (ImageView) mView.findViewById(R.id.IMVComplete);
	}

	/**
	 * Set the error on the correspondent EditText view
	 * 
	 * @param exception
	 *            the exception
	 * @return true if correspond an any EditText or false if not
	 */
	private boolean setErrorInEditText(RegisterException exception) {
		boolean ret = false;
		if (!exception.getMessage().equals("")) {
			error = exception.getMessage();
			switch (exception.getCode()) {
			case GlobalHelper.ERR_REG_EMAIL:
				TXTEmail.setError(exception.getMessage());
				TXTEmail.requestFocus();
				mEditText = TXTEmail;
				ret = true;
				break;
			case GlobalHelper.ERR_REG_PASS:
				TXTPassword.setError(exception.getMessage());
				TXTPassword.requestFocus();
				mEditText = TXTPassword;
				ret = true;
				break;
			case GlobalHelper.ERR_REG_PHONE_CODE:
				TXTCountryCode.setError(exception.getMessage());
				TXTCountryCode.requestFocus();
				mEditText = TXTCountryCode;
				ret = true;
				break;
			case GlobalHelper.ERR_REG_PHONE_NUMBER:
				TXTPhone.setError(exception.getMessage());
				TXTPhone.requestFocus();
				mEditText = TXTPhone;
				ret = true;
				break;
			case GlobalHelper.ERR_REG_USER:
				TXTUsername.setError(exception.getMessage());
				TXTUsername.requestFocus();
				mEditText = TXTUsername;
				ret = true;
				break;
			case GlobalHelper.ERR_REG_RETYPE_PASS:
				TXTRetypePass.setError(exception.getMessage());
				TXTRetypePass.requestFocus();
				ret = true;
				break;
			case ParseException.EMAIL_TAKEN:
				TXTEmail.setError(exception.getMessage());
				TXTEmail.requestFocus();
				mEditText = TXTEmail;
				ret = true;
				break;
			case ParseException.USERNAME_TAKEN:
				TXTUsername.setError(exception.getMessage());
				TXTUsername.requestFocus();
				mEditText = TXTUsername;
				ret = true;
				break;
			}
			mEditTextId = mEditText.getId();
		} else
			error = null;
		return ret;
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.BTNRegister) {
			if (!created) {
				// register user. Validate
				if (view.getId() == R.id.BTNRegister) {
					String mUserName = TXTUsername.getText().toString();
					String mEmail = TXTEmail.getText().toString();
					String mCodePhone = TXTCountryCode.getText().toString();
					String mPhone = TXTPhone.getText().toString();
					String mPassword = TXTPassword.getText().toString();
					String mRetypePass = TXTRetypePass.getText().toString();

					RegisterException exception = null;
					if ((exception = GlobalHelper.isValidNewUser(getActivity(),
							mUserName, mEmail, mCodePhone, mPhone, mPassword,
							mRetypePass)) == null) {
						final AlertDialog alert = new AlertDialog.Builder(
								getActivity()).setPositiveButton(
								getResources().getString((R.string.Ok)), null)
								.create();
						final ProgressDialog dialog = new ProgressDialog(
								getActivity());
						dialog.setMessage(getResources().getString(
								R.string.MsgWaitSignUp));
						dialog.setTitle(getResources().getString(
								R.string.titleProgressDialogs));
						dialog.setIcon(R.drawable.ic_launcher);
						dialog.setCancelable(false);
						dialog.setCanceledOnTouchOutside(false);
						dialog.show();
						// disable change screen orientation if is in portraint
						getActivity().setRequestedOrientation(ActivityInfo.
								SCREEN_ORIENTATION_NOSENSOR);
						// intent create User in parse
						ParseUser user = new ParseUser();
						user.setEmail(mEmail);
						user.setUsername(mUserName);
						user.setPassword(mPassword);

						user.put(ParseObjects.User.codePhone, mCodePhone);
						user.put(ParseObjects.User.numberPhone, mPhone);
						user.put(ParseObjects.User.pass, mPassword);

						user.signUpInBackground(new SignUpCallback() {

							@Override
							public void done(ParseException e) {
								try {
									dialog.dismiss(); 
								} catch (Exception ex) {
									ex.printStackTrace();
								}
								if (e == null) {
									finishSignUp();
								} else {
									RegisterException msj = GlobalHelper.getErrorMsg(e
											.getCode());
									if (!setErrorInEditText(msj)) {
										alert.setMessage(GlobalHelper
												.getErrorMsgString(e.getCode()));
										alert.setTitle(getResources()
												.getString(R.string.titleErrors));
										alert.setIcon(R.drawable.ic_launcher);
										alert.setCancelable(true);
										alert.setCanceledOnTouchOutside(true);
										alert.show();
									}
								}
								getActivity().setRequestedOrientation(ActivityInfo.
										SCREEN_ORIENTATION_SENSOR);
							}
						});
					} else {
						setErrorInEditText(exception);
					}
				}
			} else
				finishSignUp();
		}
	}

	private void finishSignUp() {
		created = true;
		try {
		CustomToast.makeInfoText(getActivity(), getActivity().
				getResources().getString(R.string.AccountCreated),
				Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
		// to add Picture
		RegisterActivity.mViewPager.setCurrentItem(1);
		} catch (Exception e) {
			e.printStackTrace();	
		}
		// quit error
		if (mEditText != null) {
			mEditText = null;
			error = null;
		}
		
		initStateWhenCreated();
	}
	
	private void initStateWhenCreated() {
		Log.d(TAG, "initStateWhenCreated");
		// hide keyboard
		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(TXTCountryCode.getWindowToken(), 0);
		// disable EditTexts
		TXTUsername.setKeyListener(null);
		TXTEmail.setKeyListener(null);
		TXTCountryCode.setKeyListener(null);
		TXTPhone.setKeyListener(null);
		TXTPassword.setKeyListener(null);
		TXTRetypePass.setKeyListener(null);
		// set OK image
		IMVComplete.setImageResource(R.drawable.ic_ok);
		IMVComplete.setBackground(getActivity().getResources().
				getDrawable(R.drawable.login_links));
		IMVComplete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CustomToast.makeInfoText(getActivity(), 
						getActivity().getResources().getString(R.string.AccountCreated), 
						Toast.LENGTH_LONG).show();
			}
		});

	}

	public void clearErrors() {
		if (mEditText != null)
			mEditText.setError(null);
		else
			Log.e(TAG, "mEditText is NULL");
	}

	// back to the last error
	public void restErrors() {
		if (mEditText != null && error != null) {
			mEditText.setError(error);
			mEditText.requestFocus();
		} else
			Log.e(TAG, "mEditText is NULL in rest");
	}  
	
	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "onPause");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "onResume"); 
		
		// recover editText with error
		if (mEditTextId != -1){
			mEditText = (EditText) getActivity().findViewById(mEditTextId);
			Log.d(TAG, "textId: " + mEditTextId);
		} 

		if(created) { // EditTexts not enabled
			initStateWhenCreated();
		} else
			Log.d(TAG, "create is false");
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG, "onActivityCreated");
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Log.d(TAG, "onStart");
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy");
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		// SAVE state if restarts activity 
		outState.putBoolean(CREATED, created);
		outState.putInt(TXTERROR, mEditText != null ? mEditText.getId() : -1);
		Log.e(TAG, mEditText != null ? "no es null" : "es null");
	}
	
	private class CleanErrorWatcher implements TextWatcher {

		private EditText miEditText;

		public CleanErrorWatcher(EditText miEditText) {
			this.miEditText = miEditText;
		}

		@Override
		public void afterTextChanged(Editable s) {
			if (miEditText.getError() != null)
				miEditText.setError(null);
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}

	}
}
