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
    public static void main(String[] args)
    {
        System.out.println("Hello main");
        HttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        final Innersys innersys = new Innersys(httpClient,responseHandler,null,null);
        final RealAlarmDo realAlarmDo = new RealAlarmDo(httpClient,responseHandler,null,null,null);

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

//        try
//        {
//            innersys.alarm();
//        }
//        catch (Exception e)
//        {
//            System.out.println(e);
//        }

    }
}
