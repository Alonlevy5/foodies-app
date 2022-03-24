package com.comas.foodies;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.comas.foodies.model.Model;
import com.comas.foodies.model.Recipe;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


@SuppressWarnings("ALL")
public class MapsActivity extends FragmentActivity implements  OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener{

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation ;
    private AddRecipeFragment addRecipeFragment;
    private LocationManager mlocationManager;
    private LocationRequest mLocationRequest;
    private com.google.android.gms.location.LocationListener locationListener;
    private long UPDATE_INTERVAL = 2000;
    private long FASTEST_INTERVAL = 5000;
    private LocationManager locationManager;
    private LatLng latLng;
    private boolean isPermission ;
    private SupportMapFragment mapFragment;
    private FusedLocationProviderClient client;
    private final int REQUEST_CODE = 111;
    private ConnectivityManager manager;
    private NetworkInfo networkInfo;
    private Geocoder geocoder;
    private FloatingActionButton floatingActionButton;
    private TextView editTextLocation;
    private double selectedLat ,selectedLng ;
    private List<Address> addresses;
    private String selectedAddress;
    DatabaseReference databaseReference ;
    private FirebaseDatabase tracker;
    private DatabaseReference databaseLocation;
    private Button saveLocationBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_current_place);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        saveLocationBtn = findViewById(R.id.location_save);
        editTextLocation = findViewById(R.id.addRecipe_location);
        floatingActionButton =findViewById(R.id.addRecipe_locationBtn);
        client = LocationServices.getFusedLocationProviderClient(this);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();




        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }



    }

    private boolean checkLocation() {
        if(!isLocationEnabled()){
            showAlert();
        }
        return isLocationEnabled();
    }

    private void showAlert() {
        final android.app.AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your locations Settings is set to 'Off'.\nPlease Enable Location to " )
                .setPositiveButton("Location Setting", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                        startActivity(myIntent);
                    }

                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        dialog.show();

    }

    private boolean isLocationEnabled() {
        locationManager =(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

    }

    private boolean requestSinglePermission() {
        return true;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            return;
        }

        startLocationUpdates();
        mLocation= LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLocation == null){
            startLocationUpdates();;
        }
        else
        {
            Toast.makeText(this,"Location not Detected",Toast.LENGTH_SHORT).show();
        }

    }

    private void startLocationUpdates() {
        saveLocationBtn.setVisibility(View.VISIBLE);
        mLocationRequest = com.google.android.gms.location.LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);

        LocationServices.getFusedLocationProviderClient(MapsActivity.this)
                .requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(@NonNull LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(MapsActivity.this)
                                .removeLocationUpdates(this);
                        if(locationResult != null && locationResult.getLocations().size() > 0 ){
                            int latestLocationIndex = locationResult.getLocations().size() - 1 ;
                            double latitude = locationResult
                                    .getLocations().get(latestLocationIndex).getLatitude();
                            double longitude =
                                    locationResult.getLocations().get(latestLocationIndex).getLongitude();
                            String ltude = Double.toString(latitude);
                            String logtude = Double.toString(longitude);
                            String combine = ltude + " - " + logtude ;
                            editTextLocation.setText(combine);


                            saveLocationBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    tracker = FirebaseDatabase.getInstance();
                                    databaseLocation = tracker.getReference("recipes");
                                    databaseLocation.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                            for (DataSnapshot child : snapshot.child("recipes").getChildren()) {
                                                AddRecipeFragment addRecipeFragment = snapshot.getValue(AddRecipeFragment.class);
                                                String latitude = child.child("latitude").getValue().toString();
                                                String longitude = child.child("longitude").getValue().toString();
                                                double loclatitude = Double.parseDouble(latitude);
                                                double loclongitude = Double.parseDouble(longitude);
                                                LatLng cod = new LatLng(loclatitude, loclongitude);
                                                mMap.addMarker(new MarkerOptions().position(cod).title(""));
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                    Recipe recipe = new Recipe();
                                    Model.instance.addRecipe(recipe, ()->{
                                        Navigation.findNavController(addRecipeFragment.nameEt).navigateUp();
                                    });
                                }

                            });

                        }
                    }
                }, Looper.getMainLooper());

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED){
            return;
        }


        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest,this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap= googleMap;

        if(latLng != null){
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Current Localion"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,14F));

        }

    }
  /*
    @Override
    public void onLocationChanged(@NonNull Location location) {

        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
        latLng = new LatLng(location.getLatitude(),location.getLatitude());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);



    }

   */

    public void onStart(){
        super.onStart();

        if(mGoogleApiClient != null){
            mGoogleApiClient.connect();
        }
    }

    public void  onStop(){
        super.onStop();

        if(mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
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
                                    databaseLocation.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot child : snapshot.child("recipes").getChildren()) {
                                                AddRecipeFragment recipeFragment = snapshot.getValue(AddRecipeFragment.class);
                                                String latitude = child.child("latitude").getValue().toString();
                                                String longitude = child.child("longitude").getValue().toString();
                                                double loclatitude = Double.parseDouble(latitude);
                                                double loclongitude = Double.parseDouble(longitude);
                                                LatLng cod = new LatLng(loclatitude, loclongitude);
                                                mMap.addMarker(new MarkerOptions().position(cod).title(""));
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
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


    @Override
    public void onLocationChanged(@NonNull Location location) {

    }
}