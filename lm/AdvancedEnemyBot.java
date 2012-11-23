package lm;

import robocode.Robot;
import robocode.ScannedRobotEvent;

public class AdvancedEnemyBot extends EnemyBot
{
    private double x;
    private double y;

    public double getX()
    {
        return this.x;
    }

    public double getY()
    {
        return this.y;
    }

    public void reset()
    {
        super.reset();
        this.x = 0.0D;
        this.y = 0.0D;
    }

    public AdvancedEnemyBot()
    {
        reset();
    }

    public void update(ScannedRobotEvent e, Robot robot)
    {
        super.update(e);
        double absBearingDeg = robot.getHeading() + e.getBearing();
        
        if (absBearingDeg < 0.0D) {
            absBearingDeg += 360.0D;
        }
        
        this.x = (robot.getX() + Math.sin(Math.toRadians(absBearingDeg)) * e.getDistance());
        this.y = (robot.getY() + Math.cos(Math.toRadians(absBearingDeg)) * e.getDistance());
    }

    public double getFutureX(long when)
    {
        return this.x + Math.sin(Math.toRadians(getHeading())) * getVelocity() * when;
    }

    public double getFutureY(long when)
    {
        return this.y + Math.cos(Math.toRadians(getHeading())) * getVelocity() * when;
    }
}
