package com.atthack.studybuddy;

import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StudyingAroundMeActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.studying_around_me_layout);

		String CLASS_ID = "";
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			CLASS_ID = extras.getString("CLASS_ID");
		}

		TextView tv_class1;
		TextView tv_class2;
		TextView tv_class3;
		TextView tv_class4;
		TextView tv_class5;
		TextView tv_class6;

		JSONArray course_list = null;
		LinkedList<String> relevant_courses = new LinkedList<String>();

		try {
			course_list = new RetrieveFromServerTask().execute("1").get();

			System.out.println("here:" + course_list);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		LinkedList<TextView> text_views = new LinkedList<TextView>();

		tv_class1 = (TextView) findViewById(R.id.textView1);
		tv_class2 = (TextView) findViewById(R.id.textView2);
		tv_class3 = (TextView) findViewById(R.id.textView3);
		tv_class4 = (TextView) findViewById(R.id.textView4);
		tv_class5 = (TextView) findViewById(R.id.textView5);
		tv_class6 = (TextView) findViewById(R.id.textView6);

		text_views.add(tv_class1);
		text_views.add(tv_class2);
		text_views.add(tv_class3);
		text_views.add(tv_class4);
		text_views.add(tv_class5);
		text_views.add(tv_class6);

		System.out.println(course_list);

		for (int i = 0; i < course_list.length(); i++) {
			try {
				if (course_list.getJSONObject(i).getString("course").toString()
						.equals(CLASS_ID.replaceAll("\\s+", ""))) {

					relevant_courses.add("Class: "
							+ course_list.getJSONObject(i).getString("course")
									.toString()
							+ "      TABLE #: "
							+ Integer.toString(course_list.getJSONObject(i)
									.getInt("tid"))
							+ "      PEOPLE AT TABLE: "
							+ Integer.toString(course_list.getJSONObject(i)
									.getInt("count")));

				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		boolean no_one_studying = false;

		if (relevant_courses.size() == 0) {
			no_one_studying = true;
			tv_class1.setVisibility(View.GONE);
			tv_class2.setVisibility(View.GONE);
			tv_class3.setVisibility(View.GONE);
			tv_class4.setVisibility(View.GONE);
			tv_class5.setVisibility(View.GONE);

			tv_class6
					.setText("Sorry, no one is currently studying " + CLASS_ID);

		}

		int i = 0;
		for (TextView course : text_views) {
			if (relevant_courses.size() > 0) {
				if (i < relevant_courses.size()) {
					if (relevant_courses.get(i) != (null)) {
						course.setText(relevant_courses.get(i).toString());
					}
				}
			}
			i++;
		}

		for (TextView course : text_views) {
			if (!course.getText().toString().contains("PEOPLE")
					&& !no_one_studying) {
				course.setVisibility(View.GONE);
			}
		}

	}
}
