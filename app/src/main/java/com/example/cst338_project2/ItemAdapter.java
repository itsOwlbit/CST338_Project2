package com.example.cst338_project2;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cst338_project2.data.Item;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private final IItemRecyclerView recyclerViewInterface;  //needed for onClickItem
    private List<Item> items;
    private LayoutInflater inflater;
    private Context context;
    private int userAccess;

    public ItemAdapter(List<Item> itemList, Context context, IItemRecyclerView recyclerViewInterface, int userAccess) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.items = itemList;
        this.recyclerViewInterface = recyclerViewInterface;
        this.userAccess = userAccess;
    }

    // Total number of rows
    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_cardview, null);

        return new ItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemAdapter.ViewHolder holder, final int position) {
        holder.bindData(items.get(position));
    }

    public void updateData(List<Item> data) {
        setData(data);
    }

    public void setData(List<Item> data) { items = data; }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemNameField;
        TextView itemInStockField;
        TextView itemDescriptionField;
        Button itemBtn;
        CardView cv;

        ViewHolder(View itemView) {
            super(itemView);
            itemNameField = itemView.findViewById(R.id.itemNameText);
            itemInStockField = itemView.findViewById(R.id.inStockText);
            itemDescriptionField = itemView.findViewById(R.id.descriptionText);
            itemBtn = itemView.findViewById(R.id.itemButton);
            cv = itemView.findViewById(R.id.cv_itemInventory);

            itemBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null) {
                        int pos = getAbsoluteAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onButtonClick(pos);
                        }
                    }
                }
            });
        }

        void bindData(final Item data) {
            int inStockNum = data.getInStockQty();
            String qtyString;

            itemNameField.setText(data.getItemName());

            if(inStockNum > 0) {
                qtyString = "QTY: " + inStockNum;
                itemInStockField.setTextColor(Color.parseColor("#55FF55"));
            } else {
                qtyString = "OUT OF STOCK";
                itemInStockField.setTextColor(Color.parseColor("#FF5555"));
            }

            itemInStockField.setText(qtyString);
            itemDescriptionField.setText(data.getItemDescription());

            if(userAccess == 1) {
                itemBtn.setText("EDIT");
            }

            if(data.getIsForSale() == 1) {
                itemBtn.setBackgroundColor(Color.parseColor("#B7D7D7"));
            } else {
                itemBtn.setBackgroundColor(Color.parseColor("#D7B7D7"));
            }
        }
    }
}
