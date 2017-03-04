package com.lg.vote.application;

import java.util.ArrayList;
import java.util.List;

import com.lg.vote.db.VoteMessageDB;


import com.lg.vote.model.VoteMessageModel;

import android.app.Application;

public class MyApplication extends Application{
	public static List<VoteMessageModel> messagelist;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		messagelist=new ArrayList<VoteMessageModel>();
	}




}
