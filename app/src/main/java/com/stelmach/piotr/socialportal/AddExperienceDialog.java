package com.stelmach.piotr.socialportal;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class AddExperienceDialog extends AppCompatDialogFragment {

    private DatePickerDialog.OnDateSetListener mOnDateSetListener;
    private DatePickerDialog.OnDateSetListener mOnDateSetListenerFrom;

    private AddExperienceDialogListener addExperienceDialogListener;

    EditText mTitle;
    EditText mCompany;
    EditText mLocation;
    EditText mDateFrom;
    EditText mDateTo;
    CheckBox mCurrent;
    EditText mDescription;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.add_experience_dialog_layout,null);


        builder.setView(view).setTitle("Add experience")
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("Add experience", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String title=mTitle.getText().toString();
                String company=mCompany.getText().toString();
                String location=mLocation.getText().toString();
                String dateFrom=mDateFrom.getText().toString();
                String dateTo="";
                Boolean current;
                if(mCurrent.isChecked()){current=true;}
                else {  dateTo=mDateTo.getText().toString(); current=false;}
                String desc=mDescription.getText().toString();

                addExperienceDialogListener.applyExpData(title,company,location,dateFrom,dateTo,current,
                        desc);
            }
        });

        mTitle=view.findViewById(R.id.titleAddText);
        mCompany=view.findViewById(R.id.companyAddText);
        mLocation=view.findViewById(R.id.locationAddText);
        mDateFrom=view.findViewById(R.id.fromDateAddExp);
        mDateTo=view.findViewById(R.id.toDateAddExp);
        mCurrent=view.findViewById(R.id.currentCheckBoxAddExp);
        mDescription=view.findViewById(R.id.descriptionAddExp);

        mDateFrom.setFocusable(false);
        mDateTo.setFocusable(false);

        mCurrent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    mDateTo.setEnabled(false);
                }else mDateTo.setEnabled(true);
            }
        });

        mDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(),R.style.Theme_AppCompat_Dialog_MinWidth,
                        mOnDateSetListenerFrom,year,month,day);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
                datePickerDialog.show();
            }
        });

        mDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCurrent.isChecked()){return;}
                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(),R.style.Theme_AppCompat_Dialog_MinWidth,
                        mOnDateSetListener,year,month,day);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
                datePickerDialog.show();

            }
        });

        mOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                String date=i+"-"+i1+"-"+i2;
                mDateTo.setText(date);
            }
        };

        mOnDateSetListenerFrom=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String date=i+"-"+i1+"-"+i2;
                mDateFrom.setText(date);
            }
        };

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            addExperienceDialogListener=(AddExperienceDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    public interface AddExperienceDialogListener{
        void applyExpData(String title, String company, String location,
                          String dateFrom, String dateTo, Boolean current, String description);
    }
}
