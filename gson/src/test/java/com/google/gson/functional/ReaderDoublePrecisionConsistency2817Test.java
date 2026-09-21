package com.google.gson.functional;

import static org.junit.Assert.assertThrows;

import com.google.gson.JsonObject;
import com.google.gson.internal.bind.JsonTreeReader;
import org.junit.Test;

// https://github.com/google/gson/issues/2817: JsonReader.nextInt() correctly fails when
// the next token is a double (e.g. 42.123), but JsonTreeReader.nextInt() on the exact
// same value silently casts/truncates instead of failing -- the two JsonReader
// implementations should behave consistently.
public class ReaderDoublePrecisionConsistency2817Test {
  @Test
  public void testJsonTreeReaderRejectsIntWhenNextTokenIsDouble() throws Exception {
    JsonObject json = new JsonObject();
    json.addProperty("value", 42.123);
    JsonTreeReader reader = new JsonTreeReader(json);
    reader.beginObject();
    reader.nextName();
    assertThrows(NumberFormatException.class, reader::nextInt);
  }
}
