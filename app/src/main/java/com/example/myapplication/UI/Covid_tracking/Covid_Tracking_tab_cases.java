package com.example.myapplication.UI.Covid_tracking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import com.example.myapplication.API.Model.Covid_tracking.CaseStat;
import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.Helpers.CovidTrackCaseHelper;
import com.example.myapplication.R;
import com.example.myapplication.UI.Adapter.Simple_DropdownAdapter;
import com.example.myapplication.UI.Adapter.Covid_Tracking_ListViewAdapter;
import com.example.myapplication.UI.LoadingAnimation;
import com.example.myapplication.UI.ViewCells.Covid_Tracking_ListViewCell;

import java.util.ArrayList;
import java.util.List;


public class Covid_Tracking_tab_cases extends Fragment {
    private static final int FIRST_ON_LIST = 0;

    private List<CaseStat> caseStats;
    private int selectedCounty = FIRST_ON_LIST;
    private int [] filteredDataSet;
    private Covid_Tracking_ListViewAdapter adapter;
    private Covid_Tracking_ListViewCell totalCaseCount;
    private Covid_Tracking_ListViewCell totalIntensiveCareCount;
    private Covid_Tracking_ListViewCell totalDeathCount;

    private String[] countyNames;
    private CovidTrackCaseHelper covidTrackCaseHelper;
    private AutoCompleteTextView countyFilter;
    private ArrayList<Covid_Tracking_ListViewCell> listViewItems = new ArrayList<Covid_Tracking_ListViewCell>();
    private ListView listView;

    private Button resetFilterButton;

    private View view;
    private UserResponse user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.covid_tracking_tab_cases, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        user = (UserResponse) getActivity().getIntent().getSerializableExtra("userInfo");
        covidTrackCaseHelper = new CovidTrackCaseHelper(this);
        covidTrackCaseHelper.API_getCases(getActivity(), user, new Runnable() {
            @Override
            public void run() {
                caseStats = covidTrackCaseHelper.getCaseStats();
                setupDropdownMenus();
                setupFilter();
                setupListViewItems();
                LoadingAnimation.dismissLoadingAnimation();
            }
        });
        LoadingAnimation.startLoadingAnimation(getActivity());
        return view;
    }

    private void setupDropdownMenus(){
        countyNames = covidTrackCaseHelper.getCountyNames();

        countyFilter = (AutoCompleteTextView) view.findViewById(R.id.countyFilter);
        countyFilter.setText(caseStats.get(0).getCountyName());
        Simple_DropdownAdapter countyAdapter = new Simple_DropdownAdapter(this.getContext(), R.layout.simple_dropdown_item, countyNames);

        countyFilter.setAdapter(countyAdapter);

    }

    private void setupListViewItems(){
        fillListViewItemList(selectedCounty);
        adapter = new Covid_Tracking_ListViewAdapter(this.getContext(), 0, listViewItems);
        listView.setAdapter(adapter);
    }

    private void fillListViewItemList(int selectedCounty){
        filteredDataSet = covidTrackCaseHelper.getFilteredDataSet(selectedCounty);

        totalCaseCount = new Covid_Tracking_ListViewCell(getResources().getString(R.string.totalCases), String.valueOf(filteredDataSet[0]));
        totalIntensiveCareCount = new Covid_Tracking_ListViewCell(getResources().getString(R.string.totalIntensive), String.valueOf(filteredDataSet[1]));
        totalDeathCount = new Covid_Tracking_ListViewCell(getResources().getString(R.string.totalDeaths), String.valueOf(filteredDataSet[2]));

        listViewItems.clear();
        listViewItems.add(totalCaseCount);
        listViewItems.add(totalIntensiveCareCount);
        listViewItems.add(totalDeathCount);
    }

    private void updateListView(int selectedCounty){
        fillListViewItemList(selectedCounty);
        adapter.notifyDataSetChanged();
    }

    private void setupFilter(){
        resetFilterButton = (Button) view.findViewById(R.id.resetFilterButton);
        resetFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateListView(FIRST_ON_LIST);
                countyFilter.setText(caseStats.get(0).getCountyName(), false);
            }
        });

        countyFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectedCounty = position;
                updateListView(selectedCounty);
            }
        });
    }
}