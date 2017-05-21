package com.technawabs.cityservices.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;

import java.util.Comparator;
import java.util.Objects;

public class Product implements Parcelable,Comparable<Product>{

    private int serialnumber;
    private int amountPledged;
    private String blurb;
    private String by;
    private String country;
    private String currency;
    private String endTime;
    private String location;
    private int percentageFunded;
    private int numberOfBackers;
    private String state;
    private String title;
    private String type;
    private String url;

    public int getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(int serialnumber) {
        this.serialnumber = serialnumber;
    }

    public int getAmountPledged() {
        return amountPledged;
    }

    public void setAmountPledged(int amountPledged) {
        this.amountPledged = amountPledged;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPercentageFunded() {
        return percentageFunded;
    }

    public void setPercentageFunded(int percentageFunded) {
        this.percentageFunded = percentageFunded;
    }

    public int getNumberOfBackers() {
        return numberOfBackers;
    }

    public void setNumberOfBackers(int numberOfBackers) {
        this.numberOfBackers = numberOfBackers;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private Product(Parcel in){
        serialnumber=in.readInt();
        amountPledged=in.readInt();
        blurb=in.readString();
        by=in.readString();
        country=in.readString();
        currency=in.readString();
        endTime=in.readString();
        location=in.readString();
        percentageFunded=in.readInt();
        numberOfBackers=in.readInt();
        state=in.readString();
        title=in.readString();
        type=in.readString();
        url=in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(serialnumber);
        dest.writeInt(amountPledged);
        dest.writeString(blurb);
        dest.writeString(by);
        dest.writeString(country);
        dest.writeString(currency);
        dest.writeString(endTime);
        dest.writeString(location);
        dest.writeInt(percentageFunded);
        dest.writeInt(numberOfBackers);
        dest.writeString(state);
        dest.writeString(title);
        dest.writeString(type);
        dest.writeString(url);
    }

    public static final Parcelable.Creator<Product> CREATOR=new Parcelable.Creator<Product>(){

        public Product createFromParcel(Parcel in){
            return new Product(in);
        }

        public Product[] newArray(int size){
            return new Product[size];
        }
    };

//    @Override
//    public int compareTo(@NonNull Product o) {
//        return this.serialnumber-o.serialnumber;
//    }

    public static Comparator<Product> titleComparator=new Comparator<Product>() {
        @Override
        public int compare(Product o1, Product o2) {
            return o1.getTitle().compareToIgnoreCase(o2.getTitle());
        }
    };

    public static Comparator<Product> countryComparator=new Comparator<Product>() {
        @Override
        public int compare(Product o1, Product o2) {
            return o1.getCountry().compareToIgnoreCase(o2.getCountry());
        }
    };

    public static Comparator<Product> locationComparator=new Comparator<Product>() {
        @Override
        public int compare(Product o1, Product o2) {
            return o1.getLocation().compareToIgnoreCase(o2.getLocation());
        }
    };

    public static Comparator<Product> byComparator=new Comparator<Product>() {
        @Override
        public int compare(Product o1, Product o2) {
            return o1.getBy().compareToIgnoreCase(o2.getBy());
        }
    };

    public static Comparator<Product> fundComparator=new Comparator<Product>() {
        @Override
        public int compare(Product o1, Product o2) {
            return o1.getPercentageFunded()-o2.getPercentageFunded();
        }
    };

    public static Comparator<Product> amountComparator=new Comparator<Product>() {
        @Override
        public int compare(Product o1, Product o2) {
            return o1.getAmountPledged()-o2.getAmountPledged();
        }
    };

    public static Comparator<Product> backersComparator=new Comparator<Product>() {
        @Override
        public int compare(Product o1, Product o2) {
            return o1.getNumberOfBackers()-o2.getNumberOfBackers();
        }
    };

    @Override
    public int compareTo(@NonNull Product o) {
        return 0;
    }
}
