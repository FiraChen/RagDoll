package com.example.mydoll;

import java.util.ArrayList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.view.ScaleGestureDetector;


public class Part {
    private Matrix transform;
    private Bitmap bit;

    // parts that connected with the currentp part
    private ArrayList<Part> children;
    private String name;

    // position info
    private float startX;
    private float startY;
    private float iniCenterX;
    private float iniCenterY;
    private float currCenterX;
    private float currCenterY;

    private float currDeg;
    private float maxDeg;

    // rect to use for checking if click is in a certain region
    //  can't get this part accurate enough...
    private RectF region;

    Part(String s, float x, float y, float deg, float centerX, float centerY, RectF rec) {
        name = s;
        startX = x;
        startY = y;
        iniCenterX = centerX;
        iniCenterY = centerY;
        currCenterX = centerX;
        currCenterY = centerY;
        currDeg = 0;
        maxDeg = deg;
        region = rec;

        // initial transformation
        transform = new Matrix();
        transform.postTranslate(startX, startY);

        children = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setBitmap(Bitmap b) {
        bit = b;
    }

    public void addChild(Part child){
        // connect body parts
        children.add(child);
    }


    public void draw(Canvas canvas, Paint paint) {
        for (Part child : children) {
            child.draw(canvas, paint);
        }
        // draw the body in the end to keep it look nicer
        canvas.drawBitmap(bit, transform, paint);
    }

    public void reset() {
        currDeg = 0;
        currCenterX = iniCenterX;
        currCenterY = iniCenterY;

        // clear transformed
        transform = new Matrix();
        transform.postTranslate(startX, startY);

        for (Part child : children) {
            child.reset();
        }
    }

    public boolean checkBoundary(float x, float y){
        // move to the original position
        Matrix initial = new Matrix();
        initial.set(transform);
        initial.invert(initial);

        float[] point = {x, y};
        initial.mapPoints(point);

        // check if the point is contained in the original region
        if (region.contains(point[0] + startX, point[1] + startY)) {
            return true;
        } else {
            return false;
        }
    }

    public void translate(float dx, float dy){
        // update center
        currCenterX += dx;
        currCenterY += dy;

        // move the part
        transform.postTranslate(dx, dy);

        // move all the children at the same time
        for (Part child : children) {
            child.translate(dx, dy);
        }
    }

    public void rotate(float x, float y, float prevX, float prevY) {
        // compute the rotating angle
        double prev_diff = Math.atan2(prevY - currCenterY, prevX - currCenterX);
        double new_diff = Math.atan2(y - currCenterY, x - currCenterX);
        double angle = Math.toDegrees(new_diff - prev_diff);

        // try to fix a certain bug using the following
        if (angle > 180 || angle < -180) {
            return;
        }

        // reach the max, stop rotating
        if ((currDeg == maxDeg && angle > 0) || (currDeg == -maxDeg && angle < 0)) {
            return;
        }

        // compute current angle
        if (currDeg + angle > maxDeg) {
            angle = maxDeg - currDeg;
            currDeg = maxDeg;
        } else if (currDeg + angle < -maxDeg) {
            angle = -(currDeg + maxDeg);
            currDeg = -maxDeg;
        } else {
            currDeg += angle;
        }

        // rotate using the angle
        transform.postTranslate(-currCenterX, -currCenterY);
        transform.postRotate((float) angle);
        transform.postTranslate(currCenterX, currCenterY);

        for (Part child : children) {
            child.rotateUpdate(currCenterX, currCenterY, (float) angle);
        }
    }

    public void rotateUpdate(float centerX, float centerY, float angle){
        // to keep the child consistenly with parent's rotation degree
        transform.postTranslate(-centerX, -centerY);
        transform.postRotate(angle);
        transform.postTranslate(centerX, centerY);

        // to get relative center
        Matrix initalTrans = new Matrix();
        initalTrans.postTranslate(-centerX, -centerY);
        initalTrans.postRotate(angle);
        initalTrans.postTranslate(centerX, centerY);

        // update the center
        float[] point = {currCenterX, currCenterY};
        initalTrans.mapPoints(point);
        currCenterX = point[0];
        currCenterY = point[1];

        for (Part child : children) {
            child.rotateUpdate(centerX, centerY, angle);
        }
    }

    public void scale(ScaleGestureDetector detector) {
        // how much do i need to scale
        float scaleFactor = detector.getScaleFactor();
        // don't change horizontal
        transform.postScale(1, scaleFactor, detector.getFocusX(), detector.getFocusY());

        // scaling only works on legs
        if (name.equals("upperLeg") || name.equals("lowerLeg")) {
            children.get(0).scaleUpdate(transform, startX, startY, scaleFactor);
        }
    }

    public void scaleUpdate(Matrix parentM, float parentX, float parentY, float scaleFactor) {
        // recalculate the position of center
        float[] point = new float[2];
        point[0] = iniCenterX - parentX;
        point[1] = iniCenterY - parentY;
        parentM.mapPoints(point);

        transform.postTranslate(point[0] - currCenterX, point[1] - currCenterY);
        currCenterX = point[0];
        currCenterY = point[1];

        // don't scale on feet
        if (name.equals("lowerLeg")) {
            // if called from upper leg, scale the lower leg too
            transform.postScale(1, scaleFactor, currCenterX, currCenterY);
            children.get(0).scaleUpdate(transform, startX, startY, scaleFactor);
        }
    }
}
