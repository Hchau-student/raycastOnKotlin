@file:Suppress("JniMissingFunction", "JniMissingFunction")

package com.example.myapplication

import android.graphics.Bitmap
import android.graphics.Point
//import jdk.nashorn.internal.objects.ArrayBufferView.buffer
import java.lang.Math.abs
import kotlin.math.floor as floor1

fun r_DrawWalls(size: Point, raycast: r_Raycast, start: Int, end: Int) {

    var srcPixels: IntArray = IntArray(size.x * size.y)
    var rayDirX: Float
    var rayDirY: Float
    var mapX: Int
    var mapY: Int
    var sideDistX: Float
    var sideDistY: Float
    val rayDirX0: Float =
        (raycast.player.Dir.x - raycast.player.CameraPlane.x)
    val rayDirY0: Float =
        (raycast.player.Dir.y - raycast.player.CameraPlane.y)
    val rayDirX1: Float =
        (raycast.player.Dir.x + raycast.player.CameraPlane.x)
    val rayDirY1: Float =
        (raycast.player.Dir.y + raycast.player.CameraPlane.y)
    var cameraX: Float
    var deltaDistX: Float
    var deltaDistY: Float
    var perpWallDist: Float
    var stepX: Int
    var stepY: Int
    var hit: Int
    var side: Int
    var lineHeight: Int
    var drawStart: Int
    var drawEnd: Int
    var wallX: Float
    var texX: Int
    var textYY: Int
    var text: IntArray

    var posZ: Float = 0.5f * size.y
    var posZC: Float = (-raycast.pref.cameraHeight + raycast.wallHeight) * size.y

    var rowDistance: Float
    var floorX: Float
    var floorY: Float

    var tx: Int
    var ty: Int
    var color: Int
    var DirX = raycast.player.Dir.x
    var DirY = raycast.player.Dir.y
    var PlaneX = raycast.player.CameraPlane.x
    var PlaneY = raycast.player.CameraPlane.y
    var texture: IntArray = raycast.textures.floor_text
    var ceiltexture: IntArray = raycast.textures.ceil_text

    var PosX = raycast.player.Pos.x
    var PosY = raycast.player.Pos.y
    var i: Int = start
    var resolution: Int = (21 - raycast.pref.resolution)
    while ((i < end) and (i < size.x)) {
        if (i % resolution != 0) {
            i++
            continue
        }
        cameraX = (2 * i / raycast.bm_size.x.toFloat() - 1)
        rayDirX = DirX + PlaneX * cameraX
        rayDirY = DirY + PlaneY * cameraX
        mapX = PosX.toInt()
        mapY = PosY.toInt()
        hit = 0
        deltaDistX = abs(1 / rayDirX)
        deltaDistY = abs(1 / rayDirY)
        side = 0
        if(rayDirX < 0) {
            stepX = -1
            sideDistX = (PosX - mapX) * deltaDistX
        } else {
            stepX = 1
            sideDistX = (mapX + 1.0f - PosX) * deltaDistX
        }
        if(rayDirY < 0)
        {
            stepY = -1
            sideDistY = (PosY - mapY) * deltaDistY
        } else {
            stepY = 1
            sideDistY = (mapY + 1.0f - PosY) * deltaDistY
        }

        while (hit == 0)
        {
            if(sideDistX < sideDistY)
            {
                sideDistX += deltaDistX
                mapX += stepX
                side = 0
            }
            else
            {
                sideDistY += deltaDistY
                mapY += stepY
                side = 1
            }
            if (raycast.map.worldMap[mapX + mapY * raycast.map.With] > 0) {
                hit = 1
            }
        }
        if (side == 0) {
            perpWallDist = (mapX - PosX + (1 - stepX) / 2) / rayDirX
        } else  {
            perpWallDist = (mapY - PosY + (1 - stepY) / 2) / rayDirY
        }
        lineHeight = ((raycast.bm_size.y / perpWallDist)).toInt()
        var diff: Int = (lineHeight * raycast.wallHeight - lineHeight).toInt()
        drawStart = (-lineHeight * (1.0f - raycast.pref.cameraHeight)).toInt() - diff + raycast.bm_size.y / 2

        drawEnd = (lineHeight * (raycast.pref.cameraHeight)).toInt() + raycast.bm_size.y / 2
        if (drawEnd >= raycast.bm_size.y) drawEnd = raycast.bm_size.y - 1
        wallX = if (side == 0) PosY + perpWallDist * rayDirY
        else PosX + perpWallDist * rayDirX
        wallX -= floor1(wallX)

        texX  = (wallX * (size.x)).toInt()
        if(side == 0 && rayDirX > 0) texX = size.x - texX - 1
        if(side == 1 && rayDirY < 0) texX = size.x - texX - 1

        text = if (raycast.map.worldMap[mapX + mapY * raycast.map.With] == 1)
            raycast.textures.a_text else if (raycast.map.worldMap[mapX + mapY * raycast.map.With] == 2)
            raycast.textures.b_text else raycast.textures.c_text

        //draw the pixels of the stripe as a vertical line
        textYY = 0 //find begin
        if (drawStart < 0)
            textYY = (drawStart) * -1
        if (drawStart < 0) drawStart = 0
        lineHeight += diff
//        while (textYY < 0)
//            textYY++
        var j = 0
        while (j < size.y) {
            while (((j >= drawEnd)) and (j < size.y)) {
                if (j % (resolution) != 0)
                {
                    srcPixels[i + (j) * size.x] = srcPixels[i + (j - 1) * size.x]
                    var k = 1
                    while ((i + k < size.x) and (k < resolution)) {
                        if ((i + k < size.x) and (j < size.y)) {
                            srcPixels[i + k + j * size.x] = srcPixels[i + j * size.x]
                        }
                        k++
                    }
                    j++
                    continue
                }
                rowDistance = (posZ / (j - posZ)) * (raycast.pref.cameraHeight * 2)
                floorX = ((PosX + rowDistance * rayDirX0) + (rowDistance * (rayDirX1 - rayDirX0) / size.x) * i) % 1
                floorY = ((PosY + rowDistance * rayDirY0) + (rowDistance * (rayDirY1 - rayDirY0) / size.x) * i) % 1
                tx = (((size.x * (floorX)).toInt() % (size.x - 1))).toInt()
                ty = (((size.y * (floorY))).toInt() % (size.y - 1)).toInt()
                if (tx < 0) tx * -1
                if (ty < 0) ty * -1
                if ((ty < size.y) and (tx < size.x) and (ty > 0) and (tx > 0)) {
                    color = texture[size.x * ty + tx]
                    srcPixels[i + j * size.x] = color
                    var k = 1
                    while ((i + k < size.x) and (k < resolution)) {
                        if ((i + k < size.x) and (j < size.y)) {
                            srcPixels[i + k + j * size.x] = srcPixels[i + j * size.x]
                        }
                        k++
                    }
                }
                j++
            }
            while (((j <= drawStart))) {
                if (j % (resolution) != 0)
                {
                    srcPixels[i + (j) * size.x] = srcPixels[i + (j - 1) * size.x]
                    if (srcPixels[i + (size.y - j) * size.x] == 0)
                        srcPixels[i + (size.y - j) * size.x] = srcPixels[i + (j - 1) * size.x]
                    var k: Int = 1
                    while ((i + k < size.x) and (k < resolution)) {
                        if ((i + k < size.x) and (j < size.y)) {
                            srcPixels[i + k + j * size.x] = srcPixels[i + j * size.x]
                        }
                        k++
                    }
                    j++
                    continue
                }
                rowDistance = (posZC / (size.y - j - 1 - posZ))
                floorX = ((PosX + rowDistance * rayDirX0) + (rowDistance * (rayDirX1 - rayDirX0) / size.x) * i) % 1
                floorY = ((PosY + rowDistance * rayDirY0) + (rowDistance * (rayDirY1 - rayDirY0) / size.x) * i) % 1
                tx = (((size.x * (floorX)).toInt() % (size.x - 1))).toInt()
                ty = (((size.y * (floorY))).toInt() % (size.y - 1)).toInt()
                if (tx < 0) tx * -1
                if (ty < 0) ty * -1
                if ((ty < size.y) and (tx < size.x) and (ty > 0) and (tx > 0)) {
                    color = ceiltexture[size.x * ty + tx]
                    srcPixels[i + j * size.x] = color
//                    if (srcPixels[i + (size.y - j) * size.x] == 0)
//                        srcPixels[i + (size.y - j) * size.x] = color
                    var k: Int = 1
                    while ((i + k < size.x) and (k < resolution)) {
                        if ((i + k < size.x) and (j < size.y)) {
                            srcPixels[i + k + j * size.x] = srcPixels[i + j * size.x]
//                            if (srcPixels[i + k + (size.y - j) * size.x] == 0)
//                                srcPixels[i + k + (size.y - j) * size.x] = srcPixels[i + j * size.x]
                        }
                        k++
                    }
                }
                j++
            }
            if ((j % (resolution) != 0) and ((j > drawStart) and (j < drawEnd) and (j < size.y)))
            {
                srcPixels[i + (j) * size.x] = srcPixels[i + (j - 1) * size.x]
                var k: Int = 1
                while ((i + k < size.x) and (k < resolution)) {
                    if ((i + k < size.x) and (j < size.y)) {
                        srcPixels[i + k + j * size.x] = srcPixels[i + j * size.x]
                    }
                    k++
                }
                textYY++
                j++
                continue
            }
            if ((j > drawStart) and (j < drawEnd) and (j < size.y)) {
                if ((texX > size.x) or (((textYY * size.x) / (lineHeight) * raycast.wallH) > size.y)) {
                        textYY++
                        j++
                        continue
                    }
                if ((texX + ((textYY * size.x) / (lineHeight) * raycast.wallH).toInt() * size.x > 0)
                    and (texX + ((textYY * size.x) / (lineHeight) * raycast.wallH).toInt() * size.x < size.y * size.x))
                    srcPixels[i + j * size.x] = text[texX + ((textYY * size.x) / (lineHeight) * raycast.wallH).toInt() * size.x]
                textYY++
            }
            var k: Int = 1
            while ((i + k < size.x) and (k < resolution)) {
                if ((i + k < size.x) and (j < size.y) and (j >= 0)) {
                    srcPixels[i + k + j * size.x] = srcPixels[i + j * size.x]
                }
                k++
            }
            j++
        }
        i++
    }
    raycast.bm.setPixels(srcPixels, 0, size.x,
        0, 0, size.x, size.y)
}
