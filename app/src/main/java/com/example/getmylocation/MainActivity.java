package com.example.getmylocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.getmylocation.databinding.ActivityMainBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //Initialize variable
//    Button btLocation;
//    TextView textView1, textView2, textView3, textView4, textView5;
    FusedLocationProviderClient fusedLocationProviderClient;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        //Assign variable
//        btLocation = findViewById(R.id.bt_location);
//        textView1 = findViewById(R.id.text_view1);
//        textView2 = findViewById(R.id.text_view2);
//        textView3 = findViewById(R.id.text_view3);
//        textView4 = findViewById(R.id.text_view4);
//        textView5 = findViewById(R.id.text_view5);
//
        //Intialize fusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        binding.btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check permission
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //When permission granted
                    getLocation();
                } else {
                    //When permission denied
                    ActivityCompat.requestPermissions(MainActivity.this
                            , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
                /** A safe way to get an instance of the Camera object. */

            }
            /** Check if this device has a camera */
            private boolean checkCameraHardware(Context context) {
                if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){
                    // this device has a camera
                    return true;
                } else {
                    // no camera on this device
                    return false;
                }
            }

            private void getLocation() {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }

                
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location != null) {
                            //Initialize  geoCoder
                            Geocoder geocoder = new Geocoder(MainActivity.this,
                                    Locale.getDefault());
                            //Intilaize addresss list
                            try {
                                List<Address> addresses = geocoder.getFromLocation(
                                        location.getLatitude(), location.getLongitude(), 1
                                );
                                //Set lattitude on textView
                                binding.textView1.setText(Html.fromHtml(
                                        "<font color='#6200EE'><b>Latitude :</b><br></font>"
                                                + addresses.get(0).getLatitude()
                                ));
                                binding.textView2.setText(Html.fromHtml(
                                        "<font color='#6200EE'><b>Longitude :</b><br></font>"
                                                + addresses.get(0).getLongitude()
                                ));
                                //Set Country name
                                binding.textView3.setText(Html.fromHtml(
                                        "<font color='#6200EE'><b>Country :</b><br></font>"
                                                + addresses.get(0).getCountryName()
                                ));
                                //Set Locality
                                binding.textView4.setText(Html.fromHtml(
                                        "<font color='#6200EE'><b>Locality :</b><br></font>"
                                                + addresses.get(0).getLocality()
                                ));
                                //Set address
                                binding.textView5.setText(Html.fromHtml(
                                        "<font color='#6200EE'><b>address :</b><br></font>"
                                                + addresses.get(0).getAddressLine(0)
                                ));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
    }
}