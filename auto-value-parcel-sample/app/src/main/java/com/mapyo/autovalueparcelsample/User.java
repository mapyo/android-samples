package com.mapyo.autovalueparcelsample;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

@AutoValue public abstract class User implements Parcelable {
    public abstract String firstName();
    public abstract String lastName();
    public abstract String email();

    public static Builder builder() {
        return new AutoValue_User.Builder();
    }

    @AutoValue.Builder public static abstract class Builder {
        public abstract Builder firstName(String firstName);
        public abstract Builder lastName(String lastName);
        public abstract Builder email(String email);
        public abstract User build();
    }
}
