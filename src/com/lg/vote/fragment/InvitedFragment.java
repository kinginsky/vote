package com.lg.vote.fragment;

import com.lg.vote.R;
import com.lg.vote.Activity.MessageDetailActivity;
import com.lg.vote.adapter.VoteMessageAdapter;
import com.lg.vote.application.MyApplication;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class InvitedFragment extends Fragment{
	public static VoteMessageAdapter mVoteMessageAdapter;
	public InvitedFragment(){
		
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView=inflater.inflate(R.layout.fragment_message_list,container,false);
		mVoteMessageAdapter=new VoteMessageAdapter(getActivity(),MyApplication.messagelist);
		((ListView) rootView.findViewById(R.id.fragment_message_list)).setAdapter(mVoteMessageAdapter);
		return rootView;
	}


	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(mVoteMessageAdapter!=null){
			mVoteMessageAdapter.notifyDataSetChanged();
		}
	}


	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		if(mVoteMessageAdapter!=null){
		mVoteMessageAdapter.notifyDataSetChanged();
		}
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}
	
}
