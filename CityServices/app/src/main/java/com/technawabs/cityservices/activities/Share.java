package com.technawabs.cityservices.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import com.technawabs.cityservices.GPSTracker;
import com.technawabs.cityservices.R;
import com.technawabs.cityservices.base.BaseAppCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.technawabs.cityservices.concurrency.ExecutorUtils;
import com.technawabs.cityservices.fragments.MapFragment;
import com.technawabs.cityservices.models.MobileUser;
import com.technawabs.cityservices.network.Factory;
import com.technawabs.cityservices.utils.ExceptionUtils;
import com.technawabs.cityservices.utils.UIHelper;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static com.technawabs.cityservices.R.layout.activity_share;

public class Share extends BaseAppCompat implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        MapFragment.OnFragmentInteractionListener,GoogleMap.OnMarkerClickListener{

    private final String TAG=this.getClass().getSimpleName();
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private GoogleApiClient googleApiClient;
    GoogleMapOptions options;

    private LocationRequest locationRequest;
    private LocationSettingsRequest.Builder builder;
    private PendingResult<LocationSettingsResult> result;
    final static int REQUEST_LOCATION = 199;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    private final long timeout=30*1000; //30 mins
    private final long fastTimeout=5*1000;
    private Location location;
    private LatLng myLocation=new LatLng(0,0);
//    private MapFragment mapFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Toolbar mToolbar;
    private NavigationView mNavView;

    private List<MobileUser> friends;

    public boolean isUser,isStore,isMovie;

    private List<Marker> markerList=new ArrayList<>();
    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab1,fab2,fab3,fab4;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_share);

        //Map
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapFragment.setAllowEnterTransitionOverlap(true);
        options = new GoogleMapOptions();
        options.compassEnabled(false)
                .rotateGesturesEnabled(false)
                .tiltGesturesEnabled(false);
        //Gps
        GpsRequest(Share.this);
        //Friend list
        friends=new ArrayList<>();

        //fabButtons
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);
        fab3=(FloatingActionButton)findViewById(R.id.fab3);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isUser = true;
                isMovie = false;
                isStore = false;
                mMap.clear();
                friends=new ArrayList<MobileUser>();
                MobileUser mobileUser=new MobileUser();
                mobileUser.setName("Ankit");
                mobileUser.setLatitude(26.4740);
                mobileUser.setLongithude(77.0115);
                mobileUser.setMobile("9982134245 : Request Talktime ?");
                MobileUser mobileUser2=new MobileUser();
                mobileUser2.setName("Rajesh");
                mobileUser2.setLatitude(25.4485);
                mobileUser2.setLongithude(72.0315);
                mobileUser2.setMobile("8800132143 : Request Talktime ?");
                MobileUser mobileUser3=new MobileUser();
                mobileUser3.setName("Preeti");
                mobileUser3.setLatitude(24.3212);
                mobileUser3.setLongithude(78.4312);
                mobileUser3.setMobile("7747324712 : Request Talktime ?");
                friends.add(mobileUser);
                friends.add(mobileUser2);
                friends.add(mobileUser3);
                onMapReady(mMap);
                animateFAB();
            }
        });
        fab2=(FloatingActionButton)findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                isUser = false;
                isMovie = true;
                isStore = false;
                mMap.clear();
                friends=new ArrayList<MobileUser>();
                MobileUser mobileUser=new MobileUser();
                mobileUser.setName("Fun Cinemas");
                mobileUser.setLatitude(28.636589);
                mobileUser.setLongithude(77.274315);
                mobileUser.setMobile("Grab 10% cashback");
                MobileUser mobileUser2=new MobileUser();
                mobileUser2.setName("PVR");
                mobileUser2.setLatitude(28.619570);
                mobileUser2.setLongithude(77.088104);
                mobileUser2.setMobile("Buy 2 Tickets @ 199");
                MobileUser mobileUser3=new MobileUser();
                mobileUser3.setName("Cinepolis");
                mobileUser3.setLatitude(28.7149);
                mobileUser3.setLongithude(77.1164);
                mobileUser3.setMobile("Free Popcorn on purchase of 3 tickets");
                friends.add(mobileUser);
                friends.add(mobileUser2);
                friends.add(mobileUser3);
                onMapReady(mMap);
                animateFAB();
            }
        });
        fab1=(FloatingActionButton)findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                isUser = false;
                isMovie = false;
                isStore = true;
                mMap.clear();
                friends=new ArrayList<MobileUser>();
                MobileUser mobileUser=new MobileUser();
                mobileUser.setName("LIFESTYLE");
                mobileUser.setLatitude(28.4595);
                mobileUser.setLongithude(77.0266);
                mobileUser.setMobile("Buy 2 Tee's @ 99");
                MobileUser mobileUser2=new MobileUser();
                mobileUser2.setName("CAFE COFFEE DAY");
                mobileUser2.setLatitude(28.3445);
                mobileUser2.setLongithude(77.0276);
                mobileUser2.setMobile("Free 2 smiley Cookies with a Cappucino");
                MobileUser mobileUser3=new MobileUser();
                mobileUser3.setName("HALDIRAM'S");
                mobileUser3.setLatitude(28.7041);
                mobileUser3.setLongithude(77.1025);
                mobileUser3.setMobile("Cashback of 15% on bill above 5000");
                friends.add(mobileUser);
                friends.add(mobileUser2);
                friends.add(mobileUser3);
                onMapReady(mMap);
                animateFAB();
            }
        });
        fab=(FloatingActionButton)findViewById(R.id.filter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFAB();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.addMarker(new MarkerOptions().position(myLocation).title("Me"));
        mMap.getMaxZoomLevel();
        if(friends!=null){
            for (MobileUser mobileUser:friends){
                if(mobileUser!=null){
                    LatLng position=new LatLng(mobileUser.getLatitude(),mobileUser.getLongithude());
                    if(isUser) {
                        createMarker(position.latitude, position.longitude, mobileUser.getName(), mobileUser.getMobile(), R.mipmap.ic_user);
                    }
                    if(isStore)
                    {
                        createMarker(position.latitude, position.longitude, mobileUser.getName(), mobileUser.getMobile(), R.mipmap.ic_store);
                    }
                    if(isMovie)
                    {
                        createMarker(position.latitude, position.longitude, mobileUser.getName(), mobileUser.getMobile(), R.mipmap.ic_movies);
                    }
                }
            }
        }else {

        }
    }

    private void GpsRequest(@NonNull final Activity context){
        if(googleApiClient==null){
            googleApiClient=new GoogleApiClient.Builder(context)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
            googleApiClient.connect();

            locationRequest= LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(timeout);
            locationRequest.setFastestInterval(fastTimeout);
            builder=new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);
            builder.setAlwaysShow(true);


            result=LocationServices.SettingsApi.checkLocationSettings(googleApiClient,builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                    final Status status = locationSettingsResult.getStatus();
                    final LocationSettingsStates state = locationSettingsResult.getLocationSettingsStates();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            getLocation();
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the UserActivity
                            // a dialog.
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(context, REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            UIHelper.killApp(true);
                            finish();
                            break;
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        Log.d(getTAG(),"States: "+states);
        switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        Log.d(getTAG(),"Location found");
                        getLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        // The UserActivity was asked to change settings, but chose not to
                        Log.d(getTAG(),"Location found cancelled");
                        finish();
                        break;
                    case REQUEST_LOCATION:
                        Log.d(getTAG(),"Request location");
                        switch (resultCode) {
                            case Activity.RESULT_CANCELED: {
                                // The UserActivity was asked to change settings, but chose not to
                                finish();
                                break;
                            }
                            default: {
                                break;
                            }
                        }
                        break;
                    default:
                        break;
                }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        GpsRequest(Share.this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    private void openFragment(@NonNull Fragment fragment){
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.frameView,fragment).commit();
    }

    private void getLocation(){
        GPSTracker gpsTracker=new GPSTracker(Share.this);
        if(gpsTracker.canGetLocation()){
            myLocation=new LatLng(gpsTracker.getLatitude(),gpsTracker.getLongitude());
            Log.d(getTAG(),"Location: "+gpsTracker.getLatitude());
            onMapReady(mMap);
        }
    }

    private void getUsers(final List<MobileUser> friends){
        ListenableFuture<JSONArray> getFriends= Factory.getFriendsService().getFriends();
        Futures.addCallback(getFriends, new FutureCallback<JSONArray>() {
            @Override
            public void onSuccess(@NonNull JSONArray result) {
                try{
                Log.d(TAG,result.toString());
                for(int i=0;i<result.length();i++){
                    MobileUser mobileUser=new MobileUser();
                    Gson gson=new Gson();
                    mobileUser=gson.fromJson(result.get(i).toString(),MobileUser.class);
                    friends.add(mobileUser);
                }
                    onMapReady(mMap);
                }catch (JSONException exception){
                    ExceptionUtils.exceptionMessage(exception,TAG);
                }
            }

            @Override
            public void onFailure(@NonNull Throwable t) {
                Log.d(TAG,t.getMessage());
            }
        }, ExecutorUtils.getUIThread());
    }

    protected Marker createMarker(double latitude, double longitude, String title, String snippet, int iconResID) {

        return mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.fromResource(iconResID)));
    }

    void animateFAB(){
        if(isFabOpen){
            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            fab3.setClickable(false);
            isFabOpen = false;
        } else {
            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            fab3.setClickable(true);
            isFabOpen = true;
        }
    }

}
