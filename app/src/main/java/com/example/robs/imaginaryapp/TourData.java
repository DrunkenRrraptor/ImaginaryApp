package com.example.robs.imaginaryapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Robs on 11.11.18.
 */

public class TourData implements Parcelable {

    private int id = 0;
    private String title = "";
    private String shortDescription = "";
    private String description = "";
    private String thumb = "";
    private int thumb_dummy = 0;
    private String image400x200 = "";
    private String img800x600 = "";
    private String startData = "";
    private String endData = "";
    private double price = 0;

    public TourData(int id, String title, String shortDescription, String description, String thumb, String image400x200, String img800x600, String startData, String endData, double price) {
        this.id = id;
        this.title = title;
        this.shortDescription = shortDescription;
        this.description = description;
        this.thumb = thumb;
        this.image400x200 = image400x200;
        this.img800x600 = img800x600;
        this.startData = startData;
        this.endData = endData;
        this.price = price;
    }

    public TourData(int id, String title, String shortDescription, String description, String thumb, int thumb_dummy, String image400x200, String img800x600, String startData, String endData, double price) {
        this.id = id;
        this.title = title;
        this.shortDescription = shortDescription;
        this.description = description;
        this.thumb = thumb;
        this.thumb_dummy = thumb_dummy;
        this.image400x200 = image400x200;
        this.img800x600 = img800x600;
        this.startData = startData;
        this.endData = endData;
        this.price = price;
    }

    public TourData(int id, String title, String shortDescription, String description, int thumb_dummy, String image400x200, String img800x600, String startData, String endData, double price) {
        this.id = id;
        this.title = title;
        this.shortDescription = shortDescription;
        this.description = description;
        this.thumb_dummy = thumb_dummy;
        this.image400x200 = image400x200;
        this.img800x600 = img800x600;
        this.startData = startData;
        this.endData = endData;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public int getThumb_dummy() {
        return thumb_dummy;
    }

    public void setThumb_dummy(int thumb_dummy) {
        this.thumb_dummy = thumb_dummy;
    }

    public String getImage400x200() {
        return image400x200;
    }

    public void setImage400x200(String image400x200) {
        this.image400x200 = image400x200;
    }

    public String getImg800x600() {
        return img800x600;
    }

    public void setImg800x600(String img800x600) {
        this.img800x600 = img800x600;
    }

    public String getStartData() {
        return startData;
    }

    public void setStartData(String startData) {
        this.startData = startData;
    }

    public String getEndData() {
        return endData;
    }

    public void setEndData(String endData) {
        this.endData = endData;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    /*
    public static final Parcelable.Creator<TourData> CREATOR = new Parcelable.Creator<TourData>() {
        public TourData createFromParcel(Parcel in) {
            return new TourData(in);
        }

        public TourData[] newArray(int size) {

            return new TourData[size];
        }

    };*/

    public static final Parcelable.Creator<TourData> CREATOR = new Parcelable.Creator<TourData>(){

        @Override
        public TourData createFromParcel(Parcel parcel) {
            return new TourData(parcel);
        }

        @Override
        public TourData[] newArray(int size) {
            return new TourData[size];
        }
    };

    public TourData(Parcel in) {
        super();
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt( id );
        dest.writeString( title );
        dest.writeString( shortDescription );
        dest.writeString( description );
        dest.writeString( thumb );
        dest.writeInt( thumb_dummy );
        dest.writeString( image400x200 );
        dest.writeString( img800x600 );
        dest.writeString( startData );
        dest.writeString( endData );
        dest.writeDouble( price );


        /*private int id = 0;
    private String title = "";
    private String shortDescription = "";
    private String description = "";
    private String thumb = "";
    private int thumb_dummy = 0;
    private String image400x200 = "";
    private String img800x600 = "";
    private String startData = "";
    private String endData = "";
    private double price = 0;*/

    }

    public void readFromParcel(Parcel in){

        id = in.readInt();
        title = in.readString();
        shortDescription = in.readString();
        description = in.readString();
        thumb = in.readString();
        thumb_dummy = in.readInt();
        image400x200 = in.readString();
        img800x600 = in.readString();
        startData = in.readString();
        endData = in.readString();
        price = in.readDouble();

    }

}
