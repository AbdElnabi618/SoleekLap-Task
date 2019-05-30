package com.kh618.soleektask.Registration;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public interface RegistrationView {
    /**
     * show Error message to user
     * @param msg , error message
     */
    void OnRegistrationFailed(String msg);

    /**
     * when registration Succeed do some listener
     */
    void OnRegistrationSucceed();

    /**
     * appear progress dialog to user
     */
    void ShowProgress();

    /**
     * hide progress dialog
     */
    void HideProgress();

    /**
     * open google window auth
     * @param client , used to get Intent
     */
    void openGoogleSignInWindow(GoogleSignInClient client);
}
