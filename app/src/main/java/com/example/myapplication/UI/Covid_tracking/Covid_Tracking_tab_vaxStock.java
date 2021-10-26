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

import com.example.myapplication.API.Model.Covid_tracking.StockStat;
import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.Helpers.CovidTrackVaxStockHelper;
import com.example.myapplication.R;
import com.example.myapplication.UI.Adapter.Covid_Tracking_ListViewAdapter;
import com.example.myapplication.UI.Adapter.Simple_DropdownAdapter;
import com.example.myapplication.UI.LoadingAnimation;
import com.example.myapplication.UI.ViewCells.Covid_Tracking_ListViewCell;

import java.util.ArrayList;
import java.util.List;

public class Covid_Tracking_tab_vaxStock extends Fragment {
    private static final int FIRST_ON_LIST = 0;

    private List<StockStat> stockStats;
    private int selectedCounty = FIRST_ON_LIST;
    private int [] filteredDataSet;
    private Covid_Tracking_ListViewAdapter adapter;
    private Covid_Tracking_ListViewCell amount;

    private String[] countyNames;
    private CovidTrackVaxStockHelper covidTrackVaxStockHelper;
    private AutoCompleteTextView countyFilter;
    private ArrayList<Covid_Tracking_ListViewCell> listViewItems = new ArrayList<Covid_Tracking_ListViewCell>();
    private ListView listView;

    private Button resetFilterButton;

    private View view;
    private UserResponse user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.covid_tracking_tab_vaxstock, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        user = (UserResponse) getActivity().getIntent().getSerializableExtra("userInfo");
        covidTrackVaxStockHelper = new CovidTrackVaxStockHelper(this);
        covidTrackVaxStockHelper.API_getVaxStock(getActivity(), user, new Runnable() {
            @Override
            public void run() {
                stockStats = covidTrackVaxStockHelper.getStockStats();
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
        countyNames = covidTrackVaxStockHelper.getCountyNames();

        countyFilter = (AutoCompleteTextView) view.findViewById(R.id.countyFilter);
        countyFilter.setText(stockStats.get(0).getCountyName());
        Simple_DropdownAdapter countyAdapter = new Simple_DropdownAdapter(this.getContext(), R.layout.simple_dropdown_item, countyNames);

        countyFilter.setAdapter(countyAdapter);

    }

    private void setupListViewItems(){
        fillListViewItemList(selectedCounty);
        adapter = new Covid_Tracking_ListViewAdapter(this.getContext(), 0, listViewItems);
        listView.setAdapter(adapter);
    }

    private void fillListViewItemList(int selectedCounty){
        filteredDataSet = covidTrackVaxStockHelper.getFilteredDataSet(selectedCounty);

        amount = new Covid_Tracking_ListViewCell(getResources().getString(R.string.totalAmount), String.valueOf(filteredDataSet[0]));

        listViewItems.clear();
        listViewItems.add(amount);


    }

    private void updateListView(int selectedCounty){
        fillListViewItemList(selectedCounty);
        adapter.notifyDataSetChanged();
    }

    private void setupFilter(){
//        applyFilterButton = (Button) view.findViewById(R.id.applyFilterButton);
//        applyFilterButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateListView(selectedCounty);
//            }
//        });

        resetFilterButton = (Button) view.findViewById(R.id.resetFilterButton);
        resetFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateListView(FIRST_ON_LIST);
                countyFilter.setText(stockStats.get(0).getCountyName(), false);
            }
        });

//        filterButton = (Button) view.findViewById(R.id.filterButton);
//        filterTabs = (LinearLayout) view.findViewById(R.id.filterTabs);
//
//        filterButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(filterHidden == true){
//                    filterHidden = false;
//                    showFilter();
//                } else{
//                    filterHidden = true;
//                    hideFilter();
//                }
//            }
//        });

        countyFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectedCounty = position;
                updateListView(selectedCounty);
            }
        });
    }

//    private void hideFilter() {
//        filterTabs.setVisibility(View.GONE);
//        filterButton.setText("FILTER");
//    }
//
//    private void showFilter(){
//        filterTabs.setVisibility(View.VISIBLE);
//        filterButton.setText("HIDE");
//    }
}
