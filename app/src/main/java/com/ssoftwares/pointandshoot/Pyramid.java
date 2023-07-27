package com.ssoftwares.pointandshoot;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Pyramid {

    private FloatBuffer vertexBuffer;  // Buffer for vertex-array
    private FloatBuffer colorBuffer;   // Buffer for color-array
    private ByteBuffer indexBuffer;    // Buffer for index-array

    private float[] vertices = {
       0 , 1 , 0 ,
      -1 , -1 , -1 ,
       1 , -1 , -1 ,
      -1 , -1 , 1 ,
       1 , -1 , 1
    };
    private float[] colors = {  // Colors of the 5 vertices in RGBA
            0.0f, 0.0f, 1.0f, 1.0f,  // 0. blue
            0.0f, 1.0f, 0.0f, 1.0f,  // 1. green
            0.0f, 0.0f, 1.0f, 1.0f,  // 2. blue
            0.0f, 1.0f, 0.0f, 1.0f,  // 3. green
            1.0f, 0.0f, 0.0f, 1.0f   // 4. red
    };

    private byte[] indices = {
           0 , 1 , 2 ,
           2 , 0 , 4 ,
           4 , 0 , 3 ,
           3 , 0 , 1
    };

    public Pyramid() {
        // Setup vertex-array buffer. Vertices in float. An float has 4 bytes
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder()); // Use native byte order
        vertexBuffer = vbb.asFloatBuffer(); // Convert from byte to float
        vertexBuffer.put(vertices);         // Copy data into buffer
        vertexBuffer.position(0);           // Rewind

        // Setup color-array buffer. Colors in float. An float has 4 bytes
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
        cbb.order(ByteOrder.nativeOrder());
        colorBuffer = cbb.asFloatBuffer();
        colorBuffer.put(colors);
        colorBuffer.position(0);

        // Setup index-array buffer. Indices in byte.
        indexBuffer = ByteBuffer.allocateDirect(indices.length);
        indexBuffer.put(indices);
        indexBuffer.position(0);
    }
    public void draw(GL10 gl) {
        gl.glFrontFace(GL10.GL_CCW);  // Front face in counter-clockwise orientation

        // Enable arrays and define their buffers
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);

        gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_BYTE,
                indexBuffer);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
    }
}
