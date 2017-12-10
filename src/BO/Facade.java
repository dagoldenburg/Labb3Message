package BO;

import DB.MessageDb;
import UI.MessageViewModel;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by douglas on 11/4/17.
 */
@Path("resource")
public class Facade {
    private static MessageDb messageDb= new MessageDb();

    /**
     * this method saves a new message to database
     * @param content
     * @param date
     * @param type
     * @param senderId
     * @param recipientId
     * @return
     */
    @POST
    @Path("createMessage")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)

    public static String createMessage(@FormParam("content")String content,
                                       @FormParam("date")String date, @FormParam("type")String type,
                                       @FormParam("sendId") String senderId, @FormParam("recipientId") String  recipientId){
        Gson gson = new Gson();
        return gson.toJson(messageDb.createMessage(content,new Date(),type,Long.parseLong(senderId),Long.parseLong(recipientId)));
    }

    /**
     * this method returns a list of messages for a recipient
     * @param id recipient's id
     * @return list of object messages
     */
    @GET
    @Path("messageByUser/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public  static String  getMessageByUserId(@PathParam("id")String id){
        Gson gson = new Gson();
        List<Message> messages =messageDb.getMessagesByUserId(Long.parseLong(id));
        LinkedList<MessageViewModel>  messageVMs=new LinkedList<>();
        for (Message m: messages) {
            messageVMs.add(new MessageViewModel(m.getContent(),m.getDate(),m.getId(),
                    RestClient.getUserById(Long.parseLong(id))));
        }
        return gson.toJson(messageVMs);
    }

    /**
     * this method returns the chat history between two persons
     * @param id person one
     * @param id2 person two
     * @return list of message objects
     */
    @GET
    @Path("chatHistory/{id}/{id2}")
    @Produces(MediaType.APPLICATION_JSON)
    public static String getChatHistory(@PathParam("id")String id, @PathParam("id2") String id2){
        Gson gson = new Gson();
        LinkedList<MessageViewModel> messages = new LinkedList<>();
        try {
            for (Message m : messageDb.getChatHistory(Long.parseLong(id), Long.parseLong(id2))) {
                messages.add(0,new MessageViewModel(m.getContent(), m.getDate(), m.getId(),
                        RestClient.getUserById(m.getSender().getId())
                ));
            }
            for (Message m : messageDb.getChatHistory(Long.parseLong(id2), Long.parseLong(id))) {
                messages.add(0,new MessageViewModel(m.getContent(), m.getDate(), m.getId(),
                        RestClient.getUserById(m.getSender().getId())
                ));
            }
            messages.sort(MessageViewModel::compareTo);

        }catch(NullPointerException e){
            System.out.println("66666666666666666666666666");
        }
        return gson.toJson(messages);
    }

}
