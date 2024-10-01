package com.example.Restaurantto.PDV.exception;

public class EmailAlreadyRegisteredException extends RuntimeException{


        public EmailAlreadyRegisteredException(String message) {
            super(message);
        }

        public EmailAlreadyRegisteredException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
