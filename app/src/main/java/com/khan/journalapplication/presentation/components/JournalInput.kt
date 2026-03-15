package com.khan.journalapplication.presentation.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import com.khan.journalapplication.model.Journal

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)


@Composable
fun JournalInputText(
    modifier: Modifier = Modifier,
    text: MutableState<String>,
    label:String,
    onTextChange:(String)->Unit,
    onImeAction:()->Unit={}){
    val keyboardController = LocalSoftwareKeyboardController.current


    OutlinedTextField(
        value = text.value,
        onValueChange = {
            text.value = it
            onTextChange(it)
        },
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            onImeAction()
            keyboardController?.hide()
        }),
        modifier = modifier
    )
}
