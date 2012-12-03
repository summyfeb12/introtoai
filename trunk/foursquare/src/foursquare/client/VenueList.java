package foursquare.client;

import java.io.Serializable;

public class VenueList implements Serializable {
    private String name;
    private String id;
    private String code;
    private boolean isValidated;
    private String venueId;
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(final String id) {
        this.id = id;
    }
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
     * @return the isValidated
     */
    public boolean isValidated() {
        return isValidated;
    }
    /**
     * @param isValidated the isValidated to set
     */
    public void setValidated(final boolean isValidated) {
        this.isValidated = isValidated;
    }
    /**
     * @return the venueId
     */
    public String getVenueId() {
        return venueId;
    }
    /**
     * @param venueId the venueId to set
     */
    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

}
