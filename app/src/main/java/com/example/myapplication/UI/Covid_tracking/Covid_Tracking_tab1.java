package com.example.myapplication.UI.Covid_tracking;

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

import com.example.myapplication.UI.ViewCells.Covid_Tracking_ListViewCell;
import com.example.myapplication.R;
import com.example.myapplication.UI.Adapter.Simple_DropdownAdapter;
import com.example.myapplication.UI.Adapter.Covid_Tracking_ListViewAdapter;

import java.util.ArrayList;


public class Covid_Tracking_tab1 extends Fragment {
    private String[] locations;

    private AutoCompleteTextView locationFilter;


    private ArrayList<Covid_Tracking_ListViewCell> listViewItems = new ArrayList<Covid_Tracking_ListViewCell>();
    private ListView listView;

    private Button filterButton;
    private boolean filterHidden = false;

    private LinearLayout filterTabs1;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.covid_tracking_tab_cases, container, false);

        setupDropdownMenus();
        setupListViewItems();
        setupFilter();

        // Inflate the layout for this fragment
        return view;
    }
    private void setupDropdownMenus(){
        locations = getResources().getStringArray(R.array.location);




        locationFilter = (AutoCompleteTextView) view.findViewById(R.id.locationFilter);


        Simple_DropdownAdapter locationAdapter = new Simple_DropdownAdapter(this.getContext(), R.layout.simple_dropdown_item, locations);

        locationFilter.setAdapter(locationAdapter);


    }

    private void setupListViewItems(){

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


    private void setupFilter(){
        filterButton = (Button) view.findViewById(R.id.filterButton);
        filterTabs1 = (LinearLayout) view.findViewById(R.id.filterTabs1);

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
// OBS1: use these filter to build a e.g. SQL query and query the API or database and then get the data and parse the data and display the data in the list view.
        locationFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("selected", String.valueOf(locationFilter.getText()));
                Log.d("int", String.valueOf(position));
                ArrayList<Covid_Tracking_ListViewCell> updatedListView = new ArrayList<Covid_Tracking_ListViewCell>();

            }
        });
    }

    private void hideFilter() {
        filterTabs1.setVisibility(View.GONE);
        filterButton.setText("FILTER");
    }

    private void showFilter(){
        filterTabs1.setVisibility(View.VISIBLE);
        filterButton.setText("HIDE");
    }
}