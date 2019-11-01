package net.feng.innersys;

public enum InnersysAlarmType
{
    //总是显示查询出的信息
    all,
    //只有在有警告时才显示查询出的信息，如果没有警告就只显示一个“正常”
    onlyAlarm
}
