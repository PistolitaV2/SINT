package p2;

import java.util.ArrayList;

public class Error implements Comparable<Error> {

    String SystemId;
    ArrayList<String> Message = new ArrayList<String>();

    public Error(String SystemId,String Message){
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
    public int compareTo(Error e1) {
        
        return this.SystemId.compareTo(e1.getSystemId());
    }
    




}