package fr.xebia.tv;

import android.app.Application;

import de.greenrobot.event.EventBus;
import fr.xebia.tv.api.XebiaTvApi;
import fr.xebia.tv.core.JacksonConverter;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class XebiaTvApplication extends Application {

    public static final EventBus BUS = EventBus.getDefault();

    private static XebiaTvApi xebiaTvApi;

    @Override public void onCreate() {
        super.onCreate();
        xebiaTvApi = new RestAdapter.Builder()
                .setClient(new OkClient())
                .setEndpoint("http://www.xebia.tv")
                .setConverter(new JacksonConverter())
                .build().create(XebiaTvApi.class);
    }

    public static XebiaTvApi getXebiaTvApi(){
        return xebiaTvApi;
    }
}
