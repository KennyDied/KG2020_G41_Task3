package ru.vsu.cs.kg2020.danila.polygon;

import ru.vsu.cs.kg2020.danila.IFigure.IFigure;
import ru.vsu.cs.kg2020.danila.ScreenConverter;
import ru.vsu.cs.kg2020.danila.line.LineDrawer;
import ru.vsu.cs.kg2020.danila.point.RealPoint;
import ru.vsu.cs.kg2020.danila.point.ScreenPoint;

import java.awt.*;
import java.util.List;

public interface PolygonDrawer {
    //void drawPolygon(ScreenPoint center, double r, int n, LineDrawer ld, Color c);
//    void drawPolygon(ScreenPoint[] points, LineDrawer ld, Color c);
    //void drawPolygon(List<RealPoint> points, LineDrawer ld, Color c, ScreenConverter sc);

    void drawPolygon(IFigure f, Color c);

}
