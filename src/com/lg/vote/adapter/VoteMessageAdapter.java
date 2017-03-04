package com.lg.vote.adapter;

import java.util.List;




import com.lg.vote.R;
import com.lg.vote.Activity.MessageDetailActivity;
import com.lg.vote.model.VoteMessageModel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class VoteMessageAdapter extends BaseAdapter{
	private Context mContext;
	private List<VoteMessageModel> mVoteMessageList;
	public VoteMessageAdapter(Context context,List<VoteMessageModel> VoteMessageList){
		this.mContext=context;
		this.mVoteMessageList=VoteMessageList;	
		//System.out.println("---------虚构函数已执行"+this.mVoteMessageList.get(0).getPollName());	
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
		//return 0;
		return mVoteMessageList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//System.out.println("---------已经执行getView");
		/*************************为listView每一个Item赋值************************/
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.main_list_item, null);	
		}
		
		final TextView title = (TextView) convertView
				.findViewById(R.id.voter_name_item);// 显示发票人信息
		final String Pollster=mVoteMessageList.get(position).getPollster();
		title.setText(Pollster);

		final TextView questionTextView = (TextView) convertView
				.findViewById(R.id.voter_question_item);// 显示发票人信息
		final String question=mVoteMessageList.get(position).getQuestionDetail();
		questionTextView.setText(question);
		final int ID=mVoteMessageList.get(position).getID();
		/*************************点击每一个Item查看每一条投票消息的具体内容************************/
		final int mposition=position;
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 下面是切换到好友详情界面
				Intent intent = new Intent(mContext, MessageDetailActivity.class);
				intent.putExtra("position", mposition);
				mContext.startActivity(intent);

			}
		});
		//return null;
		return convertView;
	}
	

}
