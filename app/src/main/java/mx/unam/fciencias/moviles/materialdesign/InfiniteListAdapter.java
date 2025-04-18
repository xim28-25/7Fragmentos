package mx.unam.fciencias.moviles.materialdesign;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class InfiniteListAdapter extends RecyclerView.Adapter<InfiniteListAdapter.ListEntry> {

    private final List<String> DATASET;
    private final Resources RESOURCES;
    private final MasterListItemClickHandler CLICK_HANDLER;

    public InfiniteListAdapter(Resources res, MasterListItemClickHandler listItemClickHandler) {
        DATASET = new LinkedList<>();
        RESOURCES = res;
        CLICK_HANDLER = listItemClickHandler;
    }

    public void addItem() {
        int i = DATASET.size();
        DATASET.add(i, RESOURCES.getString(R.string.infinte_list_entry_message, i + 1));
        notifyItemInserted(i);
    }

    @NonNull
    @Override
    public ListEntry onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView tv = (TextView) LayoutInflater.from(parent.getContext()).inflate(
                R.layout.infinite_list_entry, parent, false
        );
        return new ListEntry(tv);
    }

    @Override
    public void onBindViewHolder(@NonNull ListEntry holder, int position) {
        holder.entryText.setText(DATASET.get(position));
    }

    //Cuantos registros hay en la lista
    @Override
    public int getItemCount() {
        return DATASET.size();
    }

    class ListEntry extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView entryText;

        ListEntry(TextView entryTV) {
            super(entryTV);
            entryText = entryTV;
            entryTV.setClickable(true);
            entryTV.setFocusable(true);
            entryTV.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            CLICK_HANDLER.onItemClicked(getBindingAdapterPosition(), entryText.getText().toString(),
                    DATASET.size());
        }
    }

    public interface MasterListItemClickHandler {

        void onItemClicked(int clickItemIndex, String entryText, int masterListSize);
    }
}
