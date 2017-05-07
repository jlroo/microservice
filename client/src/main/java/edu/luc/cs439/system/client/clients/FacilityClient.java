package edu.luc.cs439.system.client.clients;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import edu.luc.cs439.system.client.retries.ExponentialBackoffRetryPolicy;
import edu.luc.cs439.system.client.retries.IRetryPolicy;
import edu.luc.cs439.system.client.retries.ISingleMethodPolicy;
import edu.luc.cs439.system.contracts.DefaultResponse;
import edu.luc.cs439.system.contracts.FacilityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;

@RestController
@DefaultProperties(defaultFallback = "fallback")
@RequestMapping(value="/facility/")
public class FacilityClient {

    private String _baseURL = "http://mgmt-system";

    @LoadBalanced
    @Bean
    RestTemplate loadBalanced() {
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate loadBalanced;

    @HystrixCommand
    @RequestMapping(value="all",method = RequestMethod.GET,produces = "application/json")
    public String getAllFacilities() throws Exception {
        IRetryPolicy _retry = new ExponentialBackoffRetryPolicy();
        ISingleMethodPolicy method = _retry.NewMethod();
        FacilityResponse response;
        do {
            loadBalanced.getMessageConverters().add(new StringHttpMessageConverter());
            response = loadBalanced.getForObject(_baseURL+"/facility/all",FacilityResponse.class);
            method.HadResponse(response);
            method.PerformWaitIfNeeded();
            method.ThrowErrorFromResponseIfAppropriate();
        } while (method.ShouldRetry());
        ThrowFor(response);
        return response.ToJson();
    }


    @HystrixCommand
    @RequestMapping(value="{id}",method = RequestMethod.GET,produces = "application/json")
    public String getFacility(@PathVariable("id") Integer id) throws Exception {
        IRetryPolicy _retry = new ExponentialBackoffRetryPolicy();
        ISingleMethodPolicy method = _retry.NewMethod();
        FacilityResponse response;
        do {
            loadBalanced.getMessageConverters().add(new StringHttpMessageConverter());
            response = loadBalanced.getForObject(_baseURL + "/facility/" + id.toString(), FacilityResponse.class);
            method.HadResponse(response);
            method.PerformWaitIfNeeded();
            method.ThrowErrorFromResponseIfAppropriate();
        } while (method.ShouldRetry());
        ThrowFor(response);
        return response.ToJson();
    }


    @HystrixCommand
    @RequestMapping(value = "add",method = RequestMethod.GET,produces = "application/json")
    public String addFacility(@RequestParam("id")   String id,
                              @RequestParam("room")  String room,
                              @RequestParam("media") String media,
                              @RequestParam("capacity") String capacity,
                              @RequestParam("name") String name,
                              @RequestParam("phone") String phone,
                              @RequestParam("dept") String dept,
                              @RequestParam("occupied") String occupied,
                              @RequestParam("date") String date)
            throws Exception {

        IRetryPolicy _retry = new ExponentialBackoffRetryPolicy();
        ISingleMethodPolicy method = _retry.NewMethod();
        DefaultResponse response;

        do {

            String parameters = "id="+id+"&room="+room+"&media="+media+"&capacity="+capacity+"&name="+name+
                    "&phone="+phone+"&dept="+dept+"&occupied="+occupied+"&date="+date;

            loadBalanced.getMessageConverters().add(new StringHttpMessageConverter());
            response = loadBalanced.getForObject(_baseURL + "/facility/add?" + parameters, FacilityResponse.class);
            method.HadResponse(response);
            method.PerformWaitIfNeeded();
            method.HadResponse(response);
            method.PerformWaitIfNeeded();
            method.ThrowErrorFromResponseIfAppropriate();
        }while (method.ShouldRetry());

        ThrowFor(response);

        return response.ToJson();
    }


    @HystrixCommand
    @RequestMapping(value="delete/{id}",method = RequestMethod.GET,produces = "application/json")
    public String deleteFacility(@PathVariable("id") int id) throws Exception {
        IRetryPolicy _retry = new ExponentialBackoffRetryPolicy();
        ISingleMethodPolicy method = _retry.NewMethod();
        DefaultResponse response;
        do {
            ResponseEntity<DefaultResponse> request;
            loadBalanced.getMessageConverters().add(new StringHttpMessageConverter());
            request = loadBalanced.exchange(_baseURL + "/facility/delete/"+id,HttpMethod.GET,null,DefaultResponse.class);
            response = request.getBody();
            method.HadResponse(response);
            method.PerformWaitIfNeeded();
            method.ThrowErrorFromResponseIfAppropriate();
        }while (method.ShouldRetry());
        ThrowFor(response);
        return response.ToJson();
    }

    private String fallback(Throwable e) {
        DefaultResponse response = DefaultResponse.Error(e.getMessage());
        return response.ToJson();
    }

    private void ThrowFor(DefaultResponse response) {
        if(!response.Success) {
            if(response.StackTrace.length() == 0) {
                throw new RuntimeException(response.Message);
            } else {
                throw new RuntimeException(response.Message + "\r\n" + response.StackTrace);
            }
        }
    }

}