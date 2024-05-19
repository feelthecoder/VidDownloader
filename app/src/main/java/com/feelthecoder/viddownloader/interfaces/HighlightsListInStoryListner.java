/*
 * *
 *  * Created by Debarun Lahiri on 1/31/23, 11:56 PM
 *  * Copyright (c) 2023 . All rights reserved.
 *  * Last modified 1/31/23, 11:53 PM
 *
 */

package com.feelthecoder.viddownloader.interfaces;


import com.feelthecoder.viddownloader.models.storymodels.ModelHighlightsUsrTray;

public interface HighlightsListInStoryListner {
    void onclickUserHighlightsListItem(int position, ModelHighlightsUsrTray modelUsrTray);
}
