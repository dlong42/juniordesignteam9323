package com.juniordesignteam9323.campussafari;

import java.io.Serializable;

public class UserData implements Serializable {
    private boolean admin;

    public UserData(boolean admin) {
        this.admin = admin;
    }

    public boolean getAdmin() {
        return this.admin;
    }

}
