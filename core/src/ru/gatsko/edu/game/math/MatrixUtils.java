package ru.gatsko.edu.game.math;

/**
 * Created by gatsko on 21.09.2018.
 */

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;

/**
 * Утилита для работы с матрицами
 */
public class MatrixUtils {

    private MatrixUtils() {
    }
    public static void calcTransitionMatrix(Matrix4 mat, Rect src, Rect dst) {
        float scaleX = dst.getWidth() / src.getWidth();
        float scaleY = dst.getHeight() / src.getHeight();
        mat.idt();
        System.out.println(mat);
        mat.translate(dst.pos.x, dst.pos.y, 0f);
        System.out.println(mat);
        mat.scale(scaleX, scaleY, 1f);
        System.out.println(mat);
        mat.translate(-src.pos.x, -src.pos.y, 0f);
        System.out.println(mat);
    }
    public static void calcTransitionMatrix(Matrix3 mat, Rect src, Rect dst) {
        float scaleX = dst.getWidth() / src.getWidth();
        float scaleY = dst.getHeight() / src.getHeight();
        mat.idt().translate(dst.pos.x, dst.pos.y).scale(scaleX, scaleY).translate(-src.pos.x, -src.pos.y);
    }
}
