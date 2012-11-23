package lm;

import java.awt.Color;
import java.awt.geom.Point2D;
import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;

public class gir extends AdvancedRobot
{
    private byte scanDirection = 1;
    private byte moveDirection = 1;
    private AdvancedEnemyBot enemy = new AdvancedEnemyBot();

    public void run()
    {
        this.enemy.reset();
        setBodyColor(Color.black);
        setGunColor(Color.red);
        setBulletColor(Color.red);
        setRadarColor(Color.orange);
        setScanColor(Color.orange);
        setAdjustRadarForRobotTurn(true);
        setAdjustGunForRobotTurn(true);

        while (true) {
            setAhead(4000 * this.moveDirection);
            setTurnRight(this.enemy.getBearing() + 90.0D);
            setTurnRadarRight(360 * this.scanDirection);
            execute();
        }
    }

    public void onScannedRobot(ScannedRobotEvent e)
    {
        if ((this.enemy.none()) || (e.getDistance() < this.enemy.getDistance()) || (e.getName().equals(this.enemy.getName()))) {
            this.enemy.update(e, this);
        }

        double firePower = Math.min(500.0D / this.enemy.getDistance(), 3.0D);
        double bulletSpeed = 20.0D - firePower * 3.0D;
        long time = (long)(this.enemy.getDistance() / bulletSpeed);
        double futureX = this.enemy.getFutureX(time);
        double futureY = this.enemy.getFutureY(time);
        double absDeg = absoluteBearing(getX(), getY(), futureX, futureY);

        setTurnGunRight(normalizeBearing(absDeg - getGunHeading()));

        if ((getGunHeat() == 0.0D) && (Math.abs(getGunTurnRemaining()) < 10.0D)) {
            setFire(firePower);
        }

        this.scanDirection = ((byte)(this.scanDirection * -1));
        setTurnRadarRight(360 * this.scanDirection);
        setTurnRight(this.enemy.getBearing() + 90.0D);
    }

    public void onRobotDeath(RobotDeathEvent e)
    {
        if (e.getName().equals(this.enemy.getName())) {
            this.enemy.reset();
        }
    }

    double normalizeBearing(double angle)
    {
        while (angle > 180.0D) {
            angle -= 360.0D;
        }

        while (angle < -180.0D) {
            angle += 360.0D;
        }

        return angle;
    }

    double absoluteBearing(double x1, double y1, double x2, double y2)
    {
        double xo = x2 - x1;
        double yo = y2 - y1;
        double hyp = Point2D.distance(x1, y1, x2, y2);
        double arcSin = Math.toDegrees(Math.asin(xo / hyp));
        double bearing = 0.0D;

        if ((xo > 0.0D) && (yo > 0.0D)) {
            bearing = arcSin;
        } else if ((xo < 0.0D) && (yo > 0.0D)) {
            bearing = 360.0D + arcSin;
        } else if ((xo > 0.0D) && (yo < 0.0D)) {
            bearing = 180.0D - arcSin;
        } else if ((xo < 0.0D) && (yo < 0.0D)) {
            bearing = 180.0D - arcSin;
        }

        return bearing;
    }

    public void onHitWall(HitWallEvent e)
    {
        this.moveDirection = ((byte)(this.moveDirection * -1));
    }

    public void onHitRobot(HitRobotEvent e)
    {
        this.moveDirection = ((byte)(this.moveDirection * -1));
    }
}
