package apps.nasachallengue.twentythreedegrees;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ImagenGps extends Activity
{
	private ImageView imgFotoGps;
	private TextView txtInfo;
	private double lat;
	private double log;
	private double alt;
	private ProgressDialog dialog;
	private String resultado;
	private Handler puente;
	private Bitmap loadedImage;
	private String url;
	private String urlImagen="";

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imagen_gps);
		Bundle bundle = getIntent().getExtras();
        lat = bundle.getDouble("LatitudGPS",0);
        log = bundle.getDouble("LongitudGPS",0);
        alt = bundle.getDouble("altitudGPS",0);
		inicializarElementos();
		puente();
		dialog = ProgressDialog.show(this, "", "Search...", false);
		hiloBuscar();
		
	}
	private void puente() 
	{
		puente = new Handler() 
			{
		        @Override
		        public void handleMessage(Message msg) 
		        {
		        	Drawable imagen = (Drawable)msg.obj;		        	
		        	imgFotoGps.setImageDrawable(imagen);
		        	txtInfo.setText("This image correspond to the coordinates "+lat +" "+log);
		        	dialog.dismiss();
		        	/*String mensaje =(String)msg.obj;
		        	txtInfo.setText(mensaje);
		        	dialog.dismiss();*/
		        }
		    };	
	}

	
	private void hiloBuscar() 
	{ 
		Thread thread = new Thread()
		{
	            @Override
	            public void run() 
	            {
	            	Message msg = new Message();
                    msg.obj = obtenerImagen("http://23deg.cityhero.es/api/getIt?lat="+lat+"&lng="+log);
                    puente.sendMessage(msg);
	             }
	         };
	         thread.start();		
	}
	private Drawable obtenerImagen(String url)
	{
		InputStream is = null;
		StringBuilder sb =null;
		String result = null;

		//Coneccion a la pagina
		try
		{
		     HttpClient httpclient = new DefaultHttpClient();
		     HttpPost httppost = new HttpPost(url);
		     HttpResponse response = httpclient.execute(httppost);
		     HttpEntity entity = response.getEntity();
		     is = entity.getContent();
		}
		catch(Exception e)
		{
			Log.e("log_tag", "Error in http connection!!"+e.toString());
			
		}
		try
		{
			//se extraen los datos
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		    sb = new StringBuilder();
		    sb.append(reader.readLine() + "\n");
	        String line="0";
	        while ((line = reader.readLine()) != null)  
	        {
	        	sb.append(line + "\n");
		    }  
		    is.close();
		    //result=
		    String [] datos = (sb.toString()).split("\"");
		   if(datos.length>7)
		    {

			   return  getBitmapFromURL("http://skyview.gsfc.nasa.gov/tempspace/fits/"+datos[9]+".jpg");
		    }
		    else
		    {
		    	
		    	return getBitmapFromURL("http://softtouch.com.co/img/lo%20sentimos.jpg");
		    }
		} 
		catch(Exception e)
		{
			return getBitmapFromURL("http://softtouch.com.co/img/lo%20sentimos.jpg");
		}	
	}
	public Drawable getBitmapFromURL(String src) 
	{
		 Drawable imagen=null;
		 try {
			imagen =  Drawable.createFromStream((InputStream)new URL(src).getContent(), "src");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			//imagen =  Drawable.createFromStream((InputStream)new URL("http://softtouch.com.co/img/lo%20sentimos.jpg").getContent(), "src");
			e.printStackTrace();
		}
		
		return imagen;
    
    }
	private void inicializarElementos()
	{
		imgFotoGps = (ImageView)findViewById(R.id.imgFotoEspacio);
		txtInfo = (TextView)findViewById(R.id.txtInformacion);
		txtInfo.setText("");
		
	}
}
