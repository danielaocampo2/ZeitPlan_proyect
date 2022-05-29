package com.example.zeitplan_proyect.model;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.vista.Recordar;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class WorkManagerNoti  extends Worker {
    //Contructor
    public WorkManagerNoti(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    public static void GuardarNoti(long duracion, Data data, String tag){
        // llamamos a Nuestro servicio
        OneTimeWorkRequest noti =new OneTimeWorkRequest.Builder(WorkManagerNoti.class)
                // El tiempo en milisegundos se lo restamos al tiempo actual
                .setInitialDelay(duracion, TimeUnit.MILLISECONDS).addTag(tag)
                .setInputData(data).build(); // La info que vamos a recibir para que la reconozca en work enviamos titulo y detalle
        WorkManager intance = WorkManager.getInstance();
        intance.enqueue(noti);
    }

    @NonNull
    @Override
    public Result doWork() {

        String titulo=getInputData().getString("titulo");
        String detalle=getInputData().getString("detalle");
        int id = (int) getInputData().getLong("idnoti",0);
        notificacionFire(titulo,  detalle);


        return Result.success();
    }

    public void  notificacionFire(String t, String d){
        String id= "message";
        NotificationManager nm= (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),id);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel nc= new NotificationChannel(id,"nuevo",NotificationManager.IMPORTANCE_HIGH);
            nc.setDescription("Notificación FCM");
            nc.setShowBadge(true);
            assert nm != null;
            nm.createNotificationChannel(nc);
        }
        Intent intent=new Intent(getApplicationContext(), Recordar.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent =PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_ONE_SHOT);
        builder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(t)
                .setTicker("Nueva notificación")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(d)
                .setContentIntent(pendingIntent)
                .setContentInfo("nuevo");

        Random random = new Random();
        int idNotify = random.nextInt(8000);

        assert nm != null;
        nm.notify(idNotify,builder.build());


    }
}
