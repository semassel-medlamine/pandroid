package com.leroymerlin.pandroid.demo;

import com.leroymerlin.pandroid.PandroidApplication;
import com.leroymerlin.pandroid.dagger.BaseComponent;
import com.leroymerlin.pandroid.dagger.PandroidModule;
import com.leroymerlin.pandroid.security.PandroidX509TrustManager;

import java.util.List;

import javax.net.ssl.KeyManager;
import javax.net.ssl.TrustManager;

/**
 * Created by florian on 04/01/16.
 */
public class DemoApplication extends PandroidApplication {

    //tag::createBaseComponent[]
    @Override
    protected BaseComponent createBaseComponent() {
        return DaggerDemoComponent.builder()
                .demoModule(new DemoModule())
                .pandroidModule(new PandroidModule(this) {
                    //tag::certificat[]
                    @Override
                    protected List<TrustManager> getTrustManagers() {
                        List<TrustManager> keyManagers = super.getTrustManagers();
                        PandroidX509TrustManager trustManager = new PandroidX509TrustManager(DemoApplication.this);
                        trustManager.addCertificate("leroymerlin.com", R.raw.lmfr_cert);// add certificate for your application
                        keyManagers.add(trustManager);
                        return keyManagers;
                    }
                    @Override
                    protected List<KeyManager> getKeyManagers() {
                        return super.getKeyManagers(); // here you can add you own key manager
                    }
                    //end::certificat[]
                })
                .build();
    }
    //end::createBaseComponent[]
}
