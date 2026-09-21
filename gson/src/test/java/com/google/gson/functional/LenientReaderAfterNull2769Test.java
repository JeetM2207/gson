package com.google.gson.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.Strictness;
import java.io.StringReader;
import java.io.StringWriter;
import org.junit.Test;

// https://github.com/google/gson/issues/2769: with a LENIENT JsonReader, writing a null
// followed by a string value and then reading them back returns the null correctly but
// then reads the following string value as garbage (throws or returns a mangled value)
// instead of the actual string -- lenient mode desyncs its position tracking right after
// a null.
public class LenientReaderAfterNull2769Test {
  @Test
  public void testLenientReaderCorrectlyReadsValueAfterNull() throws Exception {
    StringWriter str = new StringWriter();
    try (JsonWriter writer = new JsonWriter(str)) {
      writer.setStrictness(Strictness.LENIENT);
      TypeAdapters.STRING.write(writer, null);
      TypeAdapters.STRING.write(writer, "value1");
      writer.flush();
    }

    JsonReader reader = new JsonReader(new StringReader(str.toString()));
    reader.setStrictness(Strictness.LENIENT);

    assertNull(TypeAdapters.STRING.read(reader));
    assertEquals("value1", TypeAdapters.STRING.read(reader));
  }
}
