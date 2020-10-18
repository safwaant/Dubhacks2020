package com.example.HouseScout;

import android.os.Parcel;
import android.os.Parcelable;

public class AreaMetric implements Parcelable {
    String test_rating;
    String commute_time;
    String medianIncome;
    String unemployment;

    public AreaMetric(String test_rating, String commute_time, String medianIncome, String unemployment) {
        this.test_rating = test_rating;
        this.commute_time = commute_time;
        this.medianIncome = medianIncome;
        this.unemployment = unemployment;
    }

    private AreaMetric(Parcel parcel) {
        test_rating = parcel.readString();
        commute_time = parcel.readString();
        medianIncome = parcel.readString();
        unemployment = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(test_rating);
        parcel.writeString(commute_time);
        parcel.writeString(medianIncome);
        parcel.writeString(unemployment);
    }

    public static final Parcelable.Creator<AreaMetric> CREATOR =
            new Parcelable.Creator<AreaMetric>() {
                @Override
                public AreaMetric createFromParcel(Parcel parcel) {
                    return new AreaMetric(parcel);
                }

                @Override
                public AreaMetric[] newArray(int i) {
                    return new AreaMetric[i];
                }
            };
}