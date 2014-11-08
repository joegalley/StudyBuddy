package com.atthack.studybuddy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StudyingAroundMeActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);

		final Intent i = new Intent(this, MenuActivity.class);

		login_button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {

				startActivity(i);
			}
		});
	}

}
