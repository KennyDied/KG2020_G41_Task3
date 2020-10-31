package ru.vsu.cs.kg2020.danila.polygon;

import ru.vsu.cs.kg2020.danila.line.LineDrawer;
import ru.vsu.cs.kg2020.danila.point.ScreenPoint;

import java.awt.*;

public interface PolygonDrawer {
    void drawPolygon(ScreenPoint center, double r, int n, LineDrawer ld, Color c);
}
