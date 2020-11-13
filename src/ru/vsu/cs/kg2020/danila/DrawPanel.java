package ru.vsu.cs.kg2020.danila;

import ru.vsu.cs.kg2020.danila.IFigure.IFigure;
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
import java.util.List;

import static java.awt.Color.BLACK;
import static java.awt.Color.black;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {


    private ArrayList<Line> lines = new ArrayList<>();
    private ScreenConverter sc = new ScreenConverter(-2, 2, 4, 4, 800, 600);
    private ArrayList<IFigure> allFiguresList = new ArrayList<>();
    private IFigure editFigure = null;
    private IFigure currentFigure = null;
    private Line xAxis = new Line(0, -1, 0, 1);
    private Line yAxis = new Line(-1, 0, 1, 0);
    private int numOfSides = 0;

    public DrawPanel() {
        this.setFocusable(true);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
        this.addKeyListener(this);


    }


    @Override
    public void paint(Graphics g) {
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_BGR);
        sc.setScreenW(getWidth());
        sc.setScreenH(getHeight());
        Graphics gr = bi.createGraphics();
        gr.setColor(Color.WHITE);
        gr.fillRect(0, 0, getWidth(), getHeight());
        PixelDrawer pd = new BufferedImagePixelDrawer(bi);
        LineDrawer ld = new DDALineDrawer(pd);
        PolygonDrawer dp = new DrawPolygon(ld, sc);


        drawLine(ld, xAxis);
        drawLine(ld, yAxis);
        //if (numOfSides != 0) {
        if (currentFigure != null) {
            drawPolygon(currentFigure, dp);
            //currentPolygon.draw(sc, dp, ld, Color.red);
        }

        for (IFigure p : allFiguresList) {
            drawPolygon(p, dp);
            //p.draw(sc, dp, ld, Color.black);
        }
        //}
        if (editFigure != null) {

            drawMarkers((Graphics2D) gr);
        }

        //dp.drawPolygon(new ScreenPoint(500, 600), 200, 2000, ld);
        gr.dispose();

        g.drawImage(bi, 0, 0, null);
    }

    private void drawLine(LineDrawer ld, Line l) {
        ld.drawLine(sc.r2s(l.getP1()), sc.r2s(l.getP2()), new Color(0x000000));
    }


    private void drawPolygon(IFigure p, PolygonDrawer pd) {
        pd.drawPolygon(p, black);
    }


    private ScreenPoint prevDrag;

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {

            for (IFigure p : allFiguresList) {
                if (p.checkIfClicked(sc.s2r(new ScreenPoint(e.getX(), e.getY())))) {
                    editFigure = p;

                }
            }
        }
        if (e.getButton() == MouseEvent.BUTTON1) {
            boolean isNotCorrectNumOfSides = true;
            do {
                String result = JOptionPane.showInputDialog(
                        DrawPanel.this,
                        "Введите количество сторон");
                try {
                    numOfSides = Integer.parseInt(result);
                    if (numOfSides > 2) {
                        isNotCorrectNumOfSides = false;

                    } else {
                        isNotCorrectNumOfSides = true;
                        JOptionPane.showMessageDialog(DrawPanel.this, "Введите числов больше 2!");
                    }
                } catch (NumberFormatException exception) {
                    if (result == null) {
                        isNotCorrectNumOfSides = false;
                        if (numOfSides < 2) {
                            numOfSides = 0;
                        }


                    } else {
                        JOptionPane.showMessageDialog(DrawPanel.this, "Некоректный ввод!");

                    }
                }


            } while (isNotCorrectNumOfSides);

        }

    }


    private Line currentLine = null;
    private boolean transfer = false;
    private boolean scale = false;

    @Override
    public void mousePressed(MouseEvent e) {
        if (numOfSides != 0) {
            if (editFigure == null) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    currentLine = new Line(sc.s2r(new ScreenPoint(e.getX(), e.getY())), sc.s2r(new ScreenPoint(e.getX(), e.getY())));
                    currentFigure = new Polygon(currentLine.getP1(), 0, numOfSides);

                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    prevDrag = new ScreenPoint(e.getX(), e.getY());
                }
            } else {

                if (e.getButton() == MouseEvent.BUTTON3) {
                    prevDrag = new ScreenPoint(e.getX(), e.getY());
                    transfer = true;
                } else if (e.getButton() == MouseEvent.BUTTON1) {
                    prevDrag = new ScreenPoint(e.getX(), e.getY());
                    scale = true;
                }


//            if (editPolygon == null) {
//                if (e.getButton() == MouseEvent.BUTTON3) {
//                    prevDrag = new ScreenPoint(e.getX(), e.getY());
//                } else if (e.getButton() == MouseEvent.BUTTON1) {
//                    currentLine = new Line(sc.s2r(new ScreenPoint(e.getX(), e.getY())), sc.s2r(new ScreenPoint(e.getX(), e.getY())));
//                    currentPolygon = new Polygon(currentLine.getP1(), 0, numOfSides);
//                }
//
//            } else {
//                if (e.getButton() == MouseEvent.BUTTON3) {
//                    transfer = true;
//                    //prevDrag = new ScreenPoint(e.getX(), e.getY());
//                } else if (e.getButton() == MouseEvent.BUTTON1) {
//                    scale = true;
//                }
//            }

            }
        }
        repaint();
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        if (numOfSides != 0) {
            if (editFigure != null) {
                editFigure = null;
                scale = false;
                transfer = false;
                prevDrag = null;
            } else {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    prevDrag = null;
                } else if (e.getButton() == MouseEvent.BUTTON1) {
                    lines.add(currentLine);
                    if (currentFigure != null) {
                        allFiguresList.add(currentFigure);
                    }
                    currentLine = null;
                    currentFigure = null;
                    prevDrag = null;
                }
            }

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
        if (numOfSides != 0) {


            ScreenPoint current = new ScreenPoint(e.getX(), e.getY());

            if (editFigure == null) {
                if (prevDrag != null) {
                    ScreenPoint delta = new ScreenPoint(current.getX() - prevDrag.getX(), current.getY() - prevDrag.getY());
                    RealPoint deltaReal = sc.s2r(delta);
                    RealPoint zeroReal = sc.s2r(new ScreenPoint(0, 0));
                    RealPoint vector = new RealPoint(deltaReal.getX() - zeroReal.getX(), deltaReal.getY() - zeroReal.getY());

                    sc.setX(sc.getX() - vector.getX());
                    sc.setY(sc.getY() - vector.getY());

                    prevDrag = current;

                }
                if (currentLine != null) {
                    currentLine.setP2(sc.s2r(current));
                    currentFigure.setRadius(countRealRadius(sc.s2r(current), currentFigure));
                    //currentPolygon.setR(sc.s2r(current));
                }

            } else {
                if (scale) {
                    if (clickToScaleMarkers(current, editFigure)) {
                        editFigure.setRadius(countRealRadius(sc.s2r(current), editFigure));
                    } else {
                        if (prevDrag != null) {
                            ScreenPoint delta = new ScreenPoint(current.getX() - prevDrag.getX(), current.getY() - prevDrag.getY());
                            RealPoint deltaReal = sc.s2r(delta);
                            RealPoint zeroReal = sc.s2r(new ScreenPoint(0, 0));
                            RealPoint vector = new RealPoint(deltaReal.getX() - zeroReal.getX(), deltaReal.getY() - zeroReal.getY());

                            sc.setX(sc.getX() - vector.getX());
                            sc.setY(sc.getY() - vector.getY());

                            prevDrag = current;

                        }
                    }
                }

//
                if (transfer) {
                    if (clickToTranslationMarker(current, editFigure)) {
                        editFigure.transfer(sc.s2r(current));
                    } else {
                        if (prevDrag != null) {
                            ScreenPoint delta = new ScreenPoint(current.getX() - prevDrag.getX(), current.getY() - prevDrag.getY());
                            RealPoint deltaReal = sc.s2r(delta);
                            RealPoint zeroReal = sc.s2r(new ScreenPoint(0, 0));
                            RealPoint vector = new RealPoint(deltaReal.getX() - zeroReal.getX(), deltaReal.getY() - zeroReal.getY());

                            sc.setX(sc.getX() - vector.getX());
                            sc.setY(sc.getY() - vector.getY());

                            prevDrag = current;

                        }
                    }
                }

            }


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


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (editFigure != null) {
            if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                allFiguresList.remove(editFigure);
                editFigure = null;
            }
        } else {
            if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                allFiguresList.clear();
                editFigure = null;
            }
        }
        repaint();

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private boolean clickToScaleMarkers(ScreenPoint click, IFigure f) {
        List<RealPoint> markers = editFigure.getMarkers();
        for (RealPoint mark : markers) {
            if ((sc.r2s(mark).getX() - getWidth() / (30 * 2)) < click.getX() && (sc.r2s(mark).getX() + getWidth() / (30) > click.getX()
                    && sc.r2s(mark).getY() - getHeight() / (30 * 2) < click.getY() && sc.r2s(mark).getY() + getHeight() / (30) > click.getY())) {
                return true;
            }
        }
        return false;
    }

    private boolean clickToTranslationMarker(ScreenPoint click, IFigure f) {
        return (sc.r2s(f.getCenter()).getX() - getWidth() / (30 * 2)) < click.getX() && (sc.r2s(f.getCenter()).getX() + getWidth() / (30) > click.getX()
                && sc.r2s(f.getCenter()).getY() - getHeight() / (30 * 2) < click.getY() && sc.r2s(f.getCenter()).getY() + getHeight() / (30) > click.getY());
    }


    private double countRealRadius(RealPoint rp, IFigure f) {
        return Math.sqrt(Math.pow((rp.getX() - f.getCenter().getX()), 2) + Math.pow((rp.getY() - f.getCenter().getY()), 2));
    }

    private void drawMarkers(Graphics2D gr2) {
        List<RealPoint> markers = editFigure.getMarkers();
        gr2.setColor(BLACK);
        for (RealPoint mark : markers) {
            gr2.fillOval(sc.r2s(mark).getX() - getWidth() / (30 * 2), sc.r2s(mark).getY() - getHeight() / (30 * 2), getWidth() / 30, getHeight() / 30);
        }

        gr2.fillRect(sc.r2s(editFigure.getCenter()).getX() - getWidth() / (30 * 2), sc.r2s(editFigure.getCenter()).getY() - getHeight() / (30 * 2), getWidth() / 30, getHeight() / 30);

    }

    private void drawArrow(RealPoint rp, Graphics2D gr2){
        ScreenPoint sp = sc.r2s(rp);
        gr2.setColor(BLACK);
        //gr2.drawLine();
    }
}