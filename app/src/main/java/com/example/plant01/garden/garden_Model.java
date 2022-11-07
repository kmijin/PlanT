package com.example.plant01.garden;

public class garden_Model {

    String id, profileUri , type,  name, location, date;
    public garden_Model(String s){}

//

    public garden_Model(String id, String profileUri, String type, String name, String location, String date){
        this.id = id;
        this.name = name;
        this.type = type;
        this.location = location;
        this.date = date;
        this.profileUri = profileUri;

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfileUri() {
        return profileUri;
    }

    public void setProfileUri(String profileUri) {
        this.profileUri = profileUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
