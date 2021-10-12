package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DashboardGeneric_Adapter extends ArrayAdapter<DashboardGeneric_Cell> {

    public DashboardGeneric_Adapter(@NonNull Context context, int resource, @NonNull ArrayList<DashboardGeneric_Cell> objects) {
        super(context, resource, objects);

    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //String name = getItem(position).getName();
        String Apptime = getItem(position).getAppTime();
       // String Birthday = getItem(position).getBirthday();

        DashboardGeneric_Cell appointment = new DashboardGeneric_Cell( Apptime);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dashboard_generic_adapter_view, parent, false);
        }

       // TextView tvName = (TextView) convertView.findViewById(R.id.Textview1);
        TextView tvApptime = (TextView) convertView.findViewById(R.id.Textview1);
        //TextView tvBirthday = (TextView) convertView.findViewById(R.id.Textview3);

       // tvName.setText(name);
        tvApptime.setText(Apptime);
       // tvBirthday.setText(Birthday);
        return convertView;
    }
}
