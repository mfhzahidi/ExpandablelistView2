package au.com.nextdot.expandablelistview;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.drawable.Drawable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements DatePickerFragment.OnSetDateText{

    private LinkedHashMap<String, GroupInfo> subjects = new LinkedHashMap<String, GroupInfo>();
    private ArrayList<GroupInfo> deptList = new ArrayList<GroupInfo>();

    private CustomAdapter listAdapter;
    private ExpandableListView simpleExpandableListView;

    private TextView textView;

    private ImageButton back,next;
    SimpleDateFormat simpledateformat;
    Calendar dateSelected,curentDate;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //calender functions
        back = (ImageButton) findViewById(R.id.imageButton);
        next = (ImageButton) findViewById(R.id.imageButton2);

        textView = (TextView) findViewById(R.id.textView4);


        dateSelected=Calendar.getInstance();
        simpledateformat = new SimpleDateFormat("EEE dd MMM");
        setDateText(dateSelected);


        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openDatePicker(view);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decrement();

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increment();
            }
        });


        // add data for displaying in expandable list view
        loadData();

        //get reference of the ExpandableListView
        simpleExpandableListView = (ExpandableListView) findViewById(R.id.simpleExpandableListView);
        // create the adapter by passing your ArrayList data
        listAdapter = new CustomAdapter(MainActivity.this, deptList);
        // attach the adapter to the expandable list view
        simpleExpandableListView.setAdapter(listAdapter);

        //expand all the Groups
        //expandAll();
        collapseAll();

        // setOnChildClickListener listener for child row click
        simpleExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //get the group header
                GroupInfo headerInfo = deptList.get(groupPosition);
                //get the child info
                ChildInfo detailInfo = headerInfo.getProductList().get(childPosition);
                //display it or do something with it
               // Toast.makeText(getBaseContext(), " Clicked on :: " + headerInfo.getName()
                //        + "/" + detailInfo.getName(), Toast.LENGTH_LONG).show();
                return false;
            }
        });
        // setOnGroupClickListener listener for group heading click
        simpleExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //get the group header
                GroupInfo headerInfo = deptList.get(groupPosition);
                //display it or do something with it
               // Toast.makeText(getBaseContext(), " Header is :: " + headerInfo.getName(),
               //         Toast.LENGTH_LONG).show();

                return false;
            }
        });
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            simpleExpandableListView.setIndicatorBounds(simpleExpandableListView.getRight() - 50, simpleExpandableListView.getWidth());
        } else {
            simpleExpandableListView.setIndicatorBoundsRelative(simpleExpandableListView.getRight() - 50, simpleExpandableListView.getWidth());
        }
    }
        //
        // ethod to expand all groups

    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            simpleExpandableListView.expandGroup(i);
        }
    }

    //method to collapse all groups
    private void collapseAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            simpleExpandableListView.collapseGroup(i);
        }
    }

    //load some initial data into out list
    private void loadData() {

        String[] product={"10:00 AM","10:15 AM","10:30 AM","10:45 AM","11:00 AM"};

        addProduct("Morning(10am-12pm)",product);

        product= new String[]{"12:00 PM", "12:15 AM", "12:30 AM", "12:45 AM", "01:00 AM"};
        addProduct("Afternoon(12pm-4pm",product);
        product= new String[]{"12:00 PM", "12:15 AM", "12:30 AM", "12:45 AM", "01:00 AM"};
        addProduct("Evening(4pm-8pm)",product);
        //addProduct("Morning(10am-12pm)", "10:15 AM");
        //addProduct("Morning(10am-12pm)", "10:30 AM");
        //addProduct("Morning(10am-12pm)", "10:45 AM");
        //addProduct("Morning(10am-12pm)", "11:00 AM");



    }
    //here we maintain our products in various departments
    private int addProduct(String department, String[] product){

        int groupPosition = 0;

        //check the hash map if the group already exists
        GroupInfo headerInfo = subjects.get(department);
        //add the group if doesn't exists
        if(headerInfo == null){
            headerInfo = new GroupInfo();
            headerInfo.setName(department);

            subjects.put(department, headerInfo);
            deptList.add(headerInfo);
        }

        //get the children for the group
        ArrayList<ChildInfo> productList = headerInfo.getProductList();
        //size of the children list
        int listSize = productList.size();
        //add to the counter
        listSize++;

        //create a new child and add that to the group
        ChildInfo detailInfo = new ChildInfo();

        detailInfo.setName(product);
        productList.add(detailInfo);
        headerInfo.setProductList(productList);
        //find the group position inside the list
        groupPosition = deptList.indexOf(headerInfo);
        return groupPosition;
    }
    @Override
    public void setDateText(Calendar calendar) {
        Date date = dateSelected.getTime();
        String dateString = simpledateformat.format(date);
        textView.setText(dateString);
    }

    private void increment() {
        dateSelected.add(Calendar.DATE, 1);
        setDateText(dateSelected);
    }

    // compare date in calender with today's date and disable it when it is today's date
    private void decrement() {
        Date calenderDate = dateSelected.getTime();
        Date today = new Date();
        //comparing calender's date with today's date
        if(calenderDate.after(today)) {
            dateSelected.add(Calendar.DATE, -1);
            setDateText(dateSelected);
        }
    }

    public void openDatePicker(View view){
        DialogFragment newFragment = new DatePickerFragment((DatePickerFragment.OnSetDateText) this,dateSelected);
        FragmentManager fragmentManager = getSupportFragmentManager();
        newFragment.show(fragmentManager, "datePicker");
    }

}
