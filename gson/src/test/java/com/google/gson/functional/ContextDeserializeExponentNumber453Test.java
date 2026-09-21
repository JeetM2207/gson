package com.google.gson.functional;

import static org.junit.Assert.assertEquals;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import java.lang.reflect.Type;
import org.junit.Test;

// https://github.com/google/gson/issues/453: Gson's normal fromJson() entry point parses
// a number in exponential notation (e.g. 1.02e+12) into a long field fine, but the same
// value routed through JsonDeserializationContext.deserialize() from inside a custom
// JsonDeserializer fails to parse it -- the two entry points should be consistent.
public class ContextDeserializeExponentNumber453Test {
  public static class Content {
    long dateCreated;
  }

  public static class Wrapper {
    Content content;
  }

  @Test
  public void testContextDeserializeHandlesExponentNotationLikeDirectFromJson() {
    String innerJson = "{\"dateCreated\": 1.020204000000e+12}";

    Content viaDirect = new Gson().fromJson(innerJson, Content.class);
    assertEquals(1020204000000L, viaDirect.dateCreated);

    String wrapperJson = "{\"content\":" + innerJson + "}";
    Gson customGson = new GsonBuilder()
        .registerTypeAdapter(Wrapper.class, new JsonDeserializer<Wrapper>() {
          @Override
          public Wrapper deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            Wrapper w = new Wrapper();
            w.content = context.deserialize(json.getAsJsonObject().get("content"), Content.class);
            return w;
          }
        })
        .create();
    Wrapper viaContext = customGson.fromJson(wrapperJson, Wrapper.class);
    assertEquals(1020204000000L, viaContext.content.dateCreated);
  }
}
