package com.thirdpart.model.entity;
import java.util.List;
public class HeadMenInfos {
private Grade parent;

private List<Class> childrens ;

public void setParent(Grade parent){
this.parent = parent;
}
public Grade getParent(){
return this.parent;
}
public void setChildren(List<Class> children){
this.childrens = children;
}
public List<Class> getChildren(){
return this.childrens;
}

}