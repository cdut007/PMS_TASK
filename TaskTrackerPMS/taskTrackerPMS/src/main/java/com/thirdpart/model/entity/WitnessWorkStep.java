package com.thirdpart.model.entity;
import java.util.List;

import com.thirdpart.model.entity.base.Data;
public class WitnessWorkStep extends Data{
private String id;

private String witness;

private String witnessdes;

private String witnessaddress;

private String witnessdate;

private String status;

private String isok;

private String triggerName;

private String createdOn;

private String createdBy;

private WorkStep workStep;

private List<Class> children ;

public void setId(String id){
this.id = id;
}
public String getId(){
return this.id;
}
public void setWitness(String witness){
this.witness = witness;
}
public String getWitness(){
return this.witness;
}
public void setWitnessdes(String witnessdes){
this.witnessdes = witnessdes;
}
public String getWitnessdes(){
return this.witnessdes;
}
public void setWitnessaddress(String witnessaddress){
this.witnessaddress = witnessaddress;
}
public String getWitnessaddress(){
return this.witnessaddress;
}
public void setWitnessdate(String witnessdate){
this.witnessdate = witnessdate;
}
public String getWitnessdate(){
return this.witnessdate;
}
public void setStatus(String status){
this.status = status;
}
public String getStatus(){
return this.status;
}
public void setIsok(String isok){
this.isok = isok;
}
public String getIsok(){
return this.isok;
}
public void setTriggerName(String triggerName){
this.triggerName = triggerName;
}
public String getTriggerName(){
return this.triggerName;
}
public void setCreatedOn(String createdOn){
this.createdOn = createdOn;
}
public String getCreatedOn(){
return this.createdOn;
}
public void setCreatedBy(String createdBy){
this.createdBy = createdBy;
}
public String getCreatedBy(){
return this.createdBy;
}
public void setWorkStep(WorkStep workStep){
this.workStep = workStep;
}
public WorkStep getWorkStep(){
return this.workStep;
}
public void setChildren(List<Class> children){
this.children = children;
}
public List<Class> getChildren(){
return this.children;
}

}