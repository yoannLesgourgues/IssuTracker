package geiffel.da4.issuetracker.commentaire;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class CommentaireEmbeddedJSONSerializer extends JsonSerializer<Commentaire> {
    @Override
    public void serialize(Commentaire commentaire, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("id",commentaire.getId().toString());
        gen.writeStringField("url","/issues/"+commentaire.getId());
        gen.writeEndObject();
    }
}
