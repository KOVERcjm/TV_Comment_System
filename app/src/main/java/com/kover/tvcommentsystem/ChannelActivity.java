package com.kover.tvcommentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

public class ChannelActivity extends AppCompatActivity
{
	private long doubleClicktoExitTime = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_channel);
//
//		ImageView imageView = findViewById(R.id.channelPage_channel1);
//
//		imageView.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v)
//			{
//				Intent intent = new Intent(ChannelActivity.this, PlayerActivity.class);
//				intent.putExtra("channel", "1");
//				startActivity(intent);
//			}
//		});
	}
	
	public void onClick(View view)
	{
		Intent intent = new Intent(ChannelActivity.this, PlayerActivity.class);
		switch (view.getId())
		{
			case R.id.channelPage_channel1:
				intent.putExtra("channel", "1");
				break;
			case R.id.channelPage_channel2:
				intent.putExtra("channel", "2");
				break;
			case R.id.channelPage_channel3:
				intent.putExtra("channel", "3");
				break;
			case R.id.channelPage_channel4:
				intent.putExtra("channel", "4");
				break;
		}
		startActivity(intent);
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
