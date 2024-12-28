package main.java.services.ApiTokenStore;

import java.io.Serializable;

public class ApiTokenStoreDTO implements Serializable {

    public String Salt;

    public byte[] IV;

    public String Token;

}
