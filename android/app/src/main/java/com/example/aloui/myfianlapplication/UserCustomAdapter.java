package com.example.aloui.myfianlapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aloui.myfianlapplication.Entities.User;

import java.util.List;


import java.util.List;

public class UserCustomAdapter extends ArrayAdapter<User> {

    private int resourceId = 0;
    private LayoutInflater inflater;
    public Context mContext;

    public UserCustomAdapter(Context context, int resourceId, List<User> mediaItems) {
        super(context, 0, mediaItems);
        this.resourceId = resourceId;
        this.mContext = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    //ViewHolder Design Pattern
    static class ViewHolder {
        public TextView commentText;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        //Réutiliser les Views
        if (rowView == null) {
            rowView = inflater.inflate(resourceId, parent, false);
        }

        //Configuration du ViewHolder
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.commentText = (TextView) rowView.findViewById(R.id.nameItem2);
        rowView.setTag(viewHolder);

        //Affecter les données aux Views
        ViewHolder holder = (ViewHolder) rowView.getTag();
        User user = getItem(position);

        holder.commentText.setText(user.getUsername());
        notifyDataSetChanged();
        return rowView;
    }

}