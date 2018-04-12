package com.kover.tvcommentsystem;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

public class PlayerActivity extends Activity
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);
		
		VideoView videoView = findViewById(R.id.playerPage_videoView);
		Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mgtv_wsgs);
		videoView.setVideoURI(uri);
		videoView.start();
	}
}
