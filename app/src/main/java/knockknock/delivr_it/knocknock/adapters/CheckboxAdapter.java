package knockknock.delivr_it.knocknock.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.List;

import knockknock.delivr_it.knocknock.R;

public class CheckboxAdapter extends RecyclerView.Adapter<CheckboxAdapter.HolderView> {


    private List<String> sections;

    public CheckboxAdapter(List<String> sections) {
        this.sections = sections;
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkbox, parent, false);
        return new HolderView(inflatedView);
    }

    @Override
    public void onBindViewHolder(HolderView holder, int position) {
        holder.checkBox.setText(sections.get(position));
        holder.checkBox.setChecked(true);
    }

    @Override
    public int getItemCount() {
        return sections.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {
        CheckBox checkBox;

        public HolderView(View view) {
            super(view);
            checkBox = (CheckBox) view.findViewById(R.id.section_checkbox);
        }
    }
}
