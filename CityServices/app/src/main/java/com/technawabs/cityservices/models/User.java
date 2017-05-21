package com.technawabs.cityservices.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class User implements Comparator<User>, Parcelable {

    private long id;
    private String email;
    private List<String> mobile;
    private String name;
    private String location;
    private double lattitude;
    private double longitude;
    private double balance;
    private boolean[] booleanValues;
    private boolean isSeekingBalance;
    private boolean isSeekingTalktime;
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getMobile() {
        return mobile;
    }

    public void setMobile(List<String> mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean[] getBooleanValues() {
        return booleanValues;
    }

    public void setBooleanValues(boolean[] booleanValues) {
        this.booleanValues = booleanValues;
    }

    public boolean isSeekingBalance() {
        return isSeekingBalance;
    }

    public void setSeekingBalance(boolean seekingBalance) {
        isSeekingBalance = seekingBalance;
    }

    public boolean isSeekingTalktime() {
        return isSeekingTalktime;
    }

    public void setSeekingTalktime(boolean seekingTalktime) {
        isSeekingTalktime = seekingTalktime;
    }

    protected User(Parcel in) {
        id = in.readLong();
        email = in.readString();
//        in.readList(mobile,new ClassLoaderCreator<String>(){});
        mobile = in.readArrayList(new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                return super.loadClass(name);
            }
        });
        name = in.readString();
        location = in.readString();
        lattitude = in.readLong();
        longitude = in.readLong();
        balance = in.readDouble();
        booleanValues = new boolean[2];
        booleanValues[0] = isSeekingBalance;
        booleanValues[1] = isSeekingTalktime;
        in.readBooleanArray(booleanValues);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(email);
        dest.writeList(mobile);
        dest.writeString(name);
        dest.writeString(location);
        dest.writeDouble(lattitude);
        dest.writeDouble(longitude);
        dest.writeDouble(balance);
        dest.writeBooleanArray(booleanValues);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public User(User user){
        this.user=user;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int compare(User user1, User user2) {
        double distanceToPlace1 = distance(this.lattitude, this.longitude, user1.lattitude, user1.longitude);
        double distanceToPlace2 = distance(this.longitude, this.longitude, user1.lattitude, user2.longitude);
        return (int)(distanceToPlace1-distanceToPlace2);
    }

    public double distance(double fromLat, double fromLon, double toLat, double toLon) {
        double radius = 6378137;   // approximate Earth radius, *in meters*
        double deltaLat = toLat - fromLat;
        double deltaLon = toLon - fromLon;
        double angle = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(deltaLat / 2), 2) +
                        Math.cos(fromLat) * Math.cos(toLat) * Math.pow(Math.sin(deltaLon / 2), 2)));
        return radius * angle;
    }

    public static Comparator<User> nameSort=new Comparator<User>() {
        @Override
        public int compare(User o1, User o2) {
            return o1.getName().compareToIgnoreCase(o2.getName());
        }
    };
}
