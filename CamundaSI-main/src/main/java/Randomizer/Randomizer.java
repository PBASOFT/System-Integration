package Randomizer;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.camunda.bpm.client.ExternalTaskClient;

import java.io.IOException;
import java.util.logging.Logger;

public class Randomizer {
    private final static Logger LOGGER = Logger.getLogger(Randomizer.class.getName());
    static final String coronaAPI = "https://www.trackcorona.live/api/cities/";
    static final String hotelAPI = "http://engine.hotellook.com/api/v2/lookup.json?query=";
    static public String city;

    public static void main(String[] args) {

        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl("http://localhost:8080/engine-rest")
                .asyncResponseTimeout(10000) // long polling timeout
                .build();
        LOGGER.info("Not yet requests");

        // subscribe to an external task topic as specified in the process
        client.subscribe("FindCoronaStatus")
                .lockDuration(1000) // the default lock duration is 20 seconds, but you can override this
                .handler((externalTask, externalTaskService) -> {
                    // Put your business logic here
                    System.out.println("Test of External Service: Corona");
                    // Get a process variable
                    city = (String) externalTask.getVariable("City");
                    getAPIEndPoint(coronaAPI);
                    // Complete the task
                    externalTaskService.complete(externalTask);
                })
                .open();

        client.subscribe("FindHotel")
                .lockDuration(1000) // the default lock duration is 20 seconds, but you can override this
                .handler((externalTask, externalTaskService) -> {
                    // Put your business logic here
                    System.out.println("Test of External Service: findHotel");
                    // Get a process variable
                    city = (String) externalTask.getVariable("City");
                    getAPIEndPoint(hotelAPI);
                    // Complete the task
                    externalTaskService.complete(externalTask);
                })
                .open();
    }

    public static void getAPIEndPoint(String url) {
        HttpGet request = new HttpGet(url + city);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse httpResponse = httpClient.execute(request)) {
            HttpEntity entity = httpResponse.getEntity();
            String result = EntityUtils.toString(entity);
            System.out.println(result);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
