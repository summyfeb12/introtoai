package foursquare.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
    VenueList[] greetServer(String name[]) throws IllegalArgumentException;

    String structuredGreetServer(VenueList name)
            throws IllegalArgumentException;

    String getNumber(String id)
            throws IllegalArgumentException;
}
