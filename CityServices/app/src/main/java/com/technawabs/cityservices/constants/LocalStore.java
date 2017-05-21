package com.technawabs.cityservices.constants;

import java.io.Serializable;

public enum LocalStore implements Serializable{

    USER_STORE("USERSTORE");

    private String name;

    LocalStore(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }

}
