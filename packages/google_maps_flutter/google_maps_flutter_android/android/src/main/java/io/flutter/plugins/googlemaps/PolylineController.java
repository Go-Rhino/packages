// Copyright 2013 The Flutter Authors
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package io.flutter.plugins.googlemaps;

import com.google.android.gms.maps.model.Cap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import java.util.List;
import java.util.Objects;

/** Controller of a single Polyline on the map. */
class PolylineController implements PolylineOptionsSink {
  private final Polyline polyline;
  private final String googleMapsPolylineId;
  private boolean consumeTapEvents;
  private final float density;

  // Rhino fork: an in-place update re-applies every option, so a polyline animated every frame
  // made eleven native calls per frame where one or two values changed. These mirror the options
  // last applied to the map, and the setters skip values that are already there.
  private int color;
  private Cap endCap;
  private boolean geodesic;
  private int jointType;
  private List<PatternItem> pattern;
  private List<LatLng> points;
  private Cap startCap;
  private boolean visible;
  private float scaledWidth;
  private float zIndex;

  PolylineController(
      Polyline polyline, PolylineOptions options, boolean consumeTapEvents, float density) {
    this.polyline = polyline;
    this.consumeTapEvents = consumeTapEvents;
    this.density = density;
    this.googleMapsPolylineId = polyline.getId();
    // Rhino fork.
    this.color = options.getColor();
    this.endCap = options.getEndCap();
    this.geodesic = options.isGeodesic();
    this.jointType = options.getJointType();
    this.pattern = options.getPattern();
    this.points = options.getPoints();
    this.startCap = options.getStartCap();
    this.visible = options.isVisible();
    this.scaledWidth = options.getWidth();
    this.zIndex = options.getZIndex();
  }

  void remove() {
    polyline.remove();
  }

  @Override
  public void setConsumeTapEvents(boolean consumeTapEvents) {
    if (this.consumeTapEvents != consumeTapEvents) {
      this.consumeTapEvents = consumeTapEvents;
      polyline.setClickable(consumeTapEvents);
    }
  }

  @Override
  public void setColor(int color) {
    if (this.color != color) {
      this.color = color;
      polyline.setColor(color);
    }
  }

  @Override
  public void setEndCap(Cap endCap) {
    if (!Objects.equals(this.endCap, endCap)) {
      this.endCap = endCap;
      polyline.setEndCap(endCap);
    }
  }

  @Override
  public void setGeodesic(boolean geodesic) {
    if (this.geodesic != geodesic) {
      this.geodesic = geodesic;
      polyline.setGeodesic(geodesic);
    }
  }

  @Override
  public void setJointType(int jointType) {
    if (this.jointType != jointType) {
      this.jointType = jointType;
      polyline.setJointType(jointType);
    }
  }

  @Override
  public void setPattern(List<PatternItem> pattern) {
    if (!Objects.equals(this.pattern, pattern)) {
      this.pattern = pattern;
      polyline.setPattern(pattern);
    }
  }

  @Override
  public void setPoints(List<LatLng> points) {
    if (!Objects.equals(this.points, points)) {
      this.points = points;
      polyline.setPoints(points);
    }
  }

  @Override
  public void setStartCap(Cap startCap) {
    if (!Objects.equals(this.startCap, startCap)) {
      this.startCap = startCap;
      polyline.setStartCap(startCap);
    }
  }

  @Override
  public void setVisible(boolean visible) {
    if (this.visible != visible) {
      this.visible = visible;
      polyline.setVisible(visible);
    }
  }

  @Override
  public void setWidth(float width) {
    final float scaledWidth = width * density;
    if (this.scaledWidth != scaledWidth) {
      this.scaledWidth = scaledWidth;
      polyline.setWidth(scaledWidth);
    }
  }

  @Override
  public void setZIndex(float zIndex) {
    if (this.zIndex != zIndex) {
      this.zIndex = zIndex;
      polyline.setZIndex(zIndex);
    }
  }

  String getGoogleMapsPolylineId() {
    return googleMapsPolylineId;
  }

  boolean consumeTapEvents() {
    return consumeTapEvents;
  }
}
