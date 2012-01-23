package com.primaryt.classdroid;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.primaryt.classdroid.bo.Post;
import com.primaryt.classdroid.db.DBAdapter;
import com.primaryt.classdroid.services.ClassdroidService;

public class NotesActivity extends Activity {
	private String imagePath;
	private String grade;
	private int pupilId;
	private String note = "";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notes_activity);

		Bundle data = getIntent().getExtras();
		if (data.containsKey("image")) {
			imagePath = data.getString("image");
		}
		if (data.containsKey("pupil")) {
			pupilId = data.getInt("pupil");
		}
		if (data.containsKey("grade")) {
			grade = data.getString("grade");
		}

		Button buttonCancel = (Button) findViewById(R.id.buttonCancel);
		buttonCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});

		final EditText editTextNote = (EditText) findViewById(R.id.editTextNote);

		Button buttonUploadNow = (Button) findViewById(R.id.buttonUploadNow);
		buttonUploadNow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				note = editTextNote.getText().toString();
				startUpload();
			}
		});
	}

	private void startUpload() {
		Post post = new Post();
		post.setIsPosted(0);
		post.setLocalImagePath(imagePath);
		post.setPupilId(pupilId);
		post.setTimestamp(Calendar.getInstance().toString());
		post.setGrade(grade);
		post.setNote(note);

		DBAdapter dbAdapter = new DBAdapter(this);
		dbAdapter.open();

		post = dbAdapter.addPost(post);
		dbAdapter.close();

		Intent service = new Intent(this, ClassdroidService.class);
		service.putExtra("id", post.getId());
		startService(service);
		setResult(RESULT_OK);
		finish();
	}

}
