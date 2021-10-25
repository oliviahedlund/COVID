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
    private List<UptakeStat> uptakeStats;

    private String [] filteredDataSet_countyStats;
    private String [] filteredDataSet_uptakeStats;

    private Covid_Tracking_ListViewAdapter adapter_countyStats;
    private Covid_Tracking_ListViewAdapter adapter_uptakeStats;

    private Covid_tracking_listviewCell population;
    private Covid_tracking_listviewCell minFirstDoseAmount;
    private Covid_tracking_listviewCell fullVaccinationAmount;
    private Covid_tracking_listviewCell minFirstDoseRatio;
    private Covid_tracking_listviewCell fullVaccinationRatio;

    private Covid_tracking_listviewCell vaccinatedAmount;
    private Covid_tracking_listviewCell vaccinatedRatio;

    private String[] countyNames_countyStats;
    private String[] countyNames_uptakeStats;
    private String[] week;
    private String[] year;
    private String[] dose;
    private String[] ageGroup;

    private String selectedCounty_countyStats;
    private String selectedAgeGroup;

    private String selectedYear;
    private String selectedWeek;
    private String selectedCounty_uptakeStats;
    private String selectedDose;

    private CovidTrackVaccinationHelper covidTrackVaccinationHelper;

    private AutoCompleteTextView countyFilter_countyStats;
    private AutoCompleteTextView ageGroupFilter;

    private AutoCompleteTextView countyFilter_uptakeStats;
    private AutoCompleteTextView weekFilter;
    private AutoCompleteTextView yearFilter;
    private AutoCompleteTextView doseFilter;


    private ArrayList<Covid_tracking_listviewCell> listViewItems_countyStats = new ArrayList<Covid_tracking_listviewCell>();
    private ArrayList<Covid_tracking_listviewCell> listViewItems_uptakeStats = new ArrayList<Covid_tracking_listviewCell>();

    private ListView listView_countyStats;
    private ListView listView_uptakeStats;

    private Button applyFilterButton_countyStats;
    private Button applyFilterButton_uptakeStats;
    private Button resetFilterButton_countyStats;
    private Button resetFilterButton_uptakeStats;

    private View view;
    private UserResponse user;
    private Covid_Tracking_ListViewAdapter adapter;

    private String[] selectedDataSet_countyStats;
    private String[] selectedDataSet_uptakeStats;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.covid_tracking_tab_vaccination_stat, container, false);
        user = (UserResponse) getActivity().getIntent().getSerializableExtra("userInfo");

        listView_countyStats = (ListView) view.findViewById(R.id.listView);
        listView_uptakeStats = (ListView) view.findViewById(R.id.listView1);

        covidTrackVaccinationHelper = new CovidTrackVaccinationHelper(this);
        covidTrackVaccinationHelper.API_getVaccinationStats(getActivity(), user, new Runnable() {
            @Override
            public void run() {

                countyVaxStats = covidTrackVaccinationHelper.getCountyVaxStats();
                uptakeStats = covidTrackVaccinationHelper.getUptakeStats();

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
        countyNames_uptakeStats = covidTrackVaccinationHelper.getCountyNames_uptakeStats();
        week = covidTrackVaccinationHelper.getWeek();
        year = covidTrackVaccinationHelper.getYear();
        dose = new String[]{"1", "2"};
        ageGroup = covidTrackVaccinationHelper.getAgeGroup();

        selectedCounty_countyStats = countyNames_countyStats[FIRST_ON_LIST];
        selectedAgeGroup = ageGroup[FIRST_ON_LIST];

        selectedCounty_uptakeStats = countyNames_uptakeStats[FIRST_ON_LIST];
        selectedWeek = week[FIRST_ON_LIST];
        selectedYear = year[FIRST_ON_LIST];
        selectedDose = dose[FIRST_ON_LIST];

        countyFilter_countyStats = (AutoCompleteTextView) view.findViewById(R.id.countyFilter);
        ageGroupFilter = (AutoCompleteTextView) view.findViewById(R.id.ageGroupFilter);

        countyFilter_uptakeStats = (AutoCompleteTextView) view.findViewById(R.id.countyFilter1);
        weekFilter = (AutoCompleteTextView) view.findViewById(R.id.weekFilter);
        yearFilter = (AutoCompleteTextView) view.findViewById(R.id.yearFilter);
        doseFilter = (AutoCompleteTextView) view.findViewById(R.id.doseFilter);

        countyFilter_countyStats.setText(countyNames_countyStats[0]);
        ageGroupFilter.setText(ageGroup[0]);

        countyFilter_uptakeStats.setText(countyNames_uptakeStats[0]);
        weekFilter.setText(week[0]);
        yearFilter.setText(year[0]);
        doseFilter.setText(dose[0]);

        countyFilter_countyStats.setAdapter(new Simple_DropdownAdapter(this.getContext(), R.layout.simple_dropdown_item, countyNames_countyStats));
        ageGroupFilter.setAdapter(new Simple_DropdownAdapter(this.getContext(), R.layout.simple_dropdown_item, ageGroup));

        countyFilter_uptakeStats.setAdapter(new Simple_DropdownAdapter(this.getContext(), R.layout.simple_dropdown_item, countyNames_uptakeStats));
        weekFilter.setAdapter(new Simple_DropdownAdapter(this.getContext(), R.layout.simple_dropdown_item, week));
        yearFilter.setAdapter(new Simple_DropdownAdapter(this.getContext(), R.layout.simple_dropdown_item, year));
        doseFilter.setAdapter(new Simple_DropdownAdapter(this.getContext(), R.layout.simple_dropdown_item, dose));

    }

    private void setupListViewItems(){
        collectSelectedDataSets();
        fillListViewItemList();
        adapter_countyStats = new Covid_Tracking_ListViewAdapter(this.getContext(), 0, listViewItems_countyStats);
        adapter_uptakeStats = new Covid_Tracking_ListViewAdapter(this.getContext(), 0, listViewItems_uptakeStats);
        listView_countyStats.setAdapter(adapter_countyStats);
        listView_uptakeStats.setAdapter(adapter_uptakeStats);
    }

    private void collectSelectedDataSets(){
        selectedDataSet_countyStats = new String[]{selectedCounty_countyStats, selectedAgeGroup};
        selectedDataSet_uptakeStats = new String[]{selectedYear, selectedWeek, selectedCounty_uptakeStats, selectedDose};
    }

    private void fillListViewItemList(){

        filteredDataSet_countyStats = covidTrackVaccinationHelper.getFilteredDataSet(selectedDataSet_countyStats);

        population = new Covid_tracking_listviewCell("Population: ", String.valueOf(filteredDataSet_countyStats[0]));
        minFirstDoseAmount = new Covid_tracking_listviewCell("At least first dose amount: ", String.valueOf(filteredDataSet_countyStats[1]));
        fullVaccinationAmount = new Covid_tracking_listviewCell("Full vaccination amount: ", String.valueOf(filteredDataSet_countyStats[2]));
        minFirstDoseRatio = new Covid_tracking_listviewCell("At least first dose ratio: ", String.valueOf(filteredDataSet_countyStats[3]));
        fullVaccinationRatio = new Covid_tracking_listviewCell("Full vaccination ratio: ", String.valueOf(filteredDataSet_countyStats[4]));

        listViewItems_countyStats.clear();
        listViewItems_countyStats.add(population);
        listViewItems_countyStats.add(minFirstDoseAmount);
        listViewItems_countyStats.add(fullVaccinationAmount);
        listViewItems_countyStats.add(minFirstDoseRatio);
        listViewItems_countyStats.add(fullVaccinationRatio);

        filteredDataSet_uptakeStats = covidTrackVaccinationHelper.getFilteredDataSet(selectedDataSet_uptakeStats);

        vaccinatedAmount = new Covid_tracking_listviewCell("Cumulative vaccinated amount: ", String.valueOf(filteredDataSet_uptakeStats[0]));
        vaccinatedRatio = new Covid_tracking_listviewCell("Cumulative vaccinated ratio: ", String.valueOf(filteredDataSet_uptakeStats[1]));

        listViewItems_uptakeStats.clear();
        listViewItems_uptakeStats.add(vaccinatedAmount);
        listViewItems_uptakeStats.add(vaccinatedRatio);

    }

    private void updateListView(){
        collectSelectedDataSets();
        fillListViewItemList();
        adapter_countyStats.notifyDataSetChanged();
        adapter_uptakeStats.notifyDataSetChanged();
    }

    private void setupFilter(){
        applyFilterButton_countyStats = (Button) view.findViewById(R.id.applyFilterButton);
        applyFilterButton_uptakeStats = (Button) view.findViewById(R.id.applyFilterButton1);
        resetFilterButton_countyStats = (Button) view.findViewById(R.id.resetFilterButton);
        resetFilterButton_uptakeStats = (Button) view.findViewById(R.id.resetFilterButton1);

        // Buttons
        applyFilterButton_countyStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                updateListView(selectedCounty);
            }
        });

        applyFilterButton_uptakeStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                updateListView(selectedCounty);
            }
        });

        resetFilterButton_countyStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                updateListView(FIRST_ON_LIST);
//                countyFilter.setText(caseStats.get(0).getCountyName(), false);
            }
        });

        resetFilterButton_uptakeStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                updateListView(FIRST_ON_LIST);
//                countyFilter.setText(caseStats.get(0).getCountyName(), false);
            }
        });

        // Filters
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

        countyFilter_uptakeStats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectedCounty_uptakeStats = countyNames_uptakeStats[position];
            }
        });

        weekFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectedWeek = week[position];
            }
        });

        yearFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectedYear = year[position];
            }
        });

        doseFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectedDose = dose[position];
            }
        });
    }

}
