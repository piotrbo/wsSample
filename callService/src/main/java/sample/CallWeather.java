package sample;

import com.cdyne.ws.weatherws.*;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

/**
 * Created by piotrbo on 03/06/2015.
 */
public class CallWeather {
    public static void main(String[] args) {

        String serviceEndpoint = "http://wsf.cdyne.com/WeatherWS/Weather.asmx";

        WeatherSoap weather = getSoapClient(serviceEndpoint, WeatherSoap.class);


        ArrayOfWeatherDescription weatherInformation = weather.getWeatherInformation();

        for (WeatherDescription weatherDescription : weatherInformation.getWeatherDescription()) {
            System.out.println("- " + weatherDescription.getDescription());
            System.out.println(weatherDescription.getPictureURL());
        }

        ForecastReturn cityForecastByZIP = weather.getCityForecastByZIP("11222");
        System.out.println(cityForecastByZIP.getCity());

    }


    private static <T> T getSoapClient(String serviceEndpoint, Class<T> serviceClass) {
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setServiceClass(serviceClass);
        jaxWsProxyFactoryBean.setAddress(serviceEndpoint);

        jaxWsProxyFactoryBean.getOutInterceptors().add(new LoggingOutInterceptor());
        jaxWsProxyFactoryBean.getInInterceptors().add(new LoggingInInterceptor());

        return (T) jaxWsProxyFactoryBean.create();
    }


}
