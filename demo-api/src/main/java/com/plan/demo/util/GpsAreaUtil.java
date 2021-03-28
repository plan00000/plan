package com.plan.demo.util;

import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.List;

/**
 * @Created by linzhihua
 * @Description
 * @ClassName GpsAreaUtil
 * @Date 2020/11/25 8:54
 */
public class GpsAreaUtil {

    private static final double EARTH_RADIUS = 6371e3;

    //计算点两点之间距离
    public static double getDistance(String oneLon,String oneLat , String twoLon, String twoLat)
    {
        Double lat_one = Double.valueOf(oneLat);
        Double lon_one = Double.valueOf(oneLon);
        Double lon_two = Double.valueOf(twoLon);
        Double lat_two = Double.valueOf(twoLat);

        double latOne = toRadians(lat_one);
        double latTwo = toRadians(lat_two);
        double latDiff = toRadians(lat_two - lat_one);
        double lonDiff = toRadians(lon_two - lon_one);
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) + Math.cos(latOne) * Math.cos(latTwo) * Math.sin(lonDiff / 2) * Math.sin(lonDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        DecimalFormat format = new DecimalFormat("0.00");
        //logger.info("两点间距离：{" + format.format(EARTH_RADIUS * c) + "米}");
        return Double.parseDouble(format.format(EARTH_RADIUS * c));
    }

    //判断点是否在多边形内
    public static boolean checkWithJdkGeneralPath(Point2D.Double point, List<Point2D.Double> polygon) {
        java.awt.geom.GeneralPath p = new java.awt.geom.GeneralPath();
        Point2D.Double first = polygon.get(0);
        p.moveTo(first.x, first.y);
        polygon.remove(0);
        for (Point2D.Double d : polygon) {
            p.lineTo(d.x, d.y);
        }
        p.lineTo(first.x, first.y);
        p.closePath();
        return p.contains(point);
    }

    /**
     * 根据提供的角度值，将其转化为弧度
     *
     * @param angle 角度值
     * @return 结果
     */
    public static double toRadians(Double angle)
    {
        double result = 0L;
        if (angle != null) {
            result = angle * Math.PI / 180;
        }
        return result;
    }

    /**
     * 根据提供的经纬度，转化为弧度
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @return 结果集
     */
    public static double[] latitudeLongitudeToRadians(Double latitude, Double longitude)
    {
        double[] result = new double[2];
        if (latitude != null && longitude != null) {
            result[0] = latitude * Math.PI / 180;
            result[1] = longitude * Math.PI / 180;
        }

        return result;
    }

    /**
     * 将给定的弧度转为角度
     *
     * @param radians 弧度值
     * @return 转换结果
     */
    public static double toAngle(Double radians)
    {
        double result = 0L;
        if (radians != null) {
            result = radians * Math.PI / 180;
        }
        return result;
    }

    /**
     * 将指定的经纬度弧度值转为角度
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @return 角度
     */
    public static double[] latitudeLongitudeToAngle(Double latitude, Double longitude)
    {
        double[] result = new double[2];
        if (latitude != null && longitude != null) {
            result[0] = latitude * Math.PI / 180;
            result[1] = longitude * Math.PI / 180;
        }
        return result;
    }

}
