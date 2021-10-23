package com.example.myapplication.UI.Covid_tracking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.myapplication.API.Model.Covid_tracking.CaseStat;
import com.example.myapplication.Helpers.CovidTrackCaseHelper;
import com.example.myapplication.R;
import com.example.myapplication.UI.Adapter.Simple_DropdownAdapter;
import com.example.myapplication.UI.Adapter.Covid_Tracking_ListViewAdapter;
import com.example.myapplication.UI.ViewCells.Covid_tracking_listviewCell;

import java.util.ArrayList;
import java.util.List;


public class Covid_Tracking_tab_cases extends Fragment {
    private static final int SWEDEN = 0;

    private List<CaseStat> caseStats;
    private int selectedCounty = SWEDEN;
    private int [] filteredDataSet;
    private Covid_Tracking_ListViewAdapter adapter;
    private Covid_tracking_listviewCell totalCaseCount;
    private Covid_tracking_listviewCell totalIntensiveCareCount;
    private Covid_tracking_listviewCell totalDeathCount;

    private String[] countyNames;
    private CovidTrackCaseHelper covidTrackCaseHelper;
    private AutoCompleteTextView countyFilter;
    private ArrayList<Covid_tracking_listviewCell> listViewItems = new ArrayList<Covid_tracking_listviewCell>();
    private ListView listView;

    private Button filterButton;
    private Button applyFilterButton;
    private Button resetFilterButton;

    private boolean filterHidden = false;

    private LinearLayout filterTabs;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.covid_tracking_tab_cases, container, false);
        covidTrackCaseHelper = new CovidTrackCaseHelper();
        listView = (ListView) view.findViewById(R.id.listViewInCovidTracking_cases);

        caseStats = covidTrackCaseHelper.getCaseStats();
        setupDropdownMenus();
        setupFilter();
        setupListViewItems();

        return view;
    }


    private void setupDropdownMenus(){
        countyNames = covidTrackCaseHelper.getCountyNames();

        countyFilter = (AutoCompleteTextView) view.findViewById(R.id.countyFilter_cases);

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

        totalCaseCount = new Covid_tracking_listviewCell(getResources().getString(R.string.totalCases), String.valueOf(filteredDataSet[0]));
        totalIntensiveCareCount = new Covid_tracking_listviewCell(getResources().getString(R.string.totalIntensive), String.valueOf(filteredDataSet[1]));
        totalDeathCount = new Covid_tracking_listviewCell(getResources().getString(R.string.totalDeaths), String.valueOf(filteredDataSet[2]));

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
        applyFilterButton = (Button) view.findViewById(R.id.applyFilterButton);
        applyFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateListView(selectedCounty);
            }
        });

        resetFilterButton = (Button) view.findViewById(R.id.resetFilterButton);
        resetFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateListView(SWEDEN);
                countyFilter.clearListSelection();
            }
        });

        filterButton = (Button) view.findViewById(R.id.filterButton);
        filterTabs = (LinearLayout) view.findViewById(R.id.filterTabs);

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(filterHidden == true){
                    filterHidden = false;
                    showFilter();
                } else{
                    filterHidden = true;
                    hideFilter();
                }
            }
        });

        countyFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectedCounty = position;
            }
        });
    }

    private void hideFilter() {
        filterTabs.setVisibility(View.GONE);
        filterButton.setText("FILTER");
    }

    private void showFilter(){
        filterTabs.setVisibility(View.VISIBLE);
        filterButton.setText("HIDE");
    }
}