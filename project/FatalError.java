package p2;

import java.util.ArrayList;

public class FatalError implements Comparable<FatalError> {

    String SystemId;
    ArrayList<String> Message = new ArrayList<String>();

    public FatalError(String SystemId,String Message){
        this.SystemId=SystemId;
        this.Message.add(Message);
    }

    public String getSystemId(){
        return this.SystemId;
    }

    public ArrayList<String> getMessage(){
        return this.Message;
    }

    public void addMessage(String Message){
        this.Message.add(Message);
    }

    @Override
    public int compareTo(FatalError fe1) {
        
        return this.SystemId.compareTo(fe1.getSystemId());
    }
    




}