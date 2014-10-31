package com.example.mediaplayer;

import java.util.ArrayList;

import android.os.FileObserver;

public class MediaFilesObserver extends FileObserver {
	
	protected ArrayList<Observer> observers;
	
	public MediaFilesObserver(String path) {
		super(path, FileObserver.ALL_EVENTS);
		observers = new ArrayList<Observer>();
		// TODO Auto-generated constructor stub
	}
	
	public void registerObserver(Observer o){
		observers.add(o);
	}
	
	public void deregisterObserver(Observer o){
		observers.remove(o);
	}

	@Override
	public void onEvent(int event, String path) {
		for(Observer o : observers){
			o.update(event,path);
		}
		
	}
}
