package ru.vsu.cs.kg2020.danila.line;

import ru.vsu.cs.kg2020.danila.point.ScreenPoint;

import java.awt.*;

public interface LineDrawer {
    void drawLine(ScreenPoint p1, ScreenPoint p2, Color c);
}
