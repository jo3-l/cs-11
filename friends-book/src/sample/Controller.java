package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.time.LocalDate;

public class Controller {
    public TextField nameTextField;
    public TextField phoneNumberTextField;
    public TextField emailTextField;
    public DatePicker birthdayPicker;
    public ListView<Friend> friendsView = new ListView<>();

    public TextField selectedNameTextField;
    public TextField selectedPhoneNumberTextField;
    public TextField selectedEmailTextField;
    public DatePicker selectedBirthdayTextField;
    public Text birthdayText;
    public ImageView birthdayIcon;
    public Button updateFriendBtn;
    public Button unfriendBtn;
    public VBox friendInfoVBox;
    public Label friendInformationLabel;

    public Text phoneNumberErrorText;
    public Text nameErrorText;
    public Text emailErrorText;
    public Text birthdayErrorText;

    public void handleCreateFriend(ActionEvent actionEvent) {
        boolean isValid = true; // whether the information submitted was valid

        // verify phone number validity
        String phoneNumber = phoneNumberTextField.getText();
        if (phoneNumber.isEmpty() || Utils.isPhoneNumberValid(phoneNumber)) {
            phoneNumberErrorText.setVisible(false);
        } else {
            phoneNumberErrorText.setVisible(true);
            isValid = false;
        }

        // very email validity
        String email = emailTextField.getText();
        if (email.isEmpty() || Utils.isEmailValid(email)) {
            emailErrorText.setVisible(false);
        } else {
            emailErrorText.setVisible(true);
            isValid = false;
        }

        // make sure name is not empty
        String name = nameTextField.getText();
        if (!name.isEmpty()) {
            nameErrorText.setVisible(false);
        } else {
            nameErrorText.setVisible(true);
            isValid = false;
        }

        // make sure birthday is valid
        LocalDate birthday = birthdayPicker.getValue();
        if (birthday == null || Utils.isBirthdayValid(birthday)) {
            birthdayErrorText.setVisible(false);
        } else {
            birthdayErrorText.setVisible(true);
            isValid = false;
        }

        // stop early if not valid
        if (!isValid) return;

        Friend friend = new Friend(name, phoneNumber, email, birthdayPicker.getValue());
        friendsView.getItems().add(friend);

        // clear fields
        phoneNumberTextField.clear();
        emailTextField.clear();
        nameTextField.clear();
        birthdayPicker.getEditor().clear();
    }

    public void selectFriend(MouseEvent mouseEvent) {
        Friend friend = friendsView.getSelectionModel().getSelectedItem();
        if (friend == null) return;

        // display friend information
        refreshFriendInfo(friend);
        setFriendInformationVisibility(true);
    }

    public void handleUpdateFriend(ActionEvent actionEvent) {
        Friend friend = friendsView.getSelectionModel().getSelectedItem();
        if (friend == null) return;

        // fields are only updated if they are valid
        // this is not excellent UX but I didn't really want to get into more designing with error messages / error alerts

        // set name
        String name = selectedNameTextField.getText();
        if (!name.isEmpty()) {
            friend.setName(name);
        }

        // set phone number
        String phoneNumber = selectedPhoneNumberTextField.getText();
        if (phoneNumber.isEmpty() || Utils.isPhoneNumberValid(phoneNumber)) {
            friend.setPhone(phoneNumber);
        }

        // set email
        String email = selectedEmailTextField.getText();
        if (email.isEmpty() || Utils.isEmailValid(email)) {
            friend.setEmail(email);
        }

        // set birthday
        LocalDate birthday = selectedBirthdayTextField.getValue();
        if (birthday == null || Utils.isBirthdayValid(birthday)) {
            friend.setBirthday(birthday);
        }

        // redisplay friend information
        refreshFriendInfo(friend);
        // refresh the friend list itself in case name was updated
        friendsView.refresh();
    }

    public void handleUnfriend(ActionEvent actionEvent) {
        Friend friend = friendsView.getSelectionModel().getSelectedItem();
        if (friend == null) return;

        friendsView.getItems().remove(friend);

        // make friend information invisible
        setFriendInformationVisibility(false);
        birthdayText.setVisible(false);
        birthdayIcon.setVisible(false);
    }

    private void setFriendInformationVisibility(boolean visible) {
        friendInformationLabel.setVisible(visible);
        friendInfoVBox.setVisible(visible);

        selectedNameTextField.setVisible(visible);
        selectedPhoneNumberTextField.setVisible(visible);
        selectedEmailTextField.setVisible(visible);
        selectedBirthdayTextField.setVisible(visible);

        updateFriendBtn.setVisible(visible);
        unfriendBtn.setVisible(visible);
    }

    private void refreshFriendInfo(Friend friend) {
        selectedNameTextField.setText(friend.getName());
        selectedPhoneNumberTextField.setText(friend.getPhone().isEmpty() ? "n/a" : friend.getPhone());
        selectedEmailTextField.setText(friend.getEmail().isEmpty() ? "n/a" : friend.getEmail());
        selectedBirthdayTextField.setValue(friend.getBirthday());

        if (friend.isBirthdayToday()) {
            birthdayText.setText("It's " + friend.getName() + "'s " + Utils.getOrdinalRepresentation(friend.getAge()) + " birthday today!");
            birthdayText.setVisible(true);
            birthdayIcon.setVisible(true);
        } else {
            birthdayText.setVisible(false);
            birthdayIcon.setVisible(false);
        }
    }
}
