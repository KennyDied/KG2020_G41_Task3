package ru.vsu.cs.kg2020.danila.polygon;

import ru.vsu.cs.kg2020.danila.line.LineDrawer;
import ru.vsu.cs.kg2020.danila.pixel.PixelDrawer;
import ru.vsu.cs.kg2020.danila.point.ScreenPoint;

public class DrawPolygon implements PolygonDrawer {



    @Override
    public void drawPolygon(ScreenPoint center, double r, int n, LineDrawer ld) {
        double da = 2 * Math.PI / n;
        for (int i = 0; i < n; i++) {
            double dx1 = r * Math.cos(da * i);
            double dy1 = r * Math.sin(da * i);
            double dx2 = r * Math.cos(da * (i+ 1));
            double dy2 = r * Math.sin(da * (i+ 1));
            ld.drawLine(new ScreenPoint((int)dx1, (int)dy1), new ScreenPoint((int)dx2, (int)dy2));
        }
    }
}
