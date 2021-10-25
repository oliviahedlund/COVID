package com.example.myapplication.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.API.Model.Appointment_user.AppointmentRequest;
import com.example.myapplication.Helpers.StringFormatHelper;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ValidateAppointmentAdapter extends RecyclerView.Adapter<ValidateAppointmentAdapter.ValidateCell>{
    private List<AppointmentRequest> array;
    private Context context;
    private boolean [] checkedList;
    private List<ValidateCell> validateCellList;
    private List<String> appointmentIdList;

    public ValidateAppointmentAdapter(Context c, List<AppointmentRequest> s){
        array = s;
        context = c;
        validateCellList = new ArrayList<ValidateCell>();
        checkedList = new boolean[array.size()];
        appointmentIdList = new ArrayList<String>();
    }

    public boolean[] getCheckedList() {
        return checkedList;
    }

    public String getAppointmentId(int index) {
        return appointmentIdList.get(index);
    }

    public AppointmentRequest getAppointment(int index){
        return array.get(index);
    }

    public void setAll(boolean bool){
        for (int i = 0; i < validateCellList.size(); i++) {
            validateCellList.get(i).checkBox.setChecked(bool);
        }
    }

    @NonNull
    @Override
    public ValidateCell onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.admin_validate_cell, parent, false);
        return new ValidateCell(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ValidateCell holder, int position) {
        validateCellList.add(position,holder);
        appointmentIdList.add(position,array.get(position).getId());

        holder.appointment.setText(StringFormatHelper.yearMonthDayTime(array.get(position).getTime()));
        holder.center.setText(array.get(position).getCenterName());
        holder.name.setText(array.get(position).getUserOfAppointment().getFirstName() + " " + array.get(position).getUserOfAppointment().getLastName());
        holder.birthDate.setText(StringFormatHelper.yearMonthDay(array.get(position).getUserOfAppointment().getBirthDate()));

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkedList[holder.getAdapterPosition()] = isChecked;
            }
        });

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class ValidateCell extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView appointment;
        TextView center;
        TextView name;
        TextView birthDate;
        CheckBox checkBox;

        public ValidateCell(@NonNull View itemView) {
            super(itemView);
            appointment = itemView.findViewById(R.id.tvAppointment);
            center = itemView.findViewById(R.id.tvCenter);
            name = itemView.findViewById(R.id.tvName);
            birthDate = itemView.findViewById(R.id.tvBirthDate);
            checkBox = itemView.findViewById(R.id.checkBox);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Boolean isChecked = checkBox.isChecked();
            checkBox.setChecked(!isChecked);
        }
    }
}
