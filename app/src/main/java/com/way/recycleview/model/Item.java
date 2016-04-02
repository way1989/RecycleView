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

package com.way.recycleview.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.way.recycleview.R;


public class Item {

    public static Item[] ITEMS = new Item[]{
            new Item(R.drawable.taeyeon, R.string.taeyeon, R.string.taeyeon_detail),
            new Item(R.drawable.jessica, R.string.jessica, R.string.jessica_detail),
            new Item(R.drawable.sunny, R.string.sunny, R.string.sunny_detail),
            new Item(R.drawable.tiffany, R.string.tiffany, R.string.tiffany_detail),
            new Item(R.drawable.yuri, R.string.yuri, R.string.yuri_detail),
            new Item(R.drawable.yoona, R.string.yoona, R.string.yoona_detail)
    };

    private final int mPhoto;
    private final int mAuthor;
    private final int mAuthorDetail;
    private final int mHeight;

    Item(@DrawableRes int photo, @StringRes int author, @StringRes int authorDetail) {
        mPhoto = photo;
        mAuthor = author;
        mAuthorDetail = authorDetail;
        mHeight = (int) (300 + Math.random() * 300);
    }

    @DrawableRes
    public int getPhoto() {
        return mPhoto;
    }

    @StringRes
    public int getAuthor() {
        return mAuthor;
    }

    @StringRes
    public int getAuthorDetail() {
        return mAuthorDetail;
    }

    public int getHeight() {
        return mHeight;
    }
}
