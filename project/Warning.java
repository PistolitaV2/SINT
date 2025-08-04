package p2;

import java.util.ArrayList;

public class Warning implements Comparable<Warning> {

    String SystemId;
    ArrayList<String> Message = new ArrayList<String>();

    public Warning(String SystemId,String Message){
        this.SystemId=SystemId;
        this.Message.add(Message);
    }

    public String getSystemId(){
        return this.SystemId;
    }

    public ArrayList<String> getMessage(){
        return Message;
    }

    public void addMessage(String Message){
        this.Message.add(Message);
    }

    @Override
    public int compareTo(Warning w1) {
        
        return this.SystemId.compareTo(w1.getSystemId());
    }
    




}