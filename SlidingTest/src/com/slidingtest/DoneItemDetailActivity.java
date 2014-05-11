package com.slidingtest;
import com.sldingtest.beans.DoneItemBean;
import com.slidingtest.constant.CommonFields;
import com.slidingtest.widget.TopBar;
import com.slidingtest.widget.TopBar.HeaderButtons;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class DoneItemDetailActivity extends Activity implements HeaderButtons {
    private TextView content,dateTime;
    private TopBar topbar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doneitemdetail);
        initHeader();
        content = (TextView) findViewById(R.id.content);
        dateTime = (TextView) findViewById(R.id.dateTime);
        DoneItemBean item = (DoneItemBean)getIntent().getSerializableExtra(CommonFields.SELECTED_ITEM);  
        content.setText(item.getContent());
        dateTime.setText(item.getDate()+" "+item.getTime());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.done_item_detail, menu);
        return true;
    }

    @Override
    public void onLeftButtonClicked() {
        // TODO Auto-generated method stub
        Intent intent = new Intent(DoneItemDetailActivity.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void initHeader() {
        // TODO Auto-generated method stub
        topbar = (TopBar) findViewById(R.id.topbar);
        topbar.setButtonsClickCallback(this);
        topbar.setTitleText("My Item Detail");
    }

}
