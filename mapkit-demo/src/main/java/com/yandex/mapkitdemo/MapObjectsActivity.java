package com.yandex.mapkitdemo;

import static com.google.android.gms.common.util.CollectionUtils.listOf;
import static com.yandex.mapkitdemo.ConstantsUtils.ANIMATED_PLACEMARK_CENTER;
import static com.yandex.mapkitdemo.ConstantsUtils.ANIMATED_RECTANGLE_CENTER;
import static com.yandex.mapkitdemo.ConstantsUtils.CIRCLE_CENTER;
import static com.yandex.mapkitdemo.ConstantsUtils.DEFAULT_POINT;
import static com.yandex.mapkitdemo.ConstantsUtils.DRAGGABLE_PLACEMARK_CENTER;
import static com.yandex.mapkitdemo.ConstantsUtils.POLYLINE_CENTER;
import static com.yandex.mapkitdemo.ConstantsUtils.TRIANGLE_CENTER;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Circle;
import com.yandex.mapkit.geometry.LinearRing;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.geometry.Polygon;
import com.yandex.mapkit.geometry.Polyline;
import com.yandex.mapkit.geometry.PolylineBuilder;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CircleMapObject;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.LineStyle;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.MapObjectDragListener;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.map.PlacemarkAnimation;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.map.PolygonMapObject;
import com.yandex.mapkit.map.PolylineMapObject;
import com.yandex.mapkit.map.RotationType;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.AnimatedImageProvider;
import com.yandex.runtime.image.ImageProvider;
import com.yandex.runtime.ui_view.ViewProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * This example shows how to add simple objects such as polygons, circles and polylines to the map.
 * It also shows how to display images instead.
 */
public class MapObjectsActivity extends Activity {
    private final double OBJECT_SIZE = 0.0015;

    private MapView mapView;
    private MapObjectCollection mapObjects;
    private Handler animationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.map_objects);
        super.onCreate(savedInstanceState);
        mapView = findViewById(R.id.mapview);
        mapView.getMapWindow().getMap().move(new CameraPosition(DEFAULT_POINT, 15.0f, 0.0f, 0.0f));
        mapObjects = mapView.getMapWindow().getMap().getMapObjects().addCollection();
        animationHandler = new Handler(Looper.myLooper());
        createMapObjects();
    }

    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

    private void createMapObjects() {
/*
        AnimatedImageProvider animatedImage = AnimatedImageProvider.fromAsset(this, "animation
        .png");
        ArrayList<Point> rectPoints = new ArrayList<>();
        rectPoints.add(new Point(
                ANIMATED_RECTANGLE_CENTER.getLatitude() - OBJECT_SIZE,
                ANIMATED_RECTANGLE_CENTER.getLongitude() - OBJECT_SIZE));
        rectPoints.add(new Point(
                ANIMATED_RECTANGLE_CENTER.getLatitude() - OBJECT_SIZE,
                ANIMATED_RECTANGLE_CENTER.getLongitude() + OBJECT_SIZE));
        rectPoints.add(new Point(
                ANIMATED_RECTANGLE_CENTER.getLatitude() + OBJECT_SIZE,
                ANIMATED_RECTANGLE_CENTER.getLongitude() + OBJECT_SIZE));
        rectPoints.add(new Point(
                ANIMATED_RECTANGLE_CENTER.getLatitude() + OBJECT_SIZE,
                ANIMATED_RECTANGLE_CENTER.getLongitude() - OBJECT_SIZE));
        PolygonMapObject rect = mapObjects.addPolygon(
                new Polygon(new LinearRing(rectPoints), new ArrayList<LinearRing>()));
        rect.setStrokeColor(Color.TRANSPARENT);
        rect.setFillColor(Color.TRANSPARENT);
        rect.setPattern(animatedImage, 1.f);

        ArrayList<Point> trianglePoints = new ArrayList<>();
        trianglePoints.add(new Point(
                TRIANGLE_CENTER.getLatitude() + OBJECT_SIZE,
                TRIANGLE_CENTER.getLongitude() - OBJECT_SIZE));
        trianglePoints.add(new Point(
                TRIANGLE_CENTER.getLatitude() - OBJECT_SIZE,
                TRIANGLE_CENTER.getLongitude() - OBJECT_SIZE));
        trianglePoints.add(new Point(
                TRIANGLE_CENTER.getLatitude(),
                TRIANGLE_CENTER.getLongitude() + OBJECT_SIZE));
        PolygonMapObject triangle = mapObjects.addPolygon(
                new Polygon(new LinearRing(trianglePoints), new ArrayList<LinearRing>()));
        triangle.setFillColor(Color.BLUE);
        triangle.setStrokeColor(Color.BLACK);
        triangle.setStrokeWidth(1.0f);
        triangle.setZIndex(100.0f);

        createTappableCircle();
 */
//        createPlacemarkMapObjectWithViewProvider();
//        createAnimatedPlacemark();
//        createPlacemark();
        createPolyline();
    }

    private void createPlacemark() {
        PlacemarkMapObject mark = mapObjects.addPlacemark();
        mark.setGeometry(DRAGGABLE_PLACEMARK_CENTER);
        mark.setOpacity(0.1f);
        mark.setIcon(ImageProvider.fromResource(this, R.drawable.mark));
        mark.setDraggable(true);
        mark.setZIndex(2);
        Glide.with(this)
                .asBitmap()
                .load("https://www.iconarchive.com/download/i103472/paomedia/small-n-flat/sign-delete.48.png")
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, Transition<? super Bitmap> transition) {
                        ImageProvider imageProvider = ImageProvider.fromBitmap(resource);
                        PlacemarkMapObject placemark = mapObjects.addPlacemark(DRAGGABLE_PLACEMARK_CENTER, imageProvider);
                        // Optionally customize the placemark here
                        placemark.setOpacity(1.0f);
                        placemark.setZIndex(1);
                        var is = new IconStyle();
                        is.setScale(10.0f);
                        is.setRotationType(RotationType.ROTATE);
                        placemark.setIconStyle(is);
                        placemark.setDirection(90);
//                        placemark.setScaleFunction();
                    }
//
                    @Override
                    public void onLoadCleared(android.graphics.drawable.Drawable placeholder) {
                        // Handle if needed
                    }
                });
    }

    private void createPolyline() {
        var delta = OBJECT_SIZE * 2;
        var center = new Point(DEFAULT_POINT.getLatitude() + 2 * delta, DEFAULT_POINT.getLongitude());
        ArrayList<Point> polylinePoints = new ArrayList<>();
        polylinePoints.add(new Point(center.getLatitude() + 1 * delta, center.getLongitude() - 2 * delta));
        polylinePoints.add(new Point(center.getLatitude() + 1 * delta, center.getLongitude() + 2 * delta));
        polylinePoints.add(new Point(center.getLatitude() - 0 * delta, center.getLongitude() - 2 * delta));
        polylinePoints.add(new Point(center.getLatitude() - 0 * delta, center.getLongitude() + 2 * delta));
        PolylineMapObject polyline = mapObjects.addPolyline(new Polyline(polylinePoints));
        polyline.setStrokeColor(Color.YELLOW);
        var ls = new LineStyle();
        ls.setStrokeWidth(20);
        ls.setOutlineWidth(10);
        ls.setInnerOutlineEnabled(true);
        ls.setOutlineColor(Color.GREEN);
        ls.setArcApproximationStep(1);
        ls.setTurnRadius(00);
//        ls.setDashLength(50);
//        ls.setGapLength(20);
//        ls.setDashOffset(300);
        polyline.setStyle(ls);

    }

    private void createPolygon() {
        var points = new ArrayList<Point>();
        var delta = OBJECT_SIZE * 2;
        var center = new Point(DEFAULT_POINT.getLatitude() - 0 * delta, DEFAULT_POINT.getLongitude());
        points.add(new Point(center.getLatitude() + 0 * delta, center.getLongitude() - 2 * delta));
        points.add(new Point(center.getLatitude() + 0 * delta, center.getLongitude() + 2 * delta));
        points.add(new Point(center.getLatitude() - 0.5 * delta, center.getLongitude() + 2 * delta));
        points.add(new Point(center.getLatitude() - 0.5 * delta, center.getLongitude() - 2 * delta));
        var polygon = mapObjects.addPolygon(new Polygon(new LinearRing(points), Collections.emptyList()));
        polygon.setStrokeColor(Color.GREEN);
        polygon.setFillColor(Color.YELLOW);
        polygon.setStrokeWidth(10);

    }

    // Strong reference to the listener.
    private MapObjectTapListener circleMapObjectTapListener = new MapObjectTapListener() {
        @Override
        public boolean onMapObjectTap(MapObject mapObject, Point point) {
            if (mapObject instanceof CircleMapObject) {
                CircleMapObject circle = (CircleMapObject) mapObject;

                float randomRadius = 100.0f + 50.0f * new Random().nextFloat();

                Circle curGeometry = circle.getGeometry();
                Circle newGeometry = new Circle(curGeometry.getCenter(), randomRadius);
                circle.setGeometry(newGeometry);

                Object userData = circle.getUserData();
                if (userData instanceof CircleMapObjectUserData) {
                    CircleMapObjectUserData circleUserData = (CircleMapObjectUserData) userData;

                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Circle with id " + circleUserData.id + " " + "and description '" + circleUserData.description + "' tapped", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            return true;
        }
    };

    private class CircleMapObjectUserData {
        final int id;
        final String description;

        CircleMapObjectUserData(int id, String description) {
            this.id = id;
            this.description = description;
        }
    }

    private void createTappableCircle() {
        CircleMapObject circle = mapObjects.addCircle(new Circle(CIRCLE_CENTER, 100));
        circle.setStrokeColor(Color.GREEN);
        circle.setStrokeWidth(2.f);
        circle.setFillColor(Color.RED);
        circle.setZIndex(100.f);
        circle.setUserData(new CircleMapObjectUserData(42, "Tappable circle"));

        // Client code must retain strong reference to the listener.
        circle.addTapListener(circleMapObjectTapListener);
    }

    private void createPlacemarkMapObjectWithViewProvider() {
        final TextView textView = new TextView(this);
        final int[] colors = new int[]{Color.RED, Color.GREEN, Color.BLACK};
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(params);

        textView.setTextColor(Color.RED);
        textView.setText("Hello, World!");

        final ViewProvider viewProvider = new ViewProvider(textView);
        final PlacemarkMapObject viewPlacemark = mapObjects.addPlacemark();
        viewPlacemark.setGeometry(new Point(59.946263, 30.315181));
        viewPlacemark.setView(viewProvider);

        final Random random = new Random();
        final int delayToShowInitialText = 5000;  // milliseconds
        final int delayToShowRandomText = 500; // milliseconds;

        // Show initial text `delayToShowInitialText` milliseconds and then
        // randomly change text in textView every `delayToShowRandomText` milliseconds
        animationHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                final int randomInt = random.nextInt(1000);
                textView.setText("Some text version " + randomInt);
                textView.setTextColor(colors[randomInt % colors.length]);
                viewProvider.snapshot();
                viewPlacemark.setView(viewProvider);
                animationHandler.postDelayed(this, delayToShowRandomText);
            }
        }, delayToShowInitialText);
    }

    private void createAnimatedPlacemark() {
        final AnimatedImageProvider imageProvider = AnimatedImageProvider.fromAsset(this, "animation.png");

        mapObjects.addPlacemark(placemark -> {
            placemark.setGeometry(ANIMATED_PLACEMARK_CENTER);
            final PlacemarkAnimation animatedIcon = placemark.useAnimation();
            animatedIcon.setIcon(imageProvider, new IconStyle(), animatedIcon::play);
        });
    }
}
