package com.lg.vote.Activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lg.vote.R;
import com.lg.vote.Constants.Constants;
import com.lg.vote.application.MyApplication;
import com.lg.vote.db.SharedPreferenceUtil;
import com.lg.vote.model.VoteMessageModel;
import com.lg.vote.model.VoterModel;
import com.lg.vote.util.CircularImage;
import com.lg.vote.util.DialogUtil;
import com.lg.vote.util.HttpUtil;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnFocusChangeListener,OnClickListener,TextWatcher {

	private static ImageView head_icon,login_user,login_account_edit_clear,login_key,login_key_edit_clear;
	private static EditText login_edit_account,login_edit_key;
	private static Button login_btn;
	private static TextView login_regist,login_forget;
	private static ScrollView scrollView;
	private static LinearLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		android.app.ActionBar actionBar = getActionBar();
		actionBar.hide();
		initView();
	}

	/*********************** 初始化按钮和获取输入框内容 *********************/
	public void initView() {
	
		layout = (LinearLayout)findViewById(R.id.login_layout);
		layout.setOnClickListener(this);
		//head_icon = (ImageView)findViewById(R.id.login_icon);
		CircularImage cover_head_icon = (CircularImage)findViewById(R.id.cover_head_icon);
		cover_head_icon.setImageResource(R.drawable.ic_launcher);
		login_user = (ImageView)findViewById(R.id.login_user);
		login_account_edit_clear = (ImageView)findViewById(R.id.login_account_edit_clear);
		login_account_edit_clear.setOnClickListener(this);
		login_key = (ImageView)findViewById(R.id.login_key);
		login_key_edit_clear = (ImageView)findViewById(R.id.login_key_edit_clear);
		login_key_edit_clear.setOnClickListener(this);
		login_edit_account =(EditText)findViewById(R.id.login_edit_account);
		login_edit_account.addTextChangedListener(this);
		login_edit_account.setOnFocusChangeListener(this);
		login_edit_key = (EditText)findViewById(R.id.login_edit_key);
		login_edit_key.addTextChangedListener(this);
		login_edit_key.setOnFocusChangeListener(this);
		login_btn = (Button)findViewById(R.id.login_btn);
		login_btn.setOnClickListener(this);
		login_regist = (TextView)findViewById(R.id.login_regist);
		login_regist.setOnClickListener(this);
		login_forget = (TextView)findViewById(R.id.login_forget);
		login_forget.setOnClickListener(this);
		scrollView = (ScrollView)findViewById(R.id.login_scroller);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			case R.id.login_btn:
				Toast.makeText(this, "即将登陆", Toast.LENGTH_SHORT).show();
				submit();
				break;
			case R.id.login_account_edit_clear:
				login_edit_account.setText("");
				break;
			case R.id.login_key_edit_clear:
				login_edit_key.setText("");
				break;
			case R.id.login_regist:
				Toast.makeText(this, "即将注册", Toast.LENGTH_SHORT).show();
				goRegisterActivity();
				break;
			case R.id.login_forget:
				Toast.makeText(this, "重新注册", Toast.LENGTH_SHORT).show();
				break;
			case R.id.login_layout:
				((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
				
			default:
				break;
		}
		
	}
	
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_edit_account:
			if (hasFocus) {
				login_account_edit_clear.setVisibility(View.VISIBLE);
			}else {
				login_account_edit_clear.setVisibility(View.GONE);
			}
			break;
		case R.id.login_edit_key:
			if (hasFocus) {
				login_key_edit_clear.setVisibility(View.VISIBLE);
			}else {
				login_key_edit_clear.setVisibility(View.GONE);
			}
		default:
			break;
		}
		
		
	}
	
	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
		if (s!=null) {
			if (s.equals(login_edit_account.getText())) {
				
				login_user.setBackgroundResource(R.drawable.login_user_hightlighted);
			}
			if (s.equals(login_edit_key.getText())) {
				login_key.setBackgroundResource(R.drawable.login_key_hightlighted);
			}
			if (s.length()==0) {
				
				if (login_edit_account.getText().length()==0) {
					login_user.setBackgroundResource(R.drawable.login_user);
				}
				if(login_edit_key.getText().length()==0){
					login_key.setBackgroundResource(R.drawable.login_key);
				}
				
				
			}
		}
		
	}
	
	// 点击注册按钮后的响应-跳转到注册界面
	public void goRegisterActivity() {
		Intent intent = new Intent();
		intent.setClass(this, RegisterActivity.class);
		startActivity(intent);
	}

	// 点击登陆按钮后的响应-提交信息到服务器
	public void submit() {
		String accounts = login_edit_account.getText().toString();
		String password = login_edit_key.getText().toString();
		if (accounts.length() == 0 || password.length() == 0) {
			DialogUtil.showDialog(this, "输入有误", "账号或密码不能为空", false);
		} else {
			try {
				List<VoteMessageModel> messagelist=new ArrayList<VoteMessageModel>();
				messagelist.addAll(getMessageList(accounts, password));
				if(messagelist.size()>0){
				if (messagelist.get(0).getID() != 0) {
					SharedPreferenceUtil sp=new SharedPreferenceUtil(this,Constants.SAVE_VOTER);
					sp.setName(accounts);
					sp.setPasswd(password);
					MyApplication.messagelist=messagelist;
					goMainActivity();
				} else {
					DialogUtil.showDialog(this, "登陆失败", "账号密码输入有误！", false);
				}
				}else{
					goMainActivity();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void goMainActivity(){
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent);
		this.finish();
	}
	public List<VoteMessageModel> getMessageList(String name, String password) throws Exception {
		VoterModel voter = new VoterModel();
		voter.setName(name);
		voter.setPassword(password);
		Map<String, String> map = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();
		String voterjson = mapper.writeValueAsString(voter);
		map.put("json", voterjson);
		String url = Constants.BASE_URL + "/LoginServlet";
		String result = HttpUtil.postRequest(url, map);
		List<VoteMessageModel> messagelist=new ArrayList<VoteMessageModel>();
		VoteMessageModel messageArr[]=mapper.readValue(result, VoteMessageModel[].class);
		messagelist=Arrays.asList(messageArr);
		for(int i=0;i<messagelist.size();i++){
			System.out.println(messagelist.get(i).getID()+":"+messagelist.get(i).toString());
		}
		return messagelist;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
