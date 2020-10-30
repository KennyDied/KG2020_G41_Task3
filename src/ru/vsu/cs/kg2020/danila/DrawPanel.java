package ru.vsu.cs.kg2020.danila;

import ru.vsu.cs.kg2020.danila.line.DDALineDrawer;
import ru.vsu.cs.kg2020.danila.line.Line;
import ru.vsu.cs.kg2020.danila.line.LineDrawer;
import ru.vsu.cs.kg2020.danila.pixel.BufferedImagePixelDrawer;
import ru.vsu.cs.kg2020.danila.pixel.PixelDrawer;
import ru.vsu.cs.kg2020.danila.point.RealPoint;
import ru.vsu.cs.kg2020.danila.point.ScreenPoint;
import ru.vsu.cs.kg2020.danila.polygon.DrawPolygon;
import ru.vsu.cs.kg2020.danila.polygon.PolygonDrawer;
import ru.vsu.cs.kg2020.danila.polygon.Polygon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DrawPanel extends JPanel  implements MouseListener, MouseMotionListener, MouseWheelListener {
    public DrawPanel() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
    }

    private ArrayList<Line> lines = new ArrayList<>();
    private ScreenConverter sc = new ScreenConverter(-2, 2, 4, 4, 800, 600);
    private ArrayList<Polygon> allPolygonsList = new ArrayList<>();
    private Polygon editPolygon = null;
    private Polygon currentPolygon = null;
    private Line xAxis = new Line(0, -1, 0, 1);
    private Line yAxis = new Line(-1, 0, 1, 0);
    //private int n = 6;

    @Override
    public void paint(Graphics g){
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_BGR);
        sc.setScreenW(getWidth());
        sc.setScreenH(getHeight());
        Graphics gr = bi.createGraphics();
        gr.setColor(Color.WHITE);
        gr.fillRect(0, 0, getWidth(), getHeight());
        PixelDrawer pd = new BufferedImagePixelDrawer(bi);
        LineDrawer ld = new DDALineDrawer(pd);
        PolygonDrawer dp = new DrawPolygon();

        drawLine(ld, xAxis);
        drawLine(ld, yAxis);

        if(currentPolygon != null){
            //drawLine(ld, currentLine);
            drawPolygon(ld, currentPolygon, dp);
        }
//        for (int i = 0; i < allPolygonsList.size(); i++) {
//            drawPolygon(ld, allPolygonsList.get(i), lines.get(i).getP2(), dp);
//        }
        for(Polygon p : allPolygonsList) {
            //drawLine(ld, l);
            drawPolygon(ld, p, dp);
        }




        //dp.drawPolygon(new ScreenPoint(500, 600), 200, 2000, ld);
        gr.dispose();

        g.drawImage(bi,0,0,null);
    }

    private void drawLine(LineDrawer ld, Line l){
        ld.drawLine(sc.r2s(l.getP1()), sc.r2s(l.getP2()));
    }
    private void drawPolygon(LineDrawer ld, Polygon p, PolygonDrawer pd){
        //System.out.println( p.getRadius());

        //double r = Math.sqrt(Math.pow((sc.r2s(p.getR()).getX() - sc.r2s(p.getO()).getX()), 2) + Math.pow((sc.r2s(p.getR()).getY() - sc.r2s(p.getO()).getY()), 2));
        //System.out.println(r);
        //System.out.println(r/p.getRadius());
        pd.drawPolygon(sc.r2s(p.getO()), sc.numberR2s(p.getRadius()), 8, ld);
    }




    private ScreenPoint prevDrag;
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON3){

            for (Polygon p : allPolygonsList) {
                if (p.checkIfClicked(new ScreenPoint(e.getX(), e.getY()), sc)){
                    editPolygon = p;
                }
            }
        }

    }


    private Line currentLine = null;
    private boolean transfer = false;
    private boolean scale = false;
    @Override
    public void mousePressed(MouseEvent e) {
        if (editPolygon == null) {
            if (e.getButton() == MouseEvent.BUTTON3) {
                prevDrag = new ScreenPoint(e.getX(), e.getY());
            } else if (e.getButton() == MouseEvent.BUTTON1) {
                currentLine = new Line(sc.s2r(new ScreenPoint(e.getX(), e.getY())), sc.s2r(new ScreenPoint(e.getX(), e.getY())));
                currentPolygon = new Polygon(currentLine.getP1(), 0, 8);
            }

        } else {
            if (e.getButton() == MouseEvent.BUTTON3) {
                transfer = true;
                //prevDrag = new ScreenPoint(e.getX(), e.getY());
            } else if (e.getButton() == MouseEvent.BUTTON1){
                scale = true;
            }
        }
        repaint();
    }


    @Override
    public void mouseReleased(MouseEvent e) {

        if (e.getButton() == MouseEvent.BUTTON3) {
            prevDrag = null;
            editPolygon = null;
            transfer = false;
        }
        else if(e.getButton() == MouseEvent.BUTTON1){
            lines.add(currentLine);
            allPolygonsList.add(currentPolygon);
            currentLine = null;
            currentPolygon = null;
            scale = false;
            editPolygon = null;

        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        ScreenPoint current = new ScreenPoint(e.getX(), e.getY());
        if (editPolygon != null) {
            if (scale){
                editPolygon.setR(sc.s2r(current)); //работает - scale
            }
            if (transfer){
                editPolygon.transfer(sc.s2r(current)); // работает - перемещает
            }

            currentPolygon = editPolygon;
        }
        if (prevDrag != null) {
            ScreenPoint delta = new ScreenPoint(current.getX() - prevDrag.getX(), current.getY() - prevDrag.getY());
            RealPoint deltaReal = sc.s2r(delta);
            RealPoint zeroReal = sc.s2r(new ScreenPoint(0,0));
            RealPoint vector = new RealPoint(deltaReal.getX() - zeroReal.getX(), deltaReal.getY() - zeroReal.getY());



            sc.setX(sc.getX() - vector.getX());
            sc.setY(sc.getY() - vector.getY());


            prevDrag = current;

        }
        if (currentLine != null){
            currentLine.setP2(sc.s2r(current));
            currentPolygon.setR(currentLine.getP2());
            //currentPolygon.setR(sc.s2r(current));
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int clicks = e.getWheelRotation();
        double scale = 1;
        double coef = clicks > 0 ? 0.9 : 1.1;
        for (int i = 0; i < Math.abs(clicks); i++) {
            scale *= coef;
        }
        sc.setW(sc.getW() * scale);
        sc.setH(sc.getH() * scale);
        repaint();
    }
}