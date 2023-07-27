package com.ssoftwares.pointandshoot;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_BLEND;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.GL_ONE_MINUS_SRC_ALPHA;
import static android.opengl.GLES20.GL_SRC_ALPHA;

public class Quad {

    private FloatBuffer verticesBuffer;
    private ByteBuffer indicesBuffer;

    private float[] vertices = {
            -1 , -1 , 0    ,
             1 , -1 , 0    ,
            -1 ,  1 , 0    ,
             1 ,  1 , 0    ,
    };

//    private byte[] indices = {0 , 1 , 2   ,   2 , 0 , 3  };

    public Quad(){
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        verticesBuffer = vbb.asFloatBuffer();
        verticesBuffer.put(vertices);
        verticesBuffer.position(0);

//        indicesBuffer = ByteBuffer.allocateDirect(indices.length);
//        indicesBuffer.put(indices);
//        indicesBuffer.position(0);
    }

    public void draw(GL10 gl){
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3 , GL10.GL_FLOAT , 0 , verticesBuffer);
//        gl.glEnable(GL10.GL_BLEND);
//        gl.glBlendFunc( GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA );

        gl.glColor4f(64 , 255 , 0 , 1);
//        gl.glDrawElements(GL10.GL_TRIANGLES , indices.length , GL10.GL_UNSIGNED_BYTE , indicesBuffer);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP , 0 , vertices.length / 3);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
//        gl.glDisable( GL10.GL_BLEND);

    }
}
