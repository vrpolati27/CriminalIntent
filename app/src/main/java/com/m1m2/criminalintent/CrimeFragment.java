package com.m1m2.criminalintent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.m1m2.criminalintent.model.Crime;
import com.m1m2.criminalintent.model.CrimeLab;

import java.util.Date;
import java.util.UUID;

/**
 * Created by vinayreddypolati on 2/17/17.
 */

public class CrimeFragment extends Fragment {
    private static final String TAG ="CrimeFragment";
    private EditText crimeTitle;
    private Crime crime;
    private Typeface sportify;
    private TextView crimeTitleText;
    private TextView crimeDateText;
    private  TextView crimeDate;
    private CheckBox crimeSolved;
    public static final String ARGUMENT_CRIME_ID = "crimeId";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 9;

    public static CrimeFragment getInstance(UUID crimeId){
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARGUMENT_CRIME_ID,crimeId);
        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(arguments);
        return  fragment;
    }

    /* configure Fragment's instance here, you do not inflate Fragments view here*/
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG," onCreate() called");
        UUID crimeId = (UUID) getArguments().getSerializable(ARGUMENT_CRIME_ID);
        crime = CrimeLab.getInstance(getContext()).getCrime(crimeId);
        sportify = Typeface.createFromAsset(getActivity().getAssets(),getString(R.string.sporify));
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG," onStop() called");
        //returnResult(Activity.RESULT_OK);
    }

    private void returnResult(int result){
        Intent data = new Intent();
        data.putExtra(ARGUMENT_CRIME_ID,crime.getmId());
        getActivity().setResult(result,data);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=Activity.RESULT_OK){
            return;
        }else if(requestCode==REQUEST_DATE){
            Date d3= (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            crime.setmDate(d3);
            crimeDate.setText(crime.getmDate().toString());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG," onAttach() called");
    }

    /* create and configure Fragment's view here*/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_crime,container,false);
        Log.d(TAG," onCreateView() ");
        crimeTitleText = (TextView) rootView.findViewById(R.id.tv_crime_title);
        crimeTitleText.setTypeface(sportify);

        crimeTitle = (EditText) rootView.findViewById(R.id.et_crime_name);
        crimeTitle.setTypeface(sportify);
        crimeTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                   crime.setmTitle(s.toString());
                   returnResult(Activity.RESULT_OK);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        crimeDateText = (TextView) rootView.findViewById(R.id.tv_date_title);
        crimeDateText.setTypeface(sportify);

        crimeDate = (Button) rootView.findViewById(R.id.btn_date);
        crimeDate.setTypeface(sportify);
        //crimeDate.setEnabled(false);
        crimeDate.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                DatePickerFragment datePickerFragment = DatePickerFragment.getInstance(crime.getmDate());
                datePickerFragment.setTargetFragment(CrimeFragment.this,REQUEST_DATE);
                datePickerFragment.show(fragmentManager,DIALOG_DATE);
            }
        });

        crimeSolved = (CheckBox) rootView.findViewById(R.id.cb_crime_solved);
        crimeSolved.setTypeface(sportify);
        crimeSolved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                crime.setmMistrySolved(isChecked);
                returnResult(Activity.RESULT_OK);
            }
        });
        populateData();
        return rootView;
    }

    private void populateData(){
        if(crime!=null){
            crimeTitle.setText(crime.getmTitle());
            crimeDate.setText(crime.getmDate().toString());
            crimeSolved.setChecked(crime.ismMistrySolved());
        }
    }
}
