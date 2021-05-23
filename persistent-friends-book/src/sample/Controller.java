package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

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
    public TextField filePathTextField;

    public void handleCreateFriend(ActionEvent actionEvent) {
        boolean isValid = true;

        String phoneNumber = phoneNumberTextField.getText();
        if (phoneNumber.isEmpty() || Utils.isPhoneNumberValid(phoneNumber)) {
            phoneNumberErrorText.setVisible(false);
        } else {
            phoneNumberErrorText.setVisible(true);
            isValid = false;
        }

        String email = emailTextField.getText();
        if (email.isEmpty() || Utils.isEmailValid(email)) {
            emailErrorText.setVisible(false);
        } else {
            emailErrorText.setVisible(true);
            isValid = false;
        }

        String name = nameTextField.getText();
        if (!name.isEmpty()) {
            nameErrorText.setVisible(false);
        } else {
            nameErrorText.setVisible(true);
            isValid = false;
        }

        LocalDate birthday = birthdayPicker.getValue();
        if (birthday == null || Utils.isBirthdayValid(birthday)) {
            birthdayErrorText.setVisible(false);
        } else {
            birthdayErrorText.setVisible(true);
            isValid = false;
        }

        if (!isValid) return;

        Friend friend = new Friend(name, phoneNumber, email, birthdayPicker.getValue());
        friendsView.getItems().add(friend);

        phoneNumberTextField.clear();
        emailTextField.clear();
        nameTextField.clear();
        birthdayPicker.getEditor().clear();
    }

    public void selectFriend(MouseEvent mouseEvent) {
        Friend friend = friendsView.getSelectionModel().getSelectedItem();
        if (friend == null) return;

        refreshFriendInfo(friend);
        setFriendInformationVisibility(true);
    }

    public void handleUpdateFriend(ActionEvent actionEvent) {
        Friend friend = friendsView.getSelectionModel().getSelectedItem();
        if (friend == null) return;

        String name = selectedNameTextField.getText();
        if (!name.isEmpty()) {
            friend.setName(name);
        }

        String phoneNumber = selectedPhoneNumberTextField.getText();
        if (phoneNumber.isEmpty() || Utils.isPhoneNumberValid(phoneNumber)) {
            friend.setPhone(phoneNumber);
        }

        String email = selectedEmailTextField.getText();
        if (email.isEmpty() || Utils.isEmailValid(email)) {
            friend.setEmail(email);
        }

        LocalDate birthday = selectedBirthdayTextField.getValue();
        if (birthday == null || Utils.isBirthdayValid(birthday)) {
            friend.setBirthday(birthday);
        }

        refreshFriendInfo(friend);
        friendsView.refresh();
    }

    public void handleUnfriend(ActionEvent actionEvent) {
        Friend friend = friendsView.getSelectionModel().getSelectedItem();
        if (friend == null) return;

        friendsView.getItems().remove(friend);

        setFriendInformationVisibility(false);
        birthdayText.setVisible(false);
        birthdayIcon.setVisible(false);
    }

    public void loadFriendsFromFile(ActionEvent actionEvent) {
        String file = filePathTextField.getText();
        try {
            List<Friend> friends = DatabaseUtils.readFile(file);
            friendsView.getItems().clear();
            friendsView.getItems().addAll(friends);
            // TODO: show done
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: show error
        } finally {
            filePathTextField.clear();
        }
    }

    public void saveFriendsToFile(ActionEvent actionEvent) {
        String file = filePathTextField.getText();
        try {
            DatabaseUtils.writeFile(file, friendsView.getItems());
            // TODO: show done
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: show error
        } finally {
            filePathTextField.clear();
        }
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
