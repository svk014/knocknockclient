package knockknock.delivr_it.knocknock.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import net.cachapa.expandablelayout.ExpandableLinearLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import knockknock.delivr_it.knocknock.R;
import knockknock.delivr_it.knocknock.adapters.CheckboxAdapter;
import knockknock.delivr_it.knocknock.adapters.ItemListAdapter;
import knockknock.delivr_it.knocknock.managers.CartStorageManager;
import knockknock.delivr_it.knocknock.managers.ItemStorageManager;

public class ItemListActivity extends AppCompatActivity {
    ExpandableLinearLayout expandableFilter;
    SwitchCompat vegetarianPreferenceSwitch;
    SwitchCompat inStockControlSwitch;
    Spinner sortMethodSelector;
    View expandableFilterHeader;
    private String category;
    HashSet<String> checkedSections;
    CheckboxAdapter checkboxAdapter;
    List<String> allSections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_lists);

        init();
        inflateSectionChecks();
        //    inflateSpinner();
        inflateMainMenuItems();
    }

    private void init() {
        category = getIntent().getExtras().getString("category");
        allSections = ItemStorageManager.getAllSectionsForCategory(getApplicationContext(), category);
        checkedSections = new HashSet<>(allSections);
        expandableFilter = (ExpandableLinearLayout) findViewById(R.id.expandable_filter);
        vegetarianPreferenceSwitch = (SwitchCompat) findViewById(R.id.preference_control_switch);
        expandableFilterHeader = findViewById(R.id.expandable_filter_header);
        inStockControlSwitch = (SwitchCompat) findViewById(R.id.in_stock_control_switch);
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
        ItemListAdapter itemListAdapter =
                new ItemListAdapter(getApplicationContext(),
                        ItemStorageManager.getAllItemsForCategory(getApplicationContext(),
                                category, getCheckedSections(), vegetarianPreferenceSwitch.isChecked() + "",
                                inStockControlSwitch.isChecked() + ""));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(itemListAdapter);
    }

    private void inflateSectionChecks() {
        RecyclerView sectionsRecyclerView = (RecyclerView) findViewById(R.id.list_of_section_checkboxes);
        checkboxAdapter = new CheckboxAdapter(allSections);

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
        String text = vegetarianPreferenceSwitch.isChecked() ? "Veg Only" : "Veg/Non-veg";
        vegetarianPreferenceSwitch.setText(text);
        inflateMainMenuItems();
    }

    public List<String> getCheckedSections() {
        return new ArrayList<>(checkedSections);
    }

    public void updateListOfChecks(View view) {
        CheckBox checkBox = (CheckBox) view;
        Log.d("Checked", "called");
        if (checkBox.isChecked()) {
            checkedSections.add(checkBox.getText().toString());
        } else {
            checkedSections.remove(checkBox.getText().toString());
        }
        inflateMainMenuItems();
    }

    public void toggleStockSwitch(View view) {
        inflateMainMenuItems();
    }

    public void removeItemFromCart(View view) {
        String id = view.getTag().toString();
        View parentView = (View) (view.getParent());
        TextView quantityTextView = (TextView) parentView.findViewById(R.id.order_item_quantity);
        int quantity = Integer.parseInt(quantityTextView.getText().toString());
        if (quantity > 0) {
            quantity--;
            if (quantity == 0)
                CartStorageManager.removeItemFromCart(getApplicationContext(), id);
            else
                CartStorageManager.storeItemInCart(getApplicationContext(), id, quantity);
            quantityTextView.setText(quantity + "");
        }
    }

    public void addItemToCart(View view) {
        String id = view.getTag().toString();
        View parentView = (View) (view.getParent());
        TextView quantityTextView = (TextView) parentView.findViewById(R.id.order_item_quantity);

        int quantity = Integer.parseInt(quantityTextView.getText().toString());
        quantity++;
        CartStorageManager.storeItemInCart(getApplicationContext(), id, quantity);
        quantityTextView.setText(quantity + "");

    }
}
