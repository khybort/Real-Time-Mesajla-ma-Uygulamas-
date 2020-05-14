package com.example.chatt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    List<String> list;
    String phoneNumber;
    RecyclerView userRecyView;
    UserAdapter userAdapter;
    ImageView backMainImage;
    ImageView yeniNumara;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tanimla();
        listele();
    }
    public void tanimla()
    {
        phoneNumber = getIntent().getStringExtra("kadi");
        firebaseDatabase =FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();
        list= new ArrayList<>();
        userRecyView=(RecyclerView)findViewById(R.id.userRecyView);
        RecyclerView.LayoutManager layoutManager= new GridLayoutManager(MainActivity.this,2);
        userRecyView.setLayoutManager(layoutManager);
        userAdapter = new UserAdapter(MainActivity.this,list,MainActivity.this,phoneNumber);
        userRecyView.setAdapter(userAdapter);
        backMainImage=(ImageView)findViewById(R.id.backMainImage);
        backMainImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        yeniNumara=(ImageView)findViewById(R.id.yeniNumara);
        yeniNumara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, KisiEkle.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("kadi",phoneNumber);
                startActivity(intent);
            }
        });

    }
    public void listele()
    {
        reference.child("Kullanıcılar").child(phoneNumber).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(!dataSnapshot.getKey().equals(phoneNumber)) {
                    list.add(dataSnapshot.getKey());
                    Log.i("Kullanıcı",dataSnapshot.getKey());
                    Log.i("dyd",phoneNumber);
                    userAdapter.notifyDataSetChanged();

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
}
