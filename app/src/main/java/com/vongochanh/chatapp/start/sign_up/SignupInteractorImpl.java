package com.vongochanh.chatapp.start.sign_up;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.vongochanh.chatapp.base.Constant;
import com.vongochanh.chatapp.model.User;

import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vo Ngoc Hanh on 8/22/2017.
 */

public class SignupInteractorImpl implements SignupContract.SignupInteractor {
    private final String TAG = getClass().getSimpleName();

    //Firebase
    FirebaseAuth mAuth;

    SignupContract.SignupInteractor.SignupProgressListener mListener;

    public SignupInteractorImpl(SignupContract.SignupInteractor.SignupProgressListener listener) {
        mListener = listener;
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void createAccount(final String userEmail, String password) {
        if (mAuth != null) {
            mAuth.createUserWithEmailAndPassword(userEmail,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful() && mListener != null) {
                                //write info of new user to database
                                writeNewUserInfo(userEmail);

                                mListener.onSuccess();
                            }else{
                                Log.d(TAG, "Exception: " + task.getException());
                                mListener.onFailed();
                            }
                        }
                    });
        }
    }

    private void writeNewUserInfo(String userEmail) {
        User newUser = new User(userEmail, "" /*icon link*/);
        Map<String, Object> map = new HashMap<>();
        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference().child(Constant.USERS_NODE);
        String tempKey = mDatabaseRef.push().getKey();
        map.put(tempKey, newUser);
        mDatabaseRef.updateChildren(map);

//        searchUserByEmail(mDatabaseRef, userEmail);
    }

    public void searchUserByEmail(DatabaseReference mDatabaseRef, String emailUser) {
        Query query = mDatabaseRef.orderByChild(Constant.USER_EMAIL_NODE).startAt(emailUser).endAt(emailUser);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Log.d(TAG, "Email user: " + childSnapshot.child(Constant.USER_EMAIL_NODE));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Creating user info in database failed: " + databaseError.getMessage());
            }
        });
    }
}
