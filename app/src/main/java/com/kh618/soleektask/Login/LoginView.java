package com.kh618.soleektask.Login;

public interface LoginView {
    /**
     * show the error message to user
     * @param msg, Error Message
     */
    void LoginFailed(String msg);

    /**
     * open Home Activity
     */
    void StartHomeScreen();

    /**
     * open Registration Activity
     */
    void StartRegistrationScreen();

    /**
     * appear loading progress dialog to user
     */
    void ShowLoginProgress();

    /**
     * hide the progress dialog
     */
    void HideLoginProgress();

}
