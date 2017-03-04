package com.lg.vote.Activity;

import java.util.List;

import com.lg.vote.R;
import com.lg.vote.adapter.VoteMessageAdapter;
import com.lg.vote.fragment.InvitedFragment;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * @author LG
 *
 */
public class MainActivity extends FragmentActivity {
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mNavMenuTitles;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;


	private ListView mVoteMessageListView;
	private VoteMessageAdapter mVoteMessageAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ActionBar actionBar=getSupportActionBar();
	    //actionBar.hide();
		setContentView(R.layout.activity_main);
		//mVoteMessageListView = (ListView) findViewById(R.id.tab_vote_listView);
		initView();
		if (savedInstanceState == null) {
	        selectItem(0);
	    }
	}
	
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public void initView(){
		mDrawerTitle=mTitle=getTitle();
		mNavMenuTitles=getResources().getStringArray(R.array.nav_drawer_items);
		mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
		mDrawerList=(ListView)findViewById(R.id.left_drawer);
		
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,R.layout.drawer_list_item,
				mNavMenuTitles));
		 mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		 // 使actionbar能够控制drawer导航菜单
		 getActionBar().setDisplayHomeAsUpEnabled(true);
	     getActionBar().setHomeButtonEnabled(true);
	     //getActionBar().setIcon(R.drawable.ic_drawer);  
	     
	  // ActionBarDrawerToggle ties together the the proper interactions
	        // between the sliding drawer and the action bar app icon
	        mDrawerToggle = new ActionBarDrawerToggle(
	                this,                  /* host Activity */
	                mDrawerLayout,         /* DrawerLayout object */
	                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
	                R.string.drawer_open,  /* "open drawer" description for accessibility */
	                R.string.drawer_close  /* "close drawer" description for accessibility */
	                ) {
	            public void onDrawerClosed(View view) {
	                getActionBar().setTitle(mTitle);
	                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
	            }

	            public void onDrawerOpened(View drawerView) {
	                getActionBar().setTitle(mDrawerTitle);
	                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
	            }
	        };
	        mDrawerLayout.setDrawerListener(mDrawerToggle); 
	}

	
	 @Override
     public void onResume() {
         // TODO Auto-generated method stub
         super.onResume();
     
         //mVoteMessageAdapter=new VoteMessageAdapter(this,MyApplication.messagelist);
 		//mVoteMessageListView.setAdapter(mVoteMessageAdapter);
         //InvitedFragment.mVoteMessageAdapter.notifyDataSetChanged();
     }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    	//打开侧边导航栏以后，menu菜单的改变操作，比如隐藏某些menu.
        // If the nav drawer is open, hide action items related to the content view
        /*boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_refresh).setVisible(!drawerOpen);*/
        return super.onPrepareOptionsMenu(menu);
    }
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		 if (mDrawerToggle.onOptionsItemSelected(item)) {
	            return true;
	        }
	     switch(item.getItemId()) {
	        case R.id.action_search:
	            
	             Toast.makeText(this, R.string.action_search, Toast.LENGTH_SHORT).show();
	            
	            return true;
	        case R.id.action_edit:
	        	
	        	gotoPoll();
	        	
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	}
	
	/**
	 * 
	 * @author LG
	 *       监听导航菜单点击响应
	 */
	   private class DrawerItemClickListener implements ListView.OnItemClickListener {
	        @Override
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	            selectItem(position);
	        }
	    }
	   

	 /**
	    * 
	    * @param position
	    *       根据导航菜单选择项的变化做出相应选择
	    */
private void selectItem(int position){
	Fragment fragment = null;
	switch (position) {
	case 0:
		fragment = new InvitedFragment();
		
		break;
	case 1:
		fragment = new InvitedFragment();
		break;
	case 2:
		fragment = new InvitedFragment();
		break;
	case 3:
		fragment = new InvitedFragment();
		break;
	case 4:
		fragment = new InvitedFragment();
		break;
	case 5:
		fragment = new InvitedFragment();
		break;

	default:
		break;
	}
	
	if (fragment != null) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();

		// update selected item and title, then close the drawer
		mDrawerList.setItemChecked(position, true);
		mDrawerList.setSelection(position);
		setTitle(mNavMenuTitles[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	} else {
		// error in creating fragment
		Log.e("MainActivity", "Error in creating fragment");
	}
}
	
@Override
public void setTitle(CharSequence title) {
    mTitle = title;
    getActionBar().setTitle(mTitle);
}

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
	  
	public void gotoPoll(){
		Intent intent=new Intent(this,NewPollActivity.class);
		startActivity(intent);
	}
	 
}
