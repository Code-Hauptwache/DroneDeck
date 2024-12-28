package main.java.services.ApiTokenStore;

import javax.crypto.spec.IvParameterSpec;
import java.io.Serializable;

public class ApiTokenStoreDto implements Serializable {

    public String Salt;

    public byte[] IV;

    public String Token;

}
