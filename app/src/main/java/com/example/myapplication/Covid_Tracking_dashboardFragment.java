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


public class Covid_Tracking_dashboardFragment extends Fragment {
    private String[] locations;
    private String[] ageGroup;
    private String[] product;
    private String[] dose;
    private AutoCompleteTextView locationFilter;
    private AutoCompleteTextView ageGroupFilter;
    private AutoCompleteTextView productFilter;
    private AutoCompleteTextView doseFilter;

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
    private void setupDropdownMenus(){
        locations = getResources().getStringArray(R.array.location);
        ageGroup = getResources().getStringArray(R.array.ageGroup);
        product = getResources().getStringArray(R.array.product);
        dose = getResources().getStringArray(R.array.dose);



        locationFilter = (AutoCompleteTextView) view.findViewById(R.id.locationFilter);
        ageGroupFilter = (AutoCompleteTextView) view.findViewById(R.id.ageGroupFilter);
        productFilter = (AutoCompleteTextView) view.findViewById(R.id.productFilter);
        doseFilter = (AutoCompleteTextView) view.findViewById(R.id.doseFilter);
        Log.d("fuck", "fuck");

        Covid_Tracking_DropdownAdapter locationAdapter = new Covid_Tracking_DropdownAdapter(this.getContext(), R.layout.covid_tracking_dropdown_item, locations);

        locationFilter.setAdapter(locationAdapter);

        Covid_Tracking_DropdownAdapter ageGroupAdapter = new Covid_Tracking_DropdownAdapter(this.getActivity().getApplicationContext(), R.layout.covid_tracking_dropdown_item, ageGroup);
        ageGroupFilter.setAdapter(ageGroupAdapter);
        Covid_Tracking_DropdownAdapter productAdapter = new Covid_Tracking_DropdownAdapter(this.getActivity().getApplicationContext(), R.layout.covid_tracking_dropdown_item, product);
        productFilter.setAdapter(productAdapter);
        Covid_Tracking_DropdownAdapter doseAdapter = new Covid_Tracking_DropdownAdapter(this.getActivity().getApplicationContext(), R.layout.covid_tracking_dropdown_item, dose);
        doseFilter.setAdapter(doseAdapter);

    }

    private void setupListViewItems(){
//        OBS2: How to display the data according to the filter. when we have gotten the data from the database, we can set each ListViewCell data section in order to display the data.
//        we can use for loop to cycle through the listViewItems, put the listViewCells we need to display in the newly create ArryaList
//        and use adapter to display them
        Covid_Tracking_ListViewCell totalDistri = new Covid_Tracking_ListViewCell("0", getResources().getString(R.string.totalDistri), R.drawable.circle);
        Covid_Tracking_ListViewCell totalAdmini = new Covid_Tracking_ListViewCell("1", getResources().getString(R.string.totalAdmini), R.drawable.triangle);
        Covid_Tracking_ListViewCell totalCases = new Covid_Tracking_ListViewCell("2", getResources().getString(R.string.totalCases), R.drawable.square);
        Covid_Tracking_ListViewCell totalDeaths = new Covid_Tracking_ListViewCell("3", getResources().getString(R.string.totalDeaths), R.drawable.rectangle);
        Covid_Tracking_ListViewCell vs = new Covid_Tracking_ListViewCell("4", getResources().getString(R.string.vs), R.drawable.octagon);

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
        filterTabs2 = (LinearLayout) view.findViewById(R.id.filterTabs2);

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

        ageGroupFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            }
        });

        productFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            }
        });

        doseFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            }
        });
    }

    private void hideFilter() {
        filterTabs1.setVisibility(View.GONE);
        filterTabs2.setVisibility(View.GONE);
        filterButton.setText("FILTER");
    }

    private void showFilter(){
        filterTabs1.setVisibility(View.VISIBLE);
        filterTabs2.setVisibility(View.VISIBLE);
        filterButton.setText("HIDE");
    }
}