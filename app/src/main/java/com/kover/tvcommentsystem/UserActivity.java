package com.kover.tvcommentsystem;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
	/**
	 * The action mode which user chose at the start page.
	 */
	private String actionMode = "";
	private Database database;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		
		Intent intent = getIntent();
		actionMode = intent.getStringExtra("startPageAction");
		
		/*
		 * Set page for register.
		 */
		Button button = findViewById(R.id.userPage_action);
		button.setOnClickListener(new buttonOnClickListener());
		if ("register".equals(actionMode))
			button.setText(R.string.userPage_register);
		
		/*
		 * Set up the action bar and add a back button.
		 */
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		if (actionBar != null)
		{
			actionBar.setHomeButtonEnabled(true);
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}
	
	/**
	 * Button on click listener for the user logic.
	 */
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
			
			/*
			 * Check for empty input.
			 */
			if ("".equals(phoneNumber) || "".equals(password))
			{
				Toast.makeText(UserActivity.this, "Please input both the phone number and the password!",
						Toast.LENGTH_LONG).show();
				return;
			}
			
			/*
			 * Check for the wrong phone format.
			 */
			String regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$";
			Pattern pattern = Pattern.compile(regExp);
			Matcher matcher = pattern.matcher(phoneNumber);
			if (matcher.matches()) //TODO (!matcher.matches())
			{
				Toast.makeText(UserActivity.this, "Invalid phone number format! Please input again!",
						Toast.LENGTH_LONG).show();
				editText.setText("");
				return;
			}
			
			/*
			 * User logic.
			 */
			database = new Database(view.getContext());
			if ("signIn".equals(actionMode))
				signInProcess();
			else
				registerProcess();
		}
		
		/**
		 * User sign in process.
		 */
		private void signInProcess()
		{
			SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
			Cursor cursor = sqLiteDatabase.rawQuery("select * from user", null);
			
			while (cursor.moveToNext())
			{
				if (phoneNumber.equals(cursor.getString(1)))
				{
					if (password.equals(cursor.getString(2)))
					{
						Toast.makeText(UserActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
						sqLiteDatabase.close();
						Intent intent  = new Intent(UserActivity.this, ChannelActivity.class);
						intent.putExtra("phone", phoneNumber);
						startActivity(intent);
						return;
					}
					else
					{
						Toast.makeText(UserActivity.this, "Invalid Password! Please input again!", Toast.LENGTH_SHORT).show();
						EditText editText = findViewById(R.id.userPage_password);
						editText.setText("");
						return;
					}
				}
			}
			Toast.makeText(UserActivity.this, "This number has not been registered! Please input again!", Toast.LENGTH_LONG).show();
			EditText editText = findViewById(R.id.userPage_password);
			editText.setText("");
			editText = findViewById(R.id.userPage_phoneNumber);
			editText.setText("");
			sqLiteDatabase.close();
			return;
		}
		
		/**
		 * User register process.
		 */
		private void registerProcess()
		{
			SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
			Cursor cursor = sqLiteDatabase.rawQuery("select * from user", null);
			
			while (cursor.moveToNext())
			{
				if (phoneNumber.equals(cursor.getString(1)))
				{
					Toast.makeText(UserActivity.this, "This number has been registered.", Toast.LENGTH_LONG).show();
					EditText editText = findViewById(R.id.userPage_password);
					editText.setText("");
					editText = findViewById(R.id.userPage_phoneNumber);
					editText.setText("");
					return;
				}
			}
			
			ContentValues contentValues = new ContentValues();
			contentValues.put("phone", phoneNumber);
			contentValues.put("password", password);
			sqLiteDatabase.insert("user", null, contentValues);
			sqLiteDatabase.close();
			
			Toast.makeText(UserActivity.this, "Successful! Please sign in again!", Toast.LENGTH_LONG).show();
			startActivity(new Intent(UserActivity.this, StartActivity.class));
			return;
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
