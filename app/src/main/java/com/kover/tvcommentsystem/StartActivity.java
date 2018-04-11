package com.kover.tvcommentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity
{
	private long doubleClicktoExitTime = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		/**
		 * Start up UserActivity with signIn mode.
		 */
		Button button = findViewById(R.id.homePage_signIn);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(StartActivity.this, UserActivity.class);
				intent.putExtra("homePageAction", "signIn");
				startActivity(intent);
			}
		});
		
		/**
		 * Start up UserActivity with register mode.
		 */
		button = findViewById(R.id.homePage_register);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(StartActivity.this, UserActivity.class);
				intent.putExtra("homePageAction", "register");
				startActivity(intent);
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
		{
			if ((System.currentTimeMillis() - doubleClicktoExitTime) > 2000)
			{
				Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
				doubleClicktoExitTime = System.currentTimeMillis();
			}
			else
				finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
