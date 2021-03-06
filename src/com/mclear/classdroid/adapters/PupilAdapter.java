package com.mclear.classdroid.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mclear.classdroid.EditPupilActivity;
import com.mclear.classdroid.R;
import com.mclear.classdroid.bo.Pupil;

public class PupilAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Pupil> pupils;

	public PupilAdapter(Context context, ArrayList<Pupil> pupils) {
		this.context = context;
		this.pupils = pupils;
	}

	@Override
	public int getCount() {
		return pupils.size();
	}

	@Override
	public Object getItem(int position) {
		return pupils.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parentView) {
		Pupil pupil = pupils.get(position);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.pupil_row, null);
		convertView.setId((int) pupil.getId());

		TextView tvChildName = (TextView) convertView
				.findViewById(R.id.tvChildName);
		tvChildName.setText(pupil.getName());

		ImageView btnImage = (ImageView) convertView
				.findViewById(R.id.editButton);
		btnImage.setId((int) pupil.getId());
		btnImage.setFocusableInTouchMode(false);
		btnImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, EditPupilActivity.class);
				intent.putExtra("id", v.getId());
				context.startActivity(intent);
			}
		});

		return convertView;
	}

	public void addPupil(Pupil pupil) {
		pupils.add(pupil);
	}

}
