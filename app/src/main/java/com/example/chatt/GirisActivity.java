package com.example.chatt;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public  class GirisActivity extends AppCompatActivity {


    private Spinner spinner;
    private EditText phoneNumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        spinner = findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));

        phoneNumber = findViewById(R.id.editTextPhone);


        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];

                String number = phoneNumber.getText().toString();

                if (number.isEmpty() || number.length() < 10 ) {
                    phoneNumber.setError("Lütfen geçerli bir numara giriniz.");
                    phoneNumber.requestFocus();
                    return;
                }

               String  phoneNumber = "+" + code + number;
                Log.i("dyd",phoneNumber);
                Intent intent = new Intent(GirisActivity.this, VerifyPhoneActivity.class);
                intent.putExtra("phonenumber", phoneNumber);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            String phoneNumber= FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("kadi",phoneNumber);
            startActivity(intent);
        }
    }
}