package com.pro.powerworkouts.ui;

import static org.junit.Assert.*;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pro.powerworkouts.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class SignUpActivityTest {
  private SignUpActivity activity;
  
  @Before
  public void setUp()  {
    activity = Robolectric.buildActivity(SignUpActivity.class)
            .create()
            .start()
            .resume()
            .get();
  }

  @Test
  public void validateTextContent() {
    TextView title = activity.findViewById(R.id.sign_up_title);
    TextView subtitle = activity.findViewById(R.id.sign_up_subtitle);

    assertEquals(activity.getString(R.string.sign_up), title.getText());
    assertEquals(activity.getString(R.string.to_begin_kindly_create_an_acount), subtitle.getText());
  }

  @Test
  public void validateEditText() {
    EditText nameEditText = activity.findViewById(R.id.nameEdit);
    EditText emailEditText = activity.findViewById(R.id.emailEdit);
    EditText passwordEditText = activity.findViewById(R.id.passWord);
    EditText confirmPasswordEditText = activity.findViewById(R.id.confirmPassWord);

    assertEquals("Name", nameEditText.getHint());
    assertEquals("Email", emailEditText.getHint());
    assertEquals("Password", passwordEditText.getHint());
    assertEquals("Confirm password", confirmPasswordEditText.getHint());
  }

  @Test
  public void validateButton() {
    Button signUpButton = activity.findViewById(R.id.createUser);
    assertEquals(activity.getString(R.string.sign_up), signUpButton.getText());
  }
}