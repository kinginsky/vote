package com.lg.vote.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lg.vote.R;
import com.lg.vote.Activity.MessageDetailActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class ChoicesAdapter extends BaseAdapter{
	private List<String> choices;
	// 用来控制CheckBox的选中状况
		private static HashMap<Integer, Boolean> isSelected;
		// 上下文
		private Context mContext;
		// 构造器
	public ChoicesAdapter(List<String> choicesList, Context context) {
		this.mContext = context;
		this.choices = choicesList;
		isSelected = new HashMap<Integer, Boolean>();
		// 初始化数据
		initDate();
	}
	
	// 初始化isSelected的数据
	private void initDate() {
		for (int i = 0; i < choices.size(); i++) {
			getIsSelected().put(i, false);
		}
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return choices.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return choices.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.choices_list_item, null);	
		}
		
		 TextView choice = (TextView) convertView
				.findViewById(R.id.choice_detail);// 显示发票人信息
		final String choice_detail=choices.get(position);
		choice.setText(choice_detail);
		final CheckBox checkBox=(CheckBox)convertView.findViewById(R.id.choice_check_box);
		checkBox.setChecked(getIsSelected().get(position));
		final int mPosition=position;
		//点击改变选择框
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				checkBox.toggle();
				getIsSelected().put(mPosition, checkBox.isChecked());
			}
		});
		return convertView;
	}
	
	public static HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		ChoicesAdapter.isSelected = isSelected;
	}
}
