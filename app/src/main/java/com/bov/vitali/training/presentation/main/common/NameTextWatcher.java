package com.bov.vitali.training.presentation.main.common;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;

public class NameTextWatcher implements TextWatcher {
    private TextInputLayout input;
    private Button btnValidity;

    public NameTextWatcher(TextInputLayout input, Button btnValidity) {
        this.input = input;
        this.btnValidity = btnValidity;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        checkValidation();
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (!Validation.isNameValid(editable.toString())) {
            input.setError("Введите имя, не менее 2 букв");
        } else input.setErrorEnabled(false);
    }

    private void checkValidation() {
        if (isDataValid(input)) {
            btnValidity.setEnabled(true);
        } else {
            btnValidity.setEnabled(false);
        }
    }

    private boolean isDataValid(TextInputLayout inputName) {
        String name = inputName.getEditText().getText().toString();
        return Validation.isNameValid(name);
    }
}