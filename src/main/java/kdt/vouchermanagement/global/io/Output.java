package kdt.vouchermanagement.global.io;


import kdt.vouchermanagement.global.response.Response;

public interface Output {

    void printMenu(String message);

    void printResponse(Response response);
}
