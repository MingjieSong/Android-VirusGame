package com.androidApp.virusGame.UI;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.androidApp.virusGame.Model.Virus;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback{
    GoogleMap map ;
    private static final String[] LOCATION_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private static final int REQUEST_LOCATION_PERMISSIONS = 0;
    private static final String FINE_LOCATION  =  Manifest.permission.ACCESS_FINE_LOCATION ;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final float DEFAULT_ZOOM = 10f ;
    private static final double VIRUS_RANGE = 0.04 ;
    private LatLng mDefaultLocation ;
    private LatLng mDeviceLocation ;
    private boolean mLocationPermissionGranted =false;
    private FusedLocationProviderClient mFusedLocationProviderClient ;
    private String TAG = "mapDebugging" ;
    private String playerName  ;
    private LocationCallback mLocationCallback ;
    private boolean firstTimeGetLocation = true ;
    private boolean resetVirus = true ;
    private Marker currentLocationMark ;
    private ArrayList< LatLng> virusLocations = new ArrayList<>() ;
    private ArrayList< Marker> virusMarkers = new ArrayList<>() ;
    private ArrayList<Virus> virusList = new ArrayList<>() ;
    private boolean setLocationUpdate= false;
    private int gameCode = -1 ;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState  ) {
        super.onCreate(savedInstanceState );
        getLocationPermission()  ;
        getMapAsync(this);
        setHasOptionsMenu(true);
        //playerName= getActivity().getIntent().getStringExtra("USER");
        playerName=PreferenceManager.getDefaultSharedPreferences(getContext()).getString("USER","default");

        //updateValuesFromBundle(savedInstanceState);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
            map = googleMap;
            mDefaultLocation = new LatLng(40.0, -83.0);
            currentLocationMark = map.addMarker(new MarkerOptions().position(mDefaultLocation)
                    .title("The Ohio State University"));

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResume() {
        super.onResume();
        setLocationUpdate= false;
        }

     @Override
    public void onPause() {
        super.onPause();
        if(setLocationUpdate){
        //stop the location updates
        mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
        }

    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("MissingPermission")
    private void findVirusNearby(){
        if(!setLocationUpdate) {
            mLocationCallback = new LocationCallback() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
                                .title(playerName+"'s Location"));
                        if(resetVirus) {
                            setUpVirus();
                            resetVirus = false ;
                        }
                        gameCode =detectUserVirusCollision();

                    }

                }
            }
        };


            // Create the LocationRequest object
             LocationRequest mLocationRequest = LocationRequest.create()
                     .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY) //PRIORITY_NO_POWER
                     .setInterval(2 * 1000)        // 2 seconds, in milliseconds
                     .setFastestInterval(1 * 1000); // 1 second, in milliseconds
             mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
             mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
             setLocationUpdate = true;
         }


    }



    private void getLocationPermission(){
        Activity activity = this.getActivity() ;
        if(!(ContextCompat.checkSelfPermission(activity, FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(activity, COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(activity, LOCATION_PERMISSIONS, REQUEST_LOCATION_PERMISSIONS);
        }
    }
    /*@Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        if (requestCode == REQUEST_LOCATION_PERMISSIONS) {
            if (grantResults.length > 0) {// If request is cancelled, the result arrays are empty.
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        mLocationPermissionGranted = false;
                        return;
                    }
                }
                mLocationPermissionGranted = true;
            }
        }
    }*/

    private boolean hasLocationPermission() {
        final Activity activity = requireActivity();
        int result;
        result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }

        @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.map_menu, menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final boolean location_permission=PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("location_permission",false);
        if (hasLocationPermission()&&location_permission) {
            if (item.getItemId() == R.id.search) {
                firstTimeGetLocation = true;
                resetVirus = true;
                Toast.makeText(this.getActivity(), "Loading......", Toast.LENGTH_LONG).show();
                findVirusNearby();

            } else if (item.getItemId() == R.id.game) {
                if (gameCode >= 0) {
                    Intent intent = new Intent(getActivity(), GameActivity.class);
                    intent.putExtra("virusName", virusList.get(gameCode).getName());
                    intent.putExtra("USER",playerName);
                    startActivity(intent);
                } else {
                    Toast.makeText(this.getActivity(), "You are not within the range of any virus", Toast.LENGTH_LONG).show();
                }
            }
        }else if(!location_permission){
            Toast.makeText(this.getActivity(), "Enable location permission in settings to play.", Toast.LENGTH_LONG).show();
        }else{

            ActivityCompat.requestPermissions(getActivity() , LOCATION_PERMISSIONS, REQUEST_LOCATION_PERMISSIONS);
        }

        return true;
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setUpVirus(){
        //clean the original generated virusMarkers and virusLocation
        for(int i=0 ; i<virusMarkers.size() ; i++){
            virusMarkers.get(i).remove();
        }
        virusLocations = new ArrayList<LatLng>() ;
        //get the random virus location within +- 0.05
            double deviceLongitude = mDeviceLocation .longitude;
            double deviceLatitude = mDeviceLocation.latitude;
            VirusSingleton singleton = VirusSingleton.get(requireContext());
            Random rnd = new Random();
            double latitudeRange;
            double  longitudeRange;
            LatLng virusLoc;
            virusList = singleton.getVirus() ;
            for (int i = 0; i < virusList.size(); i++) {
                latitudeRange = (rnd.nextInt(10) - 5) / 100.0;
                longitudeRange = (rnd.nextInt(10) - 5) / 100.0;
                virusLoc = new LatLng(deviceLatitude +  latitudeRange , deviceLongitude +  longitudeRange);
                if(!virusLocations.contains(virusLoc)) {
                    virusLocations.add(virusLoc);
                //FIXME : try load it with picasso library later
                    Bitmap icon = singleton.getBitmap( virusList.get(i).getImage()) ;
                    Marker virusMarker = map.addMarker(new MarkerOptions()
                            .position(virusLoc)
                            .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons( icon, 180, 180)  ))
                            .title(virusList.get(i).getName())
                             );
                    virusMarkers.add(virusMarker);

                }else{
                    i-- ;
                }
            }
            singleton.updateVirusLocation(virusLocations);
            virusList = singleton.getVirus() ;
        }



        private int detectUserVirusCollision(){
         ArrayList<Double> distanceList = new ArrayList<>() ;
         LatLng virusLoc ;
         int closestVirusIndex = 0 ;
         if(virusList!=null){
             for(int i=0 ; i<virusList.size(); i++){
                 virusLoc = stringToLatlng(virusList.get(i).getLocation()) ;
                 distanceList.add(calculateDistance(mDeviceLocation, virusLoc ) );
             }
             double minDistance = Double.MAX_VALUE;
             for(int i=0 ;i<virusList.size(); i++){
                 if(distanceList.get(i)< minDistance){
                     minDistance = distanceList.get(i) ;
                     closestVirusIndex = i ;
                 }
             }

             if(distanceList.get(closestVirusIndex ) <= Math.pow(VIRUS_RANGE,2) ){
                 Virus cloestVirus = virusList.get(closestVirusIndex ) ;
                 Activity activity = this.getActivity() ;
                 if( activity !=null &&   cloestVirus!=null) {
                     Toast.makeText(this.getActivity(), "Hi " + playerName + "! " + cloestVirus.getName() + " nearby by! You can start the game!", Toast.LENGTH_SHORT).show();
                 }

             }else{
                 closestVirusIndex = -1 ;
             }
         }
                return closestVirusIndex ;
        }


    private double calculateDistance(LatLng l1,LatLng l2 ){
         return Math.pow((l1.latitude - l2.latitude),2) + Math.pow((l1.longitude - l2.longitude),2) ;
    }


    private String LatlngToString(LatLng location){
        return String.valueOf( location.latitude).concat(",").concat(String.valueOf( location.longitude));
    }

    private LatLng stringToLatlng(String location){
        String[] latlong =  location.split(",");
        double latitude = Double.parseDouble(latlong[0]);
        double longitude = Double.parseDouble(latlong[1]);
        return new LatLng(latitude, longitude) ;
    }

    public Bitmap resizeMapIcons(Bitmap imageBitmap, int width, int height){
        return Bitmap.createScaledBitmap(imageBitmap, width, height, false);

    }






    /*FIXME : save current state
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(virusLocations != null &&  mDeviceLocation!=null) {
            outState.putString("mDeviceLocation", LatlngToString(mDeviceLocation));
            for(int i=0; i<virusLocations.size(); i++){
               outState.putString("virus"+ i, LatlngToString(virusLocations.get(i)) );
             }
             outState.putDouble("zoom", DEFAULT_ZOOM);
        }
    }


    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return;
        }


    }

     */




}
