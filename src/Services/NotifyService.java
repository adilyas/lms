package Services;

import Objects.Document;
import Objects.Patron;
import Objects.User;

import java.util.Collection;

public class NotifyService {
    /**
     * Not trully implemented yet.
     *
     * @param receivers
     * @param message
     */
    public <T extends User> void notify(Collection<T> receivers, String message) {
        for (T receiver : receivers) {
            System.out.println("NOTIFICATION FOR:");
            System.out.println(receiver);
            System.out.println("TEXT:");
            System.out.println(message);
        }
    }


    public <T extends User> void notify(T receiver, String message) {
        System.out.println("NOTIFICATION FOR:");
        System.out.println(receiver);
        System.out.println("TEXT:");
        System.out.println(message);
    }

    public void notifyAboutFreeCopy(Patron patron, Document document) {
        notify(patron, document.getTitle() + "have copy available for check out. If you will not take it in " +
                "next two days, you request for this document will be deleted.");
    }

}
