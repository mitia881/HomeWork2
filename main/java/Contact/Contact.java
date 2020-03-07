package Contact;

import java.io.Serializable;

public class Contact implements Serializable {
    private String name;
    private String numberOrEmail;
    private int imageId;

    public Contact() {
    }

    public Contact(String name, String numberOrEmail, int imageId) {
        this.name = name;
        this.numberOrEmail = numberOrEmail;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberOrEmail() {
        return numberOrEmail;
    }

    public void setNumberOrEmail(String numberOrEmail) {
        this.numberOrEmail = numberOrEmail;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", numberOrEmail='" + numberOrEmail + '\'' +
                ", imageId=" + imageId +
                '}';
    }
}