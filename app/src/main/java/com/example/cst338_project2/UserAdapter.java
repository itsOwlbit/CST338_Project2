package com.example.cst338_project2;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cst338_project2.data.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;  //needed for onClickItem
    private List<User> users;
    private LayoutInflater inflater;
    private Context context;

    public UserAdapter(List<User> userList, Context context, RecyclerViewInterface recyclerViewInterface) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.users = userList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    // Total number of rows
    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.usermanager_cardview, null);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final UserAdapter.ViewHolder holder, final int position) {
        holder.bindData(users.get(position));
    }

    public void updateData(List<User> items) {
        setItems(items);
    }

    public void setItems(List<User> items) { users = items; }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView usernameField;
        TextView isAdminField;
        TextView statusField;
        TextView changeStatus;
        TextView deleteField;
        CardView cv;

        ViewHolder(View itemView) {
            super(itemView);
            usernameField = itemView.findViewById(R.id.usernameText);
            isAdminField = itemView.findViewById(R.id.isAdminText);
            statusField = itemView.findViewById(R.id.statusText);
            changeStatus = itemView.findViewById(R.id.changeStatusText);
            deleteField = itemView.findViewById(R.id.deleteText);
            cv = itemView.findViewById(R.id.cv_manageuser);

            changeStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null) {
                        int pos = getAbsoluteAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onStatusClick(pos);
                        }
                    }
                }
            });

//            deleteField.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(recyclerViewInterface != null) {
//                        int pos = getAbsoluteAdapterPosition();
//
//                        if(pos != RecyclerView.NO_POSITION) {
//                            recyclerViewInterface.onDeleteClick(pos);
//                        }
//                    }
//                }
//            });
        }

        void bindData(final User item) {
            usernameField.setText(item.getUserName());

            int statusValue = item.getIsActive();
            if(statusValue == 1) {
                statusField.setText("(Active)");
                statusField.setTextColor(Color.parseColor("#55FF55"));
            } else {
                statusField.setText("(Inactive)");
                statusField.setTextColor(Color.parseColor("#AAAAAA"));
//                deleteField.setText("DELETE");
//                deleteField.setTextColor(Color.parseColor("#FF5555"));
            }

            int adminValue = item.getIsAdmin();
            if(adminValue == 1) {
                isAdminField.setText("Admin");
                isAdminField.setTextColor(Color.parseColor("#55FFFF"));
                changeStatus.setVisibility(View.INVISIBLE);
                deleteField.setVisibility(View.INVISIBLE);
            } else {
                isAdminField.setText("Shopper");
                isAdminField.setTextColor(Color.parseColor("#CCA379"));

                // An admin can't deactivate the account.  Lazy way of preventing 0 admin situation.
                if(statusValue == 1) {
                    changeStatus.setText("Deactivate");
                    changeStatus.setTextColor(Color.parseColor("#AAAAAA"));
                } else {
                    changeStatus.setText("Activate");
                    changeStatus.setTextColor(Color.parseColor("#55FF55"));
                }
            }
        }
    }
}
