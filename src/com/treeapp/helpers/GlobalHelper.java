package com.treeapp.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;

import com.parse.ParseException;
import com.treeapp.R;
import com.treeapp.exceptions.RegisterException;

public class GlobalHelper {

	private static Application context;
	private static boolean phoneRequired;
	public static boolean validationWithDialog;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	public static final int ERR_REG_USER = 1;
	public static final int ERR_REG_EMAIL = 2;
	public static final int ERR_REG_PHONE_CODE = 3;
	public static final int ERR_REG_PHONE_NUMBER = 4;
	public static final int ERR_REG_PASS = 5;
	public static final int ERR_REG_RETYPE_PASS = 6;

	/**
	 * Initialize every helpers
	 * 
	 * @param app
	 */
	public static void initialize(Application app) {
		context = app;
		phoneRequired = app.getResources().getBoolean(R.bool.PhoneRequired);
		validationWithDialog = app.getResources().getBoolean(R.bool.ValidationWithDialog);
		// initialize other helpers
	}

	// RegisterActivity:
	/**
	 * Basic validate Create account
	 * 
	 * @param username
	 * @param email
	 * @param codePhone
	 * @param numberPhone
	 * @param pass
	 * @param retryPass
	 * @return
	 */
	public static RegisterException isValidNewUser(Activity activity, String username,
			String email, String codePhone, String numberPhone, String pass,
			String retryPass) {
		// for possible messages
		final AlertDialog alert = new AlertDialog.Builder(activity)
				.setPositiveButton(getSt(R.string.Ok), null).create();

		// the err message
		int ret = 0;
		String errMsg = null;
		// username
		if ((errMsg = isValidUserName(username)) != null) {
			// username empty
			ret = ERR_REG_USER;
		} else if ((errMsg = isValidEmail(email)) != null) {
			// email not valid
			ret = ERR_REG_EMAIL;
		} else if ((errMsg = isValidPhoneCode(codePhone)) != null) {
			// code not valid
			ret = ERR_REG_PHONE_CODE;
		} else if ((errMsg = isValidPhoneNumber(numberPhone)) != null) {
			// number not valid
			ret = ERR_REG_PHONE_NUMBER;
		} else if ((errMsg = isValidPass(pass)) != null) {
			// pass not valid
			ret = ERR_REG_PASS;
		} else if ((errMsg = isValidPass(pass, retryPass)) != null) {
			// retype pass not valid
			ret = ERR_REG_RETYPE_PASS;
		} else {
			return null;
		}
		if (errMsg != null && validationWithDialog) {
			alert.setMessage(errMsg);
			alert.setTitle(context.getResources().getString(
					R.string.titleErrors));
			alert.setIcon(R.drawable.ic_launcher);
			alert.setCancelable(true);
			alert.setCanceledOnTouchOutside(true);
			alert.show();
		}
		RegisterException e = new RegisterException(validationWithDialog ? 
				"" : errMsg, ret);
		return e;
	}

	/**
	 * Validate userame
	 * 
	 * @param username
	 *            the username
	 * @return message of the error or null
	 */
	private static String isValidUserName(String username) {
		if (notNullOrEmpty(username)) {
			return null;
		} else {
			return getSt(R.string.ErrorUsernameEmpty);
		}
	}

	/**
	 * Validate email
	 * 
	 * @param email
	 *            the email
	 * @return message of the error or null
	 */
	private static String isValidEmail(String email) {
		if (notNullOrEmpty(email)) {
			Pattern pattern = Pattern.compile(EMAIL_PATTERN);
			Matcher matcher = pattern.matcher(email);
			if (matcher.matches()) {
				// correct!
				return null;
			} else {
				return getSt(R.string.ErrorEmail);
			}
		} else {
			return getSt(R.string.ErrorEmailEmpty);
		}
	}

	/**
	 * Validate code
	 * 
	 * @param code
	 *            the country code
	 * @return message of the error or null
	 */
	private static String isValidPhoneCode(String code) {
		if (!phoneRequired || notNullOrEmpty(code)) {
			return null;
		} else
			return getSt(R.string.ErrorPhoneCodeEmpty);
	}

	/**
	 * Validate phone number
	 * 
	 * @param number
	 *            the number
	 * @return message of the error or null
	 */
	private static String isValidPhoneNumber(String number) {
		if (!phoneRequired || notNullOrEmpty(number))
			return null;
		else
			return getSt(R.string.ErrorPhoneEmpty);
	}
	
	/**
	 * Validate first pass
	 * @param pass
	 * @return message of the error or null
	 */
	private static String isValidPass(String pass) {
		if(notNullOrEmpty(pass)) {
			if(pass.length() >= 8) {
				return null;
			} else
				return getSt(R.string.ErrorPassLenghtShort);
		} else
			return getSt(R.string.ErrorPassEmpty);
	}

	/**
	 * Validate pass
	 * 
	 * @param pass
	 *            pass
	 * @param retpe
	 *            retype pass
	 * @return message of the error or null
	 */
	private static String isValidPass(String pass, String retpe) {
		if (notNullOrEmpty(pass)) {
			if (notNullOrEmpty(retpe)) {
				if (pass.length() >= 8) {
					if (pass.equals(retpe)) {
						return null;
					} else
						return getSt(R.string.ErrorPassNotEqual);
				} else
					return getSt(R.string.ErrorPassLenghtShort);
			} else
				return getSt(R.string.ErrorRetypePassEmpty);
		} else
			return getSt(R.string.ErrorPassEmpty);
	}

	/**
	 * get for the number code country
	 * 
	 * @param mcode
	 * @return mCode code if find, null if not
	 */
	public static String getCodeCountry(String mCode) {
		if (notNullOrEmpty(mCode)) {
			String[] codes = context.getResources().getStringArray(
					R.array.country_codes);
			for (String code : codes) {
				String[] parts = code.split(";"); // 3 parts
				if (mCode.length() == 2) {
					if (mCode.equalsIgnoreCase(parts[0])) {
						return parts[2];
					}
				} else {
					if (mCode.equalsIgnoreCase(parts[1])) {
						return parts[2];
					}
				}
			}
		}
		return null;
	}

	/**
	 * return the code of string error
	 * @param code
	 * @return
	 */
	private static int getParseExceptionTextId(int code) {
		switch (code) {
		case ParseException.ACCOUNT_ALREADY_LINKED:
			return R.string.ACCOUNT_ALREADY_LINKED;
		case ParseException.CACHE_MISS:
			return R.string.CACHE_MISS;
		case ParseException.COMMAND_UNAVAILABLE:
			return R.string.COMMAND_UNAVAILABLE;
		case ParseException.CONNECTION_FAILED:
			return R.string.CONNECTION_FAILED;
		case ParseException.DUPLICATE_VALUE:
			return R.string.DUPLICATE_VALUE;
		case ParseException.EMAIL_MISSING:
			return R.string.EMAIL_MISSING;
		case ParseException.EMAIL_NOT_FOUND:
			return R.string.EMAIL_NOT_FOUND;
		case ParseException.EMAIL_TAKEN:
			return R.string.EMAIL_TAKEN;
		case ParseException.EXCEEDED_QUOTA:
			return R.string.EXCEEDED_QUOTA;
		case ParseException.FILE_DELETE_ERROR:
			return R.string.FILE_DELETE_ERROR;
		case ParseException.INCORRECT_TYPE:
			return R.string.INCORRECT_TYPE;
		case ParseException.INTERNAL_SERVER_ERROR:
			return R.string.INTERNAL_SERVER_ERROR;
		case ParseException.INVALID_ACL:
			return R.string.INVALID_ACL;
		case ParseException.INVALID_CHANNEL_NAME:
			return R.string.INVALID_CHANNEL_NAME;
		case ParseException.INVALID_CLASS_NAME:
			return R.string.INVALID_CLASS_NAME;
		case ParseException.INVALID_EMAIL_ADDRESS:
			return R.string.INVALID_EMAIL_ADDRESS;
		case ParseException.INVALID_FILE_NAME:
			return R.string.INVALID_FILE_NAME;
		case ParseException.INVALID_JSON:
			return R.string.INVALID_JSON;
		case ParseException.INVALID_KEY_NAME:
			return R.string.INVALID_KEY_NAME;
		case ParseException.INVALID_LINKED_SESSION:
			return R.string.INVALID_LINKED_SESSION;
		case ParseException.INVALID_NESTED_KEY:
			return R.string.INVALID_NESTED_KEY;
		case ParseException.INVALID_POINTER:
			return R.string.INVALID_POINTER;
		case ParseException.INVALID_QUERY:
			return R.string.INVALID_QUERY;
		case ParseException.INVALID_ROLE_NAME:
			return R.string.INVALID_ROLE_NAME;
		case ParseException.LINKED_ID_MISSING:
			return R.string.LINKED_ID_MISSING;
		case ParseException.MISSING_OBJECT_ID:
			return R.string.MISSING_OBJECT_ID;
		case ParseException.MUST_CREATE_USER_THROUGH_SIGNUP:
			return R.string.MUST_CREATE_USER_THROUGH_SIGNUP;
		case ParseException.NOT_INITIALIZED:
			return R.string.NOT_INITIALIZED;
		case ParseException.OBJECT_NOT_FOUND:
			return R.string.OBJECT_NOT_FOUND;
		case ParseException.OBJECT_TOO_LARGE:
			return R.string.OBJECT_TOO_LARGE;
		case ParseException.OPERATION_FORBIDDEN:
			return R.string.OPERATION_FORBIDDEN;
		case ParseException.PASSWORD_MISSING:
			return R.string.PASSWORD_MISSING;
		case ParseException.PUSH_MISCONFIGURED:
			return R.string.PUSH_MISCONFIGURED;
		case ParseException.SCRIPT_ERROR:
			return R.string.SCRIPT_ERROR;
		case ParseException.SESSION_MISSING:
			return R.string.SESSION_MISSING;
		case ParseException.TIMEOUT:
			return R.string.TIMEOUT;
		case ParseException.UNSUPPORTED_SERVICE:
			return R.string.UNSUPPORTED_SERVICE;
		case ParseException.USERNAME_MISSING:
			return R.string.USERNAME_MISSING;
		case ParseException.USERNAME_TAKEN:
			return R.string.USERNAME_TAKEN;
		case ParseException.VALIDATION_ERROR:
			return R.string.VALIDATION_ERROR;
		}
		return R.string.OTHER_CAUSE;
	}

	/**
	 * Return the message of parse exception code
	 * 
	 * @param code
	 *            the ParseException code
	 * @return message error
	 */
	public static RegisterException getErrorMsg(int code) {
		return new RegisterException(validationWithDialog ? "" :
			getSt(getParseExceptionTextId(code)), code);
	}
	
	public static String getErrorMsgString(int code) {
		return getSt(getParseExceptionTextId(code));
	}

	// facilitates the work
	private static boolean notNullOrEmpty(String text) {
		return (text != null && !text.equals(""));
	}

	private static String getSt(int id) {
		return context.getResources().getString(id);
	}
}
