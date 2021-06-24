package liberryan;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import liberryan.validation.BookValidator;
import liberryan.validation.ErrorNotificationSynchronizer;
import liberryan.validation.ValidationErrorList;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private final ErrorNotificationSynchronizer<Book.Field> createBookErrorSynchronizer
            = new ErrorNotificationSynchronizer<>();
    private final ErrorNotificationSynchronizer<Book.Field> editBookErrorSynchronizer
            = new ErrorNotificationSynchronizer<>();
    private BookDatabase database = new BookDatabase();

    public TabPane tabPane;

    public Tab addBookTab;
    public Tab viewBookTab;
    public Tab recentActivityTab;
    public Tab statisticsTab;
    public Tab saveRestoreDataTab;

    // shortcut buttons
    public Button addNewBookShortcutBtn;
    public Button viewBooksShortcutBtn;
    public Button viewRecentActivityShortcutBtn;
    public Button viewStatisticsShortcutBtn;
    public Button saveRestoreDataShortcutBtn;

    // create book
    public TextField createBookNameTextField;
    public TextField createBookPageCountTextField;
    public ComboBox<Genre> createBookGenreComboBox;
    public TextField createBookAuthorNameTextField;
    public DatePicker createBookPublishedAtDatePicker;
    public ComboBox<BookFolder> createBookFolderComboBox;

    public Button addBookBtn;

    public Text createBookNameValidationFailureText;
    public Text createBookPageCountValidationFailureText;
    public Text createBookGenreValidationFailureText;
    public Text createBookAuthorNameValidationFailureText;
    public Text createBookPublishedAtValidationFailureText;
    public Text createBookFolderValidationFailureText;

    public TabPane bookFolderTab;

    // edit book
    public TextField editBookNameTextField;
    public TextField editBookPageCountTextField;
    public TextField editBookPagesReadSoFarTextField;
    public ComboBox<Genre> editBookGenreComboBox;
    public TextField editBookAuthorNameTextField;
    public DatePicker editBookPublishedAtDatePicker;

    // NOTE: Rating is something users can only see when editing, not creating, because it's rather uncommon
    // that one would rate a book when creating it.
    public TextField editBookRatingTextField;
    public ComboBox<BookFolder> editBookFolderComboBox;

    public Text editBookNameValidationFailureText;
    public Text editBookPageCountValidationFailureText;
    public Text editBookPagesReadSoFarValidationFailureText;
    public Text editBookGenreValidationFailureText;
    public Text editBookAuthorNameValidationFailureText;
    public Text editBookPublishedAtValidationFailureText;
    public Text editBookRatingValidationFailureText;
    public Text editBookFolderValidationFailureText;

    public Button saveEditsBtn;
    public Button deleteBookBtn;

    // view friends
    public ListView<Book> wantToReadListView;
    public ListView<Book> currentlyReadingListView;
    public ListView<Book> alreadyReadListView;

    public AnchorPane bookInfoAnchorPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Add the appropriate values to dropdowns.
        createBookGenreComboBox.getItems().addAll(Genre.values());
        createBookFolderComboBox.getItems().addAll(BookFolder.values());

        editBookGenreComboBox.getItems().addAll(Genre.values());
        editBookFolderComboBox.getItems().addAll(BookFolder.values());

        // register the validation failure text fields with the notification synchronizer.
        createBookErrorSynchronizer.connectField(Book.Field.NAME, createBookNameValidationFailureText);
        createBookErrorSynchronizer.connectField(Book.Field.PAGE_COUNT, createBookPageCountValidationFailureText);
        createBookErrorSynchronizer.connectField(Book.Field.GENRE, createBookGenreValidationFailureText);
        createBookErrorSynchronizer.connectField(Book.Field.AUTHOR, createBookAuthorNameValidationFailureText);
        createBookErrorSynchronizer.connectField(Book.Field.PUBLISHED_DATE, createBookPublishedAtValidationFailureText);

        editBookErrorSynchronizer.connectField(Book.Field.NAME, editBookNameValidationFailureText);
        editBookErrorSynchronizer.connectField(Book.Field.PAGE_COUNT, editBookPageCountValidationFailureText);
        editBookErrorSynchronizer.connectField(Book.Field.GENRE, editBookGenreValidationFailureText);
        editBookErrorSynchronizer.connectField(Book.Field.AUTHOR, editBookAuthorNameValidationFailureText);
        editBookErrorSynchronizer.connectField(Book.Field.PUBLISHED_DATE, editBookPublishedAtValidationFailureText);

        // when a user switches to another book folder, hide the book information.
        bookFolderTab.getSelectionModel().selectedItemProperty().addListener((tab, oldTab, newTab) -> {
            if (!oldTab.equals(newTab)) {
                bookInfoAnchorPane.setVisible(false);
            }
        });
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Sets the current tab to the add book tab.
    public void handleNewBookShortcutClicked() {
        tabPane.getSelectionModel().select(addBookTab);
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Sets the current tab to the view books tab.
    public void handleViewBooksShortcutClicked() {
        tabPane.getSelectionModel().select(viewBookTab);
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Sets the current tab to the view recent activity tab.
    public void handleViewRecentActivityShortcutClicked() {
        tabPane.getSelectionModel().select(recentActivityTab);
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Sets the current tab to the view statistics tab.
    public void handleViewStatisticsShortcutClicked() {
        tabPane.getSelectionModel().select(statisticsTab);
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Sets the current tab to the save/restore data tab.
    public void handleSaveRestoreDataShortcutClicked() {
        tabPane.getSelectionModel().select(saveRestoreDataTab);
    }

    public void handleAddBook() {
        // validate input
        ValidationErrorList<Book.Field> errors = new ValidationErrorList<>();

        String name = createBookNameTextField.getText();
        BookValidator.validateName(name, errors);

        int pageCount = -1;
        try {
            pageCount = Integer.parseInt(createBookPageCountTextField.getText());
            BookValidator.validatePageCount(pageCount, errors);
        } catch (Exception unused) {
            errors.add(Book.Field.PAGE_COUNT, "Page count must be an integer");
        }

        Genre genre = createBookGenreComboBox.getValue();
        BookValidator.validateGenre(genre, errors);

        String authorName = createBookAuthorNameTextField.getText();
        BookValidator.validateAuthor(authorName, errors);

        LocalDate publishedDate = createBookPublishedAtDatePicker.getValue();
        BookValidator.validatePublishedDate(publishedDate, errors);

        BookFolder folder = createBookFolderComboBox.getValue();
        if (folder == null) {
            createBookFolderValidationFailureText.setText("Book folder must be set");
            createBookFolderValidationFailureText.setVisible(true);
        } else {
            createBookFolderValidationFailureText.setVisible(false);
        }

        // Show the new errors in the GUI.
        createBookErrorSynchronizer.sync(errors);
        // Return early if the input isn't valid.
        if (errors.hasErrors() || createBookFolderValidationFailureText.isVisible()) return;

        // Everything's valid, so clear the input fields and add the book.
        createBookNameTextField.clear();
        createBookPageCountTextField.clear();
        createBookGenreComboBox.getSelectionModel().clearSelection();
        createBookAuthorNameTextField.clear();
        createBookPublishedAtDatePicker.getEditor().clear();
        createBookFolderComboBox.getSelectionModel().clearSelection();

        BookBuilder builder = new BookBuilder();
        builder.setName(name);
        builder.setPageCount(pageCount);
        builder.setGenre(genre);
        builder.setAuthor(authorName);
        builder.setPublishedDate(publishedDate);
        Book book = builder.build();

        // add the book to our collection.
        database.addBookToList(folder, book);
        getListViewForFolder(folder).getItems().add(book);
    }

    public void handleSelectFriend() {
        Book book = getSelectedBook();
        if (book == null) {
            // no selected book, so hide the book information.
            bookInfoAnchorPane.setVisible(false);
            return;
        }

        // make the book information visible.
        bookInfoAnchorPane.setVisible(true);
        refreshBookInfo();
    }

    public void handleEditBook() {
        Book book = getSelectedBook();
        if (book == null) return; // only go on if we have a book selected.

        // validate the input
        ValidationErrorList<Book.Field> errors = new ValidationErrorList<>();

        String newName = editBookNameTextField.getText();
        BookValidator.validateName(newName, errors);

        int newPageCount = -1;
        try {
            newPageCount = Integer.parseInt(editBookPageCountTextField.getText());
            BookValidator.validatePageCount(newPageCount, errors);
        } catch (Exception unused) {
            errors.add(Book.Field.PAGE_COUNT, "Page count must be an integer");
        }

        int newPagesReadSoFar = -1;
        if (book.getCurrentPage() != 0 || !editBookPagesReadSoFarTextField.getText().isEmpty()) {
            try {
                newPagesReadSoFar = Integer.parseInt(editBookPagesReadSoFarTextField.getText());
                if (newPagesReadSoFar < book.getCurrentPage()) {
                    editBookPagesReadSoFarValidationFailureText.setText("Pages read so far mustn't be lower than current page");
                    editBookPagesReadSoFarValidationFailureText.setVisible(true);
                } else if (newPagesReadSoFar > book.getCurrentPage()) {
                    editBookPagesReadSoFarValidationFailureText.setText("Pages read so far mustn't be greater than current page");
                    editBookPagesReadSoFarValidationFailureText.setVisible(true);
                } else {
                    editBookPagesReadSoFarValidationFailureText.setVisible(false);
                }
            } catch (Exception unused) {
                editBookPagesReadSoFarValidationFailureText.setText("Pages read so far must be an integer");
                editBookPagesReadSoFarValidationFailureText.setVisible(true);
            }
        } else {
            editBookPagesReadSoFarValidationFailureText.setVisible(false);
        }

        Genre newGenre = editBookGenreComboBox.getValue();
        BookValidator.validateGenre(newGenre, errors);

        String newAuthorName = editBookAuthorNameTextField.getText();
        BookValidator.validateAuthor(newAuthorName, errors);

        LocalDate newPublishedAt = editBookPublishedAtDatePicker.getValue();
        BookValidator.validatePublishedDate(newPublishedAt, errors);

        int newRating = -1;
        if (!editBookRatingTextField.getText().isEmpty()) {
            try {
                newRating = Integer.parseInt(editBookRatingTextField.getText());
                if (newRating < 0 || newRating > 5) {
                    editBookRatingValidationFailureText.setText("Rating must be between 0 and 5");
                    editBookRatingValidationFailureText.setVisible(true);
                } else {
                    editBookRatingValidationFailureText.setVisible(false);
                }
            } catch (Exception unused) {
                editBookRatingValidationFailureText.setText("Rating must be an integer");
                editBookRatingValidationFailureText.setVisible(true);
            }
        } else {
            editBookRatingValidationFailureText.setVisible(false);
        }

        BookFolder newFolder = editBookFolderComboBox.getValue();
        if (newFolder == null) {
            editBookFolderValidationFailureText.setText("Book folder must be provided");
            editBookFolderValidationFailureText.setVisible(true);
        } else {
            editBookFolderValidationFailureText.setVisible(false);
        }

        // sync validation errors with GUI
        editBookErrorSynchronizer.sync(errors);
        // return early if we have validation errors
        if (errors.hasErrors()
                || editBookPagesReadSoFarValidationFailureText.isVisible()
                || editBookRatingValidationFailureText.isVisible()
                || editBookFolderValidationFailureText.isVisible()) return;

        // set new data
        book.setName(newName);
        book.setPageCount(newPageCount);
        if (newPagesReadSoFar != -1 && newPagesReadSoFar != book.getCurrentPage())
            book.setCurrentPage(newPagesReadSoFar);
        book.setGenre(newGenre);
        book.setAuthor(newAuthorName);
        book.setPublishedDate(newPublishedAt);
        if (newRating != -1) book.setRating(newRating);

        // change folder if necessary
        if (newFolder != getSelectedFolder()) {
            database.moveBookToFolder(getSelectedFolder(), newFolder, book);
            getListViewForFolder(newFolder).getItems().add(book);
            getListViewForFolder(getSelectedFolder()).getItems().remove(book);
            bookInfoAnchorPane.setVisible(false);
        } else {
            // refresh book info
            refreshBookInfo();
        }

        // refresh list view in case name changed
        getListViewForFolder(newFolder).refresh();
    }

    public void handleDeleteBook() {
        Book book = getSelectedBook();
        if (book == null) return;

        database.removeBookFromList(getSelectedFolder(), book);
        getListViewForFolder(getSelectedFolder()).getItems().remove(book);
        bookInfoAnchorPane.setVisible(false);
    }

    private void refreshBookInfo() {
        Book book = getSelectedBook();

        editBookNameTextField.setText(book.getName());
        editBookPageCountTextField.setText(Integer.toString(book.getPageCount()));
        editBookPagesReadSoFarTextField.setText(Integer.toString(book.getCurrentPage()));
        editBookGenreComboBox.setValue(book.getGenre());
        editBookAuthorNameTextField.setText(book.getAuthor());
        editBookPublishedAtDatePicker.setValue(book.getPublishedDate());
        editBookRatingTextField.setText(book.getRating() == null ? "" : Integer.toString(book.getRating().getRating()));
        editBookFolderComboBox.setValue(getSelectedFolder());
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Returns the currently selected book.
    private Book getSelectedBook() {
        BookFolder folder = getSelectedFolder();
        if (folder == null) return null;
        return getListViewForFolder(folder).getSelectionModel().getSelectedItem();
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Returns the currently selected folder.
    private BookFolder getSelectedFolder() {
        if (wantToReadListView.getSelectionModel().getSelectedItem() != null) return BookFolder.WANT_TO_READ;
        if (currentlyReadingListView.getSelectionModel().getSelectedItem() != null) return BookFolder.CURRENTLY_READING;
        if (alreadyReadListView.getSelectionModel().getSelectedItem() != null) return BookFolder.ALREADY_READ;
        return null;
    }

    // Requires: BookFolder folder.
    // Modifies: Nothing.
    // Effects: Returns the list view corresponding to the book folder provided.
    private ListView<Book> getListViewForFolder(BookFolder folder) {
        switch (folder) {
            case WANT_TO_READ:
                return wantToReadListView;
            case CURRENTLY_READING:
                return currentlyReadingListView;
            case ALREADY_READ:
                return alreadyReadListView;
        }
        throw new IllegalArgumentException("Unknown book folder type.");
    }
}
