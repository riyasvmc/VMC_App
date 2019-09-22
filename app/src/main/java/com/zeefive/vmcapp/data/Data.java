package com.zeefive.vmcapp.data;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.model.MenuItem;

public class Data {

    // Queries
    public static final int BASE = 1;
    public static final int EXPIRY_DATES = 2;
    public static final int EXPIRY_DATES_ORD_BY_CREATED_AT = 3;
    public static final int LOGS = 4;
    public static final int LOGS_ORD_BY_CREATED_AT = 5;
    public static final int SALES = 6;
    public static final int SHOPS = 7;
    public static final int CHECK_LIST = 8;
    public static final int PAYMENT = 9;
    public static final int ACCOUNTS = 10;
    public static final int PROJECTS = 11;
    public static final int PROJECTS_ORD_BY_COMPLETED = 12;
    public static final int TOOLS = 13;
    public static final int TODOS = 14;
    public static final int TODOS_ORD_BY_PRIORITY = 15;
    public static final int PURCHASE = 16;
    public static final int WORKS = 17;
    public static final int CONTACTS = 18;
    public static final int USERS = 19;

    // Home menu Items
    public static final MenuItem ITEM_PROJECT = new MenuItem("Projects", "Create new or see existing projects", R.drawable.ic_home_bookmark_24dp);
    public static final MenuItem ITEM_ACCOUNTS = new MenuItem("Accounts", "", R.drawable.ic_account_balance_wallet_grey_300_24dp);
    public static final MenuItem ITEM_SHOPS = new MenuItem("Shops", "", R.drawable.ic_shopping_basket_grey_300_24dp);
    public static final MenuItem ITEM_CONTACTS = new MenuItem("Contacts", "Company contacts", R.drawable.ic_person_black_24dp);
    public static final MenuItem ITEM_SALE = new MenuItem("Sales", "", R.drawable.ic_shopping_basket_grey_300_24dp);
    public static final MenuItem ITEM_TODO = new MenuItem("ToDos", "See what to do next", R.drawable.ic_home_assignment_24dp);
    public static final MenuItem ITEM_TOOLS = new MenuItem("Tools", "List company tools here", R.drawable.ic_settings_grey_300_24dp);
    public static final MenuItem ITEM_WARRENTIES = new MenuItem("Warrenties", "", R.drawable.ic_settings_grey_300_24dp);
    public static final MenuItem ITEM_CHECK_LIST = new MenuItem("Check Lists", "Check list for easy execution", R.drawable.ic_home_assignment_24dp);
    public static final MenuItem ITEM_CONCRETE_CALCULATOR = new MenuItem("Concrete Calculator", "Get your Utilities from here", R.drawable.ic_markunread_mailbox_grey_300_24dp);
    public static final MenuItem ITEM_PROPERTIES = new MenuItem("Properties", "", R.drawable.ic_chrome_reader_mode_black_24dp);
    public static final MenuItem ITEM_EXPIRY_DATES = new MenuItem("Expiry Dates", "Save all Important dates", R.drawable.ic_today_black_24dp);
    // settings menu
    public static final MenuItem ITEM_LOGOUT = new MenuItem("Log out", "Log out of this Account", R.drawable.ic_today_black_24dp);
    public static final MenuItem ITEM_FEEDBACK = new MenuItem("Feedback", "Send your Valuable feedback", R.drawable.ic_today_black_24dp);
    public static final MenuItem ITEM_ABOUT = new MenuItem("About", "About this Application", R.drawable.ic_today_black_24dp);

    public static final MenuItem[] HOME_MENU_ITEMS = new MenuItem[]
    {
            ITEM_PROJECT,
            //ITEM_TODO,
            //ITEM_ACCOUNTS,
            //ITEM_SHOPS,
            //ITEM_SALE,
            //ITEM_TOOLS,
            ITEM_CHECK_LIST,
            //ITEM_WARRENTIES,            // TODO put this to utility
            //ITEM_CONCRETE_CALCULATOR,   // TODO put this to utility
            //ITEM_PROPERTIES,
            ITEM_EXPIRY_DATES,
            ITEM_CONTACTS,
    };

    public static final MenuItem[] SETTINGS_MENU_ITEMS = new MenuItem[]
    {
            ITEM_LOGOUT,
            ITEM_FEEDBACK,
            ITEM_ABOUT
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
    public static final String KEY_CHECK_LIST = "check_lists";
    public static final String KEY_PAYMENTS = "payments";
    public static final String KEY_ACCOUNTS = "accounts";
    public static final String KEY_PROJECT_KEY = "projectKey";
    public static final String KEY_PURCHASES = "purchases";
    public static final String KEY_WORKS = "works";
    public static final String KEY_TODOS = "todos";
    public static final String KEY_EXPIRY_DATES = "expiry_dates";
    public static final String KEY_LOGS = "logs";
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_DONE = "done";
    public static final String KEY_PRIORITY = "priority";
    public static final String KEY_CONTACTS = "contacts";
    public static final String KEY_USERS = "users";
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

    /*// QUERIES
    public static final Query QUERY_EXPIRY_DATES = getQuery(BASE).child(Data.KEY_EXPIRY_DATES);                                              // expiry dates
    public static final Query QUERY_EXPIRY_DATES_ORD_BY_CREATED_AT = QUERY_EXPIRY_DATES.orderByChild(Data.KEY_CREATED_AT);
    public static final Query QUERY_LOGS = mDatabase.child(Data.KEY_LOGS);                                                              // expiry dates
    public static final Query QUERY_LOGS_ORD_BY_CREATED_AT = QUERY_LOGS.orderByChild(Data.KEY_CREATED_AT);
    public static final Query QUERY_SALES = mDatabase.child(Data.KEY_SALES);                                                            // sales
    public static final Query QUERY_SHOP = mDatabase.child(Data.KEY_SHOPS);                                                             // shop
    public static final Query QUERY_CHECK_LIST = mDatabase.child(Data.KEY_CHECK_LIST);                                                  // check list
    public static final Query QUERY_PAYMENT = mDatabase.child(Data.KEY_PAYMENTS);                                                       // payment
    public static final Query QUERY_ACCOUNTS = mDatabase.child(Data.KEY_ACCOUNTS);                                                      // accounts
    public static final Query QUERY_PROJECTS = mDatabase.child(Data.KEY_PROJECTS);                                                      // projects
    public static final Query QUERY_PROJECTS_ORD_BY_COMPLETED = mDatabase.child(Data.KEY_PROJECTS).orderByChild(Data.KEY_COMPLETED);
    public static final Query QUERY_TOOlS = mDatabase.child(Data.KEY_TOOLS);                                                            // tools
    public static final Query QUERY_TODOS = mDatabase.child(Data.KEY_TODOS);                                                            // todos
    public static final Query QUERY_TODOS_ORD_BY_PRIORITY = mDatabase.child(Data.KEY_TODOS).orderByChild(Data.KEY_PRIORITY);
    public static final Query QUERY_PURCHASES = mDatabase.child(Data.KEY_PURCHASES);                                                    // purchases
    public static final Query QUERY_WORKS = mDatabase.child(Data.KEY_WORKS);                                                            // work
    public static final Query QUERY_CONTACTS = mDatabase.child(Data.KEY_CONTACTS);                                                      // contacts*/

    // :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::


    public static Query getQuery(Context context, int value){
        switch (value){
            case BASE: return FirebaseDatabase.getInstance().getReference().child(getCode(context));
            case EXPIRY_DATES: return
                    FirebaseDatabase.getInstance().getReference()
                            .child(getCode(context))
                            .child(KEY_EXPIRY_DATES);
            case EXPIRY_DATES_ORD_BY_CREATED_AT: return
                    FirebaseDatabase.getInstance().getReference()
                            .child(getCode(context))
                            .child(KEY_EXPIRY_DATES).orderByChild(KEY_CREATED_AT);
            case LOGS: return
                    FirebaseDatabase.getInstance().getReference()
                            .child(getCode(context))
                            .child(KEY_LOGS);
            case LOGS_ORD_BY_CREATED_AT: return
                    FirebaseDatabase.getInstance().getReference()
                            .child(getCode(context))
                            .child(KEY_LOGS).orderByChild(KEY_CREATED_AT);
            case SALES: return
                    FirebaseDatabase.getInstance().getReference()
                            .child(getCode(context))
                            .child(KEY_SALES);
            case SHOPS: return
                    FirebaseDatabase.getInstance().getReference()
                            .child(getCode(context))
                            .child(KEY_SHOPS);
            case CHECK_LIST: return
                    FirebaseDatabase.getInstance().getReference()
                            .child(getCode(context))
                            .child(KEY_CHECK_LIST);
            case PAYMENT: return
                    FirebaseDatabase.getInstance().getReference()
                            .child(getCode(context))
                            .child(KEY_PAYMENTS);
            case ACCOUNTS: return
                    FirebaseDatabase.getInstance().getReference()
                            .child(getCode(context))
                            .child(KEY_ACCOUNTS);
            case PROJECTS: return
                    FirebaseDatabase.getInstance().getReference()
                            .child(getCode(context))
                            .child(KEY_PROJECTS);
            case PROJECTS_ORD_BY_COMPLETED: return
                    FirebaseDatabase.getInstance().getReference()
                            .child(getCode(context))
                            .child(KEY_PROJECTS).orderByChild(KEY_COMPLETED);
            case TOOLS: return
                    FirebaseDatabase.getInstance().getReference()
                            .child(getCode(context))
                            .child(KEY_TOOLS);
            case TODOS: return
                    FirebaseDatabase.getInstance().getReference()
                            .child(getCode(context))
                            .child(KEY_TODOS);
            case TODOS_ORD_BY_PRIORITY: return
                    FirebaseDatabase.getInstance().getReference()
                            .child(getCode(context))
                            .child(KEY_TODOS).orderByChild(KEY_PRIORITY);
            case PURCHASE: return
                    FirebaseDatabase.getInstance().getReference()
                            .child(getCode(context))
                            .child(KEY_PURCHASES);
            case WORKS: return
                    FirebaseDatabase.getInstance().getReference()
                            .child(getCode(context))
                            .child(KEY_WORKS);
            case CONTACTS: return
                    FirebaseDatabase.getInstance().getReference()
                            .child(getCode(context))
                            .child(KEY_CONTACTS);
            case USERS: return
                    FirebaseDatabase.getInstance().getReference()
                            .child(KEY_USERS);
            default: return null;
        }
    }

    private static String getCode(Context context){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d("Riyas.Vmc", user.getUid());
        return user.getUid();
    }
}