/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.romanhawk.android.jitsimeet.restrictiontest;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * This fragment provides UI and functionality to set restrictions on the AppRestrictionSchema
 * sample.
 */
public class AppRestrictionEnforcerFragment extends Fragment implements View.OnClickListener {

    /**
     * Key for {@link SharedPreferences}
     */
    private static final String PREFS_KEY = "AppRestrictionEnforcerFragment";
    private static final String RESTRICTION_KEY_SERVER_URL = "SERVER_URL";

    // UI Components
    private EditText serverUrlEdit;
    private Button submitButton;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_app_restriction_enforcer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // Retain references for the UI elements
        serverUrlEdit = view.findViewById(R.id.server_url);

        submitButton = view.findViewById(R.id.submit);
        submitButton.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        loadRestrictions(getActivity());
    }

    /**
     * Loads the restrictions for the AppRestrictionSchema sample.
     *
     * @param activity The activity
     */
    private void loadRestrictions(Activity activity) {
        SharedPreferences prefs = activity.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);

        serverUrlEdit.setText(prefs.getString(RESTRICTION_KEY_SERVER_URL, getString(R.string.default_server_url)));
    }

    private void saveRestrictions(Activity activity, String serverUrl) {
        final Bundle currentRestrictions = new Bundle();

        currentRestrictions.putString(RESTRICTION_KEY_SERVER_URL, serverUrl);

        DevicePolicyManager devicePolicyManager
                = (DevicePolicyManager) activity.getSystemService(Context.DEVICE_POLICY_SERVICE);
        devicePolicyManager.setApplicationRestrictions(
                EnforcerDeviceAdminReceiver.getComponentName(activity),
                Constants.PACKAGE_NAME_APP_RESTRICTION_SCHEMA, currentRestrictions);

        // store restrictions in current preferences
        SharedPreferences.Editor prefs = editPreferences(activity);
        prefs.putString(RESTRICTION_KEY_SERVER_URL, serverUrl);
        prefs.apply();
    }

    private SharedPreferences.Editor editPreferences(Activity activity) {
        return activity.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE).edit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit: {
                saveRestrictions(getActivity(), serverUrlEdit.getText().toString());
                Toast.makeText(getContext(), R.string.restrictions_sent_confirmation, Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
}
