package net.feng;

import net.feng.innersys.Innersys;
import net.feng.realAlarmDo.RealAlarmDo;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainClass
{

    public static final String printCodeArg = "--printCode";
    public static final String alarmTypeArg = "--alarmType";
    public static final String sysNameArg = "--sysName";
    public static final String systemnameArg = "--systemname";
    public static final String errorTodayThresholdArg = "--et";
    public static final String stockMsgsThreshold = "--st";

    public static void main(String[] args)
    {
        System.out.println("Hello main");
        HttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        Map<String,String> argsMap = argsToMap(args);
        final Innersys innersys = getInnersysFromArgsMap(argsMap,httpClient,responseHandler);
        final RealAlarmDo realAlarmDo = getRealAlarmDoFromArgsMap(argsMap,httpClient,responseHandler);

//        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
//        scheduler.start();
//
//        JobDetail jobDetail = new Job()
//
//        scheduler.shutdown();

        Integer milliSecondPerHour = 60 * 60 * 1000;
        Calendar nextHour = Calendar.getInstance();
        nextHour.set(Calendar.MINUTE,0);
        nextHour.set(Calendar.SECOND,0);
        nextHour.set(Calendar.MILLISECOND,0);
        nextHour.add(Calendar.HOUR_OF_DAY,1);
        Calendar now = Calendar.getInstance();
        Long milliSecondToNextHour = nextHour.getTime().getTime() - now.getTime().getTime();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    innersys.alarm();
                    realAlarmDo.alarm();
                }
                catch (Exception e)
                {
                    System.out.println(e);
                }
            }
        },milliSecondToNextHour,milliSecondPerHour, TimeUnit.MILLISECONDS);
    }

    private static Map<String,String> argsToMap(String[] args)
    {
        Map<String,String> result = new HashMap<>();
        for (int i = 0;i < args.length;i = i + 2)
        {
            result.put(args[i],args[i+1]);
        }
        return result;
    }

    private static Innersys getInnersysFromArgsMap(
            Map<String,String> argsMap,
            HttpClient httpClient,
            ResponseHandler<String> responseHandler)
    {
        String printCode = argsMap.get(printCodeArg);
        String alarmType = argsMap.get(alarmTypeArg);

        return new Innersys(httpClient,responseHandler,printCode,alarmType);
    }

    private static RealAlarmDo getRealAlarmDoFromArgsMap(
            Map<String,String> argsMap,
            HttpClient httpClient,
            ResponseHandler<String> responseHandler)
    {
        String sysName = argsMap.get(sysNameArg);
        String systemname = argsMap.get(systemnameArg);
        Map<String,String> formData = null;
        if
        (
                (sysName != null && !sysName.equals(""))
                && (systemname != null && !systemname.equals(""))
        )
        {
            formData = new HashMap<>();
            formData.put("sysName", sysName);
            formData.put("systemname", systemname);
        }



        String errorTodayThresholdString = argsMap.get(errorTodayThresholdArg);
        String stockMsgsThresholdString = argsMap.get(stockMsgsThreshold);
        Integer errorTodayThreshold = null;
        Integer stockMsgsThreshold = null;
        try
        {
            errorTodayThreshold = Integer.valueOf(errorTodayThresholdString);
            stockMsgsThreshold = Integer.valueOf(stockMsgsThresholdString);
        }
        catch (Exception e)
        {
        }

        return new RealAlarmDo(httpClient,responseHandler,formData,errorTodayThreshold,stockMsgsThreshold);
    }
}
