package com.vaibhavmojidra.doctorsappointment.PatientFragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.vaibhavmojidra.doctorsappointment.Adapters.DoctorAdatper;
import com.vaibhavmojidra.doctorsappointment.Adapters.SurveyAdapter2;
import com.vaibhavmojidra.doctorsappointment.DataRetrievalClass.Doctor;
import com.vaibhavmojidra.doctorsappointment.DataRetrievalClass.Questioneer;
import com.vaibhavmojidra.doctorsappointment.R;
import com.vaibhavmojidra.doctorsappointment.ReusableFunctionsAndObjects;
import java.util.ArrayList;
import java.util.List;

public class PatientSearchDiseaseFragment  extends Fragment {
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private List<Questioneer> diseaseAndSymptoms;
    private SurveyAdapter2 diseaseAdatper;
    private SearchView searchView;
    Button btnShareWithdoctor;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_common,container,false);
        setHasOptionsMenu(true);
        recyclerView = view.findViewById(R.id.recycler_view);
        btnShareWithdoctor=view.findViewById(R.id.bookappoitment);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        diseaseAndSymptoms= new ArrayList<>();
        progressDialog= new ProgressDialog(getContext());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...\nPlease wait...");
        progressDialog.show();                         //Questioneer  DiseaseAndSymptoms
        FirebaseDatabase.getInstance().getReference().child("Questioneer").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                diseaseAndSymptoms.clear();
                for(DataSnapshot childsnapshot:snapshot.getChildren()){
                   // Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                    diseaseAndSymptoms.add(childsnapshot.getValue(Questioneer.class));
                }
              //  diseaseAndSymptoms.add(snapshot.getValue(Questioneer.class));

                diseaseAdatper=new SurveyAdapter2(diseaseAndSymptoms);
                recyclerView.setAdapter(diseaseAdatper);
                progressDialog.dismiss();
                btnShareWithdoctor.setVisibility(View.VISIBLE);
                btnShareWithdoctor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bookdoctor();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                ReusableFunctionsAndObjects.showMessageAlert(getContext(),"Network Error",error.getMessage(),"Ok",(byte)0);
            }
        });
        return view;
    }
    private List<Doctor> doctors;
    private DoctorAdatper doctorAdatper;

    public void showDoctorsListDialogue(){
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
   View v=LayoutInflater.from(getActivity()).inflate(R.layout.docs_list_dialogue,null);
RecyclerView rc=v.findViewById(R.id.docsRc);
rc.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        doctorAdatper=new DoctorAdatper(getContext(),doctors,getActivity());
        rc.setAdapter(doctorAdatper);

        materialAlertDialogBuilder.setView(v);
        materialAlertDialogBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        materialAlertDialogBuilder.show();


    }

  public static   String surverAns="";
    public void bookdoctor(){
        doctors= new ArrayList<>();
        progressDialog= new ProgressDialog(getContext());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...\nPlease wait...");
        progressDialog.show();
        FirebaseDatabase.getInstance().getReference().child("DoctorDetails").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                doctors.clear();
                for(DataSnapshot childsnapshot:snapshot.getChildren()){
                    doctors.add(childsnapshot.getValue(Doctor.class));
                }

                //doctors.add(snapshot.getValue(Doctor.class));

                progressDialog.dismiss();

                showDoctorsListDialogue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                ReusableFunctionsAndObjects.showMessageAlert(getContext(),"Network Error",error.getMessage(),"Ok",(byte)0);
            }
        });
for(int i=0;i<diseaseAndSymptoms.size();i++){

    surverAns=surverAns+"\n"+""+diseaseAndSymptoms.get(i).question+" ::" +
            "\nAns:  "+diseaseAndSymptoms.get(i).userCheckedAns;
}

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.my_search_menu,menu);
        MenuItem menuItem=menu.findItem(R.id.search_bar);
        searchView=(SearchView)menuItem.getActionView();
        searchView.setQueryHint("Search Disease");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query!=null){
                    filter(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText!=null){
                    filter(newText);
                }
                return true;
            }
        });
    }

    private void filter(String s){
        List<Questioneer> filteredlist=new ArrayList<>();
        for(Questioneer symptoms: diseaseAndSymptoms){
            if(symptoms.question.toLowerCase().contains(s.toLowerCase())){
                filteredlist.add(symptoms);
            }
        }
        diseaseAdatper=new SurveyAdapter2(filteredlist);
        recyclerView.setAdapter(diseaseAdatper);
    }
}
