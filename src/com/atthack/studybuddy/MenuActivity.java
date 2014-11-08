package com.atthack.studybuddy;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import com.atthack.studybuddy.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MenuActivity extends Activity {

	private static ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

	private boolean JOINING_OR_LEAVING;

	private Button studying_around_me_button;
	private Button see_open_tables_button;
	private Button change_subject_button;
	private Button join_table_button;
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
		change_subject_button.setVisibility(View.GONE);
		leave_table_button = (Button) findViewById(R.id.leave_table_button);
		join_table_button = (Button) findViewById(R.id.join_table_button);
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
				JOINING_OR_LEAVING = false;
				System.out.println("leave table");

				getNFC();

			}
		});

		join_table_button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				JOINING_OR_LEAVING = true;
				System.out.println("join table");

				getNFC();

			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		String s = "";
		if (requestCode == NFC_REQUEST && resultCode == RESULT_OK) {
			int table = Integer.parseInt(data.getStringExtra("table_num"));
			table_number = "Scanned table is " + table;

			Toast t = Toast.makeText(this, table_number, Toast.LENGTH_LONG);
			t.show();

			System.out.println("table" + table_number + "table");
			try {
				if (table_number == "" || table_number == null) {

					new RemoveUserActivity().execute("11").get();
				} else {
					String tbl = table_number
							.substring(table_number.length() - 1);

					System.out.println("aaa" + tbl + "aaa");
					if (!JOINING_OR_LEAVING) {
						new RemoveUserActivity().execute(tbl).get();

					} else {
						new AddUserActivity().execute(tbl).get();
					}

				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}

		}
	}

	public void getNFC() {
		Intent intent = new Intent(this, NFCReader.class);
		startActivityForResult(intent, NFC_REQUEST);
	}

}
