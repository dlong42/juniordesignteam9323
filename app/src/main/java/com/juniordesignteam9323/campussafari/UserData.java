package com.juniordesignteam9323.campussafari;

import java.io.Serializable;

public class UserData implements Serializable {
    private boolean admin;
    private String email;

    public UserData() {
    }

    public UserData(boolean admin, String email) {
        this.admin = admin;
        this.email = email;
    }

    public boolean getAdmin() {
        return this.admin;
    }
    public String getEmail() {return this.email;}

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof UserData)) {
            return false;
        }

        UserData other = (UserData) object;
        return (this.admin == other.admin && this.email.equals(other.email));
    }

}
