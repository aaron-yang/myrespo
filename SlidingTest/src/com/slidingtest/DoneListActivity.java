package com.slidingtest;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.sldingtest.beans.DoneItemBean;
import com.slidingtest.constant.CommonFields;
import com.slidingtest.provider.DbDataStore;
import com.slidingtest.provider.DbProvider;
import com.slidingtest.provider.UriHelper;

public class DoneListActivity extends Fragment {
    ListView list = null;
    ListAdapter listAdapter = null;
    private static final int EDIT_ID = 0;
    private static final int DELETE_ID = 1;
    Cursor cursor = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.donelist, container, false);
        list = (ListView) v.findViewById(R.id.doneList);
        cursor = v.getContext().getContentResolver()
                .query(UriHelper.getUri(DbProvider.DONELIST_TABLE), null, null, null, null);
        listAdapter = new SimpleCursorAdapter(v.getContext(), R.layout.donelistitem, cursor, new String[] {
                DbDataStore.DoneListTable.CONTENT, DbDataStore.DoneListTable.DATE, DbDataStore.DoneListTable.TIME },
                new int[] { R.id.doneContent, R.id.date, R.id.time }, 0);
        list.setAdapter(listAdapter);
        registerForContextMenu(list);    
        list.setOnItemClickListener(new OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                Cursor cursor = (Cursor) listAdapter.getItem(arg2);
                startActivityWithValues(cursor,DoneItemDetailActivity.class);
            }
            
        });
        return v;
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        AdapterContextMenuInfo  info = (AdapterContextMenuInfo)item.getMenuInfo();
        Cursor cursor = (Cursor) listAdapter.getItem(info.position);
        switch (item.getItemId()) {
        case EDIT_ID:
            startActivityWithValues(cursor,EditDoneItemActivity.class);
            return true;
        case DELETE_ID:
            Uri uri = UriHelper.getUri(DbProvider.DONELIST_TABLE);
            String selection = DbDataStore.DoneListTable.DATE + " =? and " + DbDataStore.DoneListTable.TIME + " =?";
            DoneListActivity.this.getActivity().getContentResolver().delete(uri, selection, new String[] {cursor.getString(2),cursor.getString(3)});
            cursor.requery();
            ((BaseAdapter) listAdapter).notifyDataSetChanged();  
            return true;
        default:
            return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Item Operation");
        menu.add(0, EDIT_ID, 0, "Edit");
        menu.add(0, DELETE_ID, 0, "Delete");
    }
    
    private void startActivityWithValues(Cursor cursor,Class className){
        DoneItemBean item = new DoneItemBean();
        item.setId(cursor.getInt(0));
        item.setContent(cursor.getString(1));
        item.setDate(cursor.getString(2));
        item.setTime(cursor.getString(3));
        Intent intent = new Intent(DoneListActivity.this.getActivity(),className);
        Bundle bundle = new Bundle();  
        bundle.putSerializable(CommonFields.SELECTED_ITEM,item);  
        intent.putExtras(bundle);  
        startActivity(intent);
    }


    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        cursor.requery();
        ((BaseAdapter) listAdapter).notifyDataSetChanged();  
        super.onResume();
    }
    
    

}
