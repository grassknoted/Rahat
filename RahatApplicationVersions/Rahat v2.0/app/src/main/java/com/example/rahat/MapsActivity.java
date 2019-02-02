package com.example.rahat;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;

import java.util.ArrayList;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public ArrayList<String> places;
    private GoogleMap mMap;
    private Double poi_latitude;
    private Double poi_longitude;

    private int places_counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Log.d("MainM", "Recieved at MapsActivity");
//        TextView map_updates = (TextView) findViewById(R.id.textOne2);
//        map_updates.setVisibility(View.INVISIBLE);

        Bundle bundle = getIntent().getExtras();
        places = bundle.getStringArrayList("Places");
        //Place places[] = (Place[]) bundle.get("Place")

        Log.d("MainM", "PLACES IS LENGTH: "+Integer.toString(places.size()));

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
//        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
//        mMap = mapFragment.getMap();
        mapFragment.getMapAsync(this);
        mapFragment.setRetainInstance(true);
        Log.d("Main", "What is happening even?");
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        //for( int i = 0; i<places.length; i++) {
        // Add a marker in Sydney and move the camera
        for(int i = 0; i<places.size(); i++) {
            Double current_latitude = Double.parseDouble(places.get(i).split("\\s+")[0]);
            Double current_longitude = Double.parseDouble(places.get(i).split("\\s+")[1]);
            LatLng place_of_interest = new LatLng(current_latitude, current_longitude);

            mMap.addMarker(new MarkerOptions().position(place_of_interest).title("Your current location"));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(16));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(place_of_interest));
        }

        LatLng safety = new LatLng(13.02023774660511, 77.65413522720338);
        mMap.addMarker(new MarkerOptions().position(safety).title("Rescue Shelter").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        //Bitmap you_are_here_marker = BitmapFactory.decodeResource(getResources(), R.drawable.you_are_here);

//        mMap.addMarker(new MarkerOptions().position(place_of_interest).title("Your current location"));
//        mMap.moveCamera(CameraUpdateFactory.zoomTo(14));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(place_of_interest));

//        CircleOptions circleOptions = new CircleOptions();
//        circleOptions.center(place_of_interest);
//        circleOptions.radius(800);
//        circleOptions.fillColor(0x22FF0000);
//        circleOptions.strokeColor(Color.RED);
//        circleOptions.strokeWidth(4);
//        mMap.addCircle(circleOptions);

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //setUpMap();
    }

    private void setUpMap() {
        Log.d("Main", "Entered setUpMap");
        mMap.setMapType(GoogleMap.MAP_TYPE_NONE);

        mMap.addTileOverlay(new TileOverlayOptions().tileProvider(new CustomMapTileProvider(getResources().getAssets())));

        CameraUpdate upd = CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(places.get(0).split("\\s+")[0]), Double.parseDouble(places.get(0).split("\\s+")[0])), 16);
        mMap.moveCamera(upd);
    }
}



//        LatLng seattle = new LatLng(47.6062095, -122.3320708);
//        LatLng bellevue = new LatLng(47.6101497, -122.2015159);
//
//        PolylineOptions plo =  new PolylineOptions();
//        plo.add(seattle);
//        plo.add(bellevue);
//        plo.color(Color.RED);
//        plo.geodesic(true);
//        plo.startCap(new RoundCap());
//        plo.width(20);
//        plo.jointType(JointType.BEVEL);
//        mMap.addPolyline(plo);

//        CircleOptions circleOptions = new CircleOptions();
//        circleOptions.center(seattle);
//        circleOptions.radius(8500);
//        circleOptions.fillColor(Color.BLUE);
//        circleOptions.strokeColor(Color.RED);
//        circleOptions.strokeWidth(4);
//
//        mMap.addCircle(circleOptions);
//
//        //seattle coordinates
//        seattle = new LatLng(47.6062095, -122.3320708);

//        GroundOverlayOptions groundOverlayOptions = new GroundOverlayOptions();
//        groundOverlayOptions.position(place_of_interest, 100, 100 );
////                .image( BitmapDescriptorFactory.fromResource(R.drawable.you_are_here));
//
//        mMap.addGroundOverlay(groundOverlayOptions);