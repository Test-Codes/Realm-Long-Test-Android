/*
 * Copyright 2014 Realm Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.realm.examples.intro;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;


public class IntroExampleActivity extends Activity {

    public static final String TAG = IntroExampleActivity.class.getName();
    private LinearLayout rootLayout = null;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realm_basic_example);
        rootLayout = ((LinearLayout) findViewById(R.id.container));
        rootLayout.removeAllViews();

        // These operations are small enough that
        // we can generally safely run them on the UI thread.

        // Open the default realm ones for the UI thread.
        realm = Realm.getInstance(this);


        {
            // Set & read properties
            realm.beginTransaction();
            for (long i = 6473924485019126l; i < 6473924485020369l; i++) {
                Dog mydog = realm.createObject(Dog.class);
                mydog.setKey(i);
                mydog.setName("Rex " + i);
                mydog.setAge(i);
            }
            realm.commitTransaction();

            RealmResults<Dog> dogs = realm.where(Dog.class)
                    .greaterThan("key", 6473924485020126l)
                    .lessThan("key", 6473924485020369l)
                    .findAll();

            showStatus("Number of dogs: " + dogs.size());
            for (int i = 0; i < dogs.size(); i++) {
                Dog dog = dogs.get(i);
                showStatus("Name : " + dog.getName() + " Age : " + dog.getAge() + ", Key : " + dog.getKey());
            }
        }

        {
            realm.beginTransaction();
            for (long i = -100l; i < 100l; i++) {
                Dog mydog = realm.createObject(Dog.class);
                mydog.setKey(i);
                mydog.setName("Rex " + i);
                mydog.setAge(i);
            }
            realm.commitTransaction();

            RealmResults<Dog> dogs = realm.where(Dog.class)
                    .lessThan("key", 9)
                    .findAll();

            showStatus("Number of dogs: " + dogs.size());
            for (int i = 0; i < dogs.size(); i++) {
                Dog dog = dogs.get(i);
                showStatus("Name : " + dog.getName() + " Age : " + dog.getAge() + ", Key : " + dog.getKey());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void showStatus(String txt) {
        Log.i(TAG, txt);
        TextView tv = new TextView(this);
        tv.setText(txt);
        rootLayout.addView(tv);
    }
}
