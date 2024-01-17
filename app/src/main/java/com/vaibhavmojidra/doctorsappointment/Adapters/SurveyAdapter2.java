package com.vaibhavmojidra.doctorsappointment.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vaibhavmojidra.doctorsappointment.DataRetrievalClass.Questioneer;
import com.vaibhavmojidra.doctorsappointment.R;

import java.util.ArrayList;
import java.util.List;

public class SurveyAdapter2 extends
        RecyclerView.Adapter<SurveyAdapter2.MyVH> {

    List<Questioneer> surveyList=new ArrayList<>();
    public SurveyAdapter2(List<Questioneer> surveyList) {
        this.surveyList=surveyList;
    }

    @NonNull
    @Override
    public SurveyAdapter2.MyVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.p_survey_item,null);
        return new MyVH(view);
    }
int lastsel=-1;
    @Override
    public void onBindViewHolder(@NonNull SurveyAdapter2.MyVH holder, int position) {

        int c=position;
        c++;
        Questioneer itm=surveyList.get(position);
        if(itm.selpos==position){
            switch (itm.checkedIndex){
                case  1: {
                   holder.op.setBackgroundResource(R.drawable.selected_option_bg);
                   holder.op2.setBackgroundResource(R.drawable.delected_option_bg);
                   holder.op3.setBackgroundResource(R.drawable.delected_option_bg);

                }
                break;
                case  2:{
                   holder.op.setBackgroundResource(R.drawable.delected_option_bg);
                   holder.op2.setBackgroundResource(R.drawable.selected_option_bg);
                   holder.op3.setBackgroundResource(R.drawable.delected_option_bg);
                }
                break;
                case  3:{
                   holder.op.setBackgroundResource(R.drawable.delected_option_bg);
                   holder.op2.setBackgroundResource(R.drawable.delected_option_bg);
                   holder.op3.setBackgroundResource(R.drawable.selected_option_bg);
                }
                break;
            }
        }else{
            holder.op.setBackgroundResource(R.drawable.delected_option_bg);
            holder.op2.setBackgroundResource(R.drawable.delected_option_bg);
            holder.op3.setBackgroundResource(R.drawable.delected_option_bg);
        }
        holder.qTv.setText(surveyList.get(position).question);
        holder.op.setText(surveyList.get(position).op);
        holder.op2.setText(surveyList.get(position).op2);
        holder.op3.setText(surveyList.get(position).op3);
        holder.op.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        surveyList.get(position).checkedIndex = 1;
                        surveyList.get(position).selpos = position;
                        surveyList.get(position).userCheckedAns = holder.op.getText().toString();
                        notifyDataSetChanged();
                        lastsel=position;
                    }
                }

        );
        holder.op2.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   surveyList.get(position).checkedIndex = 2;
                                                   surveyList.get(position).selpos=position;
                                                   surveyList.get(position).userCheckedAns=holder.op2.getText().toString();
                                                   notifyDataSetChanged();
                                               }
                                           }




        );
        holder.op3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        surveyList.get(position).checkedIndex = 3;
                        surveyList.get(position).selpos = position;
                        surveyList.get(position).userCheckedAns = holder.op3.getText().toString();
                        notifyDataSetChanged();
                    }
                }


       );

     /*   holder.radioG.setOnCheckedChangeListener({ group, checkedId ->
            val radioButton = holder.radioG.findViewById<RadioButton>(checkedId)
            surveyList.get(position).userCheckedAns=radioButton.text.toString();
            surveyList.get(position).isChecked=true
        })*/

    }

    @Override
    public int getItemCount() {
        return surveyList.size();
    }

    class MyVH extends RecyclerView.ViewHolder{
      public   TextView qTv,op,op2,op3;
        public MyVH(@NonNull View itemView) {
            super(itemView);
              qTv=itemView.findViewById(R.id.question);
            op=itemView.findViewById(R.id.op);
          op2=itemView.findViewById(R.id.op2);
              op3=itemView.findViewById(R.id.op3);
        }
    }
}
