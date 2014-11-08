package com.atthack.studybuddy;

import com.atthack.studybuddy.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MenuActivity extends Activity {

	private Button studying_around_me_button;
	private Button see_open_tables_button;
	private Button change_subject_button;
	private Button leave_table_button;
	private EditText enter_class_id_edittext;

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

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_layout);

		studying_around_me_button = (Button) findViewById(R.id.studying_around_me_button);
		see_open_tables_button = (Button) findViewById(R.id.see_open_tables_button);
		change_subject_button = (Button) findViewById(R.id.change_subject_button);
		leave_table_button = (Button) findViewById(R.id.leave_table_button);
		enter_class_id_edittext = (EditText) findViewById(R.id.enter_class_id_edittext);

		final Intent studying_around_me_intent = new Intent(this,
				StudyingAroundMeActivity.class);

		final Intent open_tables_intent = new Intent(this,
				SeeOpenTablesActivity.class);
		studying_around_me_button
				.setOnClickListener(new View.OnClickListener() {

					public void onClick(View v) {
						studying_around_me_intent.putExtra("CLASS_ID",
								enter_class_id_edittext.getText().toString());

						startActivity(studying_around_me_intent);
					}
				});

		see_open_tables_button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				System.out.println("see open tables");

				startActivity(open_tables_intent);
			}
		});

		change_subject_button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				System.out.println("change subject");
			}
		});

		leave_table_button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				System.out.println("leave table");

				getNFC();
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		String s = "";
		if (requestCode == NFC_REQUEST && resultCode == RESULT_OK) {
			int table = Integer.parseInt(data.getStringExtra("table_num"));
			table_number += "Scanned table is " + table;

			Toast t = Toast.makeText(this, table_number, Toast.LENGTH_LONG);
			t.show();

		}
	}

	public void getNFC() {
		Intent intent = new Intent(this, NFCReader.class);
		startActivityForResult(intent, NFC_REQUEST);
	}
	
	
	
	public static boolean removeUser(String tid) {

		String course = "err";
		int count = -1;

		JSONObject jsonGet = jParser.makeHttpRequest(url_all_products, "GET", params);

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

			    if (Integer.valueOf(tid) == temp_id && temp_count > 0){
				
				count = temp_count - 1;
				System.out.println(count);
				course = temp_course;
				System.out.println(course);
				tid_exists = true;
			    } else {
				System.err.println("The count is zero. cannot be set less than zero: " + temp_count);
			    }

			}

		    } else {
			System.err.println("error");
		    }
		} catch (JSONException e) {
		    e.printStackTrace();
		} 

}
