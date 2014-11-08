package com.atthack.studybuddy;

import java.util.Arrays;

import com.atthack.studybuddy.R;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NFCReader extends Activity {

	private TextView mTextView;
	private NfcAdapter mNfcAdapter;
	private PendingIntent mPendingIntent;
	private IntentFilter[] mIntentFilters;
	private String[][] mNFCTechLists;
	private ImageView mPending;
	private ImageView mSuccess;
	private Button mCancel;

	@Override
	public void onCreate(Bundle savedState) {
		super.onCreate(savedState);

		setContentView(R.layout.reader_layout);
		mTextView = (TextView) findViewById(R.id.my_view);
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		mPending = (ImageView) findViewById(R.id.read_status);
		mSuccess = (ImageView) findViewById(R.id.read_done);
		mCancel = (Button) findViewById(R.id.cancel);

		mCancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cancelRequest();
			}
		});

		if (mNfcAdapter != null) {
			mTextView.setText("Read an NFC tag");
		} else {
			mTextView.setText("This phone is not NFC enabled.");
		}

		// create an intent with tag data and deliver to this activity
		mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
				getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

		// set an intent filter for all MIME data
		IntentFilter ndefIntent = new IntentFilter(
				NfcAdapter.ACTION_NDEF_DISCOVERED);
		try {
			ndefIntent.addDataType("*/*");
			mIntentFilters = new IntentFilter[] { ndefIntent };
		} catch (Exception e) {
			Log.e("TagDispatch", e.toString());
		}

		mNFCTechLists = new String[][] { new String[] { NfcA.class.getName() } };
	}

	@Override
	public void onNewIntent(Intent intent) {

		Intent results = new Intent();
		String s = "";
		// parse through all NDEF messages and their records and pick text type
		// only
		Parcelable[] data = intent
				.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
		if (data != null) {
			mPending.setVisibility(View.GONE);
			mSuccess.setVisibility(View.VISIBLE);
			Toast toast = Toast.makeText(this, "NFC Tag has been scanned!",
					Toast.LENGTH_SHORT);
			toast.show();
			try {
				NdefRecord recs = ((NdefMessage) data[0]).getRecords()[0];
				if (recs.getTnf() == NdefRecord.TNF_WELL_KNOWN
						&& Arrays.equals(recs.getType(), NdefRecord.RTD_TEXT)) {
					byte[] payload = recs.getPayload();
					String textEncoding = ((payload[0] & 0200) == 0) ? "UTF-8"
							: "UTF-16";
					int langCodeLen = payload[0] & 0077;

					s += (new String(payload, langCodeLen + 1, payload.length
							- langCodeLen - 1, textEncoding));
				}
			} catch (Exception e) {
				s += "Unsupported Encoding format";
			}
			if (!isNumeric(s))
				s = "-1";
			Log.d(null, "Returning to main activity with " + s);
			results.putExtra("table_num", s);
			setResult(RESULT_OK, results);
			finish();
		}
	}

	@Override
	public void onResume() {
		super.onResume();

		if (mNfcAdapter != null)
			mNfcAdapter.enableForegroundDispatch(this, mPendingIntent,
					mIntentFilters, mNFCTechLists);
	}

	@Override
	public void onPause() {
		super.onPause();

		if (mNfcAdapter != null)
			mNfcAdapter.disableForegroundDispatch(this);
	}

	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?"); // match a number with optional
												// '-' and decimal.
	}

	public void cancelRequest() {
		Intent dummy = new Intent();
		setResult(RESULT_CANCELED, dummy);
		finish();
	}
}