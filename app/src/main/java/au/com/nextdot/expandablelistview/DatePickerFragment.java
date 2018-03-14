package au.com.nextdot.expandablelistview;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by sakib on 3/14/2018.
 */

@SuppressLint("ValidFragment")
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    private Calendar mCalender;
    private OnSetDateText mDateTextSetter;

    public DatePickerFragment(OnSetDateText dateTextSetter, Calendar calendar){
        mDateTextSetter = dateTextSetter;
        mCalender = calendar;
        // test line
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        int year = mCalender.get(Calendar.YEAR);
        int month = mCalender.get(Calendar.MONTH);
        int day = mCalender.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), this, year, month, day);
        Date today = new Date();
        datePickerDialog.getDatePicker()
                .setMinDate(today.getTime());
        return datePickerDialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        mCalender.set(year, month, day);
        mDateTextSetter.setDateText(mCalender);
    }

    interface OnSetDateText{
        public void setDateText(Calendar calendar);
    }
}



