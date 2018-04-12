package com.kover.tvcommentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserActivity extends AppCompatActivity
{
	String actionMode = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		
		Intent intent = getIntent();
		actionMode = intent.getStringExtra("homePageAction");
		
		Button button = findViewById(R.id.userPage_action);
		button.setOnClickListener(new buttonOnClickListener());
		if ("register".equals(actionMode))
			button.setText(R.string.userPage_register);
		
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		if (actionBar != null)
		{
			actionBar.setHomeButtonEnabled(true);
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}
	
	private class buttonOnClickListener implements View.OnClickListener
	{
		private String phoneNumber = "";
		private String password = "";
		
		public void onClick(View view)
		{
			EditText editText = findViewById(R.id.userPage_password);
			password = editText.getText().toString();
			editText = findViewById(R.id.userPage_phoneNumber);
			phoneNumber = editText.getText().toString();
			
			String regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$";
			Pattern pattern = Pattern.compile(regExp);
			Matcher matcher = pattern.matcher(phoneNumber);
			if (!matcher.matches())
			{
				Toast.makeText(UserActivity.this, "Invalid phone number format! Please input again!", Toast.LENGTH_LONG).show();
				editText.setText("");
				return;
			}
			if ("".equals(password))
			{
				Toast.makeText(UserActivity.this, "Please input the password!", Toast.LENGTH_LONG).show();
				return;
			}
			
			if ("signIn".equals(actionMode))
				signInProcess();
			else
				registerProcess();
		}
		
		private void signInProcess()
		{
			// TODO Sign in process.
			Intent intent = new Intent(UserActivity.this, ChannelActivity.class);
			startActivity(intent);
		}
		
		private void registerProcess()
		{
			// TODO Register process.
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
				Intent intent = new Intent(UserActivity.this, StartActivity.class);
				startActivity(intent);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
