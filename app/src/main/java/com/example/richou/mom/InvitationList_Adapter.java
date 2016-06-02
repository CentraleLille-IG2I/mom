package com.example.richou.mom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

import MomApi.Model.EventStatus;
import MomApi.Model.Invitation;

/**
 * Created by richou on 02/06/16.
 */
public class InvitationList_Adapter extends BaseAdapter {
    private List<Invitation> invitations;
    private LayoutInflater mInflater;

    public InvitationList_Adapter(Context context, List<Invitation> invitations) {
        super();
        this.invitations = invitations;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return invitations.size();
    }

    @Override
    public Object getItem(int position) {
        return invitations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if(convertView != null)
            view  = convertView;
        else
            view = mInflater.inflate(R.layout.simple_line, null);

        ((TextView)((ViewGroup)view).getChildAt(0)).setText(invitations.get(position).getInvited().getFullName());
        ((TextView)((ViewGroup)view).getChildAt(1)).setText(invitations.get(position).getStatusStringResourceId());

        return view;
    }
}
