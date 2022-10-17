package com.example.ajans.locationlocationfind;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

   // public static final String ACCESS_FINE_LOCATION = "1";



    private DatabaseHelper databaseHelper;

    private SlidingUpPanelLayout mLayout;
    ImageButton imgButton;
    ToggleButton pButton;
    MediaPlayer mp;
    private AudioManager mAudioManager;
    private Context mContext;
    private Activity mActivity;
    private Random mRandom = new Random();

    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;

    String addr, city, state, country, postalCode, knownName;
    String get_longitude;
    String get_latitude;
    String num1;
    String msg2;
    Timer timer;
    String interval1, interval2;
    int inter1;
    long inter2;
    String link;

    String latit;
    String longit;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // Call your Alert message


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, R.style.Dialog);
                builder.setTitle("Location Settings");
                builder.setMessage("Please enable your Location Services");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent gpsOptionsIntent = new Intent(
                                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(gpsOptionsIntent);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Location will not be shown", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Location will not be shown", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            } else {
                builder = new AlertDialog.Builder(this);
                builder.setTitle("Location Settings");
                builder.setMessage("Please enable your Location Services");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent gpsOptionsIntent = new Intent(
                                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(gpsOptionsIntent);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Location will not be shown", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Location will not be shown", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }

//            AlertDialog.Builder ab = new AlertDialog.Builder(this);
//            ab.setTitle("Location Settings");
//            ab.setMessage("Please enable your Location Services");
//            ab.setCancelable(false);
//            ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    Intent gpsOptionsIntent = new Intent(
//                            android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                    startActivity(gpsOptionsIntent);
//                }
//            });
//            ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    Toast.makeText(MainActivity.this, "Location will not be shown", Toast.LENGTH_SHORT).show();
//                }
//            });
//            ab.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    Toast.makeText(MainActivity.this, "Location will not be shown", Toast.LENGTH_SHORT).show();
//                }
//            });
//            ab.show();

        }
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] TO = {"ajansharma120897@gmail.com"};

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("mailto:"));

                intent.putExtra(Intent.EXTRA_EMAIL, TO);
                intent.setType("text/plain");
                startActivity(intent);
            }
        });

        init();    // call init method
        panelListener(); // Call paneListener method
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // Get the application context
        mContext = getApplicationContext();
        mActivity = MainActivity.this;

        // Get the audio manager instance
        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int media_current_volume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int media_max_volume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int random_volume = mRandom.nextInt(((media_max_volume - 0) + 1) + 0);
        // Set media volume level
        mAudioManager.setStreamVolume(
                AudioManager.STREAM_MUSIC, // Stream type
                media_max_volume, // Index
                AudioManager.FLAG_SHOW_UI // Flags
        );

//        mp=new MediaPlayer();
//        try{
//            mp = MediaPlayer.create(this,R.raw.policesiren);
//
//            mp.prepare();
//
//        }catch(Exception e){e.printStackTrace();}

        pButton = (ToggleButton) findViewById(R.id.pButton);
        pButton.setOnClickListener(new View.OnClickListener() {
           // int buttonpos = 0;
            @Override
            public void onClick(View v) {
                if (pButton.isChecked()){
                    mp=new MediaPlayer();
                    try{
                        mp = MediaPlayer.create(MainActivity.this,R.raw.policesiren);
                        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mp.start();
                            }
                        });
                        mp.prepare();

                    }catch(Exception e){e.printStackTrace();}
//                    mp.start();
                }else {
                    mp.stop();
                }
            }
        });


//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        databaseHelper = new DatabaseHelper(MainActivity.this);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * Initialization of the textview and SlidingUpPanelLayout
     */
    public void init(){

        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        imgButton = (ImageButton) findViewById(R.id.imgButton);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            }
        });
    }

    /**
     * Call setPanelSlidelistener method to listen open and close of slide panel
     **/
    public void panelListener(){
        mLayout.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                //Toast.makeText(MainActivity.this, "onPanelSlide", Toast.LENGTH_SHORT).show();
            }

            // Called when secondary layout is dragged down by user
            @Override
            public void onPanelCollapsed(View panel) {
               // Toast.makeText(MainActivity.this, "onPanelCollapsed", Toast.LENGTH_SHORT).show();
            }

            // Called when secondary layout is dragged up by user
            @Override
            public void onPanelExpanded(View panel) {
               // Toast.makeText(MainActivity.this, "onPanelExpanded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPanelAnchored(View panel) {
               // Toast.makeText(MainActivity.this, "onPanelAnchored", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPanelHidden(View panel) {
               // Toast.makeText(MainActivity.this, "onPanelHidden", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        if (mLayout != null &&
                (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED
                        || mLayout.getPanelState() ==
                        SlidingUpPanelLayout.PanelState.ANCHORED)) {

            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

        } else {

            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsView.class);
            startActivity(intent);
        }
        if (id == R.id.mapTypeNone) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
        }
        if (id == R.id.mapTypeNormal) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
        if (id == R.id.mapTypeSatellite) {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
        if (id == R.id.mapTypeTerrain) {
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        }
        if (id == R.id.mapTypeHybrid) {
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_addNumber) {

            // Call to Fragment to add number

            Toast.makeText(this, "number", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, AddNumbers.class);
            startActivity(intent);

        } else if (id == R.id.nav_addMessage) {

            // Call to Fragment to add message

            Toast.makeText(this, "message", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, AddMessage.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

            // Your Location in Address Form

            Intent intent = new Intent(this, YourLocationInAddr.class);
            startActivity(intent);

            Toast.makeText(this, "Your Location", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_share_location_simple) {

            // Send Message and Location via SMS

            Toast.makeText(this, "location sent", Toast.LENGTH_SHORT).show();
           // send_sms();
            calcPermissions();

        } else if (id == R.id.nav_send) {

            // Send Location At Specified Time

            SharedPreferences sp = getSharedPreferences("myPref", MODE_PRIVATE);
            num1 = sp.getString("first", null);

            if (num1 != null) {
                SharedPreferences sp10 = getSharedPreferences("myPref10", Context.MODE_PRIVATE);
                interval1 = sp10.getString("intervalS", null);

                if (interval1 != null) {
                    inter1 = Integer.parseInt(interval1);

                    // final int interval = 5000;
                    Handler handler = new Handler();
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            //send_sms();
                            calcPermissions();
                        }
                    };
                    handler.postAtTime(runnable, System.currentTimeMillis() + inter1);
                    handler.postDelayed(runnable, inter1);
                } else {
                    Toast.makeText(this, "Please specify time in settings", Toast.LENGTH_LONG).show();
                }


            } else {
                Toast.makeText(this, "Please add a number", Toast.LENGTH_SHORT).show();
            }

        }
//        else if (id == R.id.nav_send1) {
//
//            // Send Location in Intervals
//
//            SharedPreferences sp = getSharedPreferences("myPref", MODE_PRIVATE);
//            num1 = sp.getString("first", null);
//
//            if (num1 != null) {
//                SharedPreferences sp11 = getSharedPreferences("myPref11", Context.MODE_PRIVATE);
//                interval2 = sp11.getString("intervalSecond", null);
//
//                if (interval2 != null) {
//                    inter2 = Long.parseLong(interval2);
//
//
//                    final Handler handler = new Handler();
//                    timer = new Timer();
//                    TimerTask doAsynchronousTask = new TimerTask() {
//                        @Override
//                        public void run() {
//                            handler.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    try {
//                                        //send_sms();
//                                        calcPermissions();
//                                    } catch (Exception e) {
//
//                                    }
//                                }
//                            });
//                        }
//                    };
//                    timer.schedule(doAsynchronousTask, 0, inter2);
//
//                } else {
//                    Toast.makeText(this, "Please select Interval from settings", Toast.LENGTH_LONG).show();
//                }
//
//
//            } else {
//                Toast.makeText(this, "Please add a number", Toast.LENGTH_SHORT).show();
//            }
//
//        }
//        else if (id == R.id.nav_send1_cancel) {
//
//            // Send Location in intervals cancel button
//
//            SharedPreferences sp = getSharedPreferences("myPref", MODE_PRIVATE);
//            num1 = sp.getString("first", null);
//            if (num1 != null) {
//                timer.cancel();
//            } else {
//                Toast.makeText(this, "Please add a number", Toast.LENGTH_SHORT).show();
//            }
//
//        }
//        else if (id == R.id.nav_send2) {
//
//            Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
//
//        }
        else if (id == R.id.nav_logout) {
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();

            databaseHelper.deleteUser();


            finish();

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
//            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        double lati = location.getLatitude();
        latit = Double.toString(lati);
        double longi = location.getLongitude();
        longit = Double.toString(longi);

        try {
            Log.e("latitude", "inside latitude--" + lati);
            addresses = geocoder.getFromLocation(lati, longi, 1);


            if (addresses != null && addresses.size() > 0) {
                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();

                SharedPreferences sp5 = getSharedPreferences("MyPref5",MODE_PRIVATE);
                SharedPreferences.Editor editor5 = sp5.edit();
                editor5.putString("address",address);
                editor5.putString("city",city);
                editor5.putString("state",state);
                editor5.putString("country",country);
                editor5.putString("postal",postalCode);
                editor5.putString("knownName",knownName);
                editor5.commit();


            }


        }catch (IOException e){
            e.printStackTrace();
        }

        SharedPreferences sp2 = getSharedPreferences("MyPref3",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp2.edit();
        editor.putString("longitude",longit);
        editor.putString("latitude",latit);
        editor.commit();
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Your Location");
        markerOptions.snippet(latit + "," +longit);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 15);
        mMap.animateCamera(update);

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            case PERMISSION_REQUEST:{
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.SEND_SMS)
                            == PackageManager.PERMISSION_GRANTED){
                        //send_sms();
                        sendSMS();
                    }

                }else {
                    Toast.makeText(this, "Permission not Granted", Toast.LENGTH_SHORT).show();
                }
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    public void send_sms(){

        // Toast.makeText(this, "Reach sms", Toast.LENGTH_SHORT).show();

        SharedPreferences sp = getSharedPreferences("myPref",MODE_PRIVATE);
        num1 = sp.getString("first",null);

        SharedPreferences sp1 = getSharedPreferences("myPref1",MODE_PRIVATE);
        msg2 = sp1.getString("msg1",null);

        SharedPreferences sp2 = getSharedPreferences("MyPref3",MODE_PRIVATE);
        get_longitude = sp2.getString("longitude",null);
        get_latitude = sp2.getString("latitude",null);

         link = "http://maps.google.com/maps?q=loc:"+get_latitude+get_longitude;

        SharedPreferences sp5 = getSharedPreferences("MyPref5",MODE_PRIVATE);
        addr = sp5.getString("address",null);
        city = sp5.getString("city",null);
        state = sp5.getString("state",null);
        country = sp5.getString("country",null);
        postalCode = sp5.getString("postal",null);
        knownName = sp5.getString("knownName",null);

        //Toast.makeText(this, "going to send sms", Toast.LENGTH_SHORT).show();
        SmsManager smsManager = SmsManager.getDefault();

        Toast.makeText(this,"DATA = "+get_longitude + " - " + get_latitude + " = "+num1,Toast.LENGTH_SHORT).show();
        //smsManager.sendTextMessage(num1 ,null, msg2 + " - " + get_longitude + " - " + get_latitude + " - " + addr + " - " + city + " - " + state + " - " + country + " - " + postalCode + " - " + knownName+ "\nTrack me = "+link, null, null);
        //smsManager.sendTextMessage("8860025356",null,"okayokay",null,null);
        //smsManager.sendTextMessage(num1,null,msg2 + " - " + get_longitude + " - " + get_latitude + " - " + addr + "-" + city + " - " + state,null,null);
        Toast.makeText(this, "pohonch gaya", Toast.LENGTH_SHORT).show();
        smsManager.sendTextMessage(num1,null,msg2 + " -" + addr + city + "My location location",null,null);
        //smsManager.sendTextMessage("8860025356",null,"aaya message",null,null);
    }

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private void sendSMS() {

        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        SharedPreferences sp = getSharedPreferences("myPref",MODE_PRIVATE);
        num1 = sp.getString("first",null);

        SharedPreferences sp1 = getSharedPreferences("myPref1",MODE_PRIVATE);
        msg2 = sp1.getString("msg1",null);

        SharedPreferences sp2 = getSharedPreferences("MyPref3",MODE_PRIVATE);
        get_longitude = sp2.getString("longitude",null);
        get_latitude = sp2.getString("latitude",null);

        link = "http://maps.google.com/maps?q=loc:"+get_latitude+get_longitude;

        SharedPreferences sp5 = getSharedPreferences("MyPref5",MODE_PRIVATE);
        addr = sp5.getString("address",null);
        city = sp5.getString("city",null);
        state = sp5.getString("state",null);
        country = sp5.getString("country",null);
        postalCode = sp5.getString("postal",null);
        knownName = sp5.getString("knownName",null);
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse("smsto:"));
        sendIntent.putExtra("address"  , num1);
        sendIntent.putExtra("sms_body", msg2 + " - " + addr + " - " + get_latitude + ", " + get_longitude);
        sendIntent.setType("vnd.android-dir/mms-sms");
        //startActivity(sendIntent);
        try {
            startActivity(sendIntent);
            finish();
            Log.i("Finished sending SMS...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this,
                    "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private static final int PERMISSION_REQUEST = 100;
    public void calcPermissions(){
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.SEND_SMS)!=PackageManager.PERMISSION_GRANTED){
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.SEND_SMS)){
                    requestPermissions(new String[]{android.Manifest.permission.SEND_SMS},PERMISSION_REQUEST);
                }else {
                    requestPermissions(new String[]{android.Manifest.permission.SEND_SMS},PERMISSION_REQUEST);
                }
            }else {
                //send_sms();
                sendSMS();
            }
        }else {
            send_sms();
        }
    }

}
