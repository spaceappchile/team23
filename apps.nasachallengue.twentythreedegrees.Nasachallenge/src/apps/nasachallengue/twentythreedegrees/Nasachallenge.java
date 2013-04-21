package apps.nasachallengue.twentythreedegrees;

import apps.nasachallengue.gpsTracker.*; 

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.animation.AnimatorSet.Builder;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class Nasachallenge extends Activity implements OnClickListener
{
	Display display;
	int ancho, alto;
	
	private Bitmap bmExplicacion, bmBuscar, bmCamara;
	private ImageView imgExplicacion;
	private ImageButton btnFoto;
	private ImageView imgLista;
	private static int TOMAR_FOTO = 1;
	private static int SELECCIONAR_FOTO = 2;
	private int code;
	GPSTracker gps;


	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nasachallenge);
		inicializarElementos();
		verificarGPS();
	}
	private void verificarGPS()
	{
		gps = new GPSTracker(this);
	}
		private void inicializarElementos() 
	{
		imgExplicacion = (ImageView)findViewById(R.id.imgExplicacion);
		btnFoto = (ImageButton)findViewById(R.id.imgFoto);
		imgLista = (ImageView)findViewById(R.id.imgFotoEspacio);
		
		display = getWindowManager().getDefaultDisplay();
		
		bmExplicacion = BitmapFactory.decodeResource(getResources(), R.drawable.instrucciones_trans);
		bmBuscar = BitmapFactory.decodeResource(getResources(), R.drawable.back1);
		bmCamara = BitmapFactory.decodeResource(getResources(), R.drawable.camara);

		ancho = display.getWidth();
		alto = display.getHeight();
		
		bmExplicacion = Bitmap.createScaledBitmap(bmExplicacion, (ancho*48)/100, (alto*50)/100, true);
		bmBuscar = Bitmap.createScaledBitmap(bmBuscar, (ancho*27)/100, (alto*15)/100, true);
		bmCamara = Bitmap.createScaledBitmap(bmCamara, (ancho*30)/100, (alto*20)/100, true);
		
		imgLista.setImageBitmap(bmBuscar);
		imgExplicacion.setImageBitmap(bmExplicacion);
		btnFoto.setImageBitmap(bmCamara);
		
		btnFoto.setOnClickListener(this);
		imgLista.setOnClickListener(this);
	}
	public void onClick(View v) 
	{
		switch (v.getId())
		{
			case R.id.imgExplicacion:
				
				break;
			case R.id.imgFoto:
				code=TOMAR_FOTO;
				Intent intent =  new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
	   			startActivityForResult(intent, code);
				break;
				
			case R.id.imgFotoEspacio:
				Intent intentBusqueda = new Intent("apps.nasachallengue.twentythreedegrees.BuscaCuerpos");
        		startActivity(intentBusqueda);
				break;
		}
		
	}
	 @Override 
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	    {
	    	try
	    	{
	    		Intent i = new Intent("apps.nasachallengue.twentythreedegrees.ImagenGps");
		        i.putExtra("LongitudeGPS", gps.getLongitude());
		        i.putExtra("LatitudeGPS", gps.getLatitude());
		        i.putExtra("altitudGPS", gps.getAltitud());
		        
		        startActivity(i);	   	
	    	}
	    	catch (Exception e) 
	    	{
	    		e.printStackTrace();
	    	 	Toast.makeText(this, "no foto", Toast.LENGTH_SHORT).show();
	    	}
	    }
	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause();
		gps.stopUsingGPS();
	}
}
