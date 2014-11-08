package com.atthack.studybuddy;

import android.os.AsyncTask;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class RetrieveFromServerTask extends AsyncTask<String, Void, JSONArray> {

	private static String url_all_products = "http://studybud.web44.net/get_all_products.php";
	private static JSONParser jParser = new JSONParser();
	private static ArrayList<HashMap<String, String>> productsList;

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCTS = "products";
	private static final String TAG_PID = "pid";
	private static final String TAG_NAME = "name";

	JSONArray hillman = null;

	protected JSONArray doInBackground(String... urls) {
		try {

			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			JSONObject json = jParser.makeHttpRequest(url_all_products, "GET",
					params);

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// products found
					// Getting Array of Products
					JSONArray products = json.getJSONArray(TAG_PRODUCTS);

					// looping through All Products
					for (int i = 0; i < products.length(); i++) {
						JSONObject c = products.getJSONObject(i);

						// Storing each json item in variable
						String id = c.getString(TAG_PID);
						String name = c.getString(TAG_NAME);
						System.out.println(id + " " + name);

						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put(TAG_PID, id);
						map.put(TAG_NAME, name);

						// adding HashList to ArrayList
						productsList.add(map);
					}

				} else {
					// no products found
					// Launch Add New product Activity
					// Intent i = new Intent(getApplicationContext(),
					// NewProductActivity.class);
					// // Closing all previous activities
					// i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					// startActivity(i);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			try {

				hillman = json.getJSONArray("hillman");

			} finally {

			}
		} catch (Exception e) {

		}
		return hillman;

	}

	protected void onPostExecute(String result) {
		System.out.println("done");
	}
}
