package com.thirdpart.model.entity;
import java.util.List;
public class WitnesserList {
private String witnesserType;

private List<Witnesser> witnesser ;

public void setWitnesserType(String witnesserType){
this.witnesserType = witnesserType;
}
public String getWitnesserType(){
return this.witnesserType;
}
public void setWitnesser(List<Witnesser> witnesser){
this.witnesser = witnesser;
}
public List<Witnesser> getWitnesser(){
return this.witnesser;
}

}