package com.google.gson.functional;

import static org.junit.Assert.assertEquals;

import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.io.StringWriter;
import org.junit.Test;

// https://github.com/google/gson/issues/1736: a non-lenient JsonWriter.value(double) for
// NaN/Infinity writes the pending property *name* before checking the value is valid and
// throwing -- so with setSerializeNulls(false), the name is left dangling with no value
// ever written for it, corrupting subsequent output.
public class JsonWriterNaNWritesName1736Test {
  @Test
  public void testNonLenientValueDoesNotWriteNameBeforeThrowing() throws IOException {
    StringWriter stringWriter = new StringWriter();
    JsonWriter jsonWriter = new JsonWriter(stringWriter);
    jsonWriter.setSerializeNulls(false);
    jsonWriter.beginObject();

    jsonWriter.name("test");
    try {
      jsonWriter.value(Double.NaN);
      throw new AssertionError("expected IllegalArgumentException");
    } catch (IllegalArgumentException expected) {
      // expected
    }
    jsonWriter.nullValue();

    jsonWriter.endObject();
    assertEquals("{}", stringWriter.toString());
  }
}
