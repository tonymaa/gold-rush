/*
package cn.tony.goldpricepushnotification.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LoadPriceWorker extends Worker {
    public LoadPriceWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Do the work here--in this case, upload the images.
        // 返回结果数据
        Data outputData = new Data.Builder()
                .putString("result", new Date().toString())
                .build();
        // Indicate whether the work finished successfully with the Result
        return Result.success(outputData);
    }
}
*/
