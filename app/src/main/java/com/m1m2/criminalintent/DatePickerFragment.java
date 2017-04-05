package com.m1m2.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by vinayreddypolati on 2/20/17.
 */

public class DatePickerFragment extends DialogFragment {
    private static final String ARGUMENT_DATE = "date";
    private DatePicker datePicker;
    public static final String EXTRA_DATE = "date";
    public static DatePickerFragment getInstance(Date date){
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARGUMENT_DATE,date);
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setArguments(arguments);
        return  datePickerFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        /* getting Initial date from Fragment argument */
        Date date = (Date) getArguments().getSerializable(ARGUMENT_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        View datePickerWidget = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date,null);
        datePicker = (DatePicker) datePickerWidget.findViewById(R.id.dialog_date_date_picker);
        datePicker.init(year,month,day,null);
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.date_picker_title)
                .setView(datePickerWidget)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year = datePicker.getYear();
                        int month = datePicker.getMonth();
                        int day = datePicker.getDayOfMonth();
                        Date d1 = new GregorianCalendar(year,month,day).getTime();
                        sendResult(Activity.RESULT_OK,d1);
                    }
                })
                .create();

    }

    private void sendResult(int resultCode,Date date){
        if(getTargetFragment() == null){
            return ;
        }else {
            Intent data = new Intent();
            data.putExtra(EXTRA_DATE,date);
            getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,data);
        }
    }

}
