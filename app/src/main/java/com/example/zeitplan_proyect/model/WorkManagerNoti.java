package com.example.zeitplan_proyect.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.concurrent.TimeUnit;

public class WorkManagerNoti  extends Worker {
    //Contructor
    public WorkManagerNoti(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    public static void GuardarNoti(long duracion, Data data, String tag){
        // llamamos a Nuestro servicio
        OneTimeWorkRequest noti =new OneTimeWorkRequest.Builder(WorkManagerNoti.class)
                .setInitialDelay(duracion, TimeUnit.MILLISECONDS).addTag(tag)
                .setInputData(data).build(); // La info que vamos a recibir para que la reconozca en work
        WorkManager intance = WorkManager.getInstance();
        intance.enqueue(noti);
    }

    @NonNull
    @Override
    public Result doWork() {

        String titulo=getInputData().getString("titulo");
        String detalle=getInputData().getString("detalle");
        int id = (int) getInputData().getLong("idnoti",0);



        return Result.success();
    }
}
