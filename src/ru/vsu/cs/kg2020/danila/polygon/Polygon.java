package ru.vsu.cs.kg2020.danila.polygon;

import ru.vsu.cs.kg2020.danila.IFigure.IFigure;
import ru.vsu.cs.kg2020.danila.ScreenConverter;
import ru.vsu.cs.kg2020.danila.point.RealPoint;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Polygon implements IFigure {
    private RealPoint o;    //центр окружности
    //private RealPoint r;       //радиус
    private int n;          //кол-во граней
    private double radius;
    private double angle;
    List<RealPoint> markers = new ArrayList<>();


    public Polygon(RealPoint o, double radius, int n) {
        this.o = o;
        this.n = n;
        this.radius = radius;
        this.angle = 0;
        points(o, radius);
    }
    @Override
    public void setCenter(RealPoint center) {
        this.o = center;
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



    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }


    @Override
    public double getRadius() {
        return radius;
    }





    public int getN() {
        return n;
    }

    private void points(RealPoint center, Double radius){

        markers.clear();
        double da = 2 * Math.PI / n;
        for (int i = 0; i < n; i++) {
            double dx1 = radius * Math.cos(da * i) + center.getX();
            double dy1 = radius * Math.sin(da * i) + center.getY();
//            double dx2 = radius * Math.cos(da * (i + 1)) + o.getX();
//            double dy2 = radius * Math.sin(da * (i + 1)) + o.getY();
            markers.add(new RealPoint(dx1, dy1));
//            markers.add(new RealPoint(dx2, dy2));
            //ld.drawLine(new ScreenPoint((int)dx1, (int)dy1), new ScreenPoint((int)dx2, (int)dy2), c);
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

    @Override
    public List<RealPoint> markersToEdit(){
        List<RealPoint> points = new ArrayList<>();

        points.add(new RealPoint(o.getX() - radius, o.getY() + radius));
        points.add(new RealPoint(o.getX() + radius, o.getY() + radius));
        points.add(new RealPoint(o.getX() + radius, o.getY() - radius));
        points.add(new RealPoint(o.getX() - radius, o.getY() - radius));


        return points;
    }


}