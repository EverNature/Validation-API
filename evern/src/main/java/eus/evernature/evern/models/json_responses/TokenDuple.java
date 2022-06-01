package eus.evernature.evern.models.json_responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDuple {
    String access_token;
    String refresh_token;
}
