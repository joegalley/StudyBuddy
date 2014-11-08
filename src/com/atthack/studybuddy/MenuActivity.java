package com.atthack.studybuddy;

import com.atthack.studybuddy.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends Activity {

	private Button studying_around_me_button;
	private Button see_open_tables_button;
	private Button change_subject_button;
	private Button leave_table_button;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_layout);

		studying_around_me_button = (Button) findViewById(R.id.studying_around_me_button);
		see_open_tables_button = (Button) findViewById(R.id.see_open_tables_button);
		change_subject_button = (Button) findViewById(R.id.change_subject_button);
		leave_table_button = (Button) findViewById(R.id.leave_table_button);

		final Intent i = new Intent(this, StudyingAroundMeActivity.class);

		studying_around_me_button
				.setOnClickListener(new View.OnClickListener() {

					public void onClick(View v) {
						startActivity(i);
						new RetrieveFromServerTask()
								.execute("http://www.example.com");
					}
				});

		see_open_tables_button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				System.out.println("see open tables");
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
			}
		});

	}
}