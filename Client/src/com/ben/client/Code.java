package com.ben.client;


public enum Code {
    AcknowledgeMessage("FKDY-9iaN-h4gS-Mq0C-PQZX"),
    UnsuccessfulRegistration("0"),
    SuccessfulRegistration("1"),
    MessageTooLong("unKtaYkZ7MWgsZjET8tFPJRCSMkRf9oqjvCxSrlyzm39o5RvlRnFLnKp4yZebboo1NwME1wECMQEGKpzDrFvxDzDBplCwvlXSMzkWKXahFuhpS8k2V3VG6IjiVVko3QiRr4AM5ibL6RY7IIDYxITAw1JhMexWcqDpj1x9RrPu0Nb3j0csHSGavTAhb3tnVztvVSsZXuXJgPqiG2PwYKE9SgiCxGMwn1JKg2s3n9maVjVLxhiaSrxI653D8xXGb3WFSnkNp9XXt9gITAmAGGKZcZvT5jpKZ51Vv8hnpIAlXw0yZ5iAZs4TVVbBtvhU0MVHJknGBESsrC2C0r7SqmtxsYNfCvoZQbBwDqYKQ8Twl88K2uPKJH1sBjwsH6irupR6o9ttiYEtbfY3uU8v90BN3IwsXFuOFQtvN1izHe4lW1Q3mEK4SrBUaq8OsaA7EjGgHGaJXCcuLDgCwEb4PbTv0xOulfO0QxbI1KQHi9V3Q13mMPhAFs0C");

    private final String text;


    Code(final String text) {
        this.text = text;
    }

    @Override
    public String toString(){
        return text;
    }
}
