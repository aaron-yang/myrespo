package com.slidingtest;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {
    private Fragment mContent;
    private TextView title;
    private ImageButton menu;
    SlidingMenu sm = null;
    private String[] titles = {"添加任务项","我的设置"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setTitle(R.string.title);

        // set the Above View
        setContentView(R.layout.activity_main);
        title = (TextView)findViewById(R.id.title_textview);
        menu = (ImageButton) findViewById(R.id.menu);
       
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new DoneListActivity()).commit();

        sm = getSlidingMenu();
        sm.setShadowWidthRes(R.dimen.shadow_width);
        sm.setShadowDrawable(R.drawable.shadow);
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        sm.setFadeDegree(0.35f);
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        // set the Behind View
        setBehindContentView(R.layout.menu_frame);
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new FragmentFromMenuList()).commit();
        
        menu.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(sm.isMenuShowing()){
                    sm.toggle();
                } else {
                    sm.showMenu();
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (sm.isMenuShowing()) {
                toggle();
            } else {
                showMenu();
            }
        }
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if (sm.isMenuShowing()) {
                toggle();
            } else {
               if(check(getTitleText())){
                   getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new DoneListActivity()).commit();
               }else{
                   this.finish();
               }
            }
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(outState == null){
            getSupportFragmentManager().putFragment(outState, "mContent", mContent);
        }
    }

    public void switchContent(Fragment fragment) {
        mContent = fragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
        getSlidingMenu().showContent();
    }
    
    public void changeTitle(String titleText){
        title.setText(titleText);
    }
    
    public String getTitleText(){
        return title.getText().toString();
    }
    
    private boolean check(String title){
        for(String temp : titles){
            if(temp.equals(title)){
                return true;
            }
        }
        return false;
    }

}
