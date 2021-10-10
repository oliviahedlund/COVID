package com.example.myapplication.UI.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;


public class QuestionnaireAdapter_user extends RecyclerView.Adapter<QuestionnaireAdapter_user.QuestionnaireCell1> {

    private String array[];
    private Context context;
    private int [] answers;

    public QuestionnaireAdapter_user(Context c, String s[], int [] answers){
        this.array = s;
        this.context = c;
        this.answers = answers;
    }

    @NonNull
    @Override
    public QuestionnaireCell1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.questionnaire_cell_user, parent, false);
        return new QuestionnaireCell1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionnaireCell1 holder, @SuppressLint("RecyclerView") int position) {
        holder.question.setText(array[position]);

        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                View radioButton = radioGroup.findViewById(checkedId);
                int index = radioGroup.indexOfChild(radioButton);
                answers[position] = index;
            }
        });
    }

    @Override
    public int getItemCount() {
        return array.length;
    }

    public class QuestionnaireCell1 extends RecyclerView.ViewHolder{

        TextView question;
        RadioGroup radioGroup;

        public QuestionnaireCell1(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.questionQuestionnaire);
            radioGroup = itemView.findViewById(R.id.radioGroup);
        }
    }

}

