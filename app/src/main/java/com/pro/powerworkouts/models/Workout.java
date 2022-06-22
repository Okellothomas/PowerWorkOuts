
package com.pro.powerworkouts.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Workout {

    @SerializedName("bodyPart")
    @Expose
    private String bodyPart;
    @SerializedName("equipment")
    @Expose
    private String equipment;
    @SerializedName("gifUrl")
    @Expose
    private String gifUrl;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("target")
    @Expose
    private String target;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Workout() {
    }

    /**
     * 
     * @param gifUrl
     * @param name
     * @param equipment
     * @param id
     * @param bodyPart
     * @param target
     */
    public Workout(String bodyPart, String equipment, String gifUrl, String id, String name, String target) {
        super();
        this.bodyPart = bodyPart;
        this.equipment = equipment;
        this.gifUrl = gifUrl;
        this.id = id;
        this.name = name;
        this.target = target;
    }

    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getGifUrl() {
        return gifUrl;
    }

    public void setGifUrl(String gifUrl) {
        this.gifUrl = gifUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

}
