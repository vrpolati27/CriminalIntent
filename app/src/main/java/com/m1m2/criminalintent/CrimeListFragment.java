package com.m1m2.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.m1m2.criminalintent.model.Crime;
import com.m1m2.criminalintent.model.CrimeLab;

import java.util.List;

/**
 * Created by vinayreddypolati on 2/18/17.
 */

public class CrimeListFragment extends Fragment {

    private RecyclerView mRecylerView;
    private CrimeAdapter mAdapter;
    private Typeface sportify;
    private static final int REQUEST_CRIME = 13;
    private static final String TAG = "CrimeListFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sportify = Typeface.createFromAsset(getActivity().getAssets(),"fonts/sportify.ttf");
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.menu_item_new_crime:
                /* Action when user presses + menuItem*/
                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_crime_list,container,false);
        mRecylerView = (RecyclerView) rootView.findViewById(R.id.crime_recycler_view);
        mRecylerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG,"onActivityResult() ");
        if(resultCode != Activity.RESULT_OK){
            return ;
        }else{
            if(requestCode==REQUEST_CRIME){
                /* use data sent by CrimeActivity to update Adapter*/
                Log.d(TAG,"onActivityResult() "+data.getExtras().getSerializable(CrimeFragment.ARGUMENT_CRIME_ID));
                int count = 0;
                for(Crime c1:CrimeLab.getInstance(getActivity()).getCrimes()){
                    if(c1.getmId().equals(data.getExtras().getSerializable(CrimeFragment.ARGUMENT_CRIME_ID)))
                        break;
                    count++;
                }
                mAdapter.notifyItemChanged(count);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //updateUI();
    }

    private void updateUI(){
        CrimeLab crimeLab = CrimeLab.getInstance(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        if(mAdapter==null){

            mAdapter = new CrimeAdapter(crimes);
            mRecylerView.setAdapter(mAdapter);
        }else {
            mAdapter.notifyDataSetChanged();
        }


    }
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView mTitleTextView;
        public TextView mCrimeDate;
        public CheckBox mCriveMystrySolved;
        private Crime mCrime;
        public CrimeHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mTitleTextView.setTypeface(sportify);
            mCrimeDate = (TextView) itemView.findViewById(R.id.crime_date);
            mCrimeDate.setTypeface(sportify);
            mCriveMystrySolved = (CheckBox) itemView.findViewById(R.id.crime_solved);
            mCriveMystrySolved.setTypeface(sportify);
            mCriveMystrySolved.setClickable(false);
            //mTitleTextView.setTextAppearance(getActivity(),android.R.style.TextAppearance_Large);
        }

        public void bindCrime(Crime crime){
             this.mCrime = crime;
             mTitleTextView.setText(crime.getmTitle());
             mCrimeDate.setText(crime.getmDate().toString());
             mCriveMystrySolved.setChecked(crime.ismMistrySolved());
        }


        @Override
        public void onClick(View v) {
            /* take to Crime Activity*/
            Intent intent = CrimePagerActivity.getIntent(getContext(),mCrime.getmId());
            startActivityForResult(intent,REQUEST_CRIME);
        }

    }


    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{

        private List<Crime> mCrimes;
        public CrimeAdapter(List<Crime> mCrimes){
            this.mCrimes = mCrimes;
        }
        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            View view = layoutInflater.inflate(R.layout.list_item_crime,parent,false);
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bindCrime(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}
