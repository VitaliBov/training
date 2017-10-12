package com.bov.vitali.training.presentation.main.view;

import com.bov.vitali.training.data.model.Publication;
import com.bov.vitali.training.presentation.base.view.BaseView;

import java.util.List;

public interface PublicationsView extends BaseView {

    void setPublications(List<Publication> publications);

    void showUpdatingSpinner();

    void hideUpdatingSpinner();
}