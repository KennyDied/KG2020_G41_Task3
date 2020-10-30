package ru.vsu.cs.kg2020.danila.polygon;

import ru.vsu.cs.kg2020.danila.line.LineDrawer;
import ru.vsu.cs.kg2020.danila.pixel.PixelDrawer;
import ru.vsu.cs.kg2020.danila.point.ScreenPoint;

public class DrawPolygon implements PolygonDrawer {



    @Override
    public void drawPolygon(ScreenPoint center, double r, int n, LineDrawer ld) {

        //double r = Math.sqrt(Math.pow((radius.getX() - center.getX()), 2) + Math.pow((radius.getY() - center.getY()), 2));

        double da = 2 * Math.PI / n;


        for (int i = 0; i < n; i++) {
            double dx1 = r * Math.cos(da * i) + center.getX();
            double dy1 = r * Math.sin(da * i) + center.getY();
            double dx2 = r * Math.cos(da * (i + 1)) + center.getX();
            double dy2 = r * Math.sin(da * (i + 1)) + center.getY();
            ld.drawLine(new ScreenPoint((int)dx1, (int)dy1), new ScreenPoint((int)dx2, (int)dy2));
        }
    }
}
