package apps.nasachallengue.gpsTracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import apps.nasachallengue.twentythreedegrees.R;

public class GPSTracker extends Service implements LocationListener {
	 
    private final Context mContext;
 
    // flag for GPS status
    boolean isGPSEnabled = false;
 
    // flag for network status
    boolean isNetworkEnabled = false;
 
    boolean canGetLocation = false;
 
    Location location; // location
    double latitude; // latitude
    double longitude; // longitude
    double altitud;
 
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
 
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
 
    // Declaring a Location Manager
    protected LocationManager locationManager;
    
 
    public GPSTracker(Context context)
    {
        this.mContext = context;
        getLocation();
    }

    public Location getLocation() 
    {
    	try 
    	{
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
            // getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            // getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!isGPSEnabled) 
            {
               //Toast.makeText(mContext, "Tienes que activar tu GPS", Toast.LENGTH_SHORT).show();
               showSettingsAlert();
               
            } 
            else 
            {
	            this.canGetLocation = true;
	            // First get location from Network Provider
	            if (isNetworkEnabled) 
	            {
	                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
	                Log.d("Network", "Network");
	                if (locationManager != null) 
	                {
	                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	                    if (location != null) 
	                    {
	                        latitude = location.getLatitude();
	                        longitude = location.getLongitude();
	                    }
	                }
	            }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) 
                {
                    if (location == null) 
                    {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) 
                        {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) 
                            {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }
     
        } 
    	catch (Exception e) 
    	{
            e.printStackTrace();
        }
     
        return location;
    }
    public void onLocationChanged(Location location) 
    {
    	
    }
 
    public void onProviderDisabled(String provider) 
    {
    	
    }
 
    public void onProviderEnabled(String provider) 
    {
    	
    }
 
    public void onStatusChanged(String provider, int status, Bundle extras) 
    {
    	
    }
 
    @Override
    public IBinder onBind(Intent arg0) 
    {
        return null;
    }
    public double getLatitude()
    {
        if(location != null)
        {
            latitude = location.getLatitude();
        }
 
        // return latitude
        return latitude;
    }
 
    /**
     * Function to get longitude
     * */
    public double getLongitude()
    {
        if(location != null)
        {
            longitude = location.getLongitude();
        }
 
        // return longitude
        return longitude;
    }
    public double getAltitud()
    {
        if(location != null)
        {
            altitud = location.getAltitude();
        }
 
        // return longitude
        return altitud;
    }
    public boolean canGetLocation() 
    {
        return this.canGetLocation;
    }
 
    /**
     * Function to show settings alert dialog
     * */
    public void showSettingsAlert()
    {
    	
    	final Dialog dia = new Dialog(mContext);
    	dia.setTitle("GPS");
    	dia.setCancelable(false);
    	dia.setContentView(R.layout.alerta_gps);
    	dia.show();
    	ImageButton btnConfig= (ImageButton)dia.findViewById(R.id.btnConfig);
    	btnConfig.setOnClickListener(new OnClickListener()
    	{
			
			public void onClick(View v) {
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
                dia.cancel();
			}
		});
    	ImageButton btnExit = (ImageButton)dia.findViewById(R.id.btnExit);
    	btnExit.setOnClickListener(new OnClickListener()
    	{
			public void onClick(View v) 
			{
				dia.cancel();
			}
		});

    }
    public void stopUsingGPS()
    {
        if(locationManager != null)
        {
            locationManager.removeUpdates(GPSTracker.this);
        }
    }
}