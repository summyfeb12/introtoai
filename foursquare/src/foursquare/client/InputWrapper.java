package foursquare.client;

import java.io.Serializable;

public class InputWrapper implements Serializable {
    private String code;
    private String location;
    private String data;
    private boolean validated = false;
    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }
    /**
     * @param code the code to set
     */
    public void setCode(final String code) {
        this.code = code;
    }
    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }
    /**
     * @param location the location to set
     */
    public void setLocation(final String location) {
        this.location = location;
    }
    /**
     * @return the data
     */
    public String getData() {
        return data;
    }
    /**
     * @param data the data to set
     */
    public void setData(final String data) {
        this.data = data;
    }
    /**
     * @return the validated
     */
    public boolean isValidated() {
        return validated;
    }

    public void setValidated(final boolean value) {
        this.validated = value;
    }
}
