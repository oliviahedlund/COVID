package com.example.myapplication.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapplication.UI.ViewCells.Covid_Tracking_ListViewCell;
import com.example.myapplication.R;
import com.example.myapplication.UI.ViewCells.Covid_tracking_listviewCell;

import java.util.ArrayList;

public class Covid_Tracking_ListViewAdapter extends ArrayAdapter<Covid_tracking_listviewCell> {
    private ArrayList<Covid_tracking_listviewCell> mData;

    public Covid_Tracking_ListViewAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Covid_tracking_listviewCell> objects) {
        super(context, resource, objects);
        mData = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Covid_tracking_listviewCell item = getItem(position);

//        initialize the shape_cell view
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.covid_tracking_listview_item, parent, false);
        }

        TextView label = (TextView) convertView.findViewById(R.id.listViewCellLabel);
        TextView data = (TextView) convertView.findViewById(R.id.listViewCellData);

        label.setText(item.getLabel());

        if(item.getData() == null){
            data.setText("None");
        } else {
            data.setText(item.getData());
        }


        return convertView;
    }

    public ArrayList<Covid_tracking_listviewCell> getData(){
        return mData;
    }
}
