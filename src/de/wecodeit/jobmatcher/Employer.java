package de.wecodeit.jobmatcher;

public class Employer
{
    private String json;
    private int score;
    private double distanceToUserCity;

    public Employer(String json)
    {
        this.json = json;
        this.score = 0;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public String getJson()
    {
        return json;
    }

    public void setJson(String json)
    {
        this.json = json;
    }

    public void addToScore(int num)
    {
        this.score += num;
    }

    public double getDistanceToUserCity()
    {
        return distanceToUserCity;
    }

    public void setDistanceToUserCity(double distanceToUserCity)
    {
        this.distanceToUserCity = distanceToUserCity;
    }
}
