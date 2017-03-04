package com.lg.vote.db;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {
	private SharedPreferences sp;
	private SharedPreferences.Editor editor ;
	
	public SharedPreferenceUtil(Context context, String file) {
		sp = context.getSharedPreferences(file, Context.MODE_PRIVATE);
		editor = sp.edit();
	}
	
	// 用户的昵称
	public String getName() {
		return sp.getString("name", "");
	}

	public void setName(String name) {
		editor.putString("name", name);
		editor.commit();
	}
	
	// 用户的密码
		public void setPasswd(String passwd) {
			editor.putString("passwd", passwd);
			editor.commit();
		}

		public String getPasswd() {
			return sp.getString("passwd", "");
		}
}
