package eus.evernature.evern.utility;

import com.auth0.jwt.algorithms.Algorithm;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class JwtAlgorithmProvider {


    public static Algorithm getHMAC256A() {
        // TODO: change the "secret" string and get it from database
        return Algorithm.HMAC256("secret".getBytes());
    }

}
