package com.google.gson.functional;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.MalformedJsonException;
import java.io.IOException;
import java.io.StringReader;
import org.junit.Test;

// https://github.com/google/gson/issues/1735: when JsonReader.peek() encounters malformed
// JSON and throws, it has already advanced the reader's internal position -- so retrying
// or continuing to read after catching the exception desyncs from the real character
// stream instead of leaving the reader's state untouched.
public class JsonReaderPeekAdvances1735Test {
  @Test
  public void testPeekDoesNotAdvanceReaderStateWhenThrowing() throws IOException {
    JsonReader reader = new JsonReader(new StringReader("[a?$,1]"));
    reader.beginArray();
    for (int i = 0; i < 4; i++) {
      try {
        reader.peek();
      } catch (MalformedJsonException expected) {
        // expected
      }
    }
    reader.nextInt();
    reader.endArray();
  }
}
