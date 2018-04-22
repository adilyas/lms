package Services;

import Objects.User;

import java.util.Collection;

public class NotifyService {
    /**
     * Not implemented yet.
     * @param receivers
     * @param message
     */
    public <T extends User> void notify(Collection<T> receivers, String message){
        for(T receiver: receivers) {
            System.out.println("NOTIFICATION FOR:");
            System.out.println(receiver);
            System.out.println("TEXT:");
            System.out.println(message);
        }
    }

}
