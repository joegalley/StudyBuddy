package com.atthack.studybuddy;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;

class RetrieveFromServerTask extends AsyncTask<String, Void, String> {

	protected String doInBackground(String... urls) {
		try {
			URL url = null;
			try {
				url = new URL(urls[0]);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			HttpURLConnection urlCon = null;
			try {
				urlCon = (HttpURLConnection) url.openConnection();
				urlCon.setConnectTimeout(1000);

			} catch (IOException e1) {
				e1.printStackTrace();
			}

			try {

				InputStream in = new BufferedInputStream(
						urlCon.getInputStream());

				Reader r = new InputStreamReader(in);
				int c;
				String response = "";
				while ((c = r.read()) != -1) {
					response += (char) c;
				}
				System.out.println("RESONSE:" + response);
				return response;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				urlCon.disconnect();
			}
		} catch (Exception e) {

		}
		return null;

	}

	protected void onPostExecute(String result) {
		System.out.println("done");
	}
}
