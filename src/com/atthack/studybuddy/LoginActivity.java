package com.atthack.studybuddy;

import com.atthack.studybuddy.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends Activity {

	private Button login_button;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);

		login_button = (Button) findViewById(R.id.login_button);

		final Intent i = new Intent(this, MenuActivity.class);

		login_button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {

				startActivity(i);
			}
		});
	}

	protected void onPostExecute(Long result) {

	}

}
