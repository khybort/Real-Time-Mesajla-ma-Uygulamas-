package com.example.chatt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class KisiEkle extends AppCompatActivity {

    EditText mPhone;
    Button mRegisterBtn;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kisi_ekle);
        mPhone      = findViewById(R.id.phone);
        mRegisterBtn= findViewById(R.id.registerBtn);
        spinner = findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));

        firebaseDatabase =FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference();
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];
                String number =mPhone.getText().toString().trim();
                String  phoneNumber = "+" + code + number;
                final String mPhoneNumber = getIntent().getStringExtra("kadi");

                if (number.isEmpty() || number.length() < 10) {
                    mPhone.setError("Geçerli bir değer giriniz");
                    mPhone.requestFocus();
                    return;
                }
                reference.child("Kullanıcılar").child(mPhoneNumber).child(phoneNumber).setValue(phoneNumber).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                        Intent intent = new Intent(KisiEkle.this, MainActivity.class);
                        intent.putExtra("kadi",mPhoneNumber);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }}
                });

            }
        });

    }
}
