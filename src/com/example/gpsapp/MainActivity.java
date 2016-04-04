package com.example.gpsapp;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private TextView latitude, longitude,address;
	private Button getCoordinates;
	private Location location;
	private LocationManager locationManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		latitude = (TextView) findViewById(R.id.latitude);
		longitude = (TextView) findViewById(R.id.longitude);
		address = (TextView) findViewById(R.id.address);
		getCoordinates = (Button) findViewById(R.id.coordinates);
		getCoordinates.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				getCurrentLocation();
				getMyLocationAddress();
			}
		});
	}
	protected void getCurrentLocation()
	{
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, new MyLocationListener());
		if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
		{
			getLocation();
		}
		else
		{
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(intent);
		}
	}
	private void getLocation()
	{
		location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
		if(location != null && !(location.getLatitude()==0 ||  location.getLongitude() ==0))
		{
			latitude.setText(Double.toString(location.getLatitude()));
			longitude.setText(Double.toString(location.getLongitude()));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	private class MyLocationListener implements LocationListener
	{

		@Override
		public void onLocationChanged(Location arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub
			Toast.makeText(getBaseContext(), "GPS DISABLED", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub
			Toast.makeText(getBaseContext(), "GPS ENABLED", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub

		}

	}
	public void getMyLocationAddress() {

		Geocoder geocoder= new Geocoder(this, Locale.ENGLISH);

		try {

			//Place your latitude and longitude
			List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

			if(addresses != null) {

				Address fetchedAddress = addresses.get(0);
				StringBuilder strAddress = new StringBuilder();

				for(int i=0; i<fetchedAddress.getMaxAddressLineIndex(); i++) {
					strAddress.append(fetchedAddress.getAddressLine(i)).append("\n");
				}

				address.setText(strAddress.toString());

			}

			else
				address.setText("No location found..!");

		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(getApplicationContext(),"Could not get address..!", Toast.LENGTH_LONG).show();
		}
	}

}


