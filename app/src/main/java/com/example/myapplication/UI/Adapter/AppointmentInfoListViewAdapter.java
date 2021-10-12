package com.example.myapplication.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapplication.R;
import com.example.myapplication.UI.ViewCells.AppointmentInfo_ListViewCell;

import java.util.ArrayList;

public class AppointmentInfoListViewAdapter extends ArrayAdapter<AppointmentInfo_ListViewCell> {
    public AppointmentInfoListViewAdapter(@NonNull Context context, int resource, @NonNull ArrayList<AppointmentInfo_ListViewCell> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AppointmentInfo_ListViewCell item = getItem(position);

//        initialize the shape_cell view
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.appointment_info_listview_item, parent, false);
        }

        TextView label = (TextView) convertView.findViewById(R.id.listViewCellLabel1);
        TextView data = (TextView) convertView.findViewById(R.id.listViewCellData1);

        label.setText(item.getLabel());
        data.setText(item.getData());

        return convertView;
    }
}
