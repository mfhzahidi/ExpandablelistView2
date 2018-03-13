package au.com.nextdot.expandablelistview;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.drawable.Drawable;
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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private LinkedHashMap<String, GroupInfo> subjects = new LinkedHashMap<String, GroupInfo>();
    private ArrayList<GroupInfo> deptList = new ArrayList<GroupInfo>();

    private CustomAdapter listAdapter;
    private ExpandableListView simpleExpandableListView;

    private TextView textView;

    private ImageButton back,next;
    Calendar dateSelected;
    final Calendar myCalendar = Calendar.getInstance();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //calender functions
        back = (ImageButton) findViewById(R.id.imageButton);
        next = (ImageButton) findViewById(R.id.imageButton2);

        textView = (TextView) findViewById(R.id.textView4);


        dateSelected = Calendar.getInstance();
        final SimpleDateFormat simpledateformat = new SimpleDateFormat("EEE dd MMM");

        String dayOfWeek = simpledateformat.format(dateSelected.getTime());
        Log.d("date", dayOfWeek);
        textView.setText(dayOfWeek);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateSelected.add(Calendar.DATE, -1);
                String dayOfWeek = simpledateformat.format(dateSelected.getTime());


                Log.d("date", dayOfWeek);
                textView.setText(dayOfWeek);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateSelected.add(Calendar.DATE, 1);
                String dayOfWeek = simpledateformat.format(dateSelected.getTime());


                Log.d("date", dayOfWeek);
                textView.setText(dayOfWeek);
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
    private void updateLabel() {

        String myFormat = "EEE dd MMM"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        textView.setText(sdf.format(myCalendar.getTime()));

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
        addProduct("Evening(4pm-8pm",product);
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

}
