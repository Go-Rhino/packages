// Copyright 2013 The Flutter Authors
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package io.flutter.plugins.googlemaps;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import android.graphics.Color;
import com.google.android.gms.internal.maps.zzar;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.mockito.Mockito;

public class PolylineControllerTest {

  @Test
  public void controller_SetsStrokeDensity() {
    final zzar z = mock(zzar.class);
    final Polyline polyline = spy(new Polyline(z));

    final float density = 5;
    final float strokeWidth = 3;
    final PolylineController controller =
        new PolylineController(polyline, new PolylineOptions(), false, density);
    controller.setWidth(strokeWidth);

    Mockito.verify(polyline).setWidth(density * strokeWidth);
  }

  // Rhino fork.
  @Test
  public void controller_ReappliesOnlyChangedOptions() {
    final Polyline polyline = spy(new Polyline(mock(zzar.class)));
    final float density = 2;
    final List<LatLng> points = Arrays.asList(new LatLng(1, 2), new LatLng(3, 4));
    final PolylineBuilder builder = new PolylineBuilder(density);
    applyOptions(builder, Color.RED, points);
    final PolylineController controller =
        new PolylineController(polyline, builder.build(), builder.consumeTapEvents(), density);

    final List<LatLng> grownPoints =
        Arrays.asList(new LatLng(1, 2), new LatLng(3, 4), new LatLng(5, 6));
    applyOptions(controller, Color.RED, grownPoints);
    applyOptions(controller, Color.BLUE, new ArrayList<>(grownPoints));

    Mockito.verify(polyline).getId();
    Mockito.verify(polyline).setPoints(grownPoints);
    Mockito.verify(polyline).setColor(Color.BLUE);
    Mockito.verifyNoMoreInteractions(polyline);
  }

  /** Sets every option, with new instances each time, as an in-place update does. */
  private static void applyOptions(PolylineOptionsSink sink, int color, List<LatLng> points) {
    sink.setConsumeTapEvents(true);
    sink.setColor(color);
    sink.setEndCap(new RoundCap());
    sink.setStartCap(new RoundCap());
    sink.setGeodesic(true);
    sink.setJointType(JointType.ROUND);
    sink.setVisible(true);
    sink.setWidth(4);
    sink.setZIndex(1);
    sink.setPoints(points);
    sink.setPattern(Arrays.asList(new Dash(8), new Gap(4)));
  }
}
