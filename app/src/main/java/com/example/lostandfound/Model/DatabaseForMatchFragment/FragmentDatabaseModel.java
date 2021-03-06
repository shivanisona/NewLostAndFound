package com.example.lostandfound.Model.DatabaseForMatchFragment;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lostandfound.Model.CardPOJO.Card;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static com.example.lostandfound.NameClass.CHAT;
import static com.example.lostandfound.NameClass.CONNECTIONS;
import static com.example.lostandfound.NameClass.DETAILS;
import static com.example.lostandfound.NameClass.EDIT;
import static com.example.lostandfound.NameClass.IMAGE_URI;
import static com.example.lostandfound.NameClass.NAME;
import static com.example.lostandfound.NameClass.POST;
import static com.example.lostandfound.NameClass.RENDER_TIME;
import static com.example.lostandfound.NameClass.USERS;
import static com.example.lostandfound.NameClass.amOrPmForStoringDatabse;
import static com.example.lostandfound.NameClass.dateForStoringDatabse;
import static com.example.lostandfound.NameClass.descriptionForStoringDatabase;
import static com.example.lostandfound.NameClass.hourForStoringDatabse;
import static com.example.lostandfound.NameClass.minuteForStoringDatabse;
import static com.example.lostandfound.NameClass.monthForStoringDatabse;
import static com.example.lostandfound.NameClass.yearForStoringDatabse;

public class FragmentDatabaseModel {
    public double choosenLatitude;
    public double choosenLongitude;
    public List list;

    public FragmentDatabaseModel(List list) {
        this.list = list;
    }

    public void setArrayList(double longitude, double latitude) {
        this.choosenLongitude = longitude;
        this.choosenLatitude = latitude;

        final String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(USERS);
        final Card[] item = new Card[1];

        //reference = reference.child(currentUser).child(DETAILS);
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()) {

                    final String[] profileImage = {"default"};
                    final String userId[] = new String[1];
                    userId[0] = dataSnapshot.getKey();

                    //Log.e("ERROR IS -->", dataSnapshot.child(DETAILS).child(EDIT).child(IMAGE_URI).toString());

                    if (!dataSnapshot.getKey().equals(currentUser)) {

                        final String currentUserName[] = new String[1];
                        currentUserName[0] = "";

                        final String desc[] = new String[1];
                        desc[0] = "";


                        if (dataSnapshot.child(DETAILS).child(EDIT).exists() || dataSnapshot.child(DETAILS).child(EDIT).child(NAME).exists()) {
                            currentUserName[0] = dataSnapshot.child(DETAILS).child(EDIT).child(NAME).getValue().toString();
                        }


                        DatabaseReference referencePost = FirebaseDatabase.getInstance().getReference()
                                .child(USERS)
                                .child(dataSnapshot.getKey())
                                .child(DETAILS)
                                .child(POST);

                        referencePost.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                String stringLatitude = (String) dataSnapshot.child("Latitude").getValue();
                                String stringLongitude = (String) dataSnapshot.child("Longitude").getValue();
                                // Log.v("Out of IF CONDITION", stringLatitude);

                                if (stringLatitude != null && stringLongitude != null) {

                                    double currentUserLatitude = Double.parseDouble(stringLatitude);
                                    double currentUserLongitude = Double.parseDouble(stringLongitude);
                                    Log.v("In IF CONDITION", currentUserLatitude + "   " + choosenLatitude);
                                    if ((choosenLatitude >= currentUserLatitude - 0.5) && (choosenLatitude <= currentUserLatitude + 0.5)) {
                                        if ((choosenLongitude >= currentUserLongitude - 0.5) && (choosenLatitude <= currentUserLongitude + 0.5)) {
                                            Log.v("In IF CONDITION", stringLatitude);

                                            item[0] = new Card();
                                            if (dataSnapshot.child(IMAGE_URI).getValue() == null || dataSnapshot.child(IMAGE_URI).getValue().equals(profileImage[0])) {

                                                item[0].setId(userId[0]);
                                                item[0].setName(currentUserName[0]);
                                                item[0].setProfileImageUrl(profileImage[0]);

                                                Log.v("added", "doneeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                                            } else {
                                                profileImage[0] = dataSnapshot.child(IMAGE_URI).getValue().toString();

                                                item[0].setId(userId[0]);
                                                item[0].setName(currentUserName[0]);
                                                item[0].setProfileImageUrl(profileImage[0]);

                                            }

                                            if (dataSnapshot.child(descriptionForStoringDatabase) != null || dataSnapshot.child(descriptionForStoringDatabase).getValue() != null) {
                                                String desc = dataSnapshot.child(descriptionForStoringDatabase).getValue().toString();
                                                item[0].setDesc(desc);
                                            }

                                            if (dataSnapshot.child(dateForStoringDatabse) != null || dataSnapshot.child(dateForStoringDatabse).getValue() != null) {
                                                String date = (String) dataSnapshot.child(dateForStoringDatabse).getValue();
                                                item[0].setDate(date);
                                            }

                                            if (dataSnapshot.child(monthForStoringDatabse) != null || dataSnapshot.child(monthForStoringDatabse).getValue() != null) {
                                                String month = dataSnapshot.child(monthForStoringDatabse).getValue().toString();
                                                item[0].setMonth(month);
                                            }

                                            if (dataSnapshot.child(yearForStoringDatabse) != null || dataSnapshot.child(yearForStoringDatabse).getValue() != null) {
                                                String year = dataSnapshot.child(yearForStoringDatabse).getValue().toString();
                                                item[0].setYear(year);
                                            }

                                            if (dataSnapshot.child(hourForStoringDatabse) != null || dataSnapshot.child(hourForStoringDatabse).getValue() != null) {
                                                String hour = dataSnapshot.child(hourForStoringDatabse).getValue().toString();
                                                item[0].setHour(hour);
                                            }

                                            if (dataSnapshot.child(minuteForStoringDatabse) != null || dataSnapshot.child(minuteForStoringDatabse).getValue() != null) {
                                                String minute = dataSnapshot.child(minuteForStoringDatabse).getValue().toString();
                                                item[0].setMinute(minute);
                                            }
                                            if (dataSnapshot.child(amOrPmForStoringDatabse) != null || dataSnapshot.child(amOrPmForStoringDatabse).getValue() != null) {
                                                String amOrPm = dataSnapshot.child(amOrPmForStoringDatabse).getValue().toString();
                                                item[0].setFormat(amOrPm);
                                            }

                                            list.add(item[0]);
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


//
//                        String stringLatitude = (String) dataSnapshot.child(DETAILS).child(EDIT).child("Latitude").getValue();
//                        String stringLongitude = (String) dataSnapshot.child(DETAILS).child(EDIT).child("Longitude").getValue();
//                       // Log.v("Out of IF CONDITION", stringLatitude);
//
//
//                        if (stringLatitude != null && stringLongitude != null) {
//
//                            double currentUserLatitude = Double.parseDouble(stringLatitude);
//                            double currentUserLongitude = Double.parseDouble(stringLongitude);
//                            Log.v("In IF CONDITION", currentUserLatitude + "   " + choosenLatitude);
//                            if ((choosenLatitude >= currentUserLatitude - 0.5) && (choosenLatitude <= currentUserLatitude + 0.5)) {
//                                if ((choosenLongitude >= currentUserLongitude - 0.5) && (choosenLatitude <= currentUserLongitude + 0.5)) {
//                                    Log.v("In IF CONDITION", stringLatitude);
//
//                                    if (dataSnapshot.child(DETAILS).child(EDIT).child(IMAGE_URI).getValue() == null || dataSnapshot.child(DETAILS).child(IMAGE_URI).getValue().equals(profileImage)) {
//                                        item[0] = new Card(dataSnapshot.getKey(), dataSnapshot.child(DETAILS).child(EDIT).child(NAME).getValue().toString(), profileImage);
//                                        list.add(item[0]);
//                                        Log.v("added", "doneeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
//                                    } else {
//                                        profileImage = dataSnapshot.child(DETAILS).child(EDIT).child(IMAGE_URI).getValue().toString();
//                                        item[0] = new Card(dataSnapshot.getKey(), dataSnapshot.child(DETAILS).child(EDIT).child(NAME).getValue().toString(), profileImage);
//                                        list.add(item[0]);
//                                    }
//                                }
//                            }
//                        }
                    }

                    Log.v("Item[0] value is ", item[0] + "");
                    //list.add(item);
//
//                    new java.util.Timer().schedule(
//                            new java.util.TimerTask() {
//                                @Override
//                                public void run() {
//                                    // your code here
//                                    Log.v("LATER item[0] value is ",item[0]+"");
//                                    list.add(item[0]);
//                                    Log.v("Size",list.size()+"");
//                                }
//                            },
//                            RENDER_TIME
//                    );
                    int k = 9;
                    // ar.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//
//        new java.util.Timer().schedule(
//                new java.util.TimerTask() {
//                    @Override
//                    public void run() {
//                        // your code here
//                        Log.v("LATER item[0] value is ",item[0]+"");
//                        list.add(item[0]);
//                        Log.v("List Size",list.size()+"");
//                    }
//                },
//                RENDER_TIME
//        );
    }

    public List getList() {
        return list;
    }

    public void setArrayListForMessage() {
        final String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference referenceChat = FirebaseDatabase.
                getInstance().
                getReference().
                child(USERS).
                child(currentUser).
                child(CONNECTIONS);

        referenceChat.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()) {
                    DatabaseReference referenceUser = FirebaseDatabase.
                            getInstance().
                            getReference().
                            child(USERS);

                    final String key = dataSnapshot.getKey();

                    referenceUser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String name = (String) dataSnapshot.child(key).child(DETAILS).child(EDIT).child(NAME).getValue();
                            String uri = (String) dataSnapshot.child(key).child(DETAILS).child(EDIT).child(IMAGE_URI).getValue();
                            Card c = new Card();
                            c.setId(key);
                            c.setName(name);
                            c.setProfileImageUrl(uri);
                            list.add(c);
                            Log.v("Previous List Size", list.size() + "");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public List getListForMessage() {
        Log.v("Later List Size", list.size() + "");
        return list;
    }
}