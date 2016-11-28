package com.jayway.rxandroidlab;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by carl-emil on 12/10/16.
 */
public interface IRetrofitService {
    String SERVICE_ENDPOINT = "https://raw.githubusercontent.com/";

    @GET("carlemil/RxAndroidLab/master/weather.json")
    Observable<Day[]> getDays();

    CallAdapter.Factory newCallAdaptorFactory = new CallAdapter.Factory() {
        @Override
        public CallAdapter<?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
            CallAdapter<?> ca = RxJavaCallAdapterFactory.create().get(returnType, annotations, retrofit);
            return new CallAdapter<Observable<?>>() {

                @Override
                public Type responseType() {
                    return ca.responseType();
                }

                int restRetryCount = 3;

                @Override
                public <R> Observable<?> adapt(Call<R> call) {
                    Observable<?> rx = (Observable<?>) ca.adapt(call);
                    return rx.retryWhen(errors -> errors.flatMap(error -> {
                        boolean needRetry = false;
                        if (restRetryCount >= 1) {
                            if (error instanceof IOException) {
                                needRetry = true;
                            } else if (error instanceof HttpException) {
                                if (((HttpException) error).code() != 200) {
                                    needRetry = true;
                                }
                            }
                        }

                        if (needRetry) {
                            restRetryCount--;
                            return Observable.just(null);
                        } else {
                            return Observable.error(error);
                        }
                    }));
                }
            };
        }
    };

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();

    Retrofit curoRetrofit = new Retrofit.Builder()
            .addCallAdapterFactory(newCallAdaptorFactory) // RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(SERVICE_ENDPOINT)
            .build();
}

