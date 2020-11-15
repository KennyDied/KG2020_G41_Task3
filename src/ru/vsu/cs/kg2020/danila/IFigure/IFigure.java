package ru.vsu.cs.kg2020.danila.IFigure;

import ru.vsu.cs.kg2020.danila.point.RealPoint;

import java.util.List;

public interface IFigure {
    List<RealPoint> getMarkers();
    boolean checkIfClicked(RealPoint rp);
    void setRadius(Double r);
    void transfer(RealPoint to);
    RealPoint getCenter();
    void scale(double r);
    double getRadius();
}
