package com.example.cst338_project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cst338_project2.data.User;
import com.example.cst338_project2.db.AppDatabase;
import com.example.cst338_project2.db.MyDao;

import java.util.List;

/**
 * The below linked helped provide the start for the recyclerview used.
 * https://medium.com/@codelia.developer/simple-android-recyclerview-list-using-cardview-d48524bedc6a
 *
 * This for clickable recyclerview?
 * https://www.youtube.com/watch?v=7GPUpvcU1FE
 * TODO: Add delete?  Remove deactivate?  Add admins not just shoppers?  Make sure all admins are not deleted.
 */
public class AdminManageUser extends AppCompatActivity implements RecyclerViewInterface{

    ImageView backImg;          // the image from toolbar_layout to go back
    TextView toolbarTitleField; // the text for toolbar title
    TextView logoutField;       // the text from toolbar_layout to logout

    UserAdapter userAdapter;
    RecyclerView recyclerView;

    TextView usernameField;
    TextView passwordField;

    Button clearBtn;
    TextView addBtn;

    private User newUser;       // a User object for account being created

    private String username;    // used to store username retrieved from TextView field
    private String password;    // used to store password retrieved from TextView field

    private MyDao myDao;

    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manageuser);

        // Get database access
        myDao = AppDatabase.getDatabase(this);

        // Get list of users
        userList = myDao.getAllUsers();

        prepareLayout();

        buildRecyclerView();

        // Check for setOnClickListener() events
        checkListeners();
    }

    private void prepareLayout() {
        // used to set toolbar
        backImg = findViewById(R.id.backImg);
        toolbarTitleField = findViewById(R.id.toolbarTitle);
        toolbarTitleField.setTextColor(getResources().getColor(R.color.aqua));
        toolbarTitleField.setText("Manage User Accounts");
        logoutField = findViewById(R.id.logoutText);

        // used for setting layout stuff
        usernameField = findViewById(R.id.usernameValue);
        passwordField = findViewById(R.id.passwordValue);
        clearBtn = findViewById(R.id.clearButton);
        addBtn = findViewById(R.id.addButton);
    }

    private void checkListeners() {
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameField.setText("");
                passwordField.setText("");
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameField.getText().toString();
                password = passwordField.getText().toString();

                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(AdminManageUser.this,
                            "Both username and password are required.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    //  check if user is in the database (duplicate username is not allowed)
                    if(checkForUserInDatabase()) {
                        Toast.makeText(AdminManageUser.this, username + " is not available.", Toast.LENGTH_SHORT).show();
                    } else {
                        // add user to database and make sure it worked
                        if(addUserToDB()) {
                            // Need to update the adapter with an updated list of users from the database.
                            userAdapter.updateData(myDao.getAllUsers());
                            // Do the update on display.
                            recyclerView.getAdapter().notifyDataSetChanged();

                            Toast.makeText(AdminManageUser.this, newUser.getUserName() + " was added.", Toast.LENGTH_SHORT).show();

                            newUser = null;
                            usernameField.setText("");
                            passwordField.setText("");
                        } else {
                            Toast.makeText(AdminManageUser.this, "Something went wrong.  Could not add " + username, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    private boolean checkForUserInDatabase() {
        newUser = myDao.getUserByUsername(username);

        return (newUser != null);
    }

    private boolean addUserToDB() {
        newUser = new User(username, password, 0, 1);
        myDao.insert(newUser);

        return (checkForUserInDatabase());
    }

    private void buildRecyclerView() {
        userAdapter = new UserAdapter(userList, this, this);
        recyclerView = findViewById(R.id.rvUser);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(userAdapter);
    }

    @Override
    public void onStatusClick(int position) {
        int id = userList.get(position).getUserID(); // using rv position in userList to find id
        User editUser = myDao.getUserByUserId(id);  // getting user object for the position
        String status;  // Used to hold a string for Toast display

        // Make the change.
        if(editUser.getIsActive() == 1) {
            editUser.setIsActive(0);
            status = "inactive.";
        } else {
            editUser.setIsActive(1);
            status = "active.";
        }
        myDao.update(editUser);

        // Need to update the adapter with an updated list of users from the database.
        userAdapter.updateData(myDao.getAllUsers());
        // Do the update on display.
        recyclerView.getAdapter().notifyDataSetChanged();
    }

//    public void onDeleteClick(int position) {
//        int id = userList.get(position).getUserID(); // using rv position in userList to find id
//        User deleteUser = myDao.getUserByUserId(id);  // getting user object for the position
//
//        myDao.delete(deleteUser);
//
//        // Need to update the adapter with an updated list of users from the database.
//        userAdapter.updateData(myDao.getAllUsers());
//        // Do the update on display.
//        recyclerView.getAdapter().notifyItemRemoved(position);
//    }
}