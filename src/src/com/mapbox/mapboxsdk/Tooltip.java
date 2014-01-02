package com.mapbox.mapboxsdk;

import android.content.Context;
import android.graphics.*;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;

public class Tooltip extends Overlay{

    private OverlayItem item;
    private Paint paint = new Paint();
    private Point point = new Point();
    private String text;
    private MapView mapView;
    private Canvas canvas;

    private boolean visible;

    public Tooltip(Context ctx) {
        this(ctx, null);
    }
    public Tooltip(Context ctx, OverlayItem ot){
        this(ctx, ot, "");
    }
    public Tooltip(Context ctx, OverlayItem ot, String text) {
        super(ctx);
        setItem(ot);
        setText(text);
    }

    @Override
    protected void draw(Canvas canvas, org.osmdroid.views.MapView mapView, boolean shadow) {
        if(this.isVisible()){

            this.mapView = (MapView)mapView;
            this.calculatePoint();
            this.canvas = canvas;
            paint.setColor(Color.WHITE);
            this.setTooltipShape();
            TextPaint tp = new TextPaint();
            tp.setColor(Color.rgb(50, 50, 50));
            tp.setTextAlign(Paint.Align.CENTER);
            tp.setTextSize(40f);
            StaticLayout sl = new StaticLayout(text, tp, 400, Layout.Alignment.ALIGN_NORMAL, 1, 1, false);
            canvas.translate(0,-350);
            sl.draw(canvas);
            canvas.translate(0,350);

        }
    }
    private void setTooltipShape(){
        canvas.drawRect(point.x - 240, point.y - 200, point.x + 240, point.y - 100, paint);
        canvas.save();
        canvas.rotate((float) 45, point.x, point.y - 100);
        canvas.drawRect(point.x - 20, point.y - 120, point.x + 20, point.y - 80, paint);
        canvas.restore();
    }
    private void calculatePoint(){
        GeoPoint markerCoords = item.getPoint();
        MapView.Projection projection = mapView.getProjection();
        projection.toPixels(markerCoords, point);
    }


    // Getters/setters

    /**
     * Sets text to be displayed in the tooltip
     * @param text the text
     */
    public void setText(String text){
        this.text = text;
    }
    /**
     * Sets associated overlay of the tooltip
     * @param item the overlay (normally a Marker object)
     */
    public void setItem(OverlayItem item) {
        this.item = item;
    }

    /**
     * Is the tooltip visible?
     * @return true if it's visible, false otherwise
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Sets visibility of the tooltip
     * @param visible whether it's visible or not
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }


}
