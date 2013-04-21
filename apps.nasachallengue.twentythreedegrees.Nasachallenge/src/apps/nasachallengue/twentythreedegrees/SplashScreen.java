package apps.nasachallengue.twentythreedegrees;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import apps.nasachallengue.gpsTracker.GPSTracker;

public class SplashScreen extends Activity implements OnClickListener
{
	Display display;
	int ancho, alto;
	ImageView fondo;
	Bitmap bmFondo;
	ImageButton btnConfig;
	Dialog dia;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_splash);
		
		display = getWindowManager().getDefaultDisplay();
		
		fondo = (ImageView) findViewById(R.id.imgSplash);
		bmFondo = BitmapFactory.decodeResource(getResources(), R.drawable.cortinilla);

		int ancho = display.getWidth();  //Ancho Pantalla
		int alto = display.getHeight(); //Alto Pantalla.
		
		bmFondo = Bitmap.createScaledBitmap(bmFondo, (ancho*100)/100, (alto*100)/100, true);
		fondo.setImageBitmap(bmFondo);
		
		verificarGPS();
		
        Thread tiempo = new Thread()
        {

        	public void run()
        	{
	        	try
	        	{
	        		sleep(3000);
	        	}
	        	catch(InterruptedException e)
	        	{
	        		e.printStackTrace();
	        	}
	        	finally
	        	{
	        		Intent abrirInicio = new Intent("apps.nasachallengue.twentythreedegrees.Nasachallenge");
	        		startActivity(abrirInicio);
	        		finish();
	        	}
        	}
        };

        tiempo.start();
	}
	
	private void verificarGPS()
	{
		GPSTracker gps = new GPSTracker(this);
	}

	public void onClick(View v) 
	{
		switch (v.getId())
		{
			case R.id.btnConfig:
			
			break;

		}
		
	}

}
