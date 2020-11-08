package com.androidApp.virusGame.UI;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.androidApp.virusGame.Model.VirusSingleton;
import com.androidApp.virusGame.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Random;

//FIXME keep checking users' locations and user permission
public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {
    GoogleMap map ;
    private static final String[] LOCATION_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private static final int REQUEST_LOCATION_PERMISSIONS = 0;
    private static final String FINE_LOCATION  =  Manifest.permission.ACCESS_FINE_LOCATION ;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final float DEFAULT_ZOOM = 15f ;
    private LatLng mDefaultLocation ;
    private LatLng mDeviceLocation ;
    private boolean mLocationPermissionGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient ;
    private String TAG = "mapDebugging" ;
    private LocationCallback mLocationCallback ;
    private boolean firstTimeGetLocation = true ;
    private Marker currentLocationMark ;
    private ArrayList< LatLng> virusLocations = new ArrayList<>() ;
    private ArrayList< Marker> virusMarkers = new ArrayList<>() ;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState  ) {
        super.onCreate(savedInstanceState );
        getLocationPermission()  ;
        setHasOptionsMenu(true);
        getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        mDefaultLocation = new LatLng(40.0, -83.0) ;
        currentLocationMark= map.addMarker(new MarkerOptions().position(mDefaultLocation)
                .title("Ohio State University"));

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));

    }

   /* @Override
    public void onResume() {
        super.onResume();
        //start location updates

        }
    }

     @Override
    public void onPause() {
        super.onPause();
        mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);

    } */



    @SuppressLint("MissingPermission")
    private void findDeviceLocation(){

        // Create the LocationRequest object
        LocationRequest mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY) //PRIORITY_NO_POWER
                .setInterval(5* 1000)        // 5 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                     Log.d(TAG, "CANNOT GET DEVICE LOCATION") ;
                     return ;
                }
                for (Location location : locationResult.getLocations()) {
                    if(location !=null){
                        mDeviceLocation = new LatLng(location.getLatitude(),  location.getLongitude());
                        if(firstTimeGetLocation) {
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(mDeviceLocation, DEFAULT_ZOOM));
                            firstTimeGetLocation =false;
                        }
                        currentLocationMark.remove() ;
                        currentLocationMark = map.addMarker(new MarkerOptions()
                                .position(mDeviceLocation)
                                .title("Current Location"));


                    }

                }
            }
        };
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity()) ;
        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null) ;

       /* mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

            if(mLocationPermissionGranted){
                Task<Location> location = mFusedLocationProviderClient.getLastLocation() ;
                location.addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location!=null){
                            Toast.makeText(getActivity(),"Found Location!" + location.toString(), Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(getActivity(), " Location is null !" , Toast.LENGTH_SHORT).show();
                        }
                    }
                }) ;

            }

        */



    }


    private void getLocationPermission(){
        if(ContextCompat.checkSelfPermission(getActivity(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        && ContextCompat.checkSelfPermission(getActivity(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            mLocationPermissionGranted =true;
        }else{
            ActivityCompat.requestPermissions(getActivity(), LOCATION_PERMISSIONS, REQUEST_LOCATION_PERMISSIONS);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        if (requestCode == REQUEST_LOCATION_PERMISSIONS) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
            }
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.map_menu, menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.search) {
            if (mLocationPermissionGranted) {
                findDeviceLocation();
                setUpVirus() ;
                //get the random virus location within +- 0.05
                double deviceLongitude = mDeviceLocation.longitude ;
                double deviceLatitude = mDeviceLocation.latitude ;


                //FIXME
                //myLocation -> locationRange(longitude/ latitude)
                //-> use locationRange to generate random locations for three virus
                //-> show them on map
                //->if the player is closer enough(how to update ui on the screen) -> notify & ask user if they want to start the game
                //->start game activity(fragment)

                /*-> refreshing the virus location if the player is too far away
                if user location is too far away from the original generated virus, then delete the current
                virus location/image on the screen and regenerate three new virus images that is nearby the player's new location */


            } else {
                requestPermissions(LOCATION_PERMISSIONS, REQUEST_LOCATION_PERMISSIONS);
            }
        }
        return true;
    }

    //FIXME : save current state
    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setUpVirus(){
        //get the random virus location within +- 0.05
        double deviceLongitude = mDeviceLocation.longitude ;
        double deviceLatitude = mDeviceLocation.latitude ;
        VirusSingleton singleton = VirusSingleton.get(getActivity());
        Random rnd = new Random() ;
        double locationRange  ;
        LatLng  virusLoc ;
        for(int i =0 ; i<singleton.getVirusCount() ; i++){
           locationRange = (rnd.nextInt(10)-5)/100.0 ;
            virusLoc = new LatLng(deviceLatitude + locationRange  , deviceLongitude+locationRange) ;
            virusLocations.add(virusLoc) ;
            Marker virusMarker = map.addMarker(new MarkerOptions()
                    .position(virusLoc));
            virusMarkers.add(virusMarker ) ;


        }
        singleton.updateVirusLocation( virusLocations) ;


    }


}
