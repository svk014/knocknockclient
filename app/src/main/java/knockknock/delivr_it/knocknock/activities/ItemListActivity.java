package knockknock.delivr_it.knocknock.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import net.cachapa.expandablelayout.ExpandableLinearLayout;

import java.util.ArrayList;
import java.util.List;

import knockknock.delivr_it.knocknock.R;
import knockknock.delivr_it.knocknock.adapters.CheckboxAdapter;
import knockknock.delivr_it.knocknock.adapters.ItemListAdapter;

public class ItemListActivity extends AppCompatActivity {
    ExpandableLinearLayout expandableFilter;
    SwitchCompat preferenceSwitch;
    Spinner sortMethodSelector;
    View expandableFilterHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_lists);

        expandableFilter = (ExpandableLinearLayout) findViewById(R.id.expandable_filter);
        preferenceSwitch = (SwitchCompat) findViewById(R.id.preference_control_switch);
        expandableFilterHeader = findViewById(R.id.expandable_filter_header);
        inflateSpinner();
        inflateMainMenuItems();
        setUpSectionChecks();
    }

    private void inflateSpinner() {
        sortMethodSelector = (Spinner) findViewById(R.id.sort_by_spinner);
        List<String> sortCategories = new ArrayList<>();
        sortCategories.add("Automobile");
        sortCategories.add("Business Services");
        sortCategories.add("Computers");
        sortCategories.add("Education");
        sortCategories.add("Personal");
        sortCategories.add("Travel");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sortCategories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sortMethodSelector.setAdapter(dataAdapter);
    }

    private void inflateMainMenuItems() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_menu);
        ItemListAdapter itemListAdapter = new ItemListAdapter();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(itemListAdapter);
    }

    private void setUpSectionChecks() {
        RecyclerView sectionsRecyclerView = (RecyclerView) findViewById(R.id.list_of_section_checkboxes);
        CheckboxAdapter checkboxAdapter = new CheckboxAdapter();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        sectionsRecyclerView.setLayoutManager(mLayoutManager);
        sectionsRecyclerView.setItemAnimator(new DefaultItemAnimator());

        sectionsRecyclerView.setAdapter(checkboxAdapter);
    }

    public void toggleFilterView(View view) {
        expandableFilter.toggle();

        if (expandableFilter.isExpanded()) {
            expandableFilterHeader.setVisibility(View.GONE);
        } else {
            expandableFilterHeader.setVisibility(View.VISIBLE);
        }
    }

    public void togglePreferenceSwitch(View view) {
        if (preferenceSwitch.isChecked()) {
            preferenceSwitch.setText("Veg Only");
        } else {
            preferenceSwitch.setText("Veg/Non-veg");
        }
    }
}
