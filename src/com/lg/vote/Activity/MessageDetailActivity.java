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
		init();// ��ʼ��
		show();// ��ʾ����
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
		// �����Ƿ�͸�����Ͷ��Ʊ����button�ϵ����ֽ����޸�
		if ("yes".equals(mVoteMessage.getIsAnswered())) {
			View layout = (View) findViewById(R.id.detail_layout_verification);
			layout.setVisibility(View.GONE);
			btn_detail.setTag("2");
			btn_detail.setText("�鿴ͶƱ���");
		} else {
			btn_detail.setTag("1");
			btn_detail.setText("��ҪͶƱ");
		}
		// ������֤���Ƿ�Ϊ�գ������Ƿ�������֤��ѡ��
		if (mVoteMessage.getIdentifyCode() == null) {
			View layout = (View) findViewById(R.id.detail_layout_verification);
			layout.setVisibility(View.GONE);
		}
	}

	public void btn_detail(View v) {
		// �������¸�ͶƱ�ľ���������ú���ת������һ������
		String tag = (String) btn_detail.getTag();
		System.out.println("�õ���tag��ǩ�ǣ�" + tag);

		if ("1".equals(tag)) {
			// ������֤���Ƿ���ȷ
			System.out.println("��������֤�룺" + mVoteMessage.getIdentifyCode());
			System.out.println("�������֤�룺" + verification.getText().toString());
			if (mVoteMessage.getIdentifyCode() != null) {
				System.out.println("��֤���Ƿ���ȣ�"
						+ mVoteMessage.getIdentifyCode().equals(
								verification.getText().toString()));
				if (!mVoteMessage.getIdentifyCode().equals(
						verification.getText().toString())) {
					Toast.makeText(this, "��֤����������", Toast.LENGTH_SHORT).show();
					return;
				}
			}

			// ����ͶƱ����Ϣ
			SharedPreferenceUtil sp = new SharedPreferenceUtil(this,
					Constants.SAVE_VOTER);
			mVoteMessage.setAnswerName(sp.getName());
			// ����ͶƱ�˴�
			HashMap<Integer, Boolean> map = ChoicesAdapter.getIsSelected();
			List<String> answers = new ArrayList<String>();
			for (int i = 0; i < map.size(); i++) {
				if (map.get(i)) {
					System.out.println(" ѡ���ѡ�" + i);
					answers.add(mVoteMessage.getChoices().get(i));
				}
			}

			// �ж�ѡ������Ƿ���Ҫ����ϣ�������ͶƱ�˴�
			if (answers != null) {
				if (answers.size() != mVoteMessage.getAllowedAnswerNumber()) {
					Toast.makeText(this, "�𰸲�����Ҫ��", Toast.LENGTH_SHORT).show();
					;
					return;
				} else {
					mVoteMessage.setAnswerDetail(answers);
				}
			} else {
				Toast.makeText(this, "��ѡ��𰸣���", Toast.LENGTH_SHORT).show();
				return;
			}
			// ����ͶƱ��Ϣ���͸���̨����̨�Ż�����������ѡ��Ĵ�
			String result = sendToServer(mVoteMessage);
			System.out.println("�����̨����ϢID��Ϊ��" + mVoteMessage.getID());
			handleMessage(result);

		} else if ("2".equals(tag)) {
			// ֱ�ӷ��ͺ�̨ˢ�����󣬷�������ͶƱ���
			String result = sendToServer(mVoteMessage);
			handleMessage(result);
		}

	}

	// ���ܷ���������Ϣ���д�����ʾ
	public void handleMessage(String result) {
		// �����ܵ����ַ���ת��Ϊ����
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
				Toast.makeText(this, "ͶƱʧ�ܣ������²�����", Toast.LENGTH_SHORT).show();
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

	// ����ͶƱ������������ʾ
	public void gotoResultActivity() {
		Intent intent = new Intent(this, VoteResultActivity.class);
		intent.putExtra("VoteMessage", mVoteMessage);
		startActivity(intent);
		this.finish();
	}

	// ������Ϣ�б����
	public void btn_back(View v) {
		this.finish();
	}

	/**
	 * 
	 * @param message
	 * @return ���û��༭��ͶƱ�𰸷��͸��������ˣ������Ǹ��´𰸵�������Ϣ��
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
