package com.google.gson.functional;

import static org.junit.Assert.assertThrows;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import java.io.IOException;
import java.io.Reader;
import org.junit.Test;

// https://github.com/google/gson/issues/548:
// Gson.fromJson(Reader, Type) should wrap underlying IOExceptions in JsonIOException,
// not JsonSyntaxException. Network and I/O errors are distinct from JSON syntax errors.
public class IoExceptionWrappedInJsonIoException548Test {
  @Test
  public void testIOExceptionWrappedInJsonIOException() {
    Gson gson = new Gson();
    Reader reader = new Reader() {
      @Override
      public int read(char[] cbuf, int off, int len) throws IOException {
        throw new IOException("simulated IO error");
      }
      @Override
      public void close() {}
    };
    assertThrows(JsonIOException.class, () -> gson.fromJson(reader, Object.class));
  }
}
