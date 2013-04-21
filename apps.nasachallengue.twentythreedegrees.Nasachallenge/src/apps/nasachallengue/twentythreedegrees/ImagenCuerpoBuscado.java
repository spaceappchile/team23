package apps.nasachallengue.twentythreedegrees;

import java.io.InputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ImagenCuerpoBuscado extends Activity 
{
	
	private ImageView imgFotoGps;
	private TextView txtInfo;
	private String nomCuerpo;
	private ProgressDialog dialog;
	private String resultado;
	private Handler puente;
	private Bitmap loadedImage;
	private String url="http://23deg.cityhero.es/api/getAsteriode";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cuerpo_buscado);

		Bundle bundle = getIntent().getExtras();
        nomCuerpo = bundle.getString("CuerpoCeleste");
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
	        	Drawable mensaje = (Drawable)msg.obj;
	        	dialog.dismiss();
	        	imgFotoGps.setImageDrawable(mensaje);
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
                    msg.obj = obtenerImagen(url);
                    puente.sendMessage(msg);
	             }
	         };
	         thread.start();		
	}
	
	private Drawable obtenerImagen(String url)
	{
		InputStream is = null;
		StringBuilder sb=null;
		String result = null;
		//Coneccion a la pagina
		try
		{
		     HttpClient httpclient = new DefaultHttpClient();
		     HttpGet httppost = new HttpGet(url+"?cuerpo="+nomCuerpo);//CAMBIAR PARAMETRO!!!!!
		     HttpResponse response = httpclient.execute(httppost);
		     HttpEntity entity = response.getEntity();
		     is = entity.getContent();
		}
		catch(Exception e)
		{
			Log.e("log_tag", "Error in http connection"+e.toString());
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
		    result=sb.toString();
		    String [] nomFoto = result.split("\"");
		    return  obtenerImagenUrl("http://skyview.gsfc.nasa.gov/tempspace/fits/"+nomFoto[3]+".jpg");
		} 
		catch(Exception e)
		{
			Log.e("logLectura", "Error converting result "+e.toString());
			return obtenerImagenUrl("http://softtouch.com.co/img/lo%20sentimos.jpg");//URL ERROR! getBitmapFromURL("http://softtouch.com.co/img/lo%20sentimos.jpg")
		}
		
	}
	
	private Drawable obtenerImagenUrl(String url)
	{
		 Drawable imagen=null;
		 try {
			imagen =  Drawable.createFromStream((InputStream)new URL(url).getContent(), "src");
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
		imgFotoGps = (ImageView)findViewById(R.id.imgCuerpo);
		txtInfo = (TextView)findViewById(R.id.txtCuerpo);
		txtInfo.setText("");
	}

    
}
