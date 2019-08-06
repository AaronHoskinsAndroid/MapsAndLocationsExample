package examples.aaronhoskins.com.mapsandlocationsexample;

import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    EditText etAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        etAddress = findViewById(R.id.etAddressToGoTo);
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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void onClick(View view) {
        LatLng macLatLng = new LatLng(33.909057,-84.479215);
        mMap.addMarker(new MarkerOptions().position(macLatLng).title("Welcome To Mobile Apps :D"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(macLatLng));
        mMap.setMinZoomPreference(15.0f);
    }

    //Geocoding
    public String getAddresBasedOffOfLatLng(LatLng passedLatLng) throws IOException {
        Geocoder geocoder = new Geocoder(this);
        return geocoder.getFromLocation(passedLatLng.latitude, passedLatLng.longitude, 1).get(0).getAddressLine(1);
    }

    //Reverse Geocoding
    public LatLng getLatLngBassedOffOfAddress(String address) throws IOException {
        Geocoder geocoder = new Geocoder(this);
        Address addressInfo = geocoder.getFromLocationName(address,1).get(0);
        double lat = addressInfo.getLatitude();
        double lng = addressInfo.getLongitude();
        return new LatLng(lat, lng);
    }

    public void gotoAddressClicked(View view) {
        final String address = etAddress.getText().toString();
        if(!address.isEmpty()) {
            try {
                LatLng addressLatLng = getLatLngBassedOffOfAddress(address);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(addressLatLng));
                mMap.setMinZoomPreference(15.0f);


            } catch(Exception e) {
                Toast.makeText(this, "Invalid Address", Toast.LENGTH_LONG).show();
            }
        }
    }
}
