package com.example.myapplication.UI.AdminBooking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.UI.Adapter.QuestionnaireAdapter;
import com.example.myapplication.R;

public class AdminNewQuestionnaires extends Fragment {


    public AdminNewQuestionnaires() {
        // Required empty public constructor
    }
    String array[];
    View view;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.questionnaire_fragment, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewQuestionnaire);
        array = getResources().getStringArray(R.array.questions);

        QuestionnaireAdapter adapter = new QuestionnaireAdapter(getContext(), array, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}