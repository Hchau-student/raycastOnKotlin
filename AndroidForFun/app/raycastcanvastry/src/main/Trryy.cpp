//
// Created by irka on 12.05.20.
//
//#pragma clang diagnostic push
//#pragma ide diagnostic ignored "CannotResolve"


#include <jni.h>
#include <string>
using namespace std;
#include <android/bitmap.h>
//#include <android/>
#include <math.h>
//#include <android/gra>
//#include <>
#include <vector>
#define JNIIMPORT
#define JNIEXPORT  __attribute__ ((visibility ("default")))
#define JNICALL
//import android.graphics.Canvas

//#include <>

//работает отстойно, так же медленно, как и код на котлине,
//но ещё и текстуры отстойно отображаются; если получится сделать
//побыстрее каким-то образом - будет супер-кул

//typedef struct {
//    var TouchScreen: t_TapScreen
//    val bm_size: Point
//    var bm: Bitmap
//    val map: z_Map = z_Map()
//    var player: y_Player = y_Player()
//    var textures: z_Textures
//    var pref: z_Preferences
//    float wallHeight;
//} r_Raycast;

class Point {
public:
    jint x;
    jint y;
    jint full;
    Point(jint _x, jint _y) {
        x = _x;
        y = _y;
        full = _x * _y;
    }
};

//Point init_size(jint x, jint y) {
//    Point
//}

extern "C" JNIEXPORT jintArray JNICALL Java_com_example_raycastcanvastry_MainActivity_stringFromJNI( JNIEnv *env,
        jobject/* this */, jint x, jint y, jintArray a_text, jintArray b_text, jintArray c_text,
        jfloat pDirX, jfloat pDirY, jfloat pCameraPlaneX, jfloat pCameraPlaneY, jfloat PosX, jfloat PosY,
        jintArray worldMap, jint wm_w, jint wm_h) {

//    TCanvas k;
//    jclass clazz = (env)->FindClass(env, "src/");
    jboolean j;
    Point size(x, y);
    float rayDirX;
    float rayDirY;
    int mapX;
    int mapY;
    float sideDistX;
    float sideDistY;
    float rayDirX0 = (pDirX - pCameraPlaneX);
    float rayDirY0 = (pDirY - pCameraPlaneY);
    float rayDirX1 = (pDirX + pCameraPlaneX);
    float rayDirY1 = (pDirY + pCameraPlaneY);
    float cameraX;
    float deltaDistX;
    float deltaDistY;
    float perpWallDist;
    int stepX;
    int stepY;
    int hit;//was there a wall hit?
    int side; //was a NS or a EW wall hit?
    int lineHeight;
    int drawStart;
    int drawEnd;
    float wallX;
    int texX;
    int textYY;
    int *text;
    float posZ = 0.5f * size.y;
    float rowDistance;
    float floorX;
    float floorY;
    int tx;
    int ty;
    int color;
//    int DirX = pDirX;
//    int DirY = pDirY;
//    var PlaneX = raycast.player.CameraPlane.x
//    var PlaneY = raycast.player.CameraPlane.y
    int *texture = env->GetIntArrayElements(a_text, &j);
    int *World =  env->GetIntArrayElements(worldMap, &j);
//    var PosX = raycast.player.Pos.x
//    var PosY = raycast.player.Pos.y

    jintArray result;
    jint *fill;
    if (env == NULL || (result = (env)->NewIntArray(size.full)) == NULL ||
            (fill = (jint *)malloc(sizeof(jint) * size.full)) == NULL)
        return NULL;

    for (int i = 0; i < size.x; ++i) {
        cameraX = ((float) (2 * i) / (float) size.x - 1.0); //x-coordinate in camera space
        rayDirX = pDirX + pCameraPlaneX * cameraX;
        rayDirY = pDirY + pCameraPlaneY * cameraX;
        mapX = (int) PosX;
        mapY = (int) PosY;
        hit = 0;
        deltaDistX = abs(1 / rayDirX);
        deltaDistY = abs(1 / rayDirY);
        side = 0;
        if(rayDirX < 0) {
            stepX = -1;
            sideDistX = (PosX - mapX) * deltaDistX;
        } else {
            stepX = 1;
            sideDistX = (mapX + 1.0f - PosX) * deltaDistX;
        }
        if(rayDirY < 0)
        {
            stepY = -1;
            sideDistY = (PosY - mapY) * deltaDistY;
        } else {
            stepY = 1;
            sideDistY = (mapY + 1.0f - PosY) * deltaDistY;
        }
        while (hit == 0)
        {
            //jump to next map square, OR in x-direction, OR in y-direction
            if(sideDistX < sideDistY)
            {
                sideDistX += deltaDistX;
                mapX += stepX;
                side = 0;
            }
            else
            {
                sideDistY += deltaDistY;
                mapY += stepY;
                side = 1;
            }
            //Check if ray has hit a wall
            if (World[mapX + mapY * wm_w] > 0) {
                hit = 1;
            }
        }
        if (side == 0) {
            perpWallDist = ((mapX - PosX + (1 - stepX) / 2)) / rayDirX;
        } else  {
            perpWallDist = ((mapY - PosY + (1 - stepY) / 2)) / rayDirY;
        }
        //Calculate height of line to draw on screen
        //Calculate height of line to draw on screen
        lineHeight = (int)(((float)size.y / perpWallDist) * wm_h) / 10;

        //calculate lowest and highest pixel to fill in current stripe

        //calculate lowest and highest pixel to fill in current stripe
        drawStart = -lineHeight / 2 + size.y / 2;
        if (drawStart < 0) drawStart = 0;
        drawEnd = lineHeight / 2 + size.y / 2;
        if (drawEnd >= size.y) drawEnd = size.y - 1;
        //calculate value of wallX
        //where exactly the wall was hit
        wallX = (side == 0) ? PosY + perpWallDist * rayDirY : PosX + perpWallDist * rayDirX;
        wallX -= floor(wallX);
        texX  = (int)(wallX * (size.x));
        if(side == 0 && rayDirX > 0) texX = size.x - texX - 1;
        if(side == 1 && rayDirY < 0) texX = size.x - texX - 1;

        text = (World[mapX + mapY * wm_w] == 1) ? env->GetIntArrayElements(a_text, &j)
                : (World[mapX + mapY * wm_w] == 2) ? env->GetIntArrayElements(b_text, &j)
                : env->GetIntArrayElements(c_text, &j);
        textYY = 0; //find begin
        if (lineHeight > size.y)
            textYY = (lineHeight - size.y) / 2;
//        texture = raycast.textures.a_text

        int j = 0;
        while (j < size.y) {
            while ((j >= drawEnd) and (j < size.y)) {
//                p = j - size.y / 2

                // Vertical position of the camera.
//                posZ = (0.5 * size.y).toFloat()

                // Horizontal distance from the camera to the floor for the current row.
                // 0.5 is the z position exactly in the middle between floor and ceiling.
//                rowDistance = TryMakeRaycastFaster(posZ, j)
                rowDistance = (posZ / (j - posZ));
                // calculate the real world step vector we have to add for each x (parallel to camera plane)
                // adding step by step avoids multiplications with a weight in the inner loop
                float temp = ((PosX + rowDistance * rayDirX0) + (rowDistance * (rayDirX1 - rayDirX0) / size.x) * i);
                floorX = (float)(temp - (int)temp);
                temp = ((PosY + rowDistance * rayDirY0) + (rowDistance * (rayDirY1 - rayDirY0) / size.x) * i);
                floorY = (float)(temp - (int)temp);
//                tx = (((((PosX + rowDistance * rayDirX0) +
//                        ((rowDistance * (rayDirX1 - rayDirX0) / size.x) * i) % 1).to + PosX) % (size.x - 1))).toInt()
                tx = (int)(((int)(size.x * (floorX)) % (size.x - 1)));
                ty = (int)(((int)(size.y * (floorY))) % (size.y - 1));
                if (tx < 0) tx * -1;
                if (ty < 0) ty * -1;
                if ((ty < size.y) and (tx < size.x) and (ty > 0) and (tx > 0)) {
                    color = texture[size.x * ty + tx];
                    fill[i + j * size.x] = color;
                }
                j++;
            }
            if ((j > drawStart) and (j < drawEnd)) {
                fill[i + j * size.x] = text[texX + (int)((textYY * size.x) / (float)(lineHeight)) * size.x];
                textYY++;
            }
            j++;
        }
    }
    //алгос уже здесь
//    int i;
//    int *a =  env->GetIntArrayElements(a_text, &j);
//    for (i = 0; i < size.full; i++) {
//        fill[i] = a[i]; // put whatever logic you want to populate the values here.
//    }
    (env)->SetIntArrayRegion(result, 0, size.full, fill);
    return result;
}
//#pragma clang diagnostic pop