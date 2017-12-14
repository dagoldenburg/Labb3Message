package BO;

import UI.UserViewModel;
import com.google.gson.Gson;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

public class RestClient {
    private static final String userUrl ="http://localhost:8081/resource/";
    private  static Client client  = ClientBuilder.newClient();
    private static Gson gson =  new Gson();


    public static UserViewModel getUserById(long id){
        String value =client.target(userUrl+"user").path(String.valueOf(id)).request().accept(MediaType.APPLICATION_JSON_TYPE).get(String.class);
        UserViewModel userViewModel =gson.fromJson(value, UserViewModel.class);
        return userViewModel;
    }
}
