package com.example.mydoll;

import android.content.Context;
import android.graphics.RectF;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ScaleGestureDetector;
import android.view.MotionEvent;
import java.util.ArrayList;

public class DrawingView extends View implements View.OnTouchListener {
    Context mycontext;
    Paint mypaint;

    // all the parts needed
    ArrayList<Part> parts;
    Part head;
    Part torso;
    Part upperArmR;
    Part lowerArmR;
    Part upperArmL;
    Part lowerArmL;
    Part upperLegR;
    Part lowerLegR;
    Part upperLegL;
    Part lowerLegL;
    Part rightHand;
    Part leftHand;
    Part rightfoot;
    Part leftfoot;

    // record previous click
    float prevX;
    float prevY;

    // use for scaling
    Part currentP;
    Part currentP2;
    // handle scaling
    private ScaleGestureDetector sd;

    public DrawingView(Context context) {
        super(context);

        // a useless paint
        mycontext = context;
        mypaint = new Paint();
        mypaint.setColor(Color.BLACK);
        mypaint.setStrokeWidth(1);
        mypaint.setStyle(Paint.Style.FILL);

        // set controll listeners
        this.setOnTouchListener(this);
        sd = new ScaleGestureDetector(context, new ScaleListener());
        drawDoll();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw all the parts
        torso.draw(canvas, mypaint);
        head.draw(canvas, mypaint);
    }

    // reset the position and gesture of the doll
    public void reset() {
        torso.reset();
        invalidate();
    }

    public void drawDoll() {
        // store all the parts here
        parts = new ArrayList<>();

        // initialize all the parts

        RectF headRec = new RectF(424, 61, 1124, 415);
        head = new Part("head", 390, 0, 50, 751, 434, headRec);

        RectF torsoRec = new RectF(510, 438, 1019, 714);
        torso = new Part("torso", 450, 320, 0, 1, 1, torsoRec);

        RectF upperArmRRec = new RectF(1000, 426, 1183, 636);
        upperArmR = new Part("upperArm", 940, 380, 360, 996, 481, upperArmRRec);

        RectF lowerArmRRec = new RectF(1066, 652, 1156,710);
        lowerArmR = new Part("lowerArm", 1055, 630, 135, 1109, 648, lowerArmRRec);

        RectF upperArmLRec = new RectF(351, 438, 491, 687);
        upperArmL = new Part("upperArm", 325, 390, 360, 502, 512, upperArmLRec);

        RectF lowerArmLRec = new RectF(370, 648, 444, 741);
        lowerArmL = new Part("lowerArm", 350, 640, 135, 420, 671, lowerArmLRec);

        RectF upperLegRRec = new RectF(778, 730, 1008,913);
        upperLegR = new Part("upperLeg", 740, 590, 90, 907, 738, upperLegRRec);

        RectF lowerLegRRec = new RectF(887, 948, 961, 1045);
        lowerLegR = new Part("lowerLeg", 880, 930, 90, 926, 955, lowerLegRRec);

        RectF upperLegLRec = new RectF(510, 749, 786, 928);
        upperLegL = new Part("upperLeg", 460, 600, 90, 638, 726, upperLegLRec);

        RectF lowerLegLRec = new RectF(603, 940, 700, 1045);
        lowerLegL = new Part("lowerLeg", 600, 940, 90, 658, 975, lowerLegLRec);

        RectF rightHandRec = new RectF(1058, 722, 1144, 773);
        rightHand = new Part("hand", 1055, 700, 35, 1133, 722, rightHandRec);

        RectF leftHandRec = new RectF(311, 726, 428, 811);
        leftHand = new Part("hand", 310, 705, 35, 393, 741, leftHandRec);

        RectF rightfootRec = new RectF(895, 1041, 1047, 1091);
        rightfoot = new Part("foot", 880, 1000, 35, 942, 1053, rightfootRec);

        RectF leftfootRec = new RectF(568, 1064, 712, 1099);
        leftfoot = new Part("foot", 550, 1010, 35, 662, 1060, leftfootRec);

        parts.add(head);
        parts.add(torso);
        parts.add(upperArmR);
        parts.add(lowerArmR);
        parts.add(upperArmL);
        parts.add(lowerArmL);
        parts.add(upperLegR);
        parts.add(lowerLegR);
        parts.add(upperLegL);
        parts.add(lowerLegL);
        parts.add(rightHand);
        parts.add(leftHand);
        parts.add(rightfoot);
        parts.add(leftfoot);

        // connected all the parts
        torso.addChild(head);
        torso.addChild(upperArmR);
        torso.addChild(upperArmL);
        torso.addChild(upperLegR);
        torso.addChild(upperLegL);

        upperArmR.addChild(lowerArmR);
        upperArmL.addChild(lowerArmL);
        lowerArmR.addChild(rightHand);
        lowerArmL.addChild(leftHand);

        upperLegR.addChild(lowerLegR);
        upperLegL.addChild(lowerLegL);
        lowerLegR.addChild(rightfoot);
        lowerLegL.addChild(leftfoot);

        setDrawable();
        reset();
    }

    private void setDrawable() {
        Bitmap upperArmLBit = BitmapFactory.decodeResource(mycontext.getResources(), R.drawable.upperarml);
        upperArmLBit = Bitmap.createScaledBitmap(upperArmLBit, (int)(upperArmLBit.getWidth()*0.7), (int)(upperArmLBit.getHeight()*0.7), false);
        Bitmap upperArmRBit = BitmapFactory.decodeResource(mycontext.getResources(), R.drawable.upperarmr);
        upperArmRBit = Bitmap.createScaledBitmap(upperArmRBit, (int)(upperArmRBit.getWidth()*0.7), (int)(upperArmRBit.getHeight()*0.7), false);
        Bitmap lowerArmLBit = BitmapFactory.decodeResource(mycontext.getResources(), R.drawable.lowerarml);
        lowerArmLBit = Bitmap.createScaledBitmap(lowerArmLBit, (int)(lowerArmLBit.getWidth()*0.7), (int)(lowerArmLBit.getHeight()*0.7), false);
        Bitmap lowerArmRBit = BitmapFactory.decodeResource(mycontext.getResources(), R.drawable.lowerarmr);
        lowerArmRBit = Bitmap.createScaledBitmap(lowerArmRBit, (int)(lowerArmRBit.getWidth()*0.7), (int)(lowerArmRBit.getHeight()*0.7), false);
        Bitmap handLBit = BitmapFactory.decodeResource(mycontext.getResources(), R.drawable.handl);
        handLBit = Bitmap.createScaledBitmap(handLBit, (int)(handLBit.getWidth()*0.7), (int)(handLBit.getHeight()*0.7), false);
        Bitmap handRBit = BitmapFactory.decodeResource(mycontext.getResources(), R.drawable.handr);
        handRBit = Bitmap.createScaledBitmap(handRBit, (int)(handRBit.getWidth()*0.7), (int)(handRBit.getHeight()*0.7), false);

        Bitmap upperLegLBit = BitmapFactory.decodeResource(mycontext.getResources(), R.drawable.upperlegl);
        upperLegLBit = Bitmap.createScaledBitmap(upperLegLBit, (int)(upperLegLBit.getWidth()*0.7), (int)(upperLegLBit.getHeight()*0.7), false);
        Bitmap upperLegRBit = BitmapFactory.decodeResource(mycontext.getResources(), R.drawable.upperlegr);
        upperLegRBit = Bitmap.createScaledBitmap(upperLegRBit, (int)(upperLegRBit.getWidth()*0.7), (int)(upperLegRBit.getHeight()*0.7), false);
        Bitmap lowerLegLBit = BitmapFactory.decodeResource(mycontext.getResources(), R.drawable.lowerlegl);
        lowerLegLBit = Bitmap.createScaledBitmap(lowerLegLBit, (int)(lowerLegLBit.getWidth()*0.7), (int)(lowerLegLBit.getHeight()*0.7), false);
        Bitmap lowerLegRBit = BitmapFactory.decodeResource(mycontext.getResources(), R.drawable.lowerlegr);
        lowerLegRBit = Bitmap.createScaledBitmap(lowerLegRBit, (int)(lowerLegRBit.getWidth()*0.7), (int)(lowerLegRBit.getHeight()*0.7), false);
        Bitmap footLBit = BitmapFactory.decodeResource(mycontext.getResources(), R.drawable.footl);
        footLBit = Bitmap.createScaledBitmap(footLBit, (int)(footLBit.getWidth()*0.7), (int)(footLBit.getHeight()*0.7), false);
        Bitmap footRBit = BitmapFactory.decodeResource(mycontext.getResources(), R.drawable.footr);
        footRBit = Bitmap.createScaledBitmap(footRBit, (int)(footRBit.getWidth()*0.7), (int)(footRBit.getHeight()*0.7), false);

        Bitmap torsoBit = BitmapFactory.decodeResource(mycontext.getResources(), R.drawable.body);
        torsoBit = Bitmap.createScaledBitmap(torsoBit, (int)(torsoBit.getWidth()*0.7), (int)(torsoBit.getHeight()*0.7), false);
        Bitmap headBit = BitmapFactory.decodeResource(mycontext.getResources(), R.drawable.head);
        headBit = Bitmap.createScaledBitmap(headBit, (int)(headBit.getWidth()*0.7), (int)(headBit.getHeight()*0.7), false);

        upperArmL.setBitmap(upperArmLBit);
        upperArmR.setBitmap(upperArmRBit);
        lowerArmL.setBitmap(lowerArmLBit);
        lowerArmR.setBitmap(lowerArmRBit);

        upperLegL.setBitmap(upperLegLBit);
        upperLegR.setBitmap(upperLegRBit);
        lowerLegL.setBitmap(lowerLegLBit);
        lowerLegR.setBitmap(lowerLegRBit);

        leftHand.setBitmap(handLBit);
        rightHand.setBitmap(handRBit);
        leftfoot.setBitmap(footLBit);
        rightfoot.setBitmap(footRBit);

        torso.setBitmap(torsoBit);
        head.setBitmap(headBit);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        float x = event.getX();
        float y = event.getY();
        //System.out.println("x: " + x +"y: " + y);

        // get help with "& MotionEvent.ACTION_MASK" online
        switch(event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                for (Part part : parts) {
                    // find out which body region is it
                    if (part.checkBoundary(x, y)) {
                        currentP = part;
                        sd.onTouchEvent(event);
                        break;
                    }
                }
                prevX = x;
                prevY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                if (currentP == null) {
                    return true;
                }
                // torso is for translating
                if (currentP.getName().equals("torso")) {
                    currentP.translate(x - prevX, y - prevY);
                } else {
                    // other parts are for rotating
                    currentP.rotate(x, y, prevX, prevY);
                }
                invalidate();
                prevX = x;
                prevY = y;
                break;
            case MotionEvent.ACTION_UP:
                currentP = null;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (currentP == null) {
                    return true;
                }
                for (Part part : parts) {
                    if (part.checkBoundary(x, y)) {
                        currentP2 = part;
                        break;
                    }
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                currentP2 = null;
                this.setOnTouchListener(this);
                break;

        }
        // if both fingers are on legs, we can scale
        if (currentP != null && currentP2 != null) {
            if ((currentP2 == currentP) &&
                    (currentP.getName().equals("upperLeg") || currentP.getName().equals("lowerLeg"))) {
                sd.onTouchEvent(event);
            }
        }
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            currentP.scale(detector);
            invalidate();
            return true;
        }
    }
}
