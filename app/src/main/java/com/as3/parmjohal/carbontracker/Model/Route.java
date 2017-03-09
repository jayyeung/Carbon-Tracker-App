package com.as3.parmjohal.carbontracker.Model;

/**
 * Created by ParmJohal on 2017-03-03.
 */

public class Route {

    private int cityDistance = 0;
    private int hwyDistance = 0;
    private String routeName = "";

    public Route(int cityDistance, int hwyDistance, String routeName) {
        this.cityDistance = cityDistance;
        this.hwyDistance = hwyDistance;
        this.routeName = routeName;
    }

    public int getCityDistance() {
        return cityDistance;
    }

    public void setCityDistance(int cityDistance) {
        this.cityDistance = cityDistance;
    }

    public int getHwyDistance() {
        return hwyDistance;
    }

    public void setHwyDistance(int hwyDistance) {
        this.hwyDistance = hwyDistance;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Route route = (Route) o;

        if (cityDistance != route.cityDistance) return false;
        if (hwyDistance != route.hwyDistance) return false;
        return routeName != null ? routeName.equals(route.routeName) : route.routeName == null;

    }

    @Override
    public String toString() {
        return "Route{" +
                "cityDistance=" + cityDistance +
                ", hwyDistance=" + hwyDistance +
                ", routeName='" + routeName + '\'' +
                '}';
    }
}
