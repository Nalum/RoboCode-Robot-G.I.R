package lm;

import robocode.ScannedRobotEvent;

public class EnemyBot
{
    private double bearing;
    private double distance;
    private double energy;
    private double heading;
    private String name;
    private double velocity;

    public double getBearing()
    {
        return this.bearing;
    }

    public double getDistance()
    {
        return this.distance;
    }

    public double getEnergy()
    {
        return this.energy;
    }

    public double getHeading()
    {
        return this.heading;
    }

    public String getName()
    {
        return this.name;
    }

    public double getVelocity()
    {
        return this.velocity;
    }

    public void update(ScannedRobotEvent e)
    {
        this.bearing = e.getBearing();
        this.distance = e.getDistance();
        this.energy = e.getEnergy();
        this.heading = e.getHeading();
        this.name = e.getName();
        this.velocity = e.getVelocity();
    }

    public void reset()
    {
        this.bearing = 0.0D;
        this.distance = 0.0D;
        this.energy = 0.0D;
        this.heading = 0.0D;
        this.name = "";
        this.velocity = 0.0D;
    }

    public boolean none()
    {
        if (this.name.equals("")) {
            return true;
        }

        return false;
    }

    public EnemyBot()
    {
        reset();
    }
}
