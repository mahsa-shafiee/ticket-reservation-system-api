package service;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")

public class MyApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        HashSet classes = new HashSet<Class<?>>();
        classes.add(TicketService_Json.class);
        classes.add(TicketService_PlainText.class);
        return classes;
    }
}
