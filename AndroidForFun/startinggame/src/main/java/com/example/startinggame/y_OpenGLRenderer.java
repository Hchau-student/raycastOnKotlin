package com.example.startinggame;
import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_LINE_LOOP;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glLineWidth;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;

public class y_OpenGLRenderer implements Renderer {
    private Context context;
    private int programId;
    private FloatBuffer vertexData;
    private int uColorLocation;

    //    private int uColorLocation;
    private int aColorLocation;
    private int aPositionLocation;

    public y_OpenGLRenderer(Context context) {
        this.context = context;
        prepareData();
    }

    @Override
    public void onSurfaceCreated(GL10 arg0, EGLConfig arg1) {
        glClearColor(0f, 0f, 0f, 1f);
        int vertexShaderId = z_ShaderUtils.createShader(context, GL_VERTEX_SHADER, R.raw.vertex_shader);
        int fragmentShaderId = z_ShaderUtils.createShader(context, GL_FRAGMENT_SHADER, R.raw.fragment_shader);
        programId = z_ShaderUtils.createProgram(vertexShaderId, fragmentShaderId);
        glUseProgram(programId);
        bindData();
    }

    @Override
    public void onSurfaceChanged(GL10 arg0, int width, int height) {
        glViewport(0, 0, width, height);
    }

    private void prepareData() {
        //получить точки рейкаста
        float x1 = -0.5f, y1 = -0.8f, x2 = 0.5f, y2 = -0.8f;

        float[] vertices = {
                x1, y1, 0.0f, 1.0f,
                x1, y1, 0.0f, 1.5f,
                x1, y1, 0.0f, 2.0f,
                x1, y1, 0.0f, 2.5f,
                x1, y1, 0.0f, 3.0f,
                x1, y1, 0.0f, 3.5f,

                x2, y2, 0.0f, 1.0f,
                x2, y2, 0.0f, 1.5f,
                x2, y2, 0.0f, 2.0f,
                x2, y2, 0.0f, 2.5f,
                x2, y2, 0.0f, 3.0f,
                x2, y2, 0.0f, 3.5f,
        };

        vertexData = ByteBuffer.allocateDirect(vertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexData.put(vertices);
    }

    private void bindData() {
        // координаты
        aPositionLocation = glGetAttribLocation(programId, "a_Position");
        vertexData.position(0);
        glVertexAttribPointer(aPositionLocation, 4, GL_FLOAT,
                false, 0, vertexData);
        glEnableVertexAttribArray(aPositionLocation);

        // цвет
        uColorLocation = glGetUniformLocation(programId, "u_Color");
    }

    @Override
    public void onDrawFrame(GL10 arg0) {
        glLineWidth(60);
        //получить цвет из текстуры, ф-ия
        glUniform4f(uColorLocation, 0.7f, 0.3f, 0.0f, 1.0f);
        glLineWidth(60);
        glDrawArrays(GL_POINTS, 0, 12);
    }
}