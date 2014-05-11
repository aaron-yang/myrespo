package com.slidingtest;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.slidingtest.provider.DbDataStore;
import com.slidingtest.provider.DbProvider;
import com.slidingtest.provider.UriHelper;
import com.slidingtest.utils.Utils;

public class AddDoneItemActivity extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.adddoneitem, container, false);
        final EditText doneContent = (EditText) v.findViewById(R.id.doneContent);
        Button save = (Button) v.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String content = doneContent.getText().toString();
                if ("".equals(content)) {
                    Toast.makeText(v.getContext(), "You done nothing, please enter something before saving !!!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    String[] dateAndTime = Utils.getCurrentDateAndTime();
                    Uri doneListUri = UriHelper.getUri(DbProvider.DONELIST_TABLE);
                    ContentValues values = new ContentValues();
                    values.put(DbDataStore.DoneListTable.CONTENT, content);
                    values.put(DbDataStore.DoneListTable.DATE, dateAndTime[0]);
                    values.put(DbDataStore.DoneListTable.TIME, dateAndTime[1]);
                    if (v.getContext().getContentResolver().insert(doneListUri, values) != null) {
                        AlertDialog.Builder builder = new Builder(v.getContext());
                        builder.setTitle("Status");
                        builder.setMessage("Add Done!");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                Utils.changeTitleText(getActivity());
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new DoneListActivity()).commit();
                            }
                            
                        });
                        builder.create().show();
                    }else{
                        Toast.makeText(v.getContext(), "Save failed !!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return v;
    }

    @Override
    public void onDetach() {
        // TODO Auto-generated method stub
        Utils.changeTitleText(getActivity());
        super.onDetach();
    }
    
}
