package ru.vsu.cs.kg2020.danila.polygon;

import ru.vsu.cs.kg2020.danila.IFigure.IFigure;
import ru.vsu.cs.kg2020.danila.point.RealPoint;

import java.util.ArrayList;
import java.util.List;

public class Polygon implements IFigure {
    private RealPoint o;    //центр окружности
    private int n;          //кол-во граней
    private double radius;
    List<RealPoint> markers = new ArrayList<>();

    public void setN(int n) {
        this.n = n;
        points(o, radius);
    }

    public Polygon(RealPoint o, double radius, int n) {
        this.o = o;
        this.n = n;
        this.radius = radius;
        points(o, radius);
    }

    @Override
    public void setRadius(Double r) {
        this.radius = r;
        points(o, r);
    }

    @Override
    public RealPoint getCenter() {
        return o;
    }

    @Override
    public void transfer(RealPoint newO){
        this.o = newO;
        points(newO, radius);
    }

    @Override
    public void scale(double r){
        this.radius = r + radius;
    }

    @Override
    public double getRadius() {
        return radius;
    }

    private void points(RealPoint center, Double radius){

        markers.clear();
        double da = 2 * Math.PI / n;
        for (int i = 0; i < n; i++) {
            double dx1 = radius * Math.cos(da * i) + center.getX();
            double dy1 = radius * Math.sin(da * i) + center.getY();
            markers.add(new RealPoint(dx1, dy1));
        }
    }

    @Override
    public boolean checkIfClicked(RealPoint rp){
        double x = rp.getX();
        double y = rp.getY();

        return x >= o.getX() - radius && x <= o.getX() + radius && y >= o.getY() - radius && y <= o.getY() + radius; //вернет true, если клик внутри фигуры
    }


    @Override
    public List<RealPoint> getMarkers() {
        List<RealPoint> points = new ArrayList<>(this.markers);
        return points;
    }
}