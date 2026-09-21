package com.google.gson.functional;

import static org.junit.Assert.assertEquals;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import org.junit.Test;

// https://github.com/google/gson/issues/2190: registering a *de*serialization-only
// ExclusionStrategy changes *serialized* output too, because
// TypeAdapterRuntimeTypeWrapper mistakes the Excluder-wrapped reflective adapter for a
// non-reflective one and picks the wrong adapter on write().
public class ExclusionStrategyAffectsSerialization2190Test {
  static class Base {}

  static class Sub extends Base {
    int i = 0;
  }

  static class Container {
    Base b = new Sub();
  }

  @Test
  public void testDeserializationExclusionStrategyDoesNotAffectSerialization() {
    ExclusionStrategy exclusionStrategy = new ExclusionStrategy() {
      @Override
      public boolean shouldSkipField(FieldAttributes f) {
        return false;
      }

      @Override
      public boolean shouldSkipClass(Class<?> clazz) {
        return clazz == Sub.class;
      }
    };

    Gson gson = new GsonBuilder()
        .registerTypeAdapter(Base.class, new TypeAdapter<Base>() {
          @Override
          public Base read(JsonReader in) throws IOException {
            throw new AssertionError("not needed");
          }

          @Override
          public void write(JsonWriter out, Base value) throws IOException {
            out.value("custom-adapter");
          }
        })
        .addDeserializationExclusionStrategy(exclusionStrategy)
        .create();

    assertEquals("{\"b\":\"custom-adapter\"}", gson.toJson(new Container()));
  }
}
