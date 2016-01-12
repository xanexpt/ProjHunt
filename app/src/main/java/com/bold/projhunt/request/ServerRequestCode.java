package com.bold.projhunt.request;

/**
 * Created by bruno.marques on 12/07/2015.
 */
public enum ServerRequestCode {

    UNKNOWN(0),
    GET_TOKEN(1);

    private final int mServerRequestCodeId;

    ServerRequestCode(final int stateId) {
        mServerRequestCodeId = stateId;
    }

    public final int getServerRequestCodeId() {
        return mServerRequestCodeId;
    }

    public static ServerRequestCode fromInt(final int stateCode) {
        for (ServerRequestCode statesResponse : ServerRequestCode.values()) {
            if (stateCode == statesResponse.mServerRequestCodeId) {
                return statesResponse;
            }
        }
        return UNKNOWN;
    }
}
