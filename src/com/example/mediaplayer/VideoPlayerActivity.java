package com.example.mediaplayer;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoPlayerActivity extends ActionBarActivity {
	
	private State currentState;
	private VideoView myVideoView;
	private Button videoPlayButton;
	private Button videoPauseButton;
	private Button videoStopButton;
	private String videoPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_player);
		
		Intent myIntent = getIntent();
		videoPath = myIntent.getStringExtra("path");
		
		myVideoView = (VideoView)findViewById(R.id.videoView1);
		videoPlayButton = (Button)findViewById(R.id.playButtonVideo);
		videoPauseButton = (Button)findViewById(R.id.pauseButtonVideo);
		videoStopButton = (Button)findViewById(R.id.stopButtonVideo);
		
		currentState = new VideoPlayState(this);
		myVideoView.start();
		
		videoPlayButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				VideoPlayerActivity.this.currentState.play();
			}
			
		});
		
		videoPauseButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				VideoPlayerActivity.this.currentState.pause();
			}
			
		});
		
		videoStopButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				VideoPlayerActivity.this.currentState.stop();
			}
			
		});
		
		myVideoView.setVideoPath(videoPath);
		Context context = getApplicationContext();
		Toast toast = Toast.makeText(context, videoPath, Toast.LENGTH_SHORT);
		toast.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.video_player, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void setState(State s) {
		this.currentState = s;
	}
	
	public class VideoPlayState implements State {
		
		VideoPlayerActivity myVideoPlayerActivity;
		
		public VideoPlayState(VideoPlayerActivity v) {
			this.myVideoPlayerActivity = v;
		}

		@Override
		public void play() {
			Context context = getApplicationContext();
			Toast toast = Toast.makeText(context, "Video is already playing", Toast.LENGTH_SHORT);
			toast.show();
		}

		@Override
		public void pause() {
			myVideoPlayerActivity.setState(new VideoPauseState(myVideoPlayerActivity));
			myVideoPlayerActivity.myVideoView.pause();
		}

		@Override
		public void stop() {
			myVideoPlayerActivity.setState(new VideoStopState(myVideoPlayerActivity));
			myVideoPlayerActivity.myVideoView.pause();
			myVideoPlayerActivity.myVideoView.seekTo(0);
		}
	}
	
	public class VideoPauseState implements State {
		
		VideoPlayerActivity myVideoPlayerActivity;
		
		public VideoPauseState(VideoPlayerActivity v) {
			this.myVideoPlayerActivity = v;
		}

		@Override
		public void play() {
			myVideoPlayerActivity.setState(new VideoPlayState(myVideoPlayerActivity));
			myVideoPlayerActivity.myVideoView.start();
		}

		@Override
		public void pause() {
			Context context = getApplicationContext();
			Toast toast = Toast.makeText(context, "Video is already paused", Toast.LENGTH_SHORT);
			toast.show();
		}

		@Override
		public void stop() {
			myVideoPlayerActivity.setState(new VideoStopState(myVideoPlayerActivity));
			myVideoPlayerActivity.myVideoView.pause();
			myVideoPlayerActivity.myVideoView.seekTo(0);
		}
	}
	
	public class VideoStopState implements State {
		
		VideoPlayerActivity myVideoPlayerActivity;
		
		public VideoStopState(VideoPlayerActivity v) {
			this.myVideoPlayerActivity = v;
		}

		@Override
		public void play() {
			myVideoPlayerActivity.setState(new VideoPlayState(myVideoPlayerActivity));
			myVideoPlayerActivity.myVideoView.start();
		}

		@Override
		public void pause() {
			myVideoPlayerActivity.setState(new VideoPauseState(myVideoPlayerActivity));
			myVideoPlayerActivity.myVideoView.pause();
			
		}

		@Override
		public void stop() {
			Context context = getApplicationContext();
			Toast toast = Toast.makeText(context, "Video is already stopped", Toast.LENGTH_SHORT);
			toast.show();
		}
	}
}
