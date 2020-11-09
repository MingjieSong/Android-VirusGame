package com.androidApp.virusGame.UI;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Random;

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {
    GoogleMap map ;
    private static final String[] LOCATION_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private static final int REQUEST_LOCATION_PERMISSIONS = 0;
    private static final String FINE_LOCATION  =  Manifest.permission.ACCESS_FINE_LOCATION ;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final float DEFAULT_ZOOM = 10f ;
    private static final double VIRUS_RANGE = 0.05 ;
    private LatLng mDefaultLocation ;
    private LatLng mDeviceLocation ;
    private boolean mLocationPermissionGranted =false;
    private FusedLocationProviderClient mFusedLocationProviderClient ;
    private String TAG = "mapDebugging" ;
    private String playerName  ;
    private LocationCallback mLocationCallback ;
    private boolean firstTimeGetLocation = false ;
    private boolean resetVirus = true ;
    private Marker currentLocationMark ;
    private ArrayList< LatLng> virusLocations = new ArrayList<>() ;
    private ArrayList< Marker> virusMarkers = new ArrayList<>() ;
    private ArrayList<Virus> virusList = new ArrayList<>() ;
    private boolean setLocationUpdate= false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState  ) {
        super.onCreate(savedInstanceState );
        getLocationPermission()  ;
        getMapAsync(this);
        setHasOptionsMenu(true);
        playerName= getActivity().getIntent().getStringExtra("USER");

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

            map = googleMap;
            mDefaultLocation = new LatLng(40.0, -83.0);
            currentLocationMark = map.addMarker(new MarkerOptions().position(mDefaultLocation)
                    .title("Ohio State University"));

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));


    }


  /* @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        }

*/
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
                        //FIXME : detect if user is within the virus range
                        detectUserVirusCollision();
                    }

                }
            }
        };

        if(!setLocationUpdate) {
            // Create the LocationRequest object
            LocationRequest mLocationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY) //PRIORITY_NO_POWER
                    .setInterval(5* 1000)        // 5 seconds, in milliseconds
                    .setFastestInterval(1 * 1000); // 1 second, in milliseconds
            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
            mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
            setLocationUpdate =true ;
        }

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
                firstTimeGetLocation =true;
                resetVirus =true ;
                findVirusNearby();
           }
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
                     Toast.makeText(this.getActivity(), "Hi " + playerName + "! " + cloestVirus.getName() + " nearby by! Click the virus to start the game!", Toast.LENGTH_LONG).show();
                 }

                 /*
                 FIXME: ADD  onMarkerClick method to start the game activity, see more in
                 https://github.com/googlemaps/android-samples/blob/master/ApiDemos/java/app/src/gms/java/com/example/mapdemo/MarkerDemoActivity.java
                 and https://developers.google.com/maps/documentation/android-sdk/marker
                  */
                 //pass the  "closestVirus" info into the game activity through intent so that we can load the proper virus image in the game
                 /*Intent intent =new Intent( getActivity(), MapActivity.class);
                 intent.putExtra("virusName", cloestVirus.getName()) ;
                 startActivity(intent); */
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
        if (outState == null) {
            return;
        }
        //Update the value of requestingLocationUpdates from the Bundle.
        //if ( outState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
        //    requestingLocationUpdates = savedInstanceState.getBoolean(
        //            REQUESTING_LOCATION_UPDATES_KEY);
        //}

        outState.putString("mDeviceLocation", LatlngToString(mDeviceLocation ));
        for(int i=0; i<virusLocations.size(); i++){
            outState.putString("virus"+ i, LatlngToString(virusLocations.get(i)) );
        }
        outState.putDouble("zoom", DEFAULT_ZOOM);


    } */

}
