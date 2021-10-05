package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;


public class DashboardGeneric_Admin extends Fragment {
    private String[] locations;
    private String[] ageGroup;
    private String[] product;
    private String[] dose;
    private AutoCompleteTextView countyFilter; //Prev locationFilter
    private AutoCompleteTextView centerFilter; //Prev ageGroupFilter
    private AutoCompleteTextView dateFilter; //Prev productFilter
    //private AutoCompleteTextView doseFilter;

    private ArrayList<Covid_Tracking_ListViewCell> listViewItems = new ArrayList<Covid_Tracking_ListViewCell>();
    private ListView listView;

    private Button filterButton;
    private boolean filterHidden = true;

    private LinearLayout filterTabs1;
    private LinearLayout filterTabs2;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.covid_tracking_fragment_dashboard, container, false);

        setupDropdownMenus();
        setupListViewItems();
        setupFilter();

        // Inflate the layout for this fragment
        return view;
    }

    private void setupDropdownMenus() {
        //Filter
        locations = getResources().getStringArray(R.array.location);
        ageGroup = getResources().getStringArray(R.array.ageGroup);
        product = getResources().getStringArray(R.array.product);
        dose = getResources().getStringArray(R.array.dose);

        countyFilter = (AutoCompleteTextView) view.findViewById(R.id.countyFilter);
        centerFilter = (AutoCompleteTextView) view.findViewById(R.id.centerFilter);
        dateFilter = (AutoCompleteTextView) view.findViewById(R.id.dateFilter);
        //doseFilter = (AutoCompleteTextView) view.findViewById(R.id.doseFilter);

        Covid_Tracking_DropdownAdapter locationAdapter = new Covid_Tracking_DropdownAdapter(this.getContext(), R.layout.covid_tracking_dropdown_item, locations);

        countyFilter.setAdapter(locationAdapter);

        Covid_Tracking_DropdownAdapter ageGroupAdapter = new Covid_Tracking_DropdownAdapter(this.getActivity().getApplicationContext(), R.layout.covid_tracking_dropdown_item, ageGroup);
        centerFilter.setAdapter(ageGroupAdapter);
        Covid_Tracking_DropdownAdapter productAdapter = new Covid_Tracking_DropdownAdapter(this.getActivity().getApplicationContext(), R.layout.covid_tracking_dropdown_item, product);
        dateFilter.setAdapter(productAdapter);
        Covid_Tracking_DropdownAdapter doseAdapter = new Covid_Tracking_DropdownAdapter(this.getActivity().getApplicationContext(), R.layout.covid_tracking_dropdown_item, dose);
        //doseFilter.setAdapter(doseAdapter);

    }

    private void setupListViewItems() {
//        OBS2: How to display the data according to the filter. when we have gotten the data from the database, we can set each ListViewCell data section in order to display the data.
//        we can use for loop to cycle through the listViewItems, put the listViewCells we need to display in the newly create ArryaList
//        and use adapter to display them
/*
        Covid_Tracking_ListViewCell totalDistri = new Covid_Tracking_ListViewCell("0", getResources().getString(R.string.totalDistri), R.drawable.ic_virus);
        Covid_Tracking_ListViewCell totalAdmini = new Covid_Tracking_ListViewCell("1", getResources().getString(R.string.totalAdmini), R.drawable.ic_virus);
        Covid_Tracking_ListViewCell totalCases = new Covid_Tracking_ListViewCell("2", getResources().getString(R.string.totalCases), R.drawable.ic_virus);
        Covid_Tracking_ListViewCell totalDeaths = new Covid_Tracking_ListViewCell("3", getResources().getString(R.string.totalDeaths), R.drawable.ic_virus);
        Covid_Tracking_ListViewCell vs = new Covid_Tracking_ListViewCell("4", getResources().getString(R.string.vs), R.drawable.ic_virus);
*/
        Covid_Tracking_ListViewCell totalDistri = new Covid_Tracking_ListViewCell("0", getResources().getString(R.string.totalDistri));
        Covid_Tracking_ListViewCell totalAdmini = new Covid_Tracking_ListViewCell("1", getResources().getString(R.string.totalAdmini));
        Covid_Tracking_ListViewCell totalCases = new Covid_Tracking_ListViewCell("2", getResources().getString(R.string.totalCases));
        Covid_Tracking_ListViewCell totalDeaths = new Covid_Tracking_ListViewCell("3", getResources().getString(R.string.totalDeaths));
        Covid_Tracking_ListViewCell vs = new Covid_Tracking_ListViewCell("4", getResources().getString(R.string.vs));

        listViewItems.add(totalDistri);
        listViewItems.add(totalAdmini);
        listViewItems.add(totalCases);
        listViewItems.add(totalDeaths);
        listViewItems.add(vs);

        listView = (ListView) view.findViewById(R.id.listViewInCovidTracking);

        Covid_Tracking_ListViewAdapter adapter = new Covid_Tracking_ListViewAdapter(this.getContext(), 0, listViewItems);
        listView.setAdapter(adapter);
    }


    private void setupFilter() {
        filterButton = (Button) view.findViewById(R.id.filterButton);
        filterTabs1 = (LinearLayout) view.findViewById(R.id.filterTabs1);
        filterTabs2 = (LinearLayout) view.findViewById(R.id.filterTabs2);

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filterHidden == true) {
                    filterHidden = false;
                    filterTabs1.setVisibility(View.VISIBLE);
                    filterTabs2.setVisibility(View.VISIBLE);
                    filterButton.setText("HIDE");
                    //showFilter();
                } else {
                    filterHidden = true;
                    filterTabs1.setVisibility(View.GONE);
                    filterTabs2.setVisibility(View.GONE);
                    filterButton.setText("FILTER");
                    //hideFilter();
                }
            }
        });
// OBS1: use these filter to build a e.g. SQL query and query the API or database and then get the data and parse the data and display the data in the list view.
        countyFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("selected", String.valueOf(countyFilter.getText()));
                Log.d("int", String.valueOf(position));
                ArrayList<Covid_Tracking_ListViewCell> updatedListView = new ArrayList<Covid_Tracking_ListViewCell>();

            }
        });

        centerFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            }
        });

        dateFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            }
        });

        /*doseFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            }
        });
    }*/

        /*private void hideFilter(){
            filterTabs1.setVisibility(View.GONE);
            filterTabs2.setVisibility(View.GONE);
            filterButton.setText("FILTER");
        }

        private void showFilter(){
            filterTabs1.setVisibility(View.VISIBLE);
            filterTabs2.setVisibility(View.VISIBLE);
            filterButton.setText("HIDE");
        }*/
    }
}