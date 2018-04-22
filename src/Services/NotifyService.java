package Services;

import Objects.User;

import java.util.Collection;

public class NotifyService {
    /**
     * Not implemented yet.
     * @param receivers
     * @param message
     */
    public void notify(Collection<User> receivers, String message){
        for(User receiver: receivers) {
            System.out.println("NOTIFICATION FOR:");
            System.out.println(receiver);
            System.out.println("TEXT:");
            System.out.println(message);
        }
    }

}
