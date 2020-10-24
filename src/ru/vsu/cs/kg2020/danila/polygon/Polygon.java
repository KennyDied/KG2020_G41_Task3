package ru.vsu.cs.kg2020.danila.polygon;

import ru.vsu.cs.kg2020.danila.point.RealPoint;

public class Polygon {
    private RealPoint o;    //центр окружности
    private double r;       //радиус
    private int n;          //кол-во граней
    private double angle;

    public Polygon(RealPoint o, double r, int n, double angle) {
        this.o = o;
        this.r = r;
        this.n = n;
        this.angle = angle;
    }

    public Polygon(double x1, double y1, double r, int n, double angle){
        o = new RealPoint(x1, y1);
        this.r = r;
        this.n = n;
        this.angle = angle;
    }
}