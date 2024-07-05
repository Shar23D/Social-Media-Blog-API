package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController {
        this.accountService = new AccountService;
        this.messageService = new MessageService;
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("register", this::postAccountHandler);
        app.post("login", this::postLoginHandler);
        app.post("messages", this::postMessageHandler);
        app.get("messages", this::getMessagesHandler);
        app.get("messages/{message_id}", this::getAMessageHandler);
        app.delete("messages/{message_id}", this::deleteAMessageHandler);
        app.patch("messages/{message_id}", this::patchMessageHandler);
        app.get("accounts/{account_id}/messages", this::getAccountMessagesHandler);
        app.start(8080);
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void postAccountHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if (addedAccount != null && addedAccount.password.length() >= 4) {
            context.json(mapper.writeValueAsString(addedAccount));
        }
        else {
            context.status(400);
        }
    }

    private void postLoginHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account login = accountService.verifyingLogin(account);
        if (login != null) {
            context.json(mapper.writeValueAsString(login));
        }
        else {
            context.status(401);
        }
    }

    private void postMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if (addedMessage != null && addedMessage.message_text.length() <= 255) {
            context.json(mapper.writeValueAsString(addedMessage));
        }
        else {
            context.status(400);
        }
    }

    private void getMessagesHandler(Context context) {
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }

    private void getAMessageHandler(Context context, int message_id) {
        context.pathParam("message_id");
        Message message = messageService.GetAMessageById();
        context.json(message);
    }
    




}