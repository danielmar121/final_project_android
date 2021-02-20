package com.daniel.final_project.activities.shared.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.daniel.final_project.R;
import com.daniel.final_project.interfaces.ObjectCallBack;
import com.daniel.final_project.objects.User;
import com.daniel.final_project.services.MyFireBase;


public class ProfileFragment extends Fragment {
    private View root;
    private MyFireBase myFireBase;
    private TextView shared_profile_ETXT_first_name, shared_profile_ETXT_last_name, shared_profile_ETXT_email, shared_profile_ETXT_phone;
    private Button shared_profile_BTN_save;
    private User user;

    private ObjectCallBack userCallBack = new ObjectCallBack() {
        @Override
        public void sendObjectToActivity(Object object) {
            user = (User) object;
            shared_profile_ETXT_first_name.setText(user.getFirstName());
            shared_profile_ETXT_last_name.setText(user.getLastName());
            shared_profile_ETXT_email.setText(user.getEmail());
            shared_profile_ETXT_phone.setText(user.getPhone());
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_profile_shared, container, false);
        myFireBase = MyFireBase.getInstance();

        findViews();
        initViews();

        return root;
    }

    private void initViews() {
        myFireBase.getUser(userCallBack);

        shared_profile_BTN_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (updateUser()) {
                    Toast.makeText(getContext(), "Successfully update user details", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Failed to update user details", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean updateUser() {
        String firstName = shared_profile_ETXT_first_name.getText().toString();
        String lastName = shared_profile_ETXT_last_name.getText().toString();
        String email = shared_profile_ETXT_email.getText().toString();
        String phoneNumber = shared_profile_ETXT_phone.getText().toString();

        if (firstName.matches("") || lastName.matches("") || email.matches("") || phoneNumber.matches("")) {
            return false;
        }

        user.setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setPhone(phoneNumber);

        myFireBase.updateUser(user);
        return true;
    }

    private void findViews() {
        shared_profile_ETXT_first_name = root.findViewById(R.id.shared_profile_ETXT_first_name);
        shared_profile_ETXT_last_name = root.findViewById(R.id.shared_profile_ETXT_last_name);
        shared_profile_ETXT_email = root.findViewById(R.id.shared_profile_ETXT_email);
        shared_profile_ETXT_phone = root.findViewById(R.id.shared_profile_ETXT_phone);

        shared_profile_BTN_save = root.findViewById(R.id.shared_profile_BTN_save);
    }
}