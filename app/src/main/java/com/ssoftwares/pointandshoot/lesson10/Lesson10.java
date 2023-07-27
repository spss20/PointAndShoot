package com.ssoftwares.pointandshoot.lesson10;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class Lesson10 extends GLSurfaceView implements Renderer {
		
	/** Our World */				
	private World world;					
	
	private int filter = 0;				//Which texture filter?
	
	/** Is blending enabled */
	private boolean blend = false;
	
	/** The Activity Context */
	private Context context;
	
	/**
	 * Set this class as renderer for this GLSurfaceView.
	 * Request Focus and set if focusable in touch mode to
	 * receive the Input from Screen
	 * 
	 * @param context - The Activity Context
	 */
	public Lesson10(Context context) {
		super(context);
		
		//Set this as Renderer
		this.setRenderer(this);
		//Request focus
		this.requestFocus();
		this.setFocusableInTouchMode(true);
		
		//
		this.context = context;
		
		//Instance our World
		world = new World(this.context);
		
		//Set the world as listener to this view
		this.setOnKeyListener(world);
		this.setOnTouchListener(world);
	}

	/**
	 * The Surface is created/init()
	 */
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		//Settings
		gl.glDisable(GL10.GL_DITHER);						//Disable dithering
		gl.glEnable(GL10.GL_TEXTURE_2D);					//Enable Texture Mapping
		gl.glShadeModel(GL10.GL_SMOOTH); 					//Enable Smooth Shading
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f); 			//Black Background
		gl.glClearDepthf(1.0f); 							//Depth Buffer Setup
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);		//Set The Blending Function For Translucency
		gl.glDepthFunc(GL10.GL_LEQUAL); 					//The Type Of Depth Testing To Do
		
		//Really Nice Perspective Calculations
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST); 
				
		//Load our world from the textual description
		world.loadWorld("world.txt");
		//Load the texture for our world once during Surface creation
		world.loadGLTexture(gl, this.context);
	}
	
	/**
	 * Here we do our drawing
	 */
	public void onDrawFrame(GL10 gl) {
		//Clear Screen And Depth Buffer
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);	
		
		gl.glLoadIdentity();					//Reset The Current Modelview Matrix
		
		//Check if the blend flag has been set to enable/disable blending
		if(blend) {
			gl.glEnable(GL10.GL_BLEND);			//Turn Blending On
			gl.glDisable(GL10.GL_DEPTH_TEST);	//Turn Depth Testing Off
			
		} else {
			gl.glDisable(GL10.GL_BLEND);		//Turn Blending On
			gl.glEnable(GL10.GL_DEPTH_TEST);	//Turn Depth Testing Off
		}
		
		//
		world.draw(gl, filter);
	}
		

	/**
	 * If the surface changes, reset the view
	 */
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		if(height == 0) { 						//Prevent A Divide By Zero By
			height = 1; 						//Making Height Equal One
		}

		gl.glViewport(0, 0, width, height); 	//Reset The Current Viewport
		gl.glMatrixMode(GL10.GL_PROJECTION); 	//Select The Projection Matrix
		gl.glLoadIdentity(); 					//Reset The Projection Matrix

		//Calculate The Aspect Ratio Of The Window
		GLU.gluPerspective(gl, 45.0f, (float)width / (float)height, 0.1f, 100.0f);

		gl.glMatrixMode(GL10.GL_MODELVIEW); 	//Select The Modelview Matrix
		gl.glLoadIdentity(); 					//Reset The Modelview Matrix
	}
	
/* ***** Listener Events ***** */	
	/**
	 * Override the key listener to receive keyUp events.
	 * 
	 * Check for the DPad presses in the middle.
	 * Change the texture filter used through the middle press.
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		//Middle pressed
		if(keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
			filter += 1;
			if(filter > 2) {
				filter = 0;
			}
			
			//We handled the event
			return true;
		}
		
		return false;
	}
	
	/**
	 * Override the touch screen listener.
	 * 
	 * React to moves and presses on the touchscreen.
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//
		float x = event.getX();
        float y = event.getY();
        
        //A press on the screen
        if(event.getAction() == MotionEvent.ACTION_UP) {
        	//Define an upper area of 10% to define a lower area
        	int upperArea = this.getHeight() / 10;
        	int lowerArea = this.getHeight() - upperArea;
        	
        	//
        	if(y > lowerArea) {
        		//Change the blend setting if the lower area left has been pressed
        		if(x < (this.getWidth() / 2)) {
        			if(blend) {
        				blend = false;
            		} else {
            			blend = true;
            		} 
        		}
        	}
        }
        
        //
		return true;
	}
}
