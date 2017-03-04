package com.lg.vote.Activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lg.vote.R;
import com.lg.vote.Constants.Constants;
import com.lg.vote.adapter.ChoicesAdapter;
import com.lg.vote.application.MyApplication;
import com.lg.vote.db.SharedPreferenceUtil;
import com.lg.vote.model.VoteMessageModel;
import com.lg.vote.util.HttpUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MessageDetailActivity extends Activity {
	private TextView pollster, title, detail, number_answer, startTime,
			endTime, status;
	private EditText verification;
	private Button btn_detail;
	private ListView choices_list;
	private VoteMessageModel mVoteMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_detail);
		android.app.ActionBar actionBar = getActionBar();
		actionBar.hide();
		init();// 初始化
		show();// 显示界面
	}

	public void init() {
		pollster = (TextView) findViewById(R.id.detail_pollster);
		detail = (TextView) findViewById(R.id.detail_question_detail);
		verification = (EditText) findViewById(R.id.detail_verification_input);
		btn_detail = (Button) findViewById(R.id.btn_detail);
		choices_list = (ListView) findViewById(R.id.detail_choices_list);
		int position = (Integer) getIntent().getSerializableExtra("position");
		mVoteMessage = MyApplication.messagelist.get(position);
		List<String> choices = new ArrayList<String>();
		choices = mVoteMessage.getChoices();
		choices_list.setAdapter(new ChoicesAdapter(choices, this));
	}

	public void show() {
		pollster.setText(mVoteMessage.getPollster());
		// System.out.println("mVoteMessage.getPollster(): "+mVoteMessage.getPollster());
		detail.setText(mVoteMessage.getQuestionDetail());
		// 根据是否就该问题投过票，将button上的文字进行修改
		if ("yes".equals(mVoteMessage.getIsAnswered())) {
			View layout = (View) findViewById(R.id.detail_layout_verification);
			layout.setVisibility(View.GONE);
			btn_detail.setTag("2");
			btn_detail.setText("查看投票结果");
		} else {
			btn_detail.setTag("1");
			btn_detail.setText("我要投票");
		}
		// 根据验证码是否为空，设置是否隐藏验证码选项
		if (mVoteMessage.getIdentifyCode() == null) {
			View layout = (View) findViewById(R.id.detail_layout_verification);
			layout.setVisibility(View.GONE);
		}
	}

	public void btn_detail(View v) {
		// 联网更新该投票的具体结果，获得后跳转到另外一个界面
		String tag = (String) btn_detail.getTag();
		System.out.println("得到的tag标签是：" + tag);

		if ("1".equals(tag)) {
			// 检验验证码是否正确
			System.out.println("该问题验证码：" + mVoteMessage.getIdentifyCode());
			System.out.println("输入的验证码：" + verification.getText().toString());
			if (mVoteMessage.getIdentifyCode() != null) {
				System.out.println("验证码是否相等："
						+ mVoteMessage.getIdentifyCode().equals(
								verification.getText().toString()));
				if (!mVoteMessage.getIdentifyCode().equals(
						verification.getText().toString())) {
					Toast.makeText(this, "验证码输入有误", Toast.LENGTH_SHORT).show();
					return;
				}
			}

			// 设置投票人信息
			SharedPreferenceUtil sp = new SharedPreferenceUtil(this,
					Constants.SAVE_VOTER);
			mVoteMessage.setAnswerName(sp.getName());
			// 设置投票人答案
			HashMap<Integer, Boolean> map = ChoicesAdapter.getIsSelected();
			List<String> answers = new ArrayList<String>();
			for (int i = 0; i < map.size(); i++) {
				if (map.get(i)) {
					System.out.println(" 选择的选项：" + i);
					answers.add(mVoteMessage.getChoices().get(i));
				}
			}

			// 判断选项个数是否与要求符合，并设置投票人答案
			if (answers != null) {
				if (answers.size() != mVoteMessage.getAllowedAnswerNumber()) {
					Toast.makeText(this, "答案不符合要求！", Toast.LENGTH_SHORT).show();
					;
					return;
				} else {
					mVoteMessage.setAnswerDetail(answers);
				}
			} else {
				Toast.makeText(this, "请选择答案！！", Toast.LENGTH_SHORT).show();
				return;
			}
			// 将该投票信息发送给后台，后台放回这个问题各个选项的答案
			String result = sendToServer(mVoteMessage);
			System.out.println("传入后台的消息ID号为：" + mVoteMessage.getID());
			handleMessage(result);

		} else if ("2".equals(tag)) {
			// 直接发送后台刷新请求，返回最新投票结果
			String result = sendToServer(mVoteMessage);
			handleMessage(result);
		}

	}

	// 接受服务器的信息进行处理显示
	public void handleMessage(String result) {
		// 将接受到的字符串转化为对象
		ObjectMapper mapper = new ObjectMapper();
		try {
			mVoteMessage = mapper.readValue(result, VoteMessageModel.class);
			if (mVoteMessage.getID() != 0) {
				for (int i = 0; i < MyApplication.messagelist.size(); i++) {
					if (mVoteMessage.getID() == MyApplication.messagelist
							.get(i).getID()) {
						MyApplication.messagelist.remove(i);
						MyApplication.messagelist.add(mVoteMessage);
						gotoResultActivity();
					}
				}

			} else {
				Toast.makeText(this, "投票失败，请重新操作！", Toast.LENGTH_SHORT).show();
			}
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 进入投票结果界面给予显示
	public void gotoResultActivity() {
		Intent intent = new Intent(this, VoteResultActivity.class);
		intent.putExtra("VoteMessage", mVoteMessage);
		startActivity(intent);
		this.finish();
	}

	// 返回消息列表界面
	public void btn_back(View v) {
		this.finish();
	}

	/**
	 * 
	 * @param message
	 * @return 将用户编辑的投票答案发送给服务器端，返回是更新答案的最新消息。
	 */
	public String sendToServer(VoteMessageModel message) {
		Map<String, String> map = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();
		String voterjson, result = null;
		try {
			voterjson = mapper.writeValueAsString(message);
			map.put("json", voterjson);
			String url = Constants.BASE_URL + "/VoteServlet";
			try {
				result = HttpUtil.postRequest(url, map);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.message_detail, menu);
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
}
