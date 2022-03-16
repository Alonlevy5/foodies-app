package com.comas.foodies;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.Navigation;

import com.comas.foodies.model.Model;
import com.comas.foodies.model.Recipe;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MapsActivity extends AppCompatActivity {

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private FusedLocationProviderClient client;
    private final int REQUEST_CODE = 111;
    private ConnectivityManager manager;
    private NetworkInfo networkInfo;
    private Geocoder geocoder;
    private TextView editTextLocation;
    private double selectedLat ,selectedLng ;
    private List<Address> addresses;
    private String selectedAddress;
    private Button saveLocationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_current_place);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);
        saveLocationBtn = findViewById(R.id.location_save);
        editTextLocation = findViewById(R.id.addRecipe_location);
        client = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }


    }


    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                if(location != null) {
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            mMap = googleMap;
                            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                            String address = latLng.toString();
                            googleMap.addMarker(new MarkerOptions().position(latLng).title("You are here"));
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));

                            saveLocationBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    editTextLocation.setText(address);
                                    Recipe recipe = new Recipe();
                                    AddRecipeFragment addRecipeFragment = new AddRecipeFragment();
                                    Model.instance.addRecipe(recipe, ()->{
                                        Navigation.findNavController(addRecipeFragment.nameEt).navigateUp();
                                    });
                                }
                            });

                            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                                @Override
                                public void onMapClick(@NonNull LatLng latLng) {
                                    CheckConnection();

                                    if(networkInfo.isConnected() && networkInfo.isAvailable()){
                                        selectedLat = latLng.latitude;
                                        selectedLng= latLng.longitude;

                                        GetAddress(selectedLat,selectedLng);


                                    }else {
                                        Toast.makeText(MapsActivity.this,
                                                "Please Check Connection", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                        }
                    });

                }

            }
        });
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                getCurrentLocation();
            }
        }else
        {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void CheckConnection(){
        manager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = manager.getActiveNetworkInfo();
    }

    private void GetAddress(double mLat, double mLng) {
        geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
        if (mLat != 0) {
            try {
                addresses = geocoder.getFromLocation(mLat, mLng, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addresses != null) {
                String mAddress = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                String dis = addresses.get(0).getSubAdminArea();

                selectedAddress = mAddress;





                if (mAddress != null) {
                    LatLng latLng = new LatLng(mLat, mLng);
                    mMap.addMarker(new MarkerOptions().position(latLng).title(selectedAddress));
                } else {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, " LatLng null", Toast.LENGTH_SHORT).show();
        }
    }




}