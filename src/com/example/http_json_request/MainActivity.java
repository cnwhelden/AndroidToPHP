package com.example.http_json_request;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	private static final String URL = "http://bc-followme.azurewebsites.net/php/Test.php";

	@SuppressLint("UseSparseArrays")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Context context = getApplicationContext();		
		int duration = Toast.LENGTH_LONG;
		
		//String userName = "Eric";
		//String password = "gw1234";
		//int returnCode = verifyLogin(userName, password);

		String [][] arr = getArray();
		
		if (arr == null) {
			Toast.makeText(context, "Array is null", duration).show();	
			return;
		}
		
		EditText editText = (EditText) findViewById(R.id.editText1);
		editText.setText(arr[0][1]);

		editText = (EditText) findViewById(R.id.editText2);
		editText.setText(arr[1][1]);
		
	} 
	
	public String[][] getArray()
	{
		String[][] data = null;
		JSONObject jsonSend = new JSONObject();
		
		try {
			jsonSend.put("request", "test");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		// Send HTTP Post Request and get the returned JSON object
		JSONObject jsonRecv = HttpClient.sendPost(URL, jsonSend);
		Log.d(TAG, "sendPost returned: " + jsonRecv);
		
		if (jsonRecv == null) {
			Log.d(TAG, "Received null object from sendPost() call");
			return data;
		}
		
		Object jsonArrayObject = null;		
		try {
			jsonArrayObject = jsonRecv.get("test");
			Log.d(TAG, "JSON test object: " + jsonArrayObject);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		String jsonArrayString = jsonArrayObject.toString();		
		Log.d(TAG, "JSON Array object as String: " + jsonArrayString);

		Gson gson = new Gson();
        data = gson.fromJson(jsonArrayString, String[][].class);
		Log.d(TAG, "Java Array object as String: " + data.toString());
		
        return data;		
	
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