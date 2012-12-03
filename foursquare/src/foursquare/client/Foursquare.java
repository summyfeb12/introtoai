package foursquare.client;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.geolocation.client.Geolocation;
import com.google.gwt.geolocation.client.Position;
import com.google.gwt.geolocation.client.PositionError;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import foursquare.shared.FieldVerifier;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Foursquare implements EntryPoint {
    /**
     * The message displayed to the user when the server cannot be reached or
     * returns an error.
     */
    private static final String SERVER_ERROR = "An error occurred while "
            + "attempting to contact the server. Please check your network "
            + "connection and try again.";
    String code = "";
    /**
     * Create a remote service proxy to talk to the server-side Greeting service.
     */
    private final GreetingServiceAsync greetingService = GWT
            .create(GreetingService.class);

    public void geoLocationCallback(final Position position) {
        code = "" + position.getCoordinates().getLatitude();
        // ...
    }

    final StringBuffer locationBuf = new StringBuffer();
    /**
     * This is the entry point method.
     */
    @Override
    public void onModuleLoad() {
        String userId = Window.Location.getParameter("foursquid");
        String autoCheckin = Window.Location.getParameter("venueid");
        if (Cookies.getCookie("venueId") != null) {
            autoCheckin = Cookies.getCookie("venueId");

        }
        final GreetingServiceAsync async = GWT
                .create(GreetingService.class);
        if ((userId == null || userId.isEmpty())
                && (autoCheckin == null || autoCheckin.isEmpty())) {
            basicLoad();

        }
        else {
            if (userId != null && !userId.isEmpty()) {
            final Label textToServerLabel = new Label();
            RootPanel.get("nameFieldContainer").add(textToServerLabel);

            async.getNumber(userId, new AsyncCallback<String>() {

                @Override
                public void onSuccess(final String result) {
                    textToServerLabel.setText(result);

                }

                @Override
                public void onFailure(final Throwable caught) {
                    // TODO Auto-generated method stub

                }
            });
            }
            else {
                VenueList wrapper = new VenueList();
                String code = Window.Location.getParameter("code");
                if (code == null || code.isEmpty()) {
                    wrapper.setValidated(false);
                    Cookies.setCookie("venueId", autoCheckin);
                }
                else {
                    wrapper.setValidated(true);
                    wrapper.setCode(code);
                    String venueId = Cookies.getCookie("venueId");
                    Cookies.removeCookie("venueId");
                    wrapper.setVenueId(venueId);
                }
                async.structuredGreetServer(wrapper,
                        new AsyncCallback<String>() {

                            @Override
                            public void onSuccess(final String result) {
                                if (result.startsWith("surl")) {

                                    Window.Location.assign(result.replace(
                                            "surl", ""));
                                }
                                final Label textToServerLabel = new Label();
                                RootPanel.get("nameFieldContainer").add(
                                        textToServerLabel);
                                textToServerLabel
                                        .setText("done with checkin to "
                                                + result);

                            }

                            @Override
                            public void onFailure(final Throwable caught) {
                                // TODO Auto-generated method stub

                            }
                        });
            }
        }
    }

    private void basicLoad() {

        final Button sendButton = new Button("Send");
        final TextBox nameField = new TextBox();
        nameField.setText("GWT User" + code);
        final Label errorLabel = new Label();

        // We can add style names to widgets
        sendButton.addStyleName("sendButton");

        // Add the nameField and sendButton to the RootPanel
        // Use RootPanel.get() to get the entire body element
        RootPanel.get("nameFieldContainer").add(nameField);
        RootPanel.get("sendButtonContainer").add(sendButton);
        RootPanel.get("errorLabelContainer").add(errorLabel);


        // Focus the cursor on the name field when the app loads
        nameField.setFocus(true);
        nameField.selectAll();

        // Create the popup dialog box
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText("Remote Procedure Call");
        dialogBox.setAnimationEnabled(true);
        final Button closeButton = new Button("Close");
        // We can set the id of a widget by accessing its Element
        closeButton.getElement().setId("closeButton");
        final Label textToServerLabel = new Label();
        final ListBox box = new ListBox();


        final HTML serverResponseLabel = new HTML();
        VerticalPanel dialogVPanel = new VerticalPanel();
        dialogVPanel.addStyleName("dialogVPanel");
        dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
        dialogVPanel.add(textToServerLabel);
        dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
        dialogVPanel.add(serverResponseLabel);
        dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
        dialogVPanel.add(closeButton);
        dialogBox.setWidget(dialogVPanel);
        RootPanel.get().add(box);
        code = Window.Location.getParameter("code");
        String cookie = Cookies.getCookie("check");
        if (cookie == null) {
            cookie = "cookie is set";
            Cookies.setCookie("check", cookie);
            cookie = "";
        }
        else {
            cookie += "cookie was already set";
        }
        final GreetingServiceAsync async = GWT.create(GreetingService.class);


        if (code == null || code.isEmpty()) {
            VenueList wrapper = new VenueList();
            wrapper.setValidated(false);

            greetingService.structuredGreetServer(wrapper,
                    new AsyncCallback<String>() {
                        @Override
                        public void onFailure(final Throwable caught) {
                            // Show the RPC error message to the user
                            dialogBox
                                    .setText("Remote Procedure Call - Failure");
                            serverResponseLabel
                                    .addStyleName("serverResponseLabelError");
                            serverResponseLabel.setHTML(SERVER_ERROR);
                            dialogBox.center();
                            closeButton.setFocus(true);
                        }

                        @Override
                        public void onSuccess(final String result) {
                            dialogBox.setText("Remote Procedure Call");
                            serverResponseLabel
                                    .removeStyleName("serverResponseLabelError");
                            serverResponseLabel.setHTML(result);
                            dialogBox.center();
                            closeButton.setFocus(true);
                            if (result.startsWith("surl")) {

                                Window.Location.assign(result.replace(
                                        "surl", ""));
                        }

                        }
                    });
        }
        else {
            Geolocation location = Geolocation.getIfSupported();
            location.getCurrentPosition(new Callback<Position, PositionError>() {

                @Override
                public void onFailure(final PositionError reason) {
                    serverResponseLabel.setText(reason.getMessage());
                }

                @Override
                public void onSuccess(final Position result) {
                    locationBuf.append(
                            result.getCoordinates().getLatitude()
                                    + ","
                                    + result.getCoordinates().getLongitude());
                    async.greetServer(new String[] { code,
                            locationBuf.toString() },
                            new AsyncCallback<VenueList[]>() {

                                @Override
                                public void onSuccess(final VenueList[] result) {
                                    box.addItem("success");
                                    for (VenueList venue : result) {
                                        box.addItem(venue.getName(), venue
                                                .getId());
                                }

                                }

                                @Override
                                public void onFailure(final Throwable caught) {
                                    // TODO Auto-generated method stub

                                }
                            });

                }
            });


        }


        // Add a handler to close the DialogBox
        closeButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                dialogBox.hide();
                sendButton.setEnabled(true);
                sendButton.setFocus(true);
        }
        });



        // Create a handler for the sendButton and nameField
        class MyHandler implements ClickHandler, KeyUpHandler {
            /**
             * Fired when the user clicks on the sendButton.
             */
            @Override
            public void onClick(final ClickEvent event) {

                sendNameToServer();
            }

            /**
             * Fired when the user types in the nameField.
             */
            @Override
            public void onKeyUp(final KeyUpEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                sendNameToServer();
            }
            }

            /**
             * Send the name from the nameField to the server and wait for a
             * response.
             */
            private void sendNameToServer() {
                // First, we validate the input.
                errorLabel.setText("");
                nameField.setText(locationBuf.toString());
                String textToServer = nameField.getText();
                if (!FieldVerifier.isValidName(textToServer)) {
                    errorLabel.setText("Please enter at least four characters");
                    return;
            }

                // Then, we send the input to the server.
                sendButton.setEnabled(false);
                textToServerLabel.setText(textToServer);
                serverResponseLabel.setText("");
                String code = Window.Location.getParameter("code");

                String pref = "";

                final StringBuffer coord = new StringBuffer();
                VenueList wrapper = new VenueList();
                if (code != null && !code.isEmpty()) {
                    wrapper.setValidated(true);
                    wrapper.setCode(code);
                    pref += coord.toString();
                    pref += "code=" + code + "fiii";
                    wrapper.setVenueId(box.getValue(box.getSelectedIndex()));

                }


                greetingService.structuredGreetServer(wrapper,
                        new AsyncCallback<String>() {
                            @Override
                            public void onFailure(final Throwable caught) {
                                // Show the RPC error message to the user
                                dialogBox
                                        .setText("Remote Procedure Call - Failure");
                                serverResponseLabel
                                        .addStyleName("serverResponseLabelError");
                                serverResponseLabel.setHTML(SERVER_ERROR);
                                dialogBox.center();
                                closeButton.setFocus(true);
                }

                            @Override
                            public void onSuccess(final String result) {
                                dialogBox.setText("Remote Procedure Call");
                                serverResponseLabel
                                        .removeStyleName("serverResponseLabelError");
                                serverResponseLabel.setHTML(result);
                                dialogBox.center();
                                closeButton.setFocus(true);
                                if (result.startsWith("surl")) {
                                    Window.Location.assign(result.replace(
                                            "surl", ""));
                    }

                            }
                        });
        }
    }

        // Add a handler to send the name to the server
        MyHandler handler = new MyHandler();
        sendButton.addClickHandler(handler);
        nameField.addKeyUpHandler(handler);
        sendButton.click();
        nameField.setText(cookie);
    }

}
