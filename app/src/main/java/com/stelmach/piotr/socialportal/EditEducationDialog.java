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

public class EditEducationDialog extends AppCompatDialogFragment {

    private DatePickerDialog.OnDateSetListener mOnDateSetListener;
    private DatePickerDialog.OnDateSetListener mOnDateSetListenerFrom;

    private EditEducationDialogListener editEducationDialogListener;

    EditText mSchoolName;
    EditText mDeegree;
    EditText mFieldOfStudy;
    EditText mDateFrom;
    EditText mDateTo;
    CheckBox mCurrent;
    EditText mDescription;

    public static EditEducationDialog newInstance(String id,String school, String deegree,String fieldofstudy,
                                                  String from,String to,Boolean current,String description) {
        Bundle args = new Bundle();

        EditEducationDialog fragment = new EditEducationDialog();
        args.putString("id",id);
        args.putString("school",school);
        args.putString("deegree",deegree);
        args.putString("fieldofstudy",fieldofstudy);
        args.putString("from",from);
        args.putString("to",to);
        args.putBoolean("current",current);
        args.putString("description",description);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.edit_education_dialog_layout,null);


        builder.setView(view).setTitle("Add education")
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("Edit education", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String schoolName=mSchoolName.getText().toString();
                String deegree=mDeegree.getText().toString();
                String fieldOfStudy=mFieldOfStudy.getText().toString();
                String dateFrom=mDateFrom.getText().toString();
                String dateTo="";
                Boolean current;
                if(mCurrent.isChecked()){current=true;}
                else {  dateTo=mDateTo.getText().toString(); current=false;}
                String desc=mDescription.getText().toString();

                editEducationDialogListener.applyEditData(getArguments().getString("id","id"),schoolName,deegree,fieldOfStudy,
                        dateFrom,dateTo,current,desc);

            }
        });

        mSchoolName=view.findViewById(R.id.schoolEditText);
        mDeegree=view.findViewById(R.id.deegreeEditText);
        mFieldOfStudy=view.findViewById(R.id.fieldOfStudyEdit);
        mDateFrom=view.findViewById(R.id.fromDateEdit);
        mDateTo=view.findViewById(R.id.toDateAddEdit);
        mCurrent=view.findViewById(R.id.currentCheckBoxEdit);
        mDescription=view.findViewById(R.id.descriptionAddEdit);

        mDateFrom.setFocusable(false);
        mDateTo.setFocusable(false);

        mSchoolName.setText(getArguments().getString("school"));
        mDeegree.setText(getArguments().getString("deegree"));
        mFieldOfStudy.setText(getArguments().getString("fieldofstudy"));
        mDateFrom.setText(getArguments().getString("from"));
        mDateTo.setText(getArguments().getString("to"));
        if(getArguments().getBoolean("current")) mCurrent.setChecked(true);
        else mCurrent.setChecked(false);
        mDescription.setText(getArguments().getString("description"));


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
            editEducationDialogListener=(EditEducationDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }


    public interface EditEducationDialogListener{
        void applyEditData(String id,String school, String deegree, String fieldOfStudy,
                          String dateFrom, String dateTo, Boolean current, String description);
    }
}
