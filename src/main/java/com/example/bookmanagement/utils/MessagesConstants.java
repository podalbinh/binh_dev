package com.example.bookmanagement.utils;

import org.springframework.stereotype.Component;

/**
 * A class that holds constant keys for validation and error messages used throughout the application.
 */
@Component
public class MessagesConstants {

    /**
     * Key for the error message indicating that the transaction name is mandatory.
     */
    public static final String TRANSACTION_NAME_MANDATORY = "transaction.name.mandatory";

    /**
     * Key for the error message indicating that the transaction name must be less than a specified number of characters.
     */
    public static final String TRANSACTION_NAME_SIZE = "transaction.name.size";

    /**
     * Key for the error message indicating that the birth date is mandatory.
     */
    public static final String BIRTH_DATE_MANDATORY = "transaction.birthDate.mandatory";

    /**
     * Key for the error message indicating that the birth date must be in the past or present.
     */
    public static final String BIRTH_DATE_PAST_OR_PRESENT = "transaction.birthDate.pastOrPresent";

    /**
     * Key for the error message indicating that the nationality is mandatory.
     */
    public static final String NATIONALITY_MANDATORY = "transaction.nationality.mandatory";

    /**
     * Key for the error message indicating that the nationality must be less than a specified number of characters.
     */
    public static final String NATIONALITY_SIZE = "transaction.nationality.size";

    /**
     * Key for the error message indicating that the description must be less than a specified number of characters.
     */
    public static final String DESCRIPTION_SIZE = "transaction.description.size";

    /**
     * Key for the error message indicating that validation has failed.
     */
    public static final String VALIDATION_FAILED_MESSAGE = "validation.failed";

    /**
     * Key for the error message indicating that an transaction with the specified ID was not found.
     */
    public static final String TRANSACTION_NOT_FOUND_ERROR = "transaction.not-found";
    
    /**
     * Constant representing the success message template for transaction deletion.
     * Use with String.format to provide the specific transaction ID.
     */
    public static final String TRANSACTION_DELETE_SUCCESS = "transaction.delete.success";

    /**
     * Constant representing the success message template for transaction update.
     * Use with String.format to provide the specific transaction ID.
     */
    public static final String TRANSACTION_UPDATE_SUCCESS = "transaction.update.success";



    /**
     * Key for the error message indicating that an book with the specified ID was not found.
     */
    public static final String BOOK_NOT_FOUND_ERROR = "book.not-found";

    /**
     * Key for the error message indicating that title must be less than a specified number of characters.
     */
    public static final String BOOK_TITLE_SIZE = "book.title.size";

    /**
     * Key for the error message indicating that title is mandatory.
     */
    public static final String BOOK_TITLE_MANDATORY = "book.title.mandatory";

    /**
     * Key for the error message indicating that published date is mandatory.
     */
    public static final String BOOK_PUBLISHEDDATE_MANDATORY = "book.publishedDate.mandatory";

    /**
     * Key for the error message indicating that genre must be less than a specified number of characters.
     */
    public static final String BOOK_GENRE_SIZE = "book.genre.size";

    /**
     * Key for the error message indicating that genre is mandatory.
     */
    public static final String BOOK_GENRE_MANDATORY = "book.genre.mandatory";

}
