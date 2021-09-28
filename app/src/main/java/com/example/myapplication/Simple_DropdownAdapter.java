package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class Simple_DropdownAdapter extends ArrayAdapter<String> {

    LayoutInflater layoutInflater;


    public Simple_DropdownAdapter(@NonNull Context context, int resource, @NonNull String[] objects) {
        super(context, resource, objects);
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if(convertView == null)
            convertView = layoutInflater.inflate(R.layout.simple_dropdown_item, parent,false);

        String item = getItem(position);
        TextView textView = (TextView)convertView.findViewById(R.id.dropdownCell);
        textView.setText(item);
        return convertView;
    }
}
