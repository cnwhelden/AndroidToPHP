package com.example.http_json_request;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
	private static final String URL = "http://cis350.azurewebsites.net/php/login.php";
	
	@SuppressLint("UseSparseArrays")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// TODO: Get username and password elsewhere
		String userName = "Eric";
		String password = "gw1234";
		int returnCode = verifyLogin(userName, password);
		
		// login.php return codes
		Map<Integer, String> map = new HashMap<Integer, String>(3);
		map.put(0, "SUCCESS");
		map.put(1, "BAD USERNAME");
		map.put(2, "BAD PASSWORD");
		
		// Output return code string in a Toast
		CharSequence text = map.get(returnCode);
		Context context = getApplicationContext();		
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();		
		
	}
	
	public int verifyLogin(String userName, String password) {		
		// Create JSONObject
		JSONObject jsonSend = new JSONObject();

		try {
			// Put data in JSONObject
			jsonSend.put("username", userName);
			jsonSend.put("password", password);
			Log.i(TAG, jsonSend.toString());

		} catch (JSONException e) {
			Log.i(TAG, "Error packing login data");
			e.printStackTrace();
		}

		// Send HTTP Post Request and get the returned JSON object
		JSONObject jsonRecv = HttpClient.sendPost(URL, jsonSend);

		int returnCode = -1;
		try {
			returnCode = jsonRecv.getInt("returncode"); 
		} catch (JSONException e) {
			Log.i(TAG, "'returncode' not in JSON object");
			e.printStackTrace();
		}

		return returnCode;
	}
}