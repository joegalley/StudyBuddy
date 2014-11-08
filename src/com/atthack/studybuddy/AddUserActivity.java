package com.atthack.studybuddy;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.widget.Button;
import android.widget.EditText;

public class AddUserActivity extends AsyncTask<String, Void, Void> {

	private static ArrayList<NameValuePair> params2 = new ArrayList<NameValuePair>();

	String table_number = "";

	static final int NFC_REQUEST = 1;

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_DBTABLE = "hillman";

	private static final String TAG_COURSE = "course";
	private static final String TAG_TID = "tid";
	private static final String TAG_COUNT = "count";

	private static JSONParser jParser = new JSONParser();

	// url to get all products list
	private static String url_all_products = "http://studybud.web44.net/get_all_products.php";

	// url to update products
	private static String url_update_product = "http://studybud.web44.net/update_product.php";

	@Override
	protected Void doInBackground(String... params) {
		String tid = params[0];
		String course = "err";
		int count = -1;

		System.out.println("here:" + params2);

		JSONObject jsonGet = jParser.makeHttpRequest(url_all_products, "GET",
				params2);

		boolean tid_exists = false;

		try {
			// Checking for SUCCESS TAG
			int success = jsonGet.getInt(TAG_SUCCESS);

			if (success == 1) {
				// products found
				// Getting Array of Products
				JSONArray hillman = jsonGet.getJSONArray(TAG_DBTABLE);

				// looping through All Products
				for (int i = 0; i < hillman.length(); i++) {
					JSONObject c = hillman.getJSONObject(i);

					// Storing each json item in variable
					int temp_id = c.getInt(TAG_TID);
					String temp_course = c.getString(TAG_COURSE);
					int temp_count = c.getInt(TAG_COUNT);

					if (Integer.valueOf(tid) == temp_id) {

						count = temp_count + 1;
						System.out.println(count);
						course = temp_course;
						System.out.println(course);
						tid_exists = true;
					}

				}

			} else {
				System.err.println("error");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if (tid_exists) {

			ArrayList<NameValuePair> params2 = new ArrayList<NameValuePair>();
			params2.add(new BasicNameValuePair(TAG_TID, tid));
			params2.add(new BasicNameValuePair(TAG_COURSE, course));
			params2.add(new BasicNameValuePair(TAG_COUNT, Integer
					.toString(count)));
			// params.add(new BasicNameValuePair(TAG_DESCRIPTION, description));

			// sending modified data through http request
			// Notice that update product url accepts POST method
			JSONParser jsonParser = new JSONParser();

			jsonParser.makeHttpRequest(url_update_product, "POST", params2);

		} else {
			System.err.println("TID doesn't exist: " + tid);
		}

		return null;
	}

}
