package com.example.cst338_project2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cst338_project2.R;
import com.example.cst338_project2.data.Order;
import com.example.cst338_project2.interfaces.IOrderRecyclerView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private final IOrderRecyclerView recyclerViewInterface;  //needed for onClickItem
    private List<Order> orders;
    private LayoutInflater inflater;
    private Context context;
    private int userAccess;

    public OrderAdapter(List<Order> orderList, Context context, IOrderRecyclerView recyclerViewInterface, int userAccess) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.orders = orderList;
        this.recyclerViewInterface = recyclerViewInterface;
        this.userAccess = userAccess;
    }

    // Total number of rows
    @Override
    public int getItemCount() {
        return orders.size();
    }

    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.order_cardview, null);

        return new OrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final OrderAdapter.ViewHolder holder, final int position) {
        holder.bindData(orders.get(position));
    }

    public void updateData(List<Order> data) {
        setData(data);
    }

    public void setData(List<Order> data) { orders = data; }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderIdField;
        TextView pricePaidField;
        TextView shopperNameField;
        TextView descriptionField;
        Button orderBtn;
        CardView cv;

        ViewHolder(View itemView) {
            super(itemView);
                orderIdField = itemView.findViewById(R.id.orderIdValue);
                pricePaidField = itemView.findViewById(R.id.pricePaidValue);
                shopperNameField = itemView.findViewById(R.id.shopperNameValue);
                descriptionField = itemView.findViewById(R.id.descriptionText);
                orderBtn = itemView.findViewById(R.id.orderCVButton);
                cv = itemView.findViewById(R.id.cv_orderHistory);

            orderBtn.setOnClickListener(new View.OnClickListener() {
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

        void bindData(final Order data) {
            int tempInt;
            String tempStr;

            tempStr = "Order ID: " + data.getOrderId();
            orderIdField.setText(tempStr);

            tempStr = "(" + data.getOrderPrice() + " diamonds)";
            pricePaidField.setText(tempStr);

            if(userAccess == 1) {
                shopperNameField.setText("Coming soon");
            } else {
                shopperNameField.setVisibility(View.GONE);;
            }

            descriptionField.setText(data.getBoughtItemDescription());
        }
    }

}
