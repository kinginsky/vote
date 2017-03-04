package com.lg.vote.Activity;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lg.vote.R;
import com.lg.vote.Constants.Constants;
import com.lg.vote.model.VoterModel;
import com.lg.vote.util.CircularImage;
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
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class RegisterActivity extends Activity implements
OnFocusChangeListener,OnClickListener,TextWatcher {
	private Button register_btn,register_back_btn;
	EditText register_edit_account, register_edit_password, register_edit_password_confirm;
	ImageView register_account_edit_clear,register_key_edit_clear,register_key_confirm_edit_clear;
	ImageView register_user,register_key,register_key_confirm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		android.app.ActionBar actionBar = getActionBar();
		actionBar.hide();
		initView();
	}

	public void initView() {
		CircularImage cover_head_icon = (CircularImage)findViewById(R.id.register_cover_head_icon);
		cover_head_icon.setImageResource(R.drawable.ic_launcher);
		register_edit_account = (EditText) findViewById(R.id.register_edit_account);
		register_edit_password = (EditText) findViewById(R.id.register_edit_key);
		register_edit_password_confirm = (EditText) findViewById(R.id.register_edit_key_confirm);
		register_btn = (Button) super.findViewById(R.id.register_btn);
		register_btn.setOnClickListener(this);
		register_back_btn = (Button) super.findViewById(R.id.register_back_btn);
		register_back_btn.setOnClickListener(this);
		register_account_edit_clear = (ImageView)findViewById(R.id.register_account_edit_clear);
		register_account_edit_clear.setOnClickListener(this);
		register_key_edit_clear = (ImageView)findViewById(R.id.register_key_edit_clear);
		register_key_edit_clear.setOnClickListener(this);
		register_key_confirm_edit_clear=(ImageView)findViewById(R.id.register_key_confirm_edit_clear);
		register_key_confirm_edit_clear.setOnClickListener(this);
		register_user=(ImageView)findViewById(R.id.register_user);
		register_key=(ImageView)findViewById(R.id.register_key);
		register_key_confirm=(ImageView)findViewById(R.id.register_key_confirm);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

		switch (arg0.getId()) {
		case R.id.register_btn:
			register();
			break;
		case R.id.register_back_btn:
			backtoLoginActivity();
			break;
		default:
			break;
		}

	}
	
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.register_edit_account:
			if (hasFocus) {
				register_account_edit_clear.setVisibility(View.VISIBLE);
			}else {
				register_account_edit_clear.setVisibility(View.GONE);
			}
			break;
		case R.id.register_edit_key:
			if (hasFocus) {
				register_key_edit_clear.setVisibility(View.VISIBLE);
			}else {
				register_key_edit_clear.setVisibility(View.GONE);
			}
		case R.id.register_key_confirm:
			if (hasFocus) {
				register_key_confirm_edit_clear.setVisibility(View.VISIBLE);
			}else {
				register_key_confirm_edit_clear.setVisibility(View.GONE);
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
			if (s.equals(register_edit_account.getText())) {
				
				register_user.setBackgroundResource(R.drawable.login_user_hightlighted);
			}
			if (s.equals(register_edit_password.getText())) {
				register_key.setBackgroundResource(R.drawable.login_key_hightlighted);
			}
			if (s.length()==0) {
				
				if (register_edit_account.getText().length()==0) {
					register_user.setBackgroundResource(R.drawable.login_user);
				}
				if(register_edit_password.getText().length()==0){
					register_key.setBackgroundResource(R.drawable.login_key);
				}
				
				
			}
		}
		
	}
	
	/************* 点击注册按钮响应-向服务器传送注册消息 ******************/
	public void register() {
		String name =register_edit_account.getText().toString();
		String passwd = register_edit_password.getText().toString();
		String passwd1 =register_edit_password_confirm.getText().toString();
		if (name.equals("") || passwd.equals("") || passwd1.equals("")) {
			new AlertDialog.Builder(this).setTitle("注册失败")
					.setMessage("账号和密码不能为空").setPositiveButton("乐投", null)
					.create().show();
		} else {
			if (passwd.equals(passwd1)) {
				try {
					VoterModel voter = getVoter(name, passwd);
					if (voter.getName() != null) {
						new AlertDialog.Builder(this).setTitle("注册成功")
								.setMessage("返回登陆界面")
								.setPositiveButton("乐投", null).create().show();
						backtoLoginActivity();
					}else{
						new AlertDialog.Builder(this).setTitle("注册失败")
						.setMessage("请重新输入").setPositiveButton("乐投", null)
						.create().show();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				new AlertDialog.Builder(this).setTitle("注册失败")
						.setMessage("两次输入密码不一样").setPositiveButton("乐投", null)
						.create().show();
			}
		}
	}

	/**
	 * 
	 * @param name
	 * @param password
	 * @return
	 * @throws Exception
	 * @description if the process of registering succeed,the server returns the
	 *              voter information, otherwise,the name of the voter is null.
	 */
	public VoterModel getVoter(String name, String password) throws Exception {
		VoterModel voter = new VoterModel();
		voter.setName(name);
		voter.setPassword(password);
		Map<String, String> map = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();
		String voterjson = mapper.writeValueAsString(voter);
		map.put("json", voterjson);
		String url = Constants.BASE_URL + "/RegisterServlet";
		String result = HttpUtil.postRequest(url, map);
		voter = mapper.readValue(result, VoterModel.class);
		return voter;
	}

	/************* 点击返回按钮响应-返回上一层，取消注册 ******************/
	public void backtoLoginActivity() {
		Intent intent = new Intent();
		intent.setClass(this, LoginActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
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
