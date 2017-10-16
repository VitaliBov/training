package com.bov.vitali.training.data.net;

import com.bov.vitali.training.App;
import com.bov.vitali.training.common.preferences.Preferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private final TrainingApi trainingApi;
    private final FilmsApi filmsApi;

    private ServiceGenerator() {
        trainingApi = createTrainingService(TrainingApi.class, TrainingApi.BASE_URL);
        filmsApi = createFilmsService(FilmsApi.class, FilmsApi.BASE_URL);
    }

    public static ServiceGenerator getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public TrainingApi getTrainingService() {
        return trainingApi;
    }

    public FilmsApi getFilmsService() {
        return filmsApi;
    }

    private <S> S createTrainingService(Class<S> serviceClass, String baseUrl) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getTrainingUnsafeOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(getGson()));
        return builder.build().create(serviceClass);
    }

    private <S> S createFilmsService(Class<S> serviceClass, String baseUrl) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getFilmsUnsafeOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(getGson()));
        return builder.build().create(serviceClass);
    }

    private OkHttpClient getTrainingUnsafeOkHttpClient() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.addInterceptor(headerInterceptor);
            builder.addInterceptor(addHttpInterceptor());
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier((hostname, session) -> true);
            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private OkHttpClient getFilmsUnsafeOkHttpClient() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.addInterceptor(addHttpInterceptor());
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier((hostname, session) -> true);
            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Interceptor headerInterceptor = chain -> {
        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder()
                .header("Authorization", "Bearer " + Preferences.getAccessToken(App.appContext()));
        Request request = requestBuilder.build();
        return chain.proceed(request);
    };

    private static HttpLoggingInterceptor addHttpInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    private static Gson getGson() {
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    private static final class SingletonHolder {
        private static final ServiceGenerator INSTANCE = new ServiceGenerator();

        private SingletonHolder() {
        }
    }
}