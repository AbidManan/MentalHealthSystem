package com.vaibhavmojidra.doctorsappointment.DataRetrievalClass;

public class Questioneer {

    public String question,op,op2,op3;
   public int selpos=-1;
     public       int checkedIndex=-1;

     public  String userCheckedAns="";

    public String getQuesion() {
        return question;
    }

    public String getOp() {
        return op;
    }

    public String getOp2() {
        return op2;
    }

    public String getOp3() {
        return op3;
    }

    public void setQuesion(String question) {
        this.question = question;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public void setOp2(String op2) {
        this.op2 = op2;
    }

    public void setOp3(String op3) {
        this.op3 = op3;
    }

    public Questioneer() {

    }
    public Questioneer(String question, String op,String op2,String op3) {
        this.question = question;
        this.op = op;
        this.op2=op2;
        this.op3=op3;
    }

}
