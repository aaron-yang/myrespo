package com.slidingtest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sldingtest.beans.DoneItemBean;
import com.slidingtest.constant.CommonFields;
import com.slidingtest.provider.DbDataStore;
import com.slidingtest.provider.DbProvider;
import com.slidingtest.provider.UriHelper;
import com.slidingtest.utils.Utils;

public class EditDoneItemActivity extends Activity implements OnClickListener {
    private Button leftBtn, rightBtn;
    private EditText editContent;
    DoneItemBean item;
    private TextView dateAndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_done_item);
        leftBtn = (Button) findViewById(R.id.leftBtn);
        rightBtn = (Button) findViewById(R.id.rightBtn);
        dateAndTime = (TextView) findViewById(R.id.dateTime);
        leftBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);
        item = (DoneItemBean) getIntent().getSerializableExtra(CommonFields.SELECTED_ITEM);
        editContent = (EditText) findViewById(R.id.editContent);
        editContent.setText(item.getContent());
        editContent.setSelection(editContent.getText().length());
        dateAndTime.setText("Last Modify Time: " + item.getDate() + " " + item.getTime());
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
        case R.id.leftBtn:
            this.finish();
            break;
        case R.id.rightBtn:
            Uri doneListUri = UriHelper.getUri(DbProvider.DONELIST_TABLE);
            ContentValues values = new ContentValues();
            String[] dateAndTime = Utils.getCurrentDateAndTime();
            values.put(DbDataStore.DoneListTable.CONTENT, editContent.getText().toString());
            values.put(DbDataStore.DoneListTable.DATE, dateAndTime[0]);
            values.put(DbDataStore.DoneListTable.TIME, dateAndTime[1]);
            AlertDialog.Builder builder = new Builder(EditDoneItemActivity.this);
            builder.setTitle("Status");
            if (v.getContext().getContentResolver()
                    .update(doneListUri, values, DbDataStore.DoneListTable._ID + "=" + item.getId(), null) == 1) {
                builder.setMessage("Save Done!");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        EditDoneItemActivity.this.finish();
                    }
                    
                });
                
            } else {
                builder.setMessage("Save failed!");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.cancel();
                    }
                    
                });
            }
            builder.create().show();
            break;
        }
    }
}
