package com.vaibhavmojidra.doctorsappointment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
 import com.vaibhavmojidra.doctorsappointment.DataRetrievalClass.Questioneer

public class SurveyAdapter(var surveyList: List<Questioneer>) : RecyclerView.Adapter<SurveyAdapter.MyVH>() {
var lastsel=-1;
    class MyVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var qTv:TextView?=itemView.findViewById(R.id.question)
        var op:TextView?=itemView.findViewById(R.id.op)
        var op2:TextView?=itemView.findViewById(R.id.op2)
        var op3:TextView?=itemView.findViewById(R.id.op3)
       //var radioG:RadioGroup=itemView.findViewById (R.id.radioG)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVH {
        var v=LayoutInflater.from(parent.context).inflate(R.layout.p_survey_item,null)
        return MyVH(v)
    }

    override fun getItemCount(): Int {
      return  surveyList.size
    }

    override fun onBindViewHolder(holder: MyVH, position: Int) {

        var c=position
        c++
        var itm=surveyList.get(position)
        if(itm.selpos==position){
            when(itm.checkedIndex){
               1->{
                   holder.op?.setBackgroundResource(R.drawable.selected_option_bg)
                   holder.op2?.setBackgroundResource(R.drawable.delected_option_bg)
                   holder.op3?.setBackgroundResource(R.drawable.delected_option_bg)

               }
                2->{
                    holder.op?.setBackgroundResource(R.drawable.delected_option_bg)
                    holder.op2?.setBackgroundResource(R.drawable.selected_option_bg)
                    holder.op3?.setBackgroundResource(R.drawable.delected_option_bg)
                }
                3->{
                    holder.op?.setBackgroundResource(R.drawable.delected_option_bg)
                    holder.op2?.setBackgroundResource(R.drawable.delected_option_bg)
                    holder.op3?.setBackgroundResource(R.drawable.selected_option_bg)
                }
            }
        }else{
            holder.op?.setBackgroundResource(R.drawable.delected_option_bg)
            holder.op2?.setBackgroundResource(R.drawable.delected_option_bg)
            holder.op3?.setBackgroundResource(R.drawable.delected_option_bg)
        }
        holder.qTv?.setText(surveyList.get(position).quesion)
        holder.op?.setText(surveyList.get(position).op)
        holder.op2?.setText(surveyList.get(position).op2)
        holder.op3?.setText(surveyList.get(position).op3)
        holder.op?.setOnClickListener({


            surveyList.get(position).checkedIndex=1
            surveyList.get(position).selpos=position
            surveyList.get(position).userCheckedAns=holder.op?.text.toString()
            notifyDataSetChanged()
            lastsel=position



        })
        holder.op2?.setOnClickListener({


            surveyList.get(position).checkedIndex=2
            surveyList.get(position).selpos=position
            surveyList.get(position).userCheckedAns=holder.op2?.text.toString()
            notifyDataSetChanged()

        })
        holder.op3?.setOnClickListener({


            surveyList.get(position).checkedIndex=3
            surveyList.get(position).selpos=position
            surveyList.get(position).userCheckedAns=holder.op3?.text.toString()
            notifyDataSetChanged()

        })

     /*   holder.radioG.setOnCheckedChangeListener({ group, checkedId ->
            val radioButton = holder.radioG.findViewById<RadioButton>(checkedId)
            surveyList.get(position).userCheckedAns=radioButton.text.toString();
            surveyList.get(position).isChecked=true
        })*/


    }
}