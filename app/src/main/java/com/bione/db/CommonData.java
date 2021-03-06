package com.bione.db;

import com.bione.model.customerdata.Customer;
import com.bione.model.salesdetail.Data;
import com.kbeanie.multipicker.api.entity.ChosenImage;

import java.util.List;

import io.paperdb.Paper;

/**
 * Developer: Bione
 */
public final class CommonData {

    private static final String PAPER_GUEST = "paper_guest";
    private static final String PAPER_DEVICE_TOKEN = "paper_device_token";
    private static final String PAPER_ADMIN_TOKEN = "paper_admin_token";
    private static final String PAPER_CUSTOMER_TOKEN = "paper_customer_token";
    private static final String PAPER_LOGIN_DATA = "paper_login_data";
    private static final String PAPER_USER_DATA = "paper_user_data";
    private static final String PAPER_USER_PHOTO = "paper_user_photo";
    private static final String PAPER_SALES_PERSON_DATA = "paper_sales_person_data";


    /**
     * Save user data.
     *
     * @param mUserData the m user data
     */
    public static void saveUserData(final Customer mUserData) {
        if (mUserData == null) {
            return;
        }

        Paper.book().write(PAPER_USER_DATA, mUserData);
    }

    /**
     * Get user data user data.
     *
     * @return the user data
     */
    public static Customer getUserData() {
        return Paper.book().read(PAPER_USER_DATA);
    }


    /**
     * Prevent instantiation
     */
    private CommonData() {
    }

    /**
     * Update loginData.
     *
     * @param loginData the loginData
     */
    public static void updateLoginData(final String loginData) {
        Paper.book().write(PAPER_LOGIN_DATA, loginData);
    }

    /**
     * Gets loginData.
     *
     * @return the loginData
     */
    public static String getLoginData() {
        return Paper.book().read(PAPER_LOGIN_DATA);
    }


    /**
     * Update admin token.
     *
     * @param token the admin token
     */
    public static void updateAdminToken(final String token) {
        Paper.book().write(PAPER_ADMIN_TOKEN, token);
    }

    /**
     * Gets guest token.
     *
     * @return the guest token
     */
    public static boolean getGuest() {
        return Paper.book().read(PAPER_GUEST);
    }
    /**
     * Update guest token.
     *
     * @param isGuest the guest token
     */
    public static void updateGuest(final boolean isGuest) {
        Paper.book().write(PAPER_GUEST, isGuest);
    }

    /**
     * Gets admin token.
     *
     * @return the admin token
     */
    public static String getAdminToken() {
        return Paper.book().read(PAPER_ADMIN_TOKEN);
    }



    /**
     * Get sales data sales data.
     *
     * @return the sales data
     */
    public static Data getSalesData() {
        return Paper.book().read(PAPER_SALES_PERSON_DATA);
    }
    /**
     * Save sales data.
     *
     * @param mSalesData the m sales data
     */
    public static void saveSalesData(final Data mSalesData) {
        if (mSalesData == null) {
            return;
        }

        Paper.book().write(PAPER_SALES_PERSON_DATA, mSalesData);
    }



    /**
     * Update customer token.
     *
     * @param token the customer token
     */
    public static void updateCustomerToken(final String token) {
        Paper.book().write(PAPER_CUSTOMER_TOKEN, token);
    }

    /**
     * Gets customer token.
     *
     * @return the customer token
     */
    public static String getCustomerToken() {
        return Paper.book().read(PAPER_CUSTOMER_TOKEN);
    }


    /**
     * Update fcm token.
     *
     * @param token the fcm token
     */
    public static void updateFcmToken(final String token) {
        Paper.book().write(PAPER_DEVICE_TOKEN, token);
    }

    /**
     * Gets fcm token.
     *
     * @return the fcm token
     */
    public static String getFcmToken() {
        return Paper.book().read(PAPER_DEVICE_TOKEN);
    }

    /**
     * Update photo token.
     *
     * @param photo the photo
     */
    public static void updateUserPhoto(final List<ChosenImage> token) {
        Paper.book().write(PAPER_USER_PHOTO, token);
    }

    /**
     * Gets photo .
     *
     * @return the photo
     */
    public static List<ChosenImage> getUserPhoto() {
        return Paper.book().read(PAPER_USER_PHOTO);
    }

}
