package com.bov.vitali.training.presentation.main.view;

import com.bov.vitali.training.presentation.base.view.BaseView;

public interface DatabaseContract {

    interface View extends BaseView {
        void clearSaveFields();

        void clearUpdateFields();

        void clearDeleteFields();

        void clearSearchFields();

        void showSaveMessage(String result);

        void showUpdateMessage(String result);

        void showDeleteMessage(String result);

        void showSearchResult(String result);
    }

    interface Presenter {
        void save(String username, String city);

        void update(String oldUsername, String newUsername);

        void delete(String username);

        void search(String username);
    }
}