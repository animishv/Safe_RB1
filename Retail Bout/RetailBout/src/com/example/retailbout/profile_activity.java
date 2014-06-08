package com.example.retailbout;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;

public class profile_activity extends Activity {

	private EditText etgender, etage, etshoppinginterests, etzipcode,
			etlocation;
	private Button btnSubmit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_activity);
		etgender = (EditText) findViewById(R.id.etgender);
		etage = (EditText) findViewById(R.id.etage);
		etshoppinginterests = (EditText) findViewById(R.id.etshoppinginterests);
		etzipcode = (EditText) findViewById(R.id.etzipcode);
		etlocation = (EditText) findViewById(R.id.etlocation);
		btnSubmit = (Button) findViewById(R.id.btnSubmit);

		btnSubmit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				posttoserver();

			}
		});
	}

	private void posttoserver() {
		// Create a reference to a Firebase location
		Firebase ref = new Firebase("https://vivid-fire-7199.firebaseio.com/profile");
		String gender = etgender.getText().toString();
		String age = etage.getText().toString();
		String shoppinginterest = etshoppinginterests.getText().toString();
		String zipcode = etzipcode.getText().toString();
		String location = etlocation.getText().toString();
		Map<String, Object> updates = new HashMap<String, Object>();
		updates.put("shoppinginterest", shoppinginterest);
		updates.put("gender", gender);
		updates.put("age", age);
		updates.put("zipcode", zipcode);
		updates.put("location", location);
		ref.updateChildren(updates);
		// TODO Auto-generated method stub
	}
}
