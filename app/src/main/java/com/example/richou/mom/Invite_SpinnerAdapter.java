package com.example.richou.mom;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import MomApi.Model.Event;
import MomApi.Model.Rank;
import MomApi.MomApi;
import MomApi.RequestCallback;
import MomApi.MomErrors;

/**
 * Created by richou on 02/06/16.
 */
public class Invite_SpinnerAdapter implements SpinnerAdapter {
    List<Rank> ranks;
    LayoutInflater mInflater;

    public Invite_SpinnerAdapter(Context context, List<Rank> ranks) {
        this.ranks = ranks;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return ranks.size();
    }

    @Override
    public Object getItem(int i) {
        return ranks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return ranks.get(i).getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return ranks.isEmpty();
    }

    @Override
    public View getDropDownView(int i, View convertView, ViewGroup parent) {
        View view = null;

        if(convertView != null)
            view  = convertView;
        else
            view = mInflater.inflate(R.layout.simple_line, null);

        ((TextView)((ViewGroup)view).getChildAt(0)).setText(ranks.get(i).getName());
        ((TextView)((ViewGroup)view).getChildAt(1)).setText(ranks.get(i).getDescription());

        return view;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = null;

        if(convertView != null)
            view  = convertView;
        else
            view = mInflater.inflate(R.layout.simple_line, null);

        ((TextView)((ViewGroup)view).getChildAt(0)).setText(ranks.get(i).getName());
        ((TextView)((ViewGroup)view).getChildAt(1)).setText(ranks.get(i).getDescription());

        return view;
    }
}
