package main.java.services.ApiToken;

import java.io.Serializable;

public class ApiTokenStoreDto implements Serializable {

    public String Salt;

    public byte[] IV;

    public String Token;

}
