package com.example.boladecristal;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.boladecristal.ShakeDetector.OnShakeListener;
// CTRL + SHIFT + O = actualiza lo imports 

public class MainActivity extends Activity {
	
	private BolaDeCristal mBoladeCristal = new BolaDeCristal();
	private TextView mRespuestaLabel;
	private ImageView mBoladeCristalImagen;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private ShakeDetector mShakeDetector;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Declarando variables
        mRespuestaLabel = (TextView) findViewById(R.id.textView1);
        mBoladeCristalImagen = (ImageView) findViewById(R.id.imageView1);
        
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector(new OnShakeListener() {
			
			@Override
			public void onShake() {
				manejarNuevaRespuesta();
			}
		});
    }

    private void animarBolaDeCristal(){
    	mBoladeCristalImagen.setImageResource(R.drawable.ball_animation);
    	AnimationDrawable bolaAnimacion = (AnimationDrawable) mBoladeCristalImagen.getDrawable();
    	if(bolaAnimacion.isRunning()){
    		bolaAnimacion.stop();
    	}
    	bolaAnimacion.start();
    }
    
    private void animarRespuesta() {
    	AlphaAnimation fadeInAnimacion = new AlphaAnimation(0, 1);
    	fadeInAnimacion.setDuration(1500);
    	fadeInAnimacion.setFillAfter(true);
    	
    	mRespuestaLabel.setAnimation(fadeInAnimacion);
	}
    private void playSonido(){
    	MediaPlayer player = MediaPlayer.create(this, R.raw.crystal_ball);
    	player.start();
    	player.setOnCompletionListener(new OnCompletionListener() {
			
			public void onCompletion(MediaPlayer mp) {
				mp.release();
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	private void manejarNuevaRespuesta() {
		String respuesta = mBoladeCristal.getUnaRespuesta();
		
		//Actualizar el nuevo valor aleatorio en el label			
		mRespuestaLabel.setText(respuesta);

		animarBolaDeCristal();
		animarRespuesta();
		playSonido();
	}
    
}
