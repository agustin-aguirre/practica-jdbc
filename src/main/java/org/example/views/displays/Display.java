package org.example.views.displays;

public interface Display {
    void print(String msg);
    void println(String msg);
    String readString(String msg);
    int readInt(String msg);
    void flush();
}
