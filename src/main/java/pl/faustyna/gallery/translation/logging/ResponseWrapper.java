package pl.faustyna.gallery.translation.logging;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ResponseWrapper extends HttpServletResponseWrapper {

    protected ByteArrayOutputStream byteArrayOutputStream;

    public ResponseWrapper(final HttpServletResponse response) {
        super(response);
        byteArrayOutputStream = new ByteArrayOutputStream();
    }

    public byte[] getByteArray() {
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public String toString() {
        return byteArrayOutputStream.toString();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new ResponseOutputStream(super.getOutputStream(), byteArrayOutputStream);
    }
}