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

import com.example.myapplication.API.Model.Covid_tracking.UptakeStat;
import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.Helpers.CovidTrackVaccinationHelper;
import com.example.myapplication.R;
import com.example.myapplication.UI.Adapter.Covid_Tracking_ListViewAdapter;
import com.example.myapplication.UI.Adapter.Simple_DropdownAdapter;
import com.example.myapplication.UI.LoadingAnimation;
import com.example.myapplication.UI.ViewCells.Covid_tracking_listviewCell;

import java.util.ArrayList;
import java.util.List;

public class Covid_Tracking_tab_vaccinationStats_cumulative extends Fragment {
    private static final int FIRST_ON_LIST = 0;

    private List<UptakeStat> uptakeStats;

    private String [] filteredDataSet_uptakeStats;

    private Covid_Tracking_ListViewAdapter adapter_uptakeStats;

    private Covid_tracking_listviewCell vaccinatedAmount;
    private Covid_tracking_listviewCell vaccinatedRatio;

    private String[] countyNames_uptakeStats;
    private String[] yearWeek;
    private String[] week;
    private String[] year;
    private String[] dose;

    private String selectedYearWeek;
    private String selectedCounty_uptakeStats;
    private String selectedDose;

    private CovidTrackVaccinationHelper covidTrackVaccinationHelper;


    private AutoCompleteTextView countyFilter_uptakeStats;
    private AutoCompleteTextView yearWeekFilter;
    private AutoCompleteTextView doseFilter;

    private ArrayList<Covid_tracking_listviewCell> listViewItems_uptakeStats = new ArrayList<Covid_tracking_listviewCell>();

    private ListView listView_uptakeStats;

    private Button applyFilterButton_uptakeStats;
    private Button resetFilterButton_uptakeStats;

    private View view;
    private UserResponse user;

    private String[] selectedDataSet_uptakeStats;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.covid_tracking_tab_vaccination_stat_cumulative, container, false);
        user = (UserResponse) getActivity().getIntent().getSerializableExtra("userInfo");


        listView_uptakeStats = (ListView) view.findViewById(R.id.listView);

        covidTrackVaccinationHelper = new CovidTrackVaccinationHelper(this);
        covidTrackVaccinationHelper.API_getVaccinationStats(getActivity(), user, new Runnable() {
            @Override
            public void run() {
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
        countyNames_uptakeStats = covidTrackVaccinationHelper.getCountyNames_uptakeStats();
        yearWeek = covidTrackVaccinationHelper.getYearWeek();
        dose = new String[]{"1", "2"};

        selectedCounty_uptakeStats = countyNames_uptakeStats[FIRST_ON_LIST];
        selectedYearWeek = yearWeek[FIRST_ON_LIST];
        selectedDose = dose[FIRST_ON_LIST];

        countyFilter_uptakeStats = (AutoCompleteTextView) view.findViewById(R.id.countyFilter);
        yearWeekFilter = (AutoCompleteTextView) view.findViewById(R.id.yearWeekFilter);
        doseFilter = (AutoCompleteTextView) view.findViewById(R.id.doseFilter);

        countyFilter_uptakeStats.setText(countyNames_uptakeStats[0]);
        yearWeekFilter.setText(yearWeek[0]);
        doseFilter.setText(dose[0]);

        countyFilter_uptakeStats.setAdapter(new Simple_DropdownAdapter(this.getContext(), R.layout.simple_dropdown_item, countyNames_uptakeStats));
        yearWeekFilter.setAdapter(new Simple_DropdownAdapter(this.getContext(), R.layout.simple_dropdown_item, yearWeek));
        doseFilter.setAdapter(new Simple_DropdownAdapter(this.getContext(), R.layout.simple_dropdown_item, dose));

    }

    private void setupListViewItems(){
        collectSelectedDataSets();
        fillListViewItemList();
        adapter_uptakeStats = new Covid_Tracking_ListViewAdapter(this.getContext(), 0, listViewItems_uptakeStats);
        listView_uptakeStats.setAdapter(adapter_uptakeStats);
    }

    private void collectSelectedDataSets(){
        selectedDataSet_uptakeStats = new String[]{selectedYearWeek, selectedCounty_uptakeStats, selectedDose};
    }

    private void fillListViewItemList(){

        filteredDataSet_uptakeStats = covidTrackVaccinationHelper.getFilteredDataSet(selectedDataSet_uptakeStats);

        vaccinatedAmount = new Covid_tracking_listviewCell(getResources().getString(R.string.cumulativeVaccinatedAmount), String.valueOf(filteredDataSet_uptakeStats[0]));
        vaccinatedRatio = new Covid_tracking_listviewCell(getResources().getString(R.string.cumulativeVaccinatedRatio), String.valueOf(filteredDataSet_uptakeStats[1]));

        listViewItems_uptakeStats.clear();
        listViewItems_uptakeStats.add(vaccinatedAmount);
        listViewItems_uptakeStats.add(vaccinatedRatio);

    }

    private void updateListView(){
        collectSelectedDataSets();
        fillListViewItemList();
        adapter_uptakeStats.notifyDataSetChanged();
    }

    private void setupFilter(){
        applyFilterButton_uptakeStats = (Button) view.findViewById(R.id.applyFilterButton);
        resetFilterButton_uptakeStats = (Button) view.findViewById(R.id.resetFilterButton);

        applyFilterButton_uptakeStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateListView();
            }
        });

        resetFilterButton_uptakeStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedYearWeek = yearWeek[FIRST_ON_LIST];
                selectedCounty_uptakeStats = countyNames_uptakeStats[FIRST_ON_LIST];
                selectedDose = dose[FIRST_ON_LIST];
                updateListView();
                countyFilter_uptakeStats.setText(selectedCounty_uptakeStats, false);
                yearWeekFilter.setText(selectedYearWeek, false);
                doseFilter.setText(selectedDose, false);
            }
        });

        countyFilter_uptakeStats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectedCounty_uptakeStats = countyNames_uptakeStats[position];
            }
        });


        yearWeekFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectedYearWeek = yearWeek[position];
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
