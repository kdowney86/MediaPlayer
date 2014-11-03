package com.example.mediaplayer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class FilesActivity extends ActionBarActivity implements Observer {

	protected MediaFilesObserver mfo;
	protected ListView filesList;
	protected String mediaDirectoryPath;
	protected ArrayList<String> fileNames;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_files);
		mediaDirectoryPath = "/sdcard/Download/";
		mfo = new MediaFilesObserver(mediaDirectoryPath);
		mfo.registerObserver(this);
		mfo.startWatching();
		filesList = (ListView)findViewById(R.id.filesList);
		refreshList();
		
		filesList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
					Intent myIntent = null;
					String path = fileNames.get(position);
					if (getFileExt(path).equals("mp3")) {
						myIntent = new Intent(FilesActivity.this, AudioPlayerActivity.class);
					} else {
						myIntent = new Intent(FilesActivity.this, VideoPlayerActivity.class);
					}
					myIntent.putExtra("path", path);
					startActivity(myIntent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.files, menu);
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

	@Override
	public void update(int event, String path) {
		// TODO Auto-generated method stub
		refreshList();
		
	}
	
	public void refreshList(){
		File downloadDirectory = new File(mediaDirectoryPath);
	    File [] files = downloadDirectory.listFiles();
	    fileNames= new ArrayList<String>();
		for(File f : files){
			String extension = getFileExt(f.getPath());
			if (extension.equals("mp3") || extension.equals("mp4")) {
				fileNames.add(f.getPath());
			}
		}
		ArrayAdapter<String> filesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fileNames);
		filesList.setAdapter(filesAdapter);
	}
	
	public static String getFileExt(String FileName)
	{       
	     return FileName.substring((FileName.lastIndexOf(".") + 1), FileName.length());
	}
	
}
