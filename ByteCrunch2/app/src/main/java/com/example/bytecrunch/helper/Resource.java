package com.example.bytecrunch.helper;


// Abstract base class to handle the network resource state
public abstract class Resource<T> {
    private T data;
    private String message;

    /**
     * Constructors
     * @param data
     * @param message
     */
    protected Resource(T data, String message) {
        this.data = data;
        this.message = message;
    }

    protected Resource(T data) {
        this.data = data;
        this.message = null;
    }

    protected Resource() {
        this.data = null;
        this.message = null;
    }


    /**
     * Getters
     * @return
     */
    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }


    // Success subclass

    /**
     * This method handles Success network state
     * @param <T>
     */
    public static class Success<T> extends Resource<T> {
        public Success(T data) {
            super(data);
        }
    }

    /**
     * This method handles Error network state
      * @param <T>
     */
    public static class Error<T> extends Resource<T> {
        public Error(String message, T data) {
            super(data, message);
        }

        public Error(String message) {
            super(null, message);
        }
    }

    /**
     * This method handles Loading network state
     * @param <T>
     */
    public static class Loading<T> extends Resource<T> {
        public Loading() {
            super();
        }
    }
}

