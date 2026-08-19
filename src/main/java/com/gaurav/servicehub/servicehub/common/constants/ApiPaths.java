package com.gaurav.servicehub.servicehub.common.constants;

public final class ApiPaths {

    private ApiPaths() {
    }

    public static final String API_V1 = "/api/v1";

    // ==========================================
    // AUTHENTICATION
    // ==========================================

    public static final String AUTH =
            API_V1 + "/auth";

    public static final String REGISTER =
            "/register";

    public static final String LOGIN =
            "/login";

    public static final String ME =
            "/me";

    public static final String REFRESH =
            "/refresh";

    public static final String LOGOUT =
            "/logout";


    // ==========================================
    // USER
    // ==========================================

    public static final String USERS =
            API_V1 + "/users";


    // ==========================================
    // PROVIDER
    // ==========================================

    public static final String PROVIDERS =
            API_V1 + "/providers";


    // ==========================================
    // SERVICE
    // ==========================================

    public static final String SERVICES =
            API_V1 + "/services";


    // ==========================================
    // BOOKING
    // ==========================================

    public static final String BOOKINGS =
            API_V1 + "/bookings";


    // ==========================================
    // PAYMENT
    // ==========================================

    public static final String PAYMENTS =
            API_V1 + "/payments";


    // ==========================================
    // REVIEW
    // ==========================================

    public static final String REVIEWS =
            API_V1 + "/reviews";


    // ==========================================
    // ADMIN
    // ==========================================

    public static final String ADMIN =
            API_V1 + "/admin";
}