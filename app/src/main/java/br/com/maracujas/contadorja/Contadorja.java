package br.com.maracujas.contadorja;

import com.firebase.client.Firebase;

/**
 * Created by julio on 17/05/2016.
 */
public class Contadorja extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
