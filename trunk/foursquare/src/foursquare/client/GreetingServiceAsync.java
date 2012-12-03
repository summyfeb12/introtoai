package foursquare.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
    void greetServer(String input[], AsyncCallback<VenueList[]> callback)
            throws IllegalArgumentException;

    void structuredGreetServer(VenueList input,
            AsyncCallback<String> callback)
            throws IllegalArgumentException;

    void getNumber(String input,
            AsyncCallback<String> callback)
            throws IllegalArgumentException;

}
