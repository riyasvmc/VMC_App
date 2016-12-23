package com.zeefive.vmcapp.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.model.HomeMenuItem;

public class Data {

    // Home menu Items
    public static final HomeMenuItem ITEM_PROJECT = new HomeMenuItem("Projects", "10 Ongoing projects", R.drawable.ic_home_bookmark_24dp);
    public static final HomeMenuItem ITEM_ACCOUNTS = new HomeMenuItem("Accounts", "", R.drawable.ic_account_balance_wallet_grey_300_24dp);
    public static final HomeMenuItem ITEM_SHOPS = new HomeMenuItem("Shops", "", R.drawable.ic_shopping_basket_grey_300_24dp);
    public static final HomeMenuItem ITEM_SALE = new HomeMenuItem("Sales", "", R.drawable.ic_shopping_basket_grey_300_24dp);
    public static final HomeMenuItem ITEM_TODO = new HomeMenuItem("ToDos", "19 todos left", R.drawable.ic_home_assignment_24dp);
    public static final HomeMenuItem ITEM_TOOLS = new HomeMenuItem("Tools", "", R.drawable.ic_settings_grey_300_24dp);
    public static final HomeMenuItem ITEM_CONCRETE_CALCULATOR = new HomeMenuItem("Concrete Calculator", "Get your Utilities from here", R.drawable.ic_markunread_mailbox_grey_300_24dp);
    public static final HomeMenuItem ITEM_PROPERTIES = new HomeMenuItem("Properties", "", R.drawable.ic_chrome_reader_mode_black_24dp);
    public static final HomeMenuItem ITEM_EXPIRY_DATES = new HomeMenuItem("Expiry Dates", "", R.drawable.ic_today_black_24dp);

    public static final HomeMenuItem[] HOME_MENU_ITEMS = new HomeMenuItem[]
            {
                    ITEM_PROJECT,
                    //ITEM_ACCOUNTS,
                    ITEM_SHOPS,
                    ITEM_SALE,
                    ITEM_TODO,
                    ITEM_TOOLS,
                    //ITEM_CONCRETE_CALCULATOR, put this to utility
                    //ITEM_PROPERTIES,
                    ITEM_EXPIRY_DATES
            };

    public static final String PASSWORD_FOR_DELETE = "Delete";

    // KEYS
    public static final String KEY_KEY = "key";
    public static final String KEY_TITLE = "title";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_COMPLETED = "completed";
    public static final String KEY_PROJECTS = "projects";
    public static final String KEY_TOOLS = "tools";
    public static final String KEY_SALES = "sales";
    public static final String KEY_SHOPS = "shops";
    public static final String KEY_PAYMENTS = "payments";
    public static final String KEY_ACCOUNTS = "accounts";
    public static final String KEY_PROJECT_KEY = "projectKey";
    public static final String KEY_PURCHASES = "purchases";
    public static final String KEY_WORKS = "works";
    public static final String KEY_TODOS = "todos";
    public static final String KEY_EXPIRY_DATES = "expiry_dates";
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_DONE = "done";
    public static final String KEY_PRIORITY = "priority";
    // :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    // TAGS
    public static final String TAG_CONCRETE = "Concrete";
    public static final String TAG_OVERTIME = "Overtime";
    public static final String TAG_JCB = "JCB";
    public static final String TAG_TIPPER = "Tipper";
    public static final String TAG_EXTRA = "Extra";
    public static final String TAG_CLEANING = "Cleaning";
    public static final String TAG_CONCRETE_CUTTING = "Concrete Cutting";
    // :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    // QUERIES
    private static final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    public static final Query QUERY_EXPIRY_DATES = mDatabase.child(Data.KEY_EXPIRY_DATES);                                              // expiry dates
    public static final Query QUERY_EXPIRY_DATES_ORD_BY_CREATED_AT = QUERY_EXPIRY_DATES.orderByChild(Data.KEY_CREATED_AT);
    public static final Query QUERY_SALES = mDatabase.child(Data.KEY_SALES);                                                            // sales
    public static final Query QUERY_SHOP = mDatabase.child(Data.KEY_SHOPS);                                                             // shop
    public static final Query QUERY_PAYMENT = mDatabase.child(Data.KEY_PAYMENTS);                                                       // payment
    public static final Query QUERY_ACCOUNTS = mDatabase.child(Data.KEY_ACCOUNTS);                                                       // accounts
    public static final Query QUERY_PROJECTS = mDatabase.child(Data.KEY_PROJECTS);                                                      // projects
    public static final Query QUERY_PROJECTS_ORD_BY_COMPLETED = mDatabase.child(Data.KEY_PROJECTS).orderByChild(Data.KEY_COMPLETED);
    public static final Query QUERY_TOOlS = mDatabase.child(Data.KEY_TOOLS);                                                            // tools
    public static final Query QUERY_TODOS = mDatabase.child(Data.KEY_TODOS);                                                            // todos
    public static final Query QUERY_TODOS_ORD_BY_PRIORITY = mDatabase.child(Data.KEY_TODOS).orderByChild(Data.KEY_PRIORITY);
    public static final Query QUERY_PURCHASES = mDatabase.child(Data.KEY_PURCHASES);                                                    // purchases
    public static final Query QUERY_WORKS = mDatabase.child(Data.KEY_WORKS);                                                            // work
    // :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
}