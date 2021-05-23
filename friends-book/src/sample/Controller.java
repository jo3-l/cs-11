package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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

    public void handleCreateFriend(ActionEvent actionEvent) {
        String phoneNumber = phoneNumberTextField.getText();
        if (!isPhoneNumberValid(phoneNumber)) {
            System.out.println("Invalid phone number");
            return;
        }

        String email = emailTextField.getText();
        if (!isEmailValid(email)) {
            System.out.println("Invalid email");
            return;
        }

        String name = nameTextField.getText();
        if (name.isEmpty()) {
            System.out.println("Empty name");
            return;
        }

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

        if (friend.isBirthdayToday()) {
            birthdayText.setText("It's " + friend.getName() + "'s " + getOrdinalRepresentation(friend.getAge()) + " birthday today!");
            birthdayText.setVisible(true);
            birthdayIcon.setVisible(true);
        }
    }

    public void handleUpdateFriend(ActionEvent actionEvent) {
        Friend friend = friendsView.getSelectionModel().getSelectedItem();
        if (friend == null) return;

        String name = selectedNameTextField.getText();
        if (!name.isEmpty()) {
            friend.setName(name);
        }

        String phoneNumber = selectedPhoneNumberTextField.getText();
        if (isPhoneNumberValid(phoneNumber)) {
            friend.setPhone(phoneNumber);
        } else {
            System.out.println("Invalid phone number");
        }

        String email = selectedEmailTextField.getText();
        if (isEmailValid(email)) {
            friend.setEmail(email);
        } else {
            System.out.println("Invalid email");
        }

        friend.setBirthday(selectedBirthdayTextField.getValue());

        refreshFriendInfo(friend);
    }

    public void handleUnfriend(ActionEvent actionEvent) {
        Friend friend = friendsView.getSelectionModel().getSelectedItem();
        if (friend == null) return;

        friendsView.getItems().remove(friend);

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
            birthdayText.setText("It's " + friend.getName() + "'s " + getOrdinalRepresentation(friend.getAge()) + " birthday today!");
            birthdayText.setVisible(true);
            birthdayIcon.setVisible(true);
        } else {
            birthdayText.setVisible(false);
            birthdayIcon.setVisible(false);
        }
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        return phoneNumber.isEmpty() || phoneNumber.matches("\\d{3}-\\d{3}-\\d{4}");
    }

    private boolean isEmailValid(String email) {
        return email.isEmpty() || email.contains("@");
    }

    private String getOrdinalRepresentation(int num) {
        String numStr = Integer.toString(num);
        int cent = num % 100;
        int dec = num % 10;

        // 10s are always -th
        if (cent >= 10 && cent <= 19) return numStr + "th";

        switch (dec) {
            case 1:
                return numStr + "st";
            case 2:
                return numStr + "nd";
            case 3:
                return numStr + "rd";
            default:
                return numStr + "th";
        }
    }
}
