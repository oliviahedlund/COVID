package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Covid_Tracking_ListViewAdapter extends ArrayAdapter<Covid_Tracking_ListViewCell> {

    public Covid_Tracking_ListViewAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Covid_Tracking_ListViewCell> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Covid_Tracking_ListViewCell item = getItem(position);

//        initialize the shape_cell view
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.covid_tracking_listview_item, parent, false);
        }

        //ImageView image = (ImageView) convertView.findViewById(R.id.listViewCellImage);
        TextView label = (TextView) convertView.findViewById(R.id.listViewCellLabel);
        TextView data = (TextView) convertView.findViewById(R.id.listViewCellData);

        //image.setImageResource(item.getImage());
        label.setText(item.getLabel());

        if(item.getData() == null){
            data.setText("None");
        } else {
            data.setText(item.getData());
        }


        return convertView;
    }
}
