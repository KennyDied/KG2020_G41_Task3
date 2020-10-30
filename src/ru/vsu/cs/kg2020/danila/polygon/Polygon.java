package ru.vsu.cs.kg2020.danila.polygon;

import ru.vsu.cs.kg2020.danila.ScreenConverter;
import ru.vsu.cs.kg2020.danila.point.RealPoint;
import ru.vsu.cs.kg2020.danila.point.ScreenPoint;

public class Polygon {
    private RealPoint o;    //центр окружности
    //private RealPoint r;       //радиус
    private int n;          //кол-во граней
    private double radius;

    public Polygon(RealPoint o, double radius, int n) {
        this.o = o;

        this.n = n;
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public RealPoint getO() {
        return o;
    }

    public void setO(RealPoint o) {
        this.o = o;
    }



    public void setR(RealPoint r) {

        this.radius =  Math.sqrt(Math.pow((r.getX() - o.getX()), 2) + Math.pow((r.getY() - o.getY()), 2));
    }

    public void transfer(RealPoint newO){
        this.o = newO;

    }


    public boolean checkIfClicked(ScreenPoint sp, ScreenConverter sc){
        RealPoint rp = sc.s2r(sp);
        double x = rp.getX();
        double y = rp.getY();
        //double radius =  Math.sqrt(Math.pow((r.getX() - o.getX()), 2) + Math.pow((r.getY() - o.getY()), 2));
//        double x = sp.getX();
//        double y = sp.getY();
//        ScreenPoint o1 = sc.r2s(o);

        return x >= o.getX() - radius && x <= o.getX() + radius && y >= o.getY() - radius && y <= o.getY() + radius; //вернет true, если клик внутри фигуры
    }
}