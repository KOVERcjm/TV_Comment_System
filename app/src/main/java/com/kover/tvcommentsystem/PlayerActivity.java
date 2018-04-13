package com.kover.tvcommentsystem;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.Random;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

public class PlayerActivity extends Activity
{
	private boolean showDanmaku;
	
	private DanmakuView danmakuView;
	
	private DanmakuContext danmakuContext;
	
	private BaseDanmakuParser parser = new BaseDanmakuParser()
	{
		@Override
		protected IDanmakus parse()
		{
			return new Danmakus();
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);
		
		VideoView videoView = findViewById(R.id.playerPage_videoView);
		Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mgtv_wsgs);
		videoView.setVideoURI(uri);
		videoView.start();
		
		danmakuView = findViewById(R.id.playerPage_danmakuView);
		danmakuView.enableDanmakuDrawingCache(true);
		danmakuView.setCallback(new DrawHandler.Callback()
		{
			@Override
			public void prepared()
			{
				showDanmaku = true;
				danmakuView.start();
				generateSomeDanmaku();
			}
			
			@Override
			public void updateTimer(DanmakuTimer timer)
			{
			}
			
			@Override
			public void danmakuShown(BaseDanmaku danmaku)
			{
			}
			
			@Override
			public void drawingFinished()
			{
			}
		});
		danmakuContext = DanmakuContext.create();
		danmakuView.prepare(parser, danmakuContext);
		
		Button button = findViewById(R.id.playerPage_commentButton);
		button.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				EditText editText = findViewById(R.id.playerPage_commentText);
				String comment = editText.getText().toString();
				if ("".equals(comment))
				{
					Toast.makeText(PlayerActivity.this, "Please input your comment first!", Toast.LENGTH_SHORT).show();
				}
				else
				{
					addDanmaku(comment, true);
					editText.setText("");
				}
			}
		});
	}
	
	/**
	 * 向弹幕View中添加一条弹幕
	 *
	 * @param content    弹幕的具体内容
	 * @param withBorder 弹幕是否有边框
	 */
	private void addDanmaku(String content, boolean withBorder)
	{
		BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
		danmaku.text = content;
		danmaku.padding = 5;
		danmaku.textSize = sp2px(20);
		danmaku.textColor = Color.WHITE;
		danmaku.setTime(danmakuView.getCurrentTime());
		if (withBorder)
		{
			danmaku.borderColor = Color.GREEN;
		}
		danmakuView.addDanmaku(danmaku);
	}
	
	/**
	 * 随机生成一些弹幕内容以供测试
	 */
	private void generateSomeDanmaku()
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				while (showDanmaku)
				{
					int time = new Random().nextInt(3000);
					String content = "2" + time + time;
					addDanmaku(content, false);
					try
					{
						Thread.sleep(time);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	/**
	 * sp转px的方法。
	 */
	public int sp2px(float spValue)
	{
		final float fontScale = getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		if (danmakuView != null && danmakuView.isPrepared())
		{
			danmakuView.pause();
		}
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		if (danmakuView != null && danmakuView.isPrepared() && danmakuView.isPaused())
		{
			danmakuView.resume();
		}
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		showDanmaku = false;
		if (danmakuView != null)
		{
			danmakuView.release();
			danmakuView = null;
		}
	}
}
