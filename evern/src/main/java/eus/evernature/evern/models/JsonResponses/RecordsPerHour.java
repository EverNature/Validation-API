package eus.evernature.evern.models.JsonResponses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordsPerHour {
    int hora;
    int numImagenes;
}
