package com.theme.keyboardthemeapp;

import android.app.Application;

public class App extends Application {
    public static App App = null;

    public static App getInstance() {
        return App;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        App = this;

     /*   [10:23 pm, 06/10/2023] Client Hirenbhai Mavani: Quotes_List :=
        http://technoappsolution.com/app/assets/android/hindikeyboard/get_categories.json
        Quote : webapi

        https://hpqrzfjgciltygmpyjss.supabase.co/rest/v1/QuoteCategories?select=*
        Quote : =
        http://technoappsolution.com/app/assets/android/hindikeyboard/1.json

        https://hpqrzfjgciltygmpyjss.supabase.co/rest/v1/QuotesList?parent_id=eq.1(chnages 1,2,3)

[10:43 pm, 06/10/2023] Client Hirenbhai Mavani: Keyboard :

        http://technoappsolution.com/app/assets/android/keyboard/keyboard.json

        https://hpqrzfjgciltygmpyjss.supabase.co/rest/v1/keybord_fileparth?select=*,keyboard(id,name)

        https://hpqrzfjgciltygmpyjss.supabase.co/storage/v1/object/public/keybord/bg/kbd_1.png
        https://hpqrzfjgciltygmpyjss.supabase.co/storage/v1/object/public/keybord/thumb/kbd_1.png
[10:50 pm, 06/10/2023] Client Hirenbhai Mavani: Gif Api :
        http://technoappsolution.com/app/assets/android/hindikeyboard/hindithemekeyboard.json

        https://hpqrzfjgciltygmpyjss.supabase.co/rest/v1/keybord_fileparth?select=*,hindithemekeyboard(id,name)

        http://technoappsolution.com/app/assets/android/hindikeyboard/thumb/1.png

        https://hpqrzfjgciltygmpyjss.supabase.co/storage/v1/object/public/keybord/thumb/1.png


        http://technoappsolution.com/app/assets/android/hindikeyboard/bg/1.gif

        https://hpqrzfjgciltygmpyjss.supabase.co/storage/v1/object/public/keybord/bg/1.gif*/
    }
}
