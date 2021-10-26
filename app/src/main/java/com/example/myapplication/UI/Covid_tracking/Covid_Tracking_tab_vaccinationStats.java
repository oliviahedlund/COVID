package com.example.myapplication.UI.Covid_tracking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.API.Model.Covid_tracking.CaseStat;
import com.example.myapplication.API.Model.Covid_tracking.CountyVaxStat;
import com.example.myapplication.API.Model.Covid_tracking.UptakeStat;
import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.Helpers.CovidTrackCaseHelper;
import com.example.myapplication.Helpers.CovidTrackVaccinationHelper;
import com.example.myapplication.R;
import com.example.myapplication.UI.Adapter.Covid_Tracking_ListViewAdapter;
import com.example.myapplication.UI.Adapter.Simple_DropdownAdapter;
import com.example.myapplication.UI.LoadingAnimation;
import com.example.myapplication.UI.ViewCells.Covid_tracking_listviewCell;

import java.util.ArrayList;
import java.util.List;

public class Covid_Tracking_tab_vaccinationStats extends Fragment {
    private static final int FIRST_ON_LIST = 0;

    private List<CountyVaxStat> countyVaxStats;


    private String [] filteredDataSet_countyStats;

    private Covid_Tracking_ListViewAdapter adapter_countyStats;

    private Covid_tracking_listviewCell population;
    private Covid_tracking_listviewCell minFirstDoseAmount;
    private Covid_tracking_listviewCell fullVaccinationAmount;
    private Covid_tracking_listviewCell minFirstDoseRatio;
    private Covid_tracking_listviewCell fullVaccinationRatio;

    private String[] countyNames_countyStats;
    private String[] ageGroup;

    private String selectedCounty_countyStats;
    private String selectedAgeGroup;

    private CovidTrackVaccinationHelper covidTrackVaccinationHelper;

    private AutoCompleteTextView countyFilter_countyStats;
    private AutoCompleteTextView ageGroupFilter;

    private ArrayList<Covid_tracking_listviewCell> listViewItems_countyStats = new ArrayList<Covid_tracking_listviewCell>();

    private ListView listView_countyStats;

    private Button applyFilterButton_countyStats;
    private Button resetFilterButton_countyStats;


    private View view;
    private UserResponse user;

    private String[] selectedDataSet_countyStats;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.covid_tracking_tab_vaccination_stat, container, false);
        user = (UserResponse) getActivity().getIntent().getSerializableExtra("userInfo");

        listView_countyStats = (ListView) view.findViewById(R.id.listView);

        covidTrackVaccinationHelper = new CovidTrackVaccinationHelper(this);
        covidTrackVaccinationHelper.API_getVaccinationStats(getActivity(), user, new Runnable() {
            @Override
            public void run() {
                countyVaxStats = covidTrackVaccinationHelper.getCountyVaxStats();

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
        countyNames_countyStats = covidTrackVaccinationHelper.getCountyNames_countyStats();
        ageGroup = covidTrackVaccinationHelper.getAgeGroup();

        selectedCounty_countyStats = countyNames_countyStats[FIRST_ON_LIST];
        selectedAgeGroup = ageGroup[FIRST_ON_LIST];

        countyFilter_countyStats = (AutoCompleteTextView) view.findViewById(R.id.countyFilter);
        ageGroupFilter = (AutoCompleteTextView) view.findViewById(R.id.ageGroupFilter);

        countyFilter_countyStats.setText(countyNames_countyStats[0]);
        ageGroupFilter.setText(ageGroup[0]);

        countyFilter_countyStats.setAdapter(new Simple_DropdownAdapter(this.getContext(), R.layout.simple_dropdown_item, countyNames_countyStats));
        ageGroupFilter.setAdapter(new Simple_DropdownAdapter(this.getContext(), R.layout.simple_dropdown_item, ageGroup));

    }

    private void setupListViewItems(){
        collectSelectedDataSets();
        fillListViewItemList();
        adapter_countyStats = new Covid_Tracking_ListViewAdapter(this.getContext(), 0, listViewItems_countyStats);
        listView_countyStats.setAdapter(adapter_countyStats);
    }

    private void collectSelectedDataSets(){
        selectedDataSet_countyStats = new String[]{selectedCounty_countyStats, selectedAgeGroup};
    }

    private void fillListViewItemList(){

        filteredDataSet_countyStats = covidTrackVaccinationHelper.getFilteredDataSet(selectedDataSet_countyStats);

        population = new Covid_tracking_listviewCell(getResources().getString(R.string.population), String.valueOf(filteredDataSet_countyStats[0]));
        minFirstDoseAmount = new Covid_tracking_listviewCell(getResources().getString(R.string.minFirstDoseAmount), String.valueOf(filteredDataSet_countyStats[1]));
        fullVaccinationAmount = new Covid_tracking_listviewCell(getResources().getString(R.string.fullVaccinationAmount), String.valueOf(filteredDataSet_countyStats[2]));
        minFirstDoseRatio = new Covid_tracking_listviewCell(getResources().getString(R.string.minFirstDoseRatio), String.valueOf(filteredDataSet_countyStats[3]));
        fullVaccinationRatio = new Covid_tracking_listviewCell(getResources().getString(R.string.fullVaccinationRatio), String.valueOf(filteredDataSet_countyStats[4]));

        listViewItems_countyStats.clear();
        listViewItems_countyStats.add(population);
        listViewItems_countyStats.add(minFirstDoseAmount);
        listViewItems_countyStats.add(fullVaccinationAmount);
        listViewItems_countyStats.add(minFirstDoseRatio);
        listViewItems_countyStats.add(fullVaccinationRatio);

    }

    private void updateListView(){
        collectSelectedDataSets();
        fillListViewItemList();
        adapter_countyStats.notifyDataSetChanged();
    }

    private void setupFilter(){
        applyFilterButton_countyStats = (Button) view.findViewById(R.id.applyFilterButton);
        resetFilterButton_countyStats = (Button) view.findViewById(R.id.resetFilterButton);

        applyFilterButton_countyStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateListView();
            }
        });

        resetFilterButton_countyStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCounty_countyStats = countyNames_countyStats[FIRST_ON_LIST];
                selectedAgeGroup = ageGroup[FIRST_ON_LIST];
                updateListView();
                countyFilter_countyStats.setText(selectedCounty_countyStats, false);
                ageGroupFilter.setText(selectedAgeGroup, false);
            }
        });

        countyFilter_countyStats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectedCounty_countyStats = countyNames_countyStats[position];
            }
        });

        ageGroupFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectedAgeGroup = ageGroup[position];
            }
        });
    }

}
