package com.example.myapplication.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;


public class QuestionnaireAdapter extends RecyclerView.Adapter<QuestionnaireAdapter.QuestionnaireCell> {

    String array[];
    Context context;
    boolean clickable;
    public QuestionnaireAdapter(Context c, String s[], boolean _clickable){
        array = s;
        context = c;
        clickable = _clickable;
    }

    @NonNull
    @Override
    public QuestionnaireCell onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.questionnaire_cell, parent, false);
        return new QuestionnaireCell(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionnaireCell holder, int position) {
        holder.question.setText(array[position]);
        //skicka med array från ifyllt fromylär som representerar ja/nej på de 5 frågorna
        holder.checkNo.setChecked(true);
        if(!clickable){
            holder.checkYes.setClickable(false);
            holder.checkNo.setClickable(false);
        }
    }


    @Override
    public int getItemCount() {
        return array.length;
    }

    public class QuestionnaireCell extends RecyclerView.ViewHolder{

        TextView question;
        CheckBox checkYes;
        CheckBox checkNo;

        public QuestionnaireCell(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.questionQuestionnaire);
            checkYes = itemView.findViewById(R.id.checkBoxY);
            checkNo = itemView.findViewById(R.id.checkBoxN);
        }



    }

}

